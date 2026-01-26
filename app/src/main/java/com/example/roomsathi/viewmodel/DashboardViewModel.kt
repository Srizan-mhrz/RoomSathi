package com.example.roomsathi.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomsathi.model.PropertyModel
import com.example.roomsathi.repository.PropertyRepo
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DashboardViewModel(private val repo: PropertyRepo) : ViewModel() {

    private val _properties = MutableStateFlow<List<PropertyModel>>(emptyList())
    val properties: StateFlow<List<PropertyModel>> = _properties.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Filtered list for the HomeScreen search
    val filteredProperties: StateFlow<List<PropertyModel>> = combine(
        _properties,
        _searchQuery
    ) { allProperties, query ->
        if (query.isEmpty()) {
            allProperties
        } else {
            allProperties.filter { property ->
                property.title.contains(query, ignoreCase = true) ||
                        property.location.contains(query, ignoreCase = true)
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    // NEW: Filtered list for the current user's posts
    val myPosts: StateFlow<List<PropertyModel>> = _properties.map { allProps ->
        val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        allProps.filter { it.ownerId == currentUserId }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        fetchAllProperties()
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun fetchAllProperties() {
        _isLoading.value = true
        repo.getAllProperties { resultList ->
            _properties.value = resultList.map { it.second }
            _isLoading.value = false
        }
    }
    fun updateProperty(updatedProperty: PropertyModel, newImageUris: List<Uri>, onResult: (Boolean, String) -> Unit) {
        _isLoading.value = true // Start Spinner
        repo.updateProperty(updatedProperty, newImageUris) { success, msg ->
            _isLoading.value = false // Stop Spinner
            if (success) {
                fetchAllProperties()
            }
            onResult(success, msg)
        }
    }

    // NEW: Delete function
    fun deleteProperty(propertyId: String, onResult: (Boolean, String) -> Unit) {
        repo.deleteProperty(propertyId) { success, msg ->
            if (success) {
                // Refresh the list locally to update UI immediately
                fetchAllProperties()
            }
            onResult(success, msg)
        }
    }
}