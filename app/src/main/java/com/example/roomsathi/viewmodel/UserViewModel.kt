package com.example.roomsathi.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.roomsathi.model.UserModel
import com.example.roomsathi.repository.UserRepo
import com.google.firebase.auth.FirebaseUser

class UserViewModel(val repo: UserRepo) : ViewModel() {

    // --- State for the Logged-in User (Used in Home/Profile) ---
    private val _users = MutableLiveData<UserModel?>()
    val users: LiveData<UserModel?> get() = _users

    // --- NEW: State for the Property Owner (Used in Details Screen) ---
    private val _propertyOwner = MutableLiveData<UserModel?>()
    val propertyOwner: LiveData<UserModel?> get() = _propertyOwner

    private val _allUsers = MutableLiveData<List<UserModel>?>()
    val allUsers: LiveData<List<UserModel>?> get() = _allUsers

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    // Use this for the logged-in user
    fun getUserById(userId: String) {
        _loading.postValue(true)
        repo.getUserById(userId) { success, _, data ->
            _loading.postValue(false)
            if (success) _users.postValue(data) else _users.postValue(null)
        }
    }

    // --- NEW: Use this specifically for the PropertyDetailsScreen ---
    fun getPropertyOwnerById(userId: String) {
        repo.getUserById(userId) { success, _, data ->
            if (success) {
                _propertyOwner.postValue(data)
            } else {
                _propertyOwner.postValue(null)
            }
        }
    }

    // Auth functions
    fun login(email: String, password: String, callback: (Boolean, String) -> Unit) {
        repo.login(email, password, callback)
    }

    fun forgetPassword(email: String, callback: (Boolean, String) -> Unit) {
        repo.forgetPassword(email, callback)
    }

    fun updateProfile(userId: String, model: UserModel, callback: (Boolean, String) -> Unit) {
        repo.updateProfile(userId, model, callback)
    }

    fun getCurrentUser(): FirebaseUser? {
        return repo.getCurrentUser()
    }

    fun deleteAccount(userId: String, callback: (Boolean, String) -> Unit) {
        repo.deleteAccount(userId, callback)
    }

    fun logOut(callback: (Boolean, String) -> Unit) {
        repo.logOut(callback)
    }

    fun getAllUser() {
        _loading.postValue(true)
        repo.getAllUser { success, _, data ->
            _loading.postValue(false)
            if (success) _allUsers.postValue(data) else _allUsers.postValue(null)
        }
    }

    fun register(email: String, password: String, callback: (Boolean, String, String) -> Unit) {
        repo.register(email, password, callback)
    }

    fun addUserToDatabase(userId: String, model: UserModel, callback: (Boolean, String) -> Unit) {
        repo.addUserToDatabase(userId, model, callback)
    }
}