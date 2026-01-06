package com.example.roomsathi.repository

import com.example.roomsathi.model.PropertyModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PropertyRepoImpl : PropertyRepo {

    private val database = FirebaseDatabase.getInstance().getReference("Properties")

    override fun addProperty(userId: String, property: PropertyModel, callback: (isSuccess: Boolean, message: String, propertyId: String?) -> Unit) {
        // Generate a unique key for the new property
        val propertyId = database.push().key

        if (propertyId == null) {
            callback(false, "Couldn't generate a property ID.", null)
            return
        }

        // Set the ownerId in the property model before saving
        val newProperty = property.copy(ownerId = userId)

        database.child(propertyId).setValue(newProperty)
            .addOnSuccessListener {
                callback(true, "Property added successfully.", propertyId)
            }
            .addOnFailureListener { exception ->
                callback(false, "Failed to add property: ${exception.message}", null)
            }
    }

    override fun updateProperty(propertyId: String, updatedProperty: PropertyModel, callback: (isSuccess: Boolean, message: String) -> Unit) {
        database.child(propertyId).setValue(updatedProperty)
            .addOnSuccessListener {
                callback(true, "Property updated successfully.")
            }
            .addOnFailureListener { exception ->
                callback(false, "Failed to update property: ${exception.message}")
            }
    }

    override fun deleteProperty(propertyId: String, callback: (isSuccess: Boolean, message: String) -> Unit) {
        database.child(propertyId).removeValue()
            .addOnSuccessListener {
                callback(true, "Property deleted successfully.")
            }
            .addOnFailureListener { exception ->
                callback(false, "Failed to delete property: ${exception.message}")
            }
    }

    override fun getPropertyById(propertyId: String, callback: (isSuccess: Boolean, message: String, property: PropertyModel?) -> Unit) {
        database.child(propertyId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
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

    override fun getAllProperties(callback: (properties: List<Pair<String, PropertyModel>>) -> Unit) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val propertyList = mutableListOf<Pair<String, PropertyModel>>()
                for (propertySnapshot in snapshot.children) {
                    val property = propertySnapshot.getValue(PropertyModel::class.java)
                    val propertyId = propertySnapshot.key
                    if (property != null && propertyId != null) {
                        propertyList.add(propertyId to property)
                    }
                }
                callback(propertyList)
            }

            override fun onCancelled(error: DatabaseError) {
                // Log the error or handle it as needed
                callback(emptyList()) // Return an empty list on error
            }
        })
    }

    override fun updatePropertyImages(propertyId: String, imageUrls: List<String>, callback: (isSuccess: Boolean, message: String) -> Unit) {
        database.child(propertyId).child("images").setValue(imageUrls)
            .addOnSuccessListener {
                callback(true, "Images updated successfully.")
            }
            .addOnFailureListener { exception ->
                callback(false, "Failed to update images: ${exception.message}")
            }
    }
}
