package com.example.roomsathi.repository

import com.example.roomsathi.model.PropertyModel

interface PropertyRepo {


    fun generatePropertyId(): String


    fun addProperty(
        userId: String,
        property: PropertyModel,
        imageUrls: List<String>,
        callback: (isSuccess: Boolean, message: String, propertyId: String?) -> Unit
    )


    fun getPropertyById(
        propertyId: String,
        callback: (isSuccess: Boolean, message: String, property: PropertyModel?) -> Unit
    )

    fun getAllProperties(callback: (properties: List<Pair<String, PropertyModel>>) -> Unit)


    fun updateProperty(
        propertyId: String,
        updatedProperty: PropertyModel,
        callback: (Boolean, String) -> Unit
    )


    fun deleteProperty(
        propertyId: String,
        callback: (isSuccess: Boolean, message: String) -> Unit
    )


    fun getPropertyImages(
        indexOfImages: Int,
        noOfImages: Int,
        callback: (imageUrls: List<String>?) -> Unit
    )

    fun updateImageAtSlot(
        propertyId: String,
        indexOfImages: Int,
        slotOffset: Int,
        newUrl: String,
        callback: (Boolean, String) -> Unit
    )

    fun getFilteredProperties(
        maxCost: Double?,
        locationQuery: String?,
        callback: (properties: List<Pair<String, PropertyModel>>) -> Unit
    )
}
