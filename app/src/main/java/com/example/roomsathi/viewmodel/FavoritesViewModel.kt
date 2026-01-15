package com.example.roomsathi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.roomsathi.repository.FavoritesRepo

class FavoriteViewModel(private val repo: FavoritesRepo) : ViewModel() {

    private val _favoriteIds = MutableLiveData<List<String>>(emptyList())
    val favoriteIds: LiveData<List<String>> get() = _favoriteIds

    fun getFavorites(userId: String) {
        repo.getFavoritePropertyIds(userId) { success, ids ->
            if (success) {
                _favoriteIds.postValue(ids)
            }
        }
    }

    fun toggleFavorite(userId: String, propertyId: String, onResult: (String) -> Unit) {
        repo.toggleFavorite(userId, propertyId) { success, message ->
            if (success) {
                onResult(message)
            }
        }
    }
}