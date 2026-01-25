package com.example.roomsathi.view

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.roomsathi.R
import com.example.roomsathi.repository.UserRepoImpl
import com.example.roomsathi.ui.theme.LightBlue
import com.example.roomsathi.ui.theme.Yellow
import com.example.roomsathi.viewmodel.UserViewModel
import androidx.compose.ui.res.painterResource


class EditProfileActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EditProfileScreen(onBack = { finish() })
        }
    }
}

@Composable
fun EditProfileScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    val userViewModel = remember { UserViewModel(UserRepoImpl()) }
    val user by userViewModel.users.observeAsState(initial = null)

    // UI State
    var name by rememberSaveable { mutableStateOf("") }
    var phoneNumber by rememberSaveable { mutableStateOf("") }
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    var isUpdating by remember { mutableStateOf(false) }

    // Fetch user data on entry
    LaunchedEffect(Unit) {
        userViewModel.getCurrentUser()?.uid?.let { userViewModel.getUserById(it) }
    }

    // Populate fields when user data is loaded
    LaunchedEffect(user) {
        user?.let {
            if (name.isEmpty()) name = it.fullName ?: ""
            if (phoneNumber.isEmpty()) phoneNumber = it.phoneNumber ?: ""
        }
    }

    // Gallery Launcher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedImageUri = uri
    }

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
            // Header Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable { onBack() }
                        .align(Alignment.CenterStart)
                )
                Text(
                    text = "Edit Profile",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(30.dp))

            // Replace the old text fields with this block:
            GlassSurface(
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.White.copy(alpha = 0.1f)
            ) {
                Column(modifier = Modifier.padding(vertical = 8.dp)) {
                    ThemedInputField(
                        label = "Full Name",
                        value = name,
                        icon = R.drawable.baseline_person_24,
                        onValueChange = { name = it }
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    ThemedInputField(
                        label = "Phone Number",
                        value = phoneNumber,
                        icon = R.drawable.outline_call_24,
                        keyboardType = KeyboardType.Phone,
                        onValueChange = { phoneNumber = it }
                    )
                }
            }
            Spacer(modifier = Modifier.height(40.dp))
            Button(
                onClick = {
                    isUpdating = true
                    userViewModel.updateUserProfile(name, phoneNumber, selectedImageUri) { success, msg ->
                        isUpdating = false
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        if (success) onBack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Yellow),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
                enabled = !isUpdating
            ) {
                if (isUpdating) {
                    CircularProgressIndicator(color = Color.Black, modifier = Modifier.size(24.dp))
                } else {
                    Text(
                        "SAVE CHANGES",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ProfileImageWithEditor(displayImage: Any, onImageClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(130.dp)
            .clickable { onImageClick() }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.1f))
            .border(2.dp, Color.White.copy(alpha = 0.2f), CircleShape))
        Image(
            painter = rememberAsyncImagePainter(model = if (displayImage == "" || displayImage == null) R.drawable.baseline_person_24 else displayImage),
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(115.dp).clip(CircleShape)
        )
        Box(modifier = Modifier
            .align(Alignment.BottomEnd)
            .size(36.dp)
            .clip(CircleShape)
            .background(Yellow)
            .border(2.dp, LightBlue, CircleShape)) {
            Icon(painter = painterResource(id = R.drawable.baseline_photo_camera_24)
                , contentDescription = null,
                modifier = Modifier
                    .size(18.dp)
                    .align(Alignment.Center), tint = Color.Black)
        }
    }
}


