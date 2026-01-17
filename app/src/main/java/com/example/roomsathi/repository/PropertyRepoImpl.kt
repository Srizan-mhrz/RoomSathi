package com.example.roomsathi.repository

import android.net.Uri
import com.example.roomsathi.model.PropertyModel
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback

class PropertyRepoImpl : PropertyRepo {

    private val propertiesRef = FirebaseDatabase.getInstance().getReference("Properties")
    private val imagesRef = FirebaseDatabase.getInstance().getReference("Images")
    private val counterRef = FirebaseDatabase.getInstance().getReference("ImageCounter")

    // Add this to your imports


    override fun updateProperty(
        property: PropertyModel,
        newImageUris: List<Uri>,
        callback: (Boolean, String) -> Unit
    ) {
        if (newImageUris.isEmpty()) {
            finalizeUpdate(property.copy(noOfImages = property.imageUrls.size), property.imageUrls, callback)
            return
        }

        val uploadedUrls = mutableListOf<String>()
        var uploadCount = 0
        var hasError = false

        newImageUris.forEach { uri ->
            // Cloudinary Upload Logic
            MediaManager.get().upload(uri)
                .option("folder", "properties/${property.propertyId}")
                .callback(object : UploadCallback {
                    override fun onStart(requestId: String?) {}
                    override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {}

                    override fun onSuccess(requestId: String?, resultData: Map<*, *>?) {
                        val imageUrl = resultData?.get("secure_url") as? String
                        if (imageUrl != null) {
                            uploadedUrls.add(imageUrl)
                        }

                        uploadCount++
                        if (uploadCount == newImageUris.size) {
                            val finalImageUrls = property.imageUrls + uploadedUrls
                            val updatedProperty = property.copy(
                                imageUrls = finalImageUrls,
                                noOfImages = finalImageUrls.size
                            )
                            finalizeUpdate(updatedProperty, finalImageUrls, callback)
                        }
                    }

                    override fun onError(requestId: String?, error: ErrorInfo?) {
                        hasError = true
                        uploadCount++
                        if (uploadCount == newImageUris.size) {
                            if (uploadedUrls.isNotEmpty()) {
                                // Partial success: save what we got
                                val finalImageUrls = property.imageUrls + uploadedUrls
                                finalizeUpdate(property.copy(imageUrls = finalImageUrls, noOfImages = finalImageUrls.size), finalImageUrls, callback)
                            } else {
                                callback(false, "Cloudinary Upload Failed: ${error?.description}")
                            }
                        }
                    }

                    override fun onReschedule(requestId: String?, error: ErrorInfo?) {}
                }).dispatch()
        }
    }

    private fun finalizeUpdate(
        property: PropertyModel,
        allUrls: List<String>,
        callback: (Boolean, String) -> Unit
    ) {
        // Update the main node and the image slot node as we did before
        propertiesRef.child(property.propertyId).setValue(property)
            .addOnSuccessListener {
                val updates = mutableMapOf<String, Any?>()
                for (i in 0..7) {
                    val slotIndex = property.indexOfImages + i
                    updates[slotIndex.toString()] = allUrls.getOrNull(i)
                }
                imagesRef.updateChildren(updates).addOnSuccessListener {
                    callback(true, "Updated successfully with Cloudinary!")
                }
            }
    }

    override fun addProperty(
        userId: String,
        property: PropertyModel,
        imageUrls: List<String>,
        callback: (isSuccess: Boolean, message: String, propertyId: String?) -> Unit
    ) {
        counterRef.runTransaction(object : Transaction.Handler {
            override fun doTransaction(currentData: MutableData): Transaction.Result {
                val currentCount = currentData.getValue(Long::class.java) ?: 0L
                currentData.value = currentCount + 8
                return Transaction.success(currentData)
            }

            override fun onComplete(error: DatabaseError?, committed: Boolean, snapshot: DataSnapshot?) {
                if (committed && snapshot != null) {
                    val startIndex = (snapshot.value as Long) - 8
                    val updates = mutableMapOf<String, Any?>()

                    for (i in 0..7) {
                        val slotIndex = startIndex + i
                        updates[slotIndex.toString()] = imageUrls.getOrNull(i)
                    }

                    imagesRef.updateChildren(updates).addOnSuccessListener {
                        val propertyId = propertiesRef.push().key ?: ""
                        val finalProperty = property.copy(
                            propertyId = propertyId,
                            ownerId = userId,
                            indexOfImages = startIndex.toLong(),
                            noOfImages = imageUrls.size,
                            imageUrls = imageUrls
                        )

                        propertiesRef.child(propertyId).setValue(finalProperty)
                            .addOnSuccessListener { callback(true, "Success", propertyId) }
                    }
                }
            }
        })
    }

    override fun getPropertyImages(
        property: PropertyModel,
        callback: (imageUrls: List<String>?) -> Unit
    ) {
        if (property.noOfImages == 0 || property.indexOfImages < 0) {
            callback(emptyList())
            return
        }

        imagesRef.orderByKey()
            .startAt(property.indexOfImages.toString())
            .limitToFirst(property.noOfImages)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val urls = snapshot.children.mapNotNull { it.getValue(String::class.java) }
                    callback(urls)
                }
                override fun onCancelled(error: DatabaseError) {
                    callback(null)
                }
            })
    }

    // --- Remaining methods kept for completeness ---

    override fun getFilteredProperties(
        maxCost: Double?,
        locationQuery: String?,
        callback: (properties: List<Pair<String, PropertyModel>>) -> Unit
    ) {
        propertiesRef.orderByChild("status").equalTo(false)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val propertyList = mutableListOf<Pair<String, PropertyModel>>()
                    snapshot.children.forEach { propertySnapshot ->
                        val property = propertySnapshot.getValue(PropertyModel::class.java)
                        val propertyId = propertySnapshot.key
                        if (property != null && propertyId != null) {
                            propertyList.add(propertyId to property)
                        }
                    }
                    val filteredList = propertyList.filter { (_, property) ->
                        val costMatch = maxCost == null || property.cost <= maxCost
                        val locationMatch = locationQuery.isNullOrBlank() ||
                                property.location.contains(locationQuery, ignoreCase = true) ||
                                property.title.contains(locationQuery, ignoreCase = true)
                        costMatch && locationMatch
                    }
                    callback(filteredList)
                }
                override fun onCancelled(error: DatabaseError) { callback(emptyList()) }
            })
    }

    override fun deleteProperty(propertyId: String, callback: (Boolean, String) -> Unit) {
        propertiesRef.child(propertyId).removeValue().addOnSuccessListener {
            callback(true, "Property deleted successfully.")
        }.addOnFailureListener {
            callback(false, "Failed to delete: ${it.message}")
        }
    }

    override fun getPropertyById(propertyId: String, callback: (Boolean, String, PropertyModel?) -> Unit) {
        propertiesRef.child(propertyId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val property = snapshot.getValue(PropertyModel::class.java)
                callback(property != null, if (property != null) "Fetched" else "Not found", property)
            }
            override fun onCancelled(error: DatabaseError) { callback(false, error.message, null) }
        })
    }

    override fun updateImageAtSlot(property: PropertyModel, slotOffset: Int, newUrl: String, callback: (Boolean, String) -> Unit) {
        if (slotOffset in 0..7) {
            val targetBit = property.indexOfImages + slotOffset
            imagesRef.child(targetBit.toString()).setValue(newUrl)
                .addOnSuccessListener { callback(true, "Updated slot $slotOffset") }
                .addOnFailureListener { callback(false, it.message ?: "Error") }
        } else { callback(false, "Invalid slot") }
    }

    override fun getAllProperties(callback: (List<Pair<String, PropertyModel>>) -> Unit) {
        propertiesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val propertyList = snapshot.children.mapNotNull {
                    val prop = it.getValue(PropertyModel::class.java)
                    val id = it.key
                    if (prop != null && id != null) id to prop else null
                }
                callback(propertyList)
            }
            override fun onCancelled(error: DatabaseError) { callback(emptyList()) }
        })
    }
}