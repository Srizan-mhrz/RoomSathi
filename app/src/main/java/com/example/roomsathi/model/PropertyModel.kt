package com.example.roomsathi.model



data class PropertyModel
    (
    val ownerId : String = "",
    val propertyId: String = "",
    val userId: String = "",
    val title: String = "",
    val location: String = "",
    val description: String = "",
    val cost: Double = 0.0,
    val status: Boolean = false,
    val indexOfImages: Int = 0,
    val noOfImages: Int = 0,


){
}