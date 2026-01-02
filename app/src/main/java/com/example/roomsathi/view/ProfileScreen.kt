package com.example.roomsathi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomsathi.ui.theme.*

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


@Composable
fun ProfileScreen() {
    val backgroundColor = LightBlue

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
                GlassSurface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                ) {
                    ProfileHeaderContent(
                        profileName = "Billie Eilish",
                        profileHandle = "@billieeillish123",
                        profileImageRes = R.drawable.billieeilish
                    )
                }

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
                            onClick = { }
                        )
                        Divider(color = White.copy(alpha = 0.3f), thickness = 0.5.dp)
                        ProfileItem(
                            iconRes = R.drawable.baseline_bookmark_24,
                            label = "Saved",
                            onClick = { }
                        )
                        Divider(color = White.copy(alpha = 0.3f), thickness = 0.5.dp)
                        ProfileItem(
                            iconRes = R.drawable.baseline_history_24,
                            label = "History",
                            onClick = { }
                        )
                        Divider(color = White.copy(alpha = 0.3f), thickness = 0.5.dp)
                        ProfileItem(
                            iconRes = R.drawable.baseline_settings_24,
                            label = "Privacy setting",
                            onClick = { }
                        )
                        Divider(color = White.copy(alpha = 0.3f), thickness = 0.5.dp)
                        ProfileItem(
                            iconRes = R.drawable.baseline_logout_24,
                            label = "Log Out",
                            onClick = { },
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
    profileImageRes: Int
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(profileImageRes),
            contentDescription = null,
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
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
                )
            )
            Text(
                profileHandle,
                style = TextStyle(
                    color = Color.LightGray.copy(alpha = 0.8f),
                    fontSize = 14.sp
                )
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Button(
            onClick = { },
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

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen()
}