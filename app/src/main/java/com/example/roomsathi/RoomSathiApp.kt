package com.example.roomsathi // Use your app's package name

import android.app.Application
import com.cloudinary.android.MediaManager

class RoomSathiApp : Application() {
    override fun onCreate() {
        super.onCreate()
        println("DEBUG_APP: RoomSathiApp has started and Cloudinary is initializing!")

        val config = mapOf(
            "cloud_name" to "dkayf2ohe",
            "api_key" to "714768571673822",
            "api_secret" to "DNwQ6QeZ-I7mf0foLORMeU0IVLo"
        )
        MediaManager.init(this, config)

    }

}
    