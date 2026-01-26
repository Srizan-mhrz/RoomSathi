package com.example.roomsathi.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.roomsathi.R
import com.example.roomsathi.repository.UserRepoImpl
import com.example.roomsathi.ui.theme.LightBlue
import com.example.roomsathi.ui.theme.White
import com.example.roomsathi.ui.theme.Yellow
import com.example.roomsathi.viewmodel.UserViewModel

class LoginUi : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LoginScreen()
        }
    }
}

@Composable
fun LoginScreen() {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val userViewModel: UserViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return UserViewModel(UserRepoImpl()) as T
            }
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue)
            .imePadding()

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            // Brand Header
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Home Icon",
                tint = White,
                modifier = Modifier.size(48.dp)
            )

            Text(
                text = "Room Sathi",
                color = Color.White,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Welcome Back!",
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "Sign in to continue your search for your perfect room",
                color = Color.White.copy(alpha = 0.6f),
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp, bottom = 40.dp)
            )

            // Glass Container for Inputs
            GlassSurface(
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.White.copy(alpha = 0.1f)
            ) {
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    ThemedInputField(
                        label = "Email Address",
                        value = email,
                        icon = R.drawable.outline_alternate_email_24,
                        keyboardType = KeyboardType.Email,
                        onValueChange = { email = it }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    ThemedInputField(
                        label = "Password",
                        value = password,
                        icon = R.drawable.outline_key_vertical_24, // Substitute for lock icon if available
                        keyboardType = KeyboardType.Password,
                        isPassword = true,
                        isPasswordVisible = passwordVisible,
                        onVisibilityChange = { passwordVisible = !passwordVisible },
                        onValueChange = { password = it }
                    )
                }
            }

            // Forgot Password Link
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Text(
                    text = "Forgot password?",
                    color = Yellow,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.clickable {  val intent = Intent(context, ForgotPassword::class.java)
                        context.startActivity(intent)
                    }
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Action Button
            Button(
                onClick = {
                    if (email.isEmpty() || password.isEmpty()) {
                        Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                    } else {
                        userViewModel.login(email, password) { success, msg ->
                            if (success) {
                                val intent = Intent(context, DashboardActivity::class.java).apply {
                                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                }
                                context.startActivity(intent)
                                (context as? Activity)?.finish()
                            } else {
                                Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                },
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Yellow),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Text(
                    "SIGN IN",
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.weight(1f)) // Push Sign Up section to bottom

            // Sign Up Footer
            Row(
                modifier = Modifier.padding(vertical = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Don't have an account? ",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 14.sp
                )
                Text(
                    text = "Sign Up",
                    modifier = Modifier.clickable {
                        context.startActivity(Intent(context, RegistrationActivity::class.java))
                    },
                    color = Yellow,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 14.sp
                )
            }
        }
    }
}