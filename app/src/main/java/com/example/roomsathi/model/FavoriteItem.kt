package com.example.roomsathi.model


enum class FavoriteType {
    ROOM, ROOMMATE
}


data class FavoriteItem(
    val id: String = "",
    val title: String = "",
    val location: String = "",
    val priceORBudget: String = "",
    val type1: String,
    val type: FavoriteType = FavoriteType.ROOM
)