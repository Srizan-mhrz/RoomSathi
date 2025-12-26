package com.example.roomsathi.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.roomsathi.model.FavoriteItem
import com.example.roomsathi.model.FavoriteType

class FavoritesViewModel : ViewModel() {
    val favorites = mutableStateListOf(
        FavoriteItem("1", "Budanilkantha Home", "Budanilkantha, KTM", "NPR10,000/mo", "", FavoriteType.ROOM),
        FavoriteItem("2", "Alex Shah", "Budanilkantha, KTM", "Budget: NPR5,000", "", FavoriteType.ROOMMATE)
    )
    var selectedFilter = mutableStateOf("All")

    fun getFilteredFavorites(): List<FavoriteItem> {
        return when (selectedFilter.value) {
            "Rooms" -> favorites.filter { it.type == FavoriteType.ROOM }
            "Roommates" -> favorites.filter { it.type == FavoriteType.ROOMMATE }
            else -> favorites
        }
    }

    fun removeItem(id: String) {
        favorites.removeAll { it.id == id }
    }
}