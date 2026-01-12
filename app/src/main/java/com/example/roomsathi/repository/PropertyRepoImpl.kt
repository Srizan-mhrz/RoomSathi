package com.example.roomsathi.repository

import com.example.roomsathi.model.PropertyModel
import com.google.firebase.database.*

class PropertyRepoImpl : PropertyRepo {


    private val propertiesRef = FirebaseDatabase.getInstance().getReference("Properties")


    private val imagesRef = FirebaseDatabase.getInstance().getReference("Images")



    private val counterRef = FirebaseDatabase.getInstance().getReference("ImageCounter")

    override fun updateProperty(
        propertyId: String,
        updatedProperty: PropertyModel,
        callback: (Boolean, String) -> Unit
    ) {

        propertiesRef.child(propertyId).setValue(updatedProperty)
            .addOnSuccessListener {
                callback(true, "Property updated successfully")
            }
            .addOnFailureListener {
                callback(false, it.message ?: "Failed to update property")
            }
    }
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
                                    property.title.contains(locationQuery, ignoreCase = true) // Also check title as you suggested

                            costMatch && locationMatch
                        }
                        callback(filteredList)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        callback(emptyList())
                    }
                })
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

            override fun onComplete(
                error: DatabaseError?,
                committed: Boolean,
                snapshot: DataSnapshot?
            ) {
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
                            noOfImages = imageUrls.size
                        )

                        propertiesRef.child(propertyId).setValue(finalProperty)
                            .addOnSuccessListener { callback(true, "Success", propertyId) }
                    }
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
    override fun getPropertyImages(
        indexOfImages: Int,
        noOfImages: Int,
        callback: (List<String>?) -> Unit
    ) {
        if (noOfImages == 0 || indexOfImages < 0) {
            callback(emptyList())
            return
        }

        imagesRef.orderByKey()
            .startAt(indexOfImages.toString())
            .limitToFirst(noOfImages)
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




    override fun updateImageAtSlot(
        propertyId: String,
        indexOfImages: Int,
        slotOffset: Int,
        newUrl: String,
        callback: (Boolean, String) -> Unit
    ) {
        if (slotOffset in 0..7) {
            // Target Bit = Pointer + Offset
            val targetBit = indexOfImages + slotOffset

            imagesRef.child(targetBit.toString()).setValue(newUrl)
                .addOnSuccessListener {
                    callback(true, "Image updated in slot $slotOffset for property $propertyId")
                }
                .addOnFailureListener {
                    callback(false, "Failed to update image: ${it.message}")
                }
        } else {
            callback(false, "Invalid slot: Must be 0 to 7")
        }
    }



    override fun generatePropertyId(): String {
            return FirebaseDatabase.getInstance().getReference("Properties").push().key ?: System.currentTimeMillis().toString()


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
    override fun getPropertyById(
        propertyId: String,
        callback: (isSuccess: Boolean, message: String, property: PropertyModel?) -> Unit
    ) {
        propertiesRef.child(propertyId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Fetch the property data and convert it to our PropertyModel
                val property = snapshot.getValue(PropertyModel::class.java)

                if (property != null) {
                    callback(true, "Property fetched successfully.", property)
                } else {
                    callback(false, "Property not found.", null)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false, "Database error: ${error.message}", null)
            }
        })
    }



}
