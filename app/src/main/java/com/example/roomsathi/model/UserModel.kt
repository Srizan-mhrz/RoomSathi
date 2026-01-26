package com.example.roomsathi.model

data class UserModel(
    val userId: String = "",
    val fullName: String = "",
    val phoneNumber: String = "",
    val email: String = "",
     val password: String = "", // Best to remove password from DB for security
    val profileImageUrl: String = ""
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "userId" to userId,
            "fullName" to fullName,
            "phoneNumber" to phoneNumber,
            "email" to email,
            "profileImageUrl" to profileImageUrl
        )
    }
}