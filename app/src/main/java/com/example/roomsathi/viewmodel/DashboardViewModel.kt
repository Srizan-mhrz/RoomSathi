package com.example.roomsathi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomsathi.model.PropertyModel
import com.example.roomsathi.repository.PropertyRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(private val repo: PropertyRepo) : ViewModel() {
    private val _properties = MutableStateFlow<List<PropertyModel>>(emptyList())
    val properties: StateFlow<List<PropertyModel>> = _properties

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        fetchAllProperties()
    }

    fun fetchAllProperties() {
        _isLoading.value = true
        repo.getAllProperties { resultList ->
            _properties.value = resultList.map { it.second }
            _isLoading.value = false
        }
    }
}