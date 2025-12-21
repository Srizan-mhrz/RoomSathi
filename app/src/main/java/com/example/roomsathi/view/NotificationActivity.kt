package com.example.roomsathi.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.roomsathi.R // Assuming you have your drawables here

class NotificationActivity {
    // The main entry point for your Activity's UI
    @Composable
    fun NotificationScreen() {
        // A list of sample notifications. In a real app, this would come from a ViewModel.
        val notifications = List(20) { index ->
            NotificationItemData(
                id = index,
                userAvatar = R.drawable.billieeilish, // Replace with your placeholder
                username = "user_${index + 1}",
                message = "Posted a room",
                timestamp = "${index + 1}h ago",
                postPreview = R.drawable.baseline_bookmark_24// Replace with your placeholder
            )
        }

        Scaffold { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(notifications) { notification ->
                    NotificationItem(data = notification)
                    Divider(color = colorResource(id = R.color.teal_700), thickness = 0.5.dp)
                }
            }
        }
    }

    // Represents a single notification item in the list
    @Composable
    fun NotificationItem(data: NotificationItemData) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // User Avatar
            Image(
                painter = painterResource(id = data.userAvatar),
                contentDescription = "${data.username}'s avatar",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Notification Text
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                        append(data.username)
                    }
                    append(" ")
                    append(data.message)
                    append(" ")
                    withStyle(style = SpanStyle(color = Color.Gray)) {
                        append(data.timestamp)
                    }
                },
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(12.dp))

            // Post Preview Image
            Image(
                painter = painterResource(id = data.postPreview),
                contentDescription = "Post preview",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(44.dp)
            )
        }
    }

    // Data class to hold the information for each notification
    data class NotificationItemData(
        val id: Int,
        val userAvatar: Int, // Using drawable resource ID
        val username: String,
        val message: String,
        val timestamp: String,
        val postPreview: Int // Using drawable resource ID
    )

    // Preview for the entire screen
    @Preview(showBackground = true)
    @Composable
    fun NotificationScreenPreview() {
        NotificationScreen()
    }

    // Preview for a single item
    @Preview(showBackground = true)
    @Composable
    fun NotificationItemPreview() {
        NotificationItem(
            data = NotificationItemData(
                id = 1,
                userAvatar = R.drawable.ic_launcher_background,
                username = "Begangeli",
                message = "looks like something you're interested in",
                timestamp = "2h ago",
                postPreview = R.drawable.billieeilish
            )
        )
    }
}
