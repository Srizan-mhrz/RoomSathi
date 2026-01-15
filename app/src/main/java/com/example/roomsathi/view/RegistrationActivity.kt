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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.roomsathi.R
import com.example.roomsathi.model.UserModel
import com.example.roomsathi.repository.UserRepoImpl
import com.example.roomsathi.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth

class RegistrationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { RegistrationScreen() }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationScreen() {
    val context = LocalContext.current
    val activity = context as? Activity
    val userViewModel = remember { UserViewModel(UserRepoImpl()) }

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var fullName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }
    var confirmPasswordVisibility by remember { mutableStateOf(false) }
    var isUploading by remember { mutableStateOf(false) }
    var showImageError by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? -> imageUri = uri }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Sign Up") },
                navigationIcon = {
                    IconButton(onClick = { activity?.finish() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .size(110.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .border(
                        width = 2.dp,
                        color = if (showImageError && imageUri == null) Color.Red else MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    )
                    .clickable {
                        showImageError = false
                        launcher.launch("image/*")
                    },
                contentAlignment = Alignment.Center
            ) {
                if (imageUri == null) {
                    Icon(Icons.Default.AddAPhoto, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                } else {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(Modifier.height(16.dp))
            Text("Create Your Account", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(32.dp))

            RegistrationInputField("Full Name", fullName, { fullName = it }, KeyboardType.Text)
            Spacer(Modifier.height(16.dp))
            RegistrationInputField("Phone Number", phoneNumber, { phoneNumber = it }, KeyboardType.Phone)
            Spacer(Modifier.height(16.dp))
            RegistrationInputField("Email", email, { email = it }, KeyboardType.Email)
            Spacer(Modifier.height(16.dp))
            RegistrationInputField("Password", password, { password = it }, KeyboardType.Password, true, passwordVisibility, { passwordVisibility = !passwordVisibility })
            Spacer(Modifier.height(16.dp))
            RegistrationInputField("Confirm Password", confirmPassword, { confirmPassword = it }, KeyboardType.Password, true, confirmPasswordVisibility, { confirmPasswordVisibility = !confirmPasswordVisibility }, confirmPassword.isNotEmpty() && password != confirmPassword)

            Spacer(Modifier.height(32.dp))

            if (isUploading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = {
                        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || phoneNumber.isEmpty()) {
                            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                        } else if (imageUri == null) {
                            showImageError = true
                            Toast.makeText(context, "Profile picture is required", Toast.LENGTH_LONG).show()
                        } else if (password != confirmPassword) {
                            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                        } else {
                            isUploading = true

                            // 1. First, Create Account in Firebase Auth
                            userViewModel.register(email, password) { success, msg, userId ->
                                if (success) {
                                    // 2. Auth Success! Now we have the 'userId'. Upload Image to Cloudinary.
                                    userViewModel.uploadProfilePicture(imageUri!!) { uploadSuccess, uploadError, url ->
                                        if (uploadSuccess) {
                                            // 3. Image Success! Save everything to Realtime Database
                                            val model = UserModel(
                                                userId = userId,
                                                fullName = fullName,
                                                phoneNumber = phoneNumber,
                                                email = email,
                                                profileImageUrl = url ?: "", // URL goes here
                                                password = "" // DB stays clean
                                            )

                                            userViewModel.addUserToDatabase(userId, model) { dbSuccess, dbMsg ->
                                                isUploading = false
                                                if (dbSuccess) {
                                                    activity?.finish()
                                                    Toast.makeText(context, "Registration Success", Toast.LENGTH_SHORT).show()
                                                } else {
                                                    Toast.makeText(context, dbMsg, Toast.LENGTH_SHORT).show()
                                                }
                                            }
                                        } else {
                                            // Cloudinary failed: Clean up Auth so email can be reused
                                            isUploading = false
                                            FirebaseAuth.getInstance().signOut()
                                            Toast.makeText(context, "Image upload failed: $uploadError", Toast.LENGTH_LONG).show()
                                        }
                                    }
                                } else {
                                    isUploading = false
                                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("SIGN UP", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
fun RegistrationInputField(
    label: String, value: String, onValueChange: (String) -> Unit, keyboardType: KeyboardType,
    isPassword: Boolean = false, isPasswordVisible: Boolean = false,
    onVisibilityChange: () -> Unit = {}, isError: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 4.dp))
        OutlinedTextField(
            value = value, onValueChange = onValueChange, modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp), singleLine = true, isError = isError,
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = if (isPassword && !isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                if (isPassword) {
                    IconButton(onClick = onVisibilityChange) {
                        val iconId = if (isPasswordVisible) R.drawable.baseline_visibility_24 else R.drawable.outline_visibility_off_24
                        Icon(painterResource(iconId), null)
                    }
                }
            }
        )
    }
}