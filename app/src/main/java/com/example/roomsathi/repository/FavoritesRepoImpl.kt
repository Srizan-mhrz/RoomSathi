package com.example.roomsathi.repository

import com.google.firebase.database.*

class FavoritesRepoImpl: FavoritesRepo {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val favRef: DatabaseReference = database.getReference("Favorites")

    override fun toggleFavorite(
        userId: String,
        propertyId: String,
        callback: (Boolean, String) -> Unit
    ) {
        val userFavPath = favRef.child(userId).child(propertyId)

        userFavPath.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // If it exists, remove it (Unsave)
                    userFavPath.removeValue().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            callback(true, "Removed from saved")
                        } else {
                            callback(false, task.exception?.message ?: "Error")
                        }
                    }
                } else {
                    // If it doesn't exist, add it (Save)
                    userFavPath.setValue(true).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            callback(true, "Saved")
                        } else {
                            callback(false, task.exception?.message ?: "Error")
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false, error.message)
            }
        })
    }

    override fun getFavoritePropertyIds(
        userId: String,
        callback: (Boolean, List<String>) -> Unit
    ) {
        favRef.child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val propertyIds = mutableListOf<String>()
                for (data in snapshot.children) {
                    data.key?.let { propertyIds.add(it) }
                }
                callback(true, propertyIds)
            }

            override fun onCancelled(error: DatabaseError) {
                callback(false, emptyList())
            }
        })
    }
}
