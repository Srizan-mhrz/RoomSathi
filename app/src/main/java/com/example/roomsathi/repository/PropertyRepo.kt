package com.example.roomsathi.repository

import com.example.roomsathi.model.PropertyModel

interface PropertyRepo {


    fun addProperty(
        userId: String,
        property: PropertyModel,
        imageUrls: List<String>,
        callback: (isSuccess: Boolean, message: String, propertyId: String?) -> Unit
    )
    fun deleteProperty(
        propertyId: String,
        callback: (isSuccess: Boolean, message: String) -> Unit
    )
    fun getPropertyById(
        propertyId: String,
        callback: (isSuccess: Boolean, message: String, property: PropertyModel?) -> Unit
    )

    fun getAllProperties(callback: (properties: List<Pair<String, PropertyModel>>) -> Unit)

    fun getPropertyImages(
        property: PropertyModel,
        callback: (imageUrls: List<String>?) -> Unit
    )
}
