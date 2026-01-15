package com.example.roomsathi.repository

interface FavoritesRepo {
    fun toggleFavorite(userId: String, propertyId: String, callback: (Boolean, String) -> Unit)
    fun getFavoritePropertyIds(userId: String, callback: (Boolean, List<String>) -> Unit)

}