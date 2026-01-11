package com.example.roomsathi.viewmodel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.example.roomsathi.model.PropertyModel
import com.example.roomsathi.repository.PropertyRepo
import com.example.roomsathi.repository.PropertyRepoImpl
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume


sealed class AddPropertyUiState {
    object Idle : AddPropertyUiState()
    object Loading : AddPropertyUiState()
    data class Success(val message: String, val propertyId: String) : AddPropertyUiState()
    data class Error(val message: String) : AddPropertyUiState()
}

class AddingPropertyViewModel : ViewModel() {

    private val repository: PropertyRepo = PropertyRepoImpl()
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()


    private val _uiState = MutableStateFlow<AddPropertyUiState>(AddPropertyUiState.Idle)
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

            val currentUserId = auth.currentUser?.uid
            if (currentUserId == null) {
                _uiState.value = AddPropertyUiState.Error("You must be logged in to add a property.")
                return@launch
            }

            if (imageUris.isEmpty()) {
                _uiState.value = AddPropertyUiState.Error("Please select at least one image.")
                return@launch
            }


            val imageUrlsResult = uploadImagesToCloudinary(imageUris)

            if (imageUrlsResult.isSuccess) {
                val downloadedUrls = imageUrlsResult.getOrNull()!!

                val property = PropertyModel(
                    ownerId = currentUserId,
                    title = title,
                    location = location,
                    description = description,
                    cost = cost,
                    status = false,
                    renterId = ""
                )

                repository.addProperty(currentUserId, property, downloadedUrls) { isSuccess, message, propertyId ->
                    if (isSuccess && propertyId != null) {
                        _uiState.value = AddPropertyUiState.Success(message, propertyId)
                    } else {
                        _uiState.value = AddPropertyUiState.Error(message)
                    }
                }
            } else {
                val errorMessage = imageUrlsResult.exceptionOrNull()?.message ?: "Failed to upload images."
                _uiState.value = AddPropertyUiState.Error(errorMessage)
            }
        }
    }

    fun resetState() {
        _uiState.value = AddPropertyUiState.Idle
    }

//    fun addPropertyWithHardcodedOwner(
//        ownerId: String,
//
//        title: String,
//        location: String,
//        description: String,
//        cost: Double,
//        imageUris: List<Uri>
//    ) {
//        viewModelScope.launch {
//            _uiState.value = AddPropertyUiState.Loading
//
//
//            val currentUserId = ownerId
//
//
//            if (imageUris.isEmpty()) {
//                _uiState.value = AddPropertyUiState.Error("Please select at least one image.")
//                return@launch
//            }
//
//
//            val imageUrlsResult = uploadImagesToCloudinary(imageUris)
//
//            if (imageUrlsResult.isSuccess) {
//                val downloadedUrls = imageUrlsResult.getOrNull()!!
//
//                val property = PropertyModel(
//                    ownerId = currentUserId,
//                    title = title,
//                    location = location,
//                    description = description,
//                    cost = cost,
//                    status = false,
//                    renterId = ""
//                )
//
//                repository.addProperty(currentUserId, property, downloadedUrls) { isSuccess, message, propertyId ->
//                    if (isSuccess && propertyId != null) {
//                        _uiState.value = AddPropertyUiState.Success(message, propertyId)
//                    } else {
//                        _uiState.value = AddPropertyUiState.Error(message)
//                    }
//                }
//            } else {
//                val errorMessage = imageUrlsResult.exceptionOrNull()?.message ?: "Failed to upload images."
//                _uiState.value = AddPropertyUiState.Error(errorMessage)
//            }
//        }
//    }

    private suspend fun uploadImagesToCloudinary(uris: List<Uri>): Result<List<String>> {
        val downloadUrls = mutableListOf<String>()
        try {
            for (uri in uris) {

                val url = suspendCancellableCoroutine { continuation ->

                    val requestId = MediaManager.get().upload(uri)
                        .unsigned("roomsathi")
                        .callback(object : UploadCallback {
                            override fun onStart(requestId: String) {  }
                            override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) { }

                            override fun onSuccess(requestId: String, resultData: Map<*, *>) {

                                val secureUrl = resultData["secure_url"] as? String
                                if (secureUrl != null) {
                                    if (continuation.isActive) continuation.resume(secureUrl)
                                } else {
                                    if (continuation.isActive) continuation.resumeWith(Result.failure(Exception("Cloudinary did not return a secure URL.")))
                                }
                            }

                            override fun onError(requestId: String, error: ErrorInfo) {
                                if (continuation.isActive) continuation.resumeWith(Result.failure(Exception("Cloudinary upload error: ${error.description}")))
                            }

                            override fun onReschedule(requestId: String, error: ErrorInfo) { }
                        }).dispatch()
                }
                downloadUrls.add(url)
            }
            return Result.success(downloadUrls)
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }
}



