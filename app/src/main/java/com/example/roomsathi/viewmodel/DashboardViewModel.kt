package com.example.roomsathi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomsathi.model.PropertyModel
import com.example.roomsathi.repository.PropertyRepo
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DashboardViewModel(private val repo: PropertyRepo) : ViewModel() {

    private val _properties = MutableStateFlow<List<PropertyModel>>(emptyList())
    // Publicly expose the full list for the SavedScreen
    val properties: StateFlow<List<PropertyModel>> = _properties.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Filtered list specifically for the HomeScreen
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
}