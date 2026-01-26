package com.example.roomsathi.repository



import android.net.Uri
import com.example.roomsathi.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback

class UserRepoImpl : UserRepo{

    val auth : FirebaseAuth= FirebaseAuth.getInstance()
    val database : FirebaseDatabase = FirebaseDatabase.getInstance()
    val ref : DatabaseReference= database.getReference("users")
    private val storage = FirebaseStorage.getInstance().reference
    override fun login(
        email: String,
        password: String,
        callback: (Boolean, String) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){
                callback(true, "login successful")
            }else{
                callback(false, "${it.exception?.message}")

            }
        }
    }

    override fun forgetPassword(
        email: String,
        callback: (Boolean, String) -> Unit
    ) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful){
                callback(true, "Reset email sent")
            }
            else{
                callback(false, "${it.exception?.message}")
            }
        }
    }

    override fun updateProfile(
        userId: String,
        model: UserModel,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(userId).updateChildren(model.toMap()).addOnCompleteListener {
            if(it.isSuccessful){
                callback(true,"Profile updated successfully")
            }
            else{
                callback(false, "${it.exception?.message}")
            }

        }
    }
    // Inside UserRepoImpl


    // Inside UserRepoImpl
    override fun uploadProfilePicture(imageUri: Uri, callback: (Boolean, String, String?) -> Unit) {
        MediaManager.get().upload(imageUri)
            .option("folder", "profile_pics")
            .callback(object : UploadCallback {
                override fun onStart(requestId: String) { }

                override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) { }

                override fun onSuccess(requestId: String, resultData: Map<*, *>) {
                    val imageUrl = resultData["secure_url"] as? String
                    callback(true, "Upload Success", imageUrl)
                }

                override fun onError(requestId: String, error: ErrorInfo) {
                    callback(false, error.description, null)
                }

                override fun onReschedule(requestId: String, error: ErrorInfo) {
                    callback(false, "Rescheduled", null)
                }
            }).dispatch()
    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override fun deleteAccount(
        userId: String,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(userId).removeValue().addOnCompleteListener {
            if(it.isSuccessful){
                callback(true,"Account Deleted")
            }
            else{
                callback(false, "${it.exception?.message}")
            }

        }
    }

    override fun logOut(callback: (Boolean, String) -> Unit) {
        try {
            auth.signOut()
            callback(true, "Logout")
        } catch (e: Exception) {
            callback(false, e.message.toString())
        }
    }

    override fun getUserById(
        userId: String,
        callback: (Boolean, String, UserModel?) -> Unit
    ) {
        ref.child(userId).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val users = snapshot.getValue(UserModel::class.java)
                    if(users !=null){
                        callback(true,"Profile fetched", users)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false, error.message , null)
            }

        })
    }

    override fun getAllUser(callback: (Boolean, String, List<UserModel>?) -> Unit) {
        ref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val allUsers= mutableListOf<UserModel>()
                    for (data in snapshot.children){val user= data.getValue(UserModel::class.java)
                        if (user != null){
                            allUsers.add(user)
                        }
                    }
                    callback(true,"User Fetched", allUsers)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false, error.message , emptyList())
            }
        })
    }

    override fun register(
        email: String,
        password: String,
        callback: (Boolean, String, String) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful){
                callback(true, "Account created Successfully","${auth.currentUser?.uid}")
            }
            else{
                callback(false,"${it.exception?.message}","")
            }
        }
    }

    //create -> setValue()
    //Update -> updateChildren()
    //delete -> removeValue()
    override fun addUserToDatabase(
        userId: String,
        model: UserModel,
        callback: (Boolean, String) -> Unit
    ) {
        ref.child(userId).setValue(model).addOnCompleteListener {
            if(it.isSuccessful){
                callback(true,"Registration Success")
            }
            else{
                callback(false, "${it.exception?.message}")
            }
        }
    }

}