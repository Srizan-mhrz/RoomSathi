package com.example.roomsathi.repository


import android.net.Uri
import com.example.roomsathi.model.UserModel
import com.google.firebase.auth.FirebaseUser

interface UserRepo {

    fun login(email:String, password:String, callback:
        (Boolean, String)-> Unit)


    fun forgetPassword(email:String,
                       callback:(Boolean, String)-> Unit)

    fun updateProfile(userId: String,model: UserModel, callback:(Boolean, String)-> Unit )

    fun getCurrentUser(): FirebaseUser?

    fun deleteAccount(userId: String,callback:(Boolean, String)-> Unit)

    fun logOut(callback:(Boolean, String)-> Unit)

    fun getUserById(userId: String,callback:(Boolean, String, UserModel?)-> Unit)

    fun getAllUser(callback: (Boolean, String,List<UserModel>?) -> Unit)

    fun register(email: String, password: String,callback: (Boolean, String, String) -> Unit)

    fun addUserToDatabase(
        userId: String, model: UserModel,callback: (Boolean, String) -> Unit
    )
    fun uploadProfilePicture(imageUri: Uri, callback: (Boolean, String, String?) -> Unit)

}