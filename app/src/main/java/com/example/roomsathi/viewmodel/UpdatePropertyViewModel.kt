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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class UpdatePropertyViewModel : ViewModel() {
    private val repository: PropertyRepo = PropertyRepoImpl()

    private val _uiState = MutableStateFlow<AddPropertyUiState>(AddPropertyUiState.Idle)
    val uiState: StateFlow<AddPropertyUiState> = _uiState


    fun updatePropertyDetails(propertyId: String, updatedProperty: PropertyModel) {
        viewModelScope.launch {
            _uiState.value = AddPropertyUiState.Loading

        }
    }



    fun updateImageSlot(property: PropertyModel, slotIndex: Int, newImageUri: Uri) {
        viewModelScope.launch {
            _uiState.value = AddPropertyUiState.Loading

            val uploadResult = uploadSingleImage(newImageUri)
            if (uploadResult.isSuccess) {
                repository.updateImageAtSlot(property, slotIndex, uploadResult.getOrNull()!!) { success, msg ->
                    if (success) _uiState.value = AddPropertyUiState.Idle
                    else _uiState.value = AddPropertyUiState.Error(msg)
                }
            }
        }
    }

    private suspend fun uploadSingleImage(uri: Uri): Result<String> = suspendCancellableCoroutine { continuation ->
        MediaManager.get().upload(uri)
            .unsigned("roomsathi")
            .callback(object : UploadCallback {
                override fun onSuccess(requestId: String?, resultData: Map<*, *>?) {
                    continuation.resume(Result.success(resultData?.get("secure_url").toString()))
                }
                override fun onError(requestId: String?, error: ErrorInfo?) {
                    continuation.resume(Result.failure(Exception(error?.description)))
                }
                override fun onStart(requestId: String?) {}
                override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {}
                override fun onReschedule(requestId: String?, error: ErrorInfo?) {}
            }).dispatch()
    }
}
