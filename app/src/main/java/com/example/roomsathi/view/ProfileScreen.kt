package com.example.roomsathi

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.roomsathi.ui.theme.*
import com.example.roomsathi.view.EditProfileActivity
import com.example.roomsathi.viewmodel.UserViewModel

@Composable
fun GlassSurface(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    val cornerShape = RoundedCornerShape(16.dp)

    Box(
        modifier = modifier
            .clip(cornerShape)
            .border(
                width = 1.dp,
                color = White.copy(alpha = 0.3f),
                shape = cornerShape
            )
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(White.copy(alpha = 0.15f))
                .blur(20.dp)
        )

        Box(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}

// Full body composable to be used in Dashboard or as a standalone activity
@Composable
fun ProfileScreenBody(userViewModel: UserViewModel) {
    val context = LocalContext.current
    val backgroundColor = LightBlue

    // Observe user data from ViewModel
    val userModel by userViewModel.users.observeAsState()

    // Extract dynamic values
    val profileName = userModel?.fullName ?: "Loading..."
    val profileEmail = userModel?.email ?: "user@example.com"
    val profileImageUrl = userModel?.profileImageUrl ?: ""

    // Fetch data if user state is empty
    LaunchedEffect(Unit) {
        userViewModel.getCurrentUser()?.uid?.let { uid ->
            userViewModel.getUserById(uid)
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(backgroundColor)) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Header section with dynamic data
                GlassSurface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                ) {
                    ProfileHeaderContent(
                        profileName = profileName,
                        profileHandle = profileEmail,
                        profileImageUrl = profileImageUrl
                    )
                }

                // Menu items section
                GlassSurface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 24.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        ProfileItem(
                            iconRes = R.drawable.outline_location_on_24,
                            label = "Location",
                            onClick = { /* Handle Location */ }
                        )
                        HorizontalDivider(color = White.copy(alpha = 0.3f), thickness = 0.5.dp)
                        ProfileItem(
                            iconRes = R.drawable.baseline_bookmark_24,
                            label = "Saved",
                            onClick = { /* Navigate to Saved */ }
                        )
                        HorizontalDivider(color = White.copy(alpha = 0.3f), thickness = 0.5.dp)
                        ProfileItem(
                            iconRes = R.drawable.baseline_history_24,
                            label = "History",
                            onClick = { /* Navigate to History */ }
                        )
                        HorizontalDivider(color = White.copy(alpha = 0.3f), thickness = 0.5.dp)
                        ProfileItem(
                            iconRes = R.drawable.baseline_settings_24,
                            label = "Privacy setting",
                            onClick = { /* Navigate to Settings */ }
                        )
                        HorizontalDivider(color = White.copy(alpha = 0.3f), thickness = 0.5.dp)
                        ProfileItem(
                            iconRes = R.drawable.baseline_logout_24,
                            label = "Log Out",
                            onClick = {
                                userViewModel.logOut { success, _ ->
                                    if(success) {
                                        // Logic to return to Login Screen
                                        (context as? android.app.Activity)?.finish()
                                    }
                                }
                            },
                            isLogout = true
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileHeaderContent(
    profileName: String,
    profileHandle: String,
    profileImageUrl: String
) {
    val context = LocalContext.current
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Dynamic Profile Image from Cloudinary
        AsyncImage(
            model = profileImageUrl,
            contentDescription = "User Profile Picture",
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .background(White.copy(alpha = 0.1f)),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.baseline_person_24),
            error = painterResource(R.drawable.baseline_person_24)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                profileName,
                style = TextStyle(
                    color = White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                profileHandle, // Now showing email
                style = TextStyle(
                    color = Color.LightGray.copy(alpha = 0.8f),
                    fontSize = 14.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Button(
            onClick = {
                val intent = Intent(context, EditProfileActivity::class.java)
                context.startActivity(intent)
            },
            colors = ButtonDefaults.buttonColors(containerColor = DarkBlue),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text("Edit Profile", color = White)
        }
    }
}

@Composable
fun ProfileItem(
    iconRes: Int,
    label: String,
    onClick: () -> Unit,
    isLogout: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = if (isLogout) Color(0xFFFF4444) else White
        )

        Spacer(modifier = Modifier.width(20.dp))

        Text(
            label,
            modifier = Modifier.weight(1f),
            style = TextStyle(
                color = White,
                fontSize = 18.sp
            )
        )

        Icon(
            painter = painterResource(R.drawable.baseline_arrow_forward_ios_24),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = White.copy(alpha = 0.8f)
        )
    }
}