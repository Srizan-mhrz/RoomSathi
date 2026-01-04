package com.example.roomsathi.repository

import com.example.roomsathi.model.PropertyModel

interface PropertyRepo {

    fun updateProperty(userId: String, propertyId: String, model: PropertyModel, callback:(Boolean, String)-> Unit)
    fun deleteProperty(userId: String, propertyId: String,callback: (Boolean, String) -> Unit)
    fun getPropertybyId(userId: String, callback:(Boolean, String, PropertyModel?)-> Unit)
    fun addProperty(userId: String,propertyId: String, model: PropertyModel, callback: (Boolean, String)-> Unit)


}