package com.example.roomsathi.view

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.roomsathi.R
import com.example.roomsathi.model.UserModel
import com.example.roomsathi.repository.UserRepoImpl
import com.example.roomsathi.ui.theme.LightBlue
import com.example.roomsathi.ui.theme.Yellow
import com.example.roomsathi.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth

class RegistrationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistrationScreen()
        }
    }
}

@Composable
fun RegistrationScreen() {
    val context = LocalContext.current
    val activity = context as? Activity

    val userViewModel: UserViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return UserViewModel(UserRepoImpl()) as T
            }
        }
    )

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var fullName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var isUploading by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> imageUri = uri }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { activity?.finish() }) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Create Account",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.1f))
                    .border(2.dp, if (imageUri == null) Yellow.copy(alpha = 0.5f) else Yellow, CircleShape)
                    .clickable { launcher.launch("image/*") }
                    .testTag("profileImagePicker"), // Tag added for UI testing
                contentAlignment = Alignment.Center
            ) {
                if (imageUri == null) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Default.AddAPhoto,
                            contentDescription = null,
                            tint = Yellow,
                            modifier = Modifier.size(32.dp)
                        )
                        Text("Add Photo", color = Yellow, fontSize = 12.sp)
                    }
                } else {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Glass Surface Container
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White.copy(alpha = 0.1f),
                shape = RoundedCornerShape(24.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    ThemedInputField(
                        label = "Full Name",
                        value = fullName,
                        icon = R.drawable.baseline_person_24,
                        testTag = "fullNameField", // Tag added
                        onValueChange = { fullName = it }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    ThemedInputField(
                        label = "Email Address",
                        value = email,
                        icon = R.drawable.outline_alternate_email_24,
                        keyboardType = KeyboardType.Email,
                        testTag = "emailField", // Tag added
                        onValueChange = { email = it }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    ThemedInputField(
                        label = "Phone Number",
                        value = phoneNumber,
                        icon = R.drawable.outline_contact_page_24,
                        keyboardType = KeyboardType.Phone,
                        testTag = "phoneField", // Tag added
                        onValueChange = { phoneNumber = it }
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    ThemedInputField(
                        label = "Password",
                        value = password,
                        icon = R.drawable.outline_key_vertical_24,
                        keyboardType = KeyboardType.Password,
                        isPassword = true,
                        isPasswordVisible = passwordVisibility,
                        onVisibilityChange = { passwordVisibility = !passwordVisibility },
                        testTag = "passwordField", // Tag added
                        onValueChange = { password = it }
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            if (isUploading) {
                CircularProgressIndicator(color = Yellow)
            } else {
                Button(
                    onClick = {
                        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || phoneNumber.isEmpty()) {
                            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                        } else if (imageUri == null) {
                            Toast.makeText(context, "Profile picture is required", Toast.LENGTH_LONG).show()
                        } else {
                            isUploading = true
                            userViewModel.register(email, password) { success, msg, userId ->
                                if (success) {
                                    userViewModel.uploadProfilePicture(imageUri!!) { uploadSuccess, uploadError, url ->
                                        if (uploadSuccess) {
                                            val model = UserModel(
                                                userId = userId,
                                                fullName = fullName,
                                                phoneNumber = phoneNumber,
                                                email = email,
                                                profileImageUrl = url ?: "",
                                                password = ""
                                            )
                                            userViewModel.addUserToDatabase(userId, model) { dbSuccess, dbMsg ->
                                                isUploading = false
                                                if (dbSuccess) {
                                                    activity?.finish()
                                                    Toast.makeText(context, "Welcome to RoomSathi!", Toast.LENGTH_SHORT).show()
                                                } else {
                                                    Toast.makeText(context, dbMsg, Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        } else {
                                            isUploading = false
                                            FirebaseAuth.getInstance().signOut()
                                            Toast.makeText(context, "Upload failed: $uploadError", Toast.LENGTH_LONG).show()
                                        }
                                    }
                                } else {
                                    isUploading = false
                                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .testTag("signUpButton"), // Tag added
                    colors = ButtonDefaults.buttonColors(containerColor = Yellow),
                    shape = RoundedCornerShape(16.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                ) {
                    Text(
                        "SIGN UP",
                        color = Color.Black,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

@Composable
fun ThemedInputField(
    label: String,
    value: String,
    icon: Int,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    isPasswordVisible: Boolean = false,
    onVisibilityChange: () -> Unit = {},
    testTag: String = "", // Parameter added for testing
    onValueChange: (String) -> Unit
) {
    Column {
        Text(
            text = label,
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(start = 4.dp, bottom = 6.dp)
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .testTag(testTag), // Applying the testTag here
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedBorderColor = Yellow,
                unfocusedBorderColor = Color.White.copy(alpha = 0.2f),
                cursorColor = Yellow,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
            ),
            leadingIcon = {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    tint = Yellow,
                    modifier = Modifier.size(20.dp)
                )
            },
            trailingIcon = {
                if (isPassword) {
                    IconButton(onClick = onVisibilityChange) {
                        val iconRes = if (isPasswordVisible)
                            R.drawable.baseline_visibility_24
                        else
                            R.drawable.outline_visibility_off_24
                        Icon(
                            painter = painterResource(iconRes),
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.5f)
                        )
                    }
                }
            },
            visualTransformation = if (isPassword && !isPasswordVisible)
                PasswordVisualTransformation()
            else
                VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            singleLine = true
        )
    }
}