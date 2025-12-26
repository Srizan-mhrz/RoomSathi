package com.example.roomsathi.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    var phoneNumber = mutableStateOf("")
    var password = mutableStateOf("")
    var isLoading = mutableStateOf(false)
    var errorMessage = mutableStateOf<String?>(null)

    // 3. Logic: This handles the login process
    fun onLoginClicked(onSuccess: () -> Unit) {
        // Reset error message
        errorMessage.value = null


        if (phoneNumber.value.isBlank() || password.value.isBlank()) {
            errorMessage.value = "Phone number and password cannot be empty."
            return
        }


        isLoading.value = true


        if (phoneNumber.value.length >= 10 && password.value.length >= 6) {
            onSuccess()
        } else {
            errorMessage.value = "Invalid phone number or password."
        }

        isLoading.value = false
    }
}