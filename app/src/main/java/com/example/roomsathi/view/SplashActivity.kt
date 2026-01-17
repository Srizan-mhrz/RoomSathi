package com.example.roomsathi.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.roomsathi.R
import com.example.roomsathi.ui.theme.DarkBlue
import com.google.firebase.auth.FirebaseAuth

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SplashBody(onAnimationFinished = {
                navigateToNextScreen()
            })
        }
    }

    private fun navigateToNextScreen() {
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        val targetActivity = if (currentUser != null) {
            // User is logged in, go to Dashboard
            DashboardActivity::class.java // Ensure this name matches your dashboard
        } else {
            // User is not logged in, go to Login
            LoginUi::class.java // Ensure this name matches your login activity
        }

        val intent = Intent(this, targetActivity)
        startActivity(intent)
        finish() // Close SplashActivity so user can't go back to it
    }
}

@Composable
fun SplashBody(onAnimationFinished: () -> Unit) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.home)
    )

    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = 1
    )

    // Check if animation is done (progress reaches 1.0)
    LaunchedEffect(progress) {
        if (progress == 1f) {
            onAnimationFinished()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBlue), // Matches your app theme
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { progress }
        )
    }
}