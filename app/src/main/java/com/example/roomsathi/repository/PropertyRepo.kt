package com.example.roomsathi.repository

import com.example.roomsathi.model.PropertyModel

interface PropertyRepo {


    fun addProperty(userId: String, property: PropertyModel, initialImages: List<String>, callback: (isSuccess: Boolean, message: String, propertyId: String?) -> Unit)


    fun updateProperty(propertyId: String, updatedProperty: PropertyModel, callback: (isSuccess: Boolean, message: String) -> Unit)

    fun deleteProperty(propertyId: String, callback: (isSuccess: Boolean, message: String) -> Unit)

    fun getPropertyById(propertyId: String, callback: (isSuccess: Boolean, message: String, property: PropertyModel?) -> Unit)

    fun getAllProperties(callback: (properties: List<Pair<String, PropertyModel>>) -> Unit)

    fun updateOrAddImage(propertyId: String, imageUrl: String, index: Int, callback: (isSuccess: Boolean, message: String) -> Unit)

    fun deleteImage(propertyId: String, index: Int, callback: (isSuccess: Boolean, message: String) -> Unit)

    fun getPropertyImages(propertyId: String, callback: (images: Map<String, String>?) -> Unit)
}
