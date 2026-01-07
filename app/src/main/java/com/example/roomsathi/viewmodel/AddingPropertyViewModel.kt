package com.example.roomsathi.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomsathi.model.PropertyModel
import com.example.roomsathi.repository.PropertyRepo
import com.example.roomsathi.repository.PropertyRepoImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.UUID

sealed class AddPropertyUiState {
    object Idle : AddPropertyUiState() // The screen is ready for user input.
    object Loading : AddPropertyUiState() // An operation is in progress (e.g., uploading images).
    data class Success(val message: String, val propertyId: String) : AddPropertyUiState() // The property was added.
    data class Error(val message: String) : AddPropertyUiState() // An error occurred.
}

class AddingPropertyViewModel : ViewModel() {

    // --- Dependencies ---
    // In a real app with dependency injection (like Hilt), you would inject these.
    private val repository: PropertyRepo = PropertyRepoImpl()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()

    // --- State Management ---
    // Backing property for the UI state.
    private val _uiState = MutableStateFlow<AddPropertyUiState>(AddPropertyUiState.Idle)
    // Public, read-only StateFlow for the UI to observe.
    val uiState: StateFlow<AddPropertyUiState> = _uiState

    fun addProperty(
        title: String,
        location: String,
        description: String,
        cost: Double,
        imageUris: List<Uri>
    ) {
        viewModelScope.launch {
            _uiState.value = AddPropertyUiState.Loading

            // 1. Get the current user's ID.
            val currentUserId = auth.currentUser?.uid
            if (currentUserId == null) {
                _uiState.value = AddPropertyUiState.Error("You must be logged in to add a property.")
                return@launch
            }

            if (imageUris.isEmpty()) {
                _uiState.value = AddPropertyUiState.Error("Please select at least one image.")
                return@launch
            }

            // 2. Upload images to Firebase Storage and get their download URLs.
            val imageUrlsResult = uploadImages(imageUris)

            if (imageUrlsResult.isSuccess) {
                val downloadedUrls = imageUrlsResult.getOrNull()!!

                // 3. Create the PropertyModel with the data (but without image indices yet).
                val property = PropertyModel(
                    ownerId = currentUserId,
                    title = title,
                    location = location,
                    description = description,
                    cost = cost,
                    status = false, // New properties are always available.
                    renterId = ""
                )

                // 4. Call the repository to save the property and image URLs.
                repository.addProperty(currentUserId, property, downloadedUrls) { isSuccess, message, propertyId ->
                    if (isSuccess && propertyId != null) {
                        _uiState.value = AddPropertyUiState.Success(message, propertyId)
                    } else {
                        _uiState.value = AddPropertyUiState.Error(message)
                    }
                }
            } else {
                // Handle image upload failure.
                val errorMessage = imageUrlsResult.exceptionOrNull()?.message ?: "Failed to upload images."
                _uiState.value = AddPropertyUiState.Error(errorMessage)
            }
        }
    }

    /**
     * Resets the UI state back to Idle, for example, after the user dismisses a success or error message.
     */
    fun resetState() {
        _uiState.value = AddPropertyUiState.Idle
    }

    /**
     * A helper function to upload a list of image files to Firebase Storage.
     *
     * @param uris The list of local file Uris.
     * @return A Result object containing either the list of download URLs or an exception.
     */
    private suspend fun uploadImages(uris: List<Uri>): Result<List<String>> {
        return try {
            val downloadUrls = mutableListOf<String>()
            val storageRef = storage.reference

            for (uri in uris) {
                // Create a unique file name for each image.
                val imageRef = storageRef.child("property_images/${UUID.randomUUID()}")
                // Upload the file.
                val uploadTask = imageRef.putFile(uri).await()
                // Get the download URL.
                val downloadUrl = uploadTask.storage.downloadUrl.await().toString()
                downloadUrls.add(downloadUrl)
            }
            Result.success(downloadUrls)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
