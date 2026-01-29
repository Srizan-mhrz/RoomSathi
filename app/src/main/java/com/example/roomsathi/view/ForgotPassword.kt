package com.example.roomsathi.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag // Added import for testing
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomsathi.R
import com.example.roomsathi.repository.UserRepoImpl
import com.example.roomsathi.ui.theme.*
import com.example.roomsathi.viewmodel.UserViewModel

class ForgotPassword : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ForgotPasswordScreen()
        }
    }
}

@Composable
fun ForgotPasswordScreen() {
    var email by remember { mutableStateOf("") }
    val context = LocalContext.current
    val userViewModel = remember { UserViewModel(UserRepoImpl()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // --- Custom Themed Top Bar ---
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { (context as? ComponentActivity)?.finish() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                        contentDescription = "Back",
                        tint = White,
                        modifier = Modifier.size(20.dp)
                    )
                }
                Text(
                    text = "Forgot Password",
                    color = White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(40.dp))

                Icon(
                    painter = painterResource(id = R.drawable.outline_key_vertical_24),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = Yellow
                )

                Spacer(Modifier.height(24.dp))

                Text(
                    text = "Reset Your Password",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = White
                )

                Spacer(Modifier.height(12.dp))

                Text(
                    text = "Enter your email address and we will send you a link to reset your password.",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = White.copy(alpha = 0.6f),
                    lineHeight = 20.sp
                )

                Spacer(Modifier.height(40.dp))

                Text(
                    text = "Email Address",
                    color = Yellow,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
                )

                // Added testTag to identify this field in Instrumented Tests
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    placeholder = { Text("Enter your registered email", color = White.copy(alpha = 0.3f)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .background(White.copy(alpha = 0.07f), RoundedCornerShape(12.dp))
                        .testTag("forgotEmailField"),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = White,
                        unfocusedTextColor = White,
                        focusedIndicatorColor = Yellow,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = Yellow
                    )
                )

                Spacer(Modifier.height(40.dp))

                // Added testTag to identify the button in Instrumented Tests
                Button(
                    onClick = {
                        userViewModel.forgetPassword(email) { success, msg ->
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        }
                    },
                    enabled = email.isNotBlank(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .testTag("resetButton"),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Yellow,
                        disabledContainerColor = Yellow.copy(alpha = 0.2f)
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
                ) {
                    Text(
                        "SEND RESET LINK",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (email.isNotBlank()) Color.Black else Color.Black.copy(alpha = 0.5f)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    ForgotPasswordScreen()
}