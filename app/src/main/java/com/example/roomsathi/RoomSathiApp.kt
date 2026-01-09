package com.example.roomsathi // Use your app's package name

import android.app.Application
import com.cloudinary.android.MediaManager

class RoomSathiApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // !! IMPORTANT: Replace with your actual Cloudinary credentials !!
        val config = mapOf(
            "cloud_name" to "roomsathi",
            "api_key" to "714768571673822",
            "api_secret" to "DNwQ6QeZ-I7mf0foLORMeU0IVLo"
        )
        MediaManager.init(this, config)
    }
}
    