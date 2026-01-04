package com.example.roomsathi.model

import android.R

data class PropertyModel
    (
    val propertyId: String = "",
    val userId: String = "",
    val title: String = "",
    val location: String = "",
    val description: String = "",
    val cost: Double = 0.0,
    val status: Boolean = false,
){
}