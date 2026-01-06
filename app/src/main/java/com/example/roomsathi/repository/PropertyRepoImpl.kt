package com.example.roomsathi.repository

import com.example.roomsathi.model.PropertyModel
import com.google.firebase.database.*

class PropertyRepoImpl : PropertyRepo {


    private val propertiesRef = FirebaseDatabase.getInstance().getReference("Properties")


    private val imagesRef = FirebaseDatabase.getInstance().getReference("Images")



    private val imageCounterRef = FirebaseDatabase.getInstance().getReference("ImageCounter")

    override fun addProperty(
        userId: String,
        property: PropertyModel,
        imageUrls: List<String>,
        callback: (isSuccess: Boolean, message: String, propertyId: String?) -> Unit
    ) {

        imageCounterRef.runTransaction(object : Transaction.Handler {
            override fun doTransaction(currentData: MutableData): Transaction.Result {

                val currentCount = currentData.getValue(Long::class.java) ?: 0L


                val startIndex = currentCount
                val newCount = currentCount + imageUrls.size


                currentData.value = newCount


                currentData.child("context/startIndex").value = startIndex

                return Transaction.success(currentData)
            }


            override fun onComplete(
                error: DatabaseError?,
                committed: Boolean,
                dataSnapshot: DataSnapshot?
            ) {
                if (error != null || !committed) {
                    callback(false, "Failed to secure image index: ${error?.message}", null)
                    return
                }



                val startIndex = dataSnapshot?.child("context/startIndex")?.getValue(Long::class.java)

                if (startIndex == null) {
                    callback(false, "Internal error: Could not retrieve start index.", null)
                    return
                }


                val imageUpdates = mutableMapOf<String, Any>()
                imageUrls.forEachIndexed { index, url ->
                    val imageDbIndex = startIndex + index
                    imageUpdates[imageDbIndex.toString()] = url
                }

                imagesRef.updateChildren(imageUpdates).addOnSuccessListener {

                    val propertyId = propertiesRef.push().key
                    if (propertyId == null) {
                        callback(false, "Could not generate property ID.", null)
                        return@addOnSuccessListener
                    }

                    val finalProperty = property.copy(
                        ownerId = userId,
                        indexOfImages = startIndex,
                        noOfImages = imageUrls.size
                    )

                    propertiesRef.child(propertyId).setValue(finalProperty)
                        .addOnSuccessListener {
                            callback(true, "Property added successfully.", propertyId)
                        }
                        .addOnFailureListener {
                            callback(false, "Failed to save property data.", null)
                        }
                }.addOnFailureListener {
                    callback(false, "Failed to save images.", null)
                }
            }
        })
    }

    override fun deleteProperty(
        propertyId: String,
        callback: (isSuccess: Boolean, message: String) -> Unit
    ) {

        propertiesRef.child(propertyId).removeValue()
            .addOnSuccessListener {
                callback(true, "Property deleted successfully.")
            }
            .addOnFailureListener {
                callback(false, "Failed to delete property: ${it.message}")
            }
    }

    override fun getPropertyById(
        propertyId: String,
        callback: (isSuccess: Boolean, message: String, property: PropertyModel?) -> Unit
    ) {
        propertiesRef.child(propertyId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val property = snapshot.getValue(PropertyModel::class.java)
                if (property != null) {
                    callback(true, "Property fetched.", property)
                } else {
                    callback(false, "Property not found.", null)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                callback(false, "Database error: ${error.message}", null)
            }
        })
    }

    override fun getAllProperties(callback: (properties: List<Pair<String, PropertyModel>>) -> Unit) {
        propertiesRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val propertyList = mutableListOf<Pair<String, PropertyModel>>()
                snapshot.children.forEach { propertySnapshot ->
                    val property = propertySnapshot.getValue(PropertyModel::class.java)
                    val propertyId = propertySnapshot.key
                    if (property != null && propertyId != null) {
                        propertyList.add(propertyId to property)
                    }
                }
                callback(propertyList)
            }
            override fun onCancelled(error: DatabaseError) {
                callback(emptyList())
            }
        })
    }

    override fun getPropertyImages(
        property: PropertyModel,
        callback: (imageUrls: List<String>?) -> Unit
    ) {
        if (property.noOfImages == 0 || property.indexOfImages < 0) {
            callback(emptyList()) // No images to fetch.
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
}
