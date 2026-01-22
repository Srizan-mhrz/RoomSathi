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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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

    Scaffold(
        topBar = {
            EditProfileTopAppBar(onBackClicked = onBack)
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize().background(LightBlue)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Edit Profile",
                    color = Color.White.copy(alpha = 0.9f),

                    style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(vertical = 16.dp).align(Alignment.Start)
                )

                // Profile Image Section
                ProfileImageWithEditor(
                    // Show picked image if exists, otherwise show firebase image
                    displayImage = selectedImageUri ?: user?.profileImageUrl ?: "",
                    onImageClick = { launcher.launch("image/*") }
                )

                Spacer(modifier = Modifier.height(32.dp))

                EditProfileTextField(label = "Full Name", value = name, onValueChange = { name = it })
                EditProfileTextField(label = "Phone Number", value = phoneNumber, onValueChange = { phoneNumber = it }, keyboardType = KeyboardType.Phone)

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
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Yellow),
                    enabled = !isUpdating
                ) {
                    if (isUpdating) {
                        CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        Text("Save Changes",color = Color.Black,
                            fontSize = 16.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileImageWithEditor(displayImage: Any, onImageClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.clickable { onImageClick() }
    ) {
        Image(
            painter = rememberAsyncImagePainter(
                model = if (displayImage == "") R.drawable.baseline_person_24 else displayImage
            ),
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color.Gray.copy(alpha = 0.2f))
        )
        // Camera Overlay
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(36.dp)
                .clip(CircleShape)
                .background(Color.White)
                .padding(4.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_photo_camera_24),
                contentDescription = null,
                modifier = Modifier.size(20.dp).align(Alignment.Center),
                tint = Color.Black
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileTopAppBar(onBackClicked: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .background(brush = Brush.linearGradient(colors = listOf(Color(0xFF3A60F5), Color(0xFF5D85F6))))
    ) {
        IconButton(onClick = onBackClicked, modifier = Modifier.align(Alignment.CenterStart)) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
        }
    }
}

@Composable
fun EditProfileTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.Gray.copy(alpha = 0.1f),
                unfocusedContainerColor = Color.Gray.copy(alpha = 0.1f),
                focusedBorderColor = Color(0xFF3A60F5),
                unfocusedBorderColor = Color.Transparent,
            ),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(20.dp))
    }
}