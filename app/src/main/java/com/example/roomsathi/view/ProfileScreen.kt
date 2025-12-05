package com.example.roomsathi.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomsathi.R
import com.example.roomsathi.ui.theme.DarkBrown
import com.example.roomsathi.ui.theme.Lightbeige
import com.example.roomsathi.ui.theme.SoftCream
val OutlineColor = DarkBrown.copy(alpha = 0.7f)

@Composable
fun ProfileScreen() {
    Column(modifier = Modifier.fillMaxSize().background(color = SoftCream)) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "My Profile", fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 24.dp),
                color = DarkBrown
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp)
        ) {

            Surface(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                color = Color(0xFFE0E0E0)
            ) {

                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = null,
                    modifier = Modifier.padding(16.dp),
                    tint = DarkBrown
                )
            }

            Spacer(modifier = Modifier.width(16.dp))


            Column {
                Text(
                    text = "User",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = DarkBrown
                )
                Text(
                    text = "@whoami123",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(8.dp))


                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF3F51B5)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
                    modifier = Modifier.height(36.dp)
                ) {
                    Text(text = "Edit Profile", fontSize = 12.sp)
                }
            }
        }


        ProfileOptionItem(icon = Icons.Default.LocationOn, title = "Location", onClick = { })
        ProfileOptionItem(icon = R.drawable.baseline_bookmark_24, title = "Saved", onClick = { })
        ProfileOptionItem(icon = R.drawable.baseline_history_24, title = "History", onClick = { })
        ProfileOptionItem(icon = R.drawable.baseline_settings_24, title = "Privacy setting", onClick = { })


        ProfileOptionItem(
            icon = R.drawable.baseline_logout_24,
            title = "Log Out",
            isDestructive = true,
            onClick = { }
        )
    }
}



@Composable
fun ProfileOptionItem(
    icon: Any,
    title: String,
    isDestructive: Boolean = false,
    onClick: () -> Unit
) {
    val itemShape = RoundedCornerShape(12.dp)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .border(width = 1.dp, color = OutlineColor, shape = itemShape)
            .clip(itemShape)
            .clickable(onClick = onClick),
        color = Color.Transparent
    ) {
        ListItem(
            headlineContent = {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = DarkBrown
                )
            },
            leadingContent = {
                when (icon) {
                    is ImageVector -> {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = DarkBrown,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    is Int -> {
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = null,
                            tint = DarkBrown,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            },
            trailingContent = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = DarkBrown,
                )
            },
            colors = ListItemDefaults.colors(
                containerColor = SoftCream
            ),
        )
    }
}


@Preview
@Composable
fun PreviewProfile(){
    ProfileScreen()
}