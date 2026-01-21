package com.example.roomsathi.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.roomsathi.R
import com.example.roomsathi.ui.theme.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MessageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MessageBody { selectedUser ->
                val intent = Intent(this, InboxActivity::class.java).apply {
                    putExtra("RECEIVER_ID", selectedUser.uid)
                    putExtra("RECEIVER_NAME", selectedUser.name)
                    putExtra("RECEIVER_IMAGE", selectedUser.imageUrl)
                }
                startActivity(intent)
            }
        }
    }
}

@Composable
fun MessageBody(onChatClick: (UserItemData) -> Unit) {
    val userList = remember { mutableStateListOf<UserItemData>() }
    val currentUser = FirebaseAuth.getInstance().currentUser
    val database = FirebaseDatabase.getInstance().reference

    LaunchedEffect(currentUser?.uid) {
        if (currentUser == null) return@LaunchedEffect
        val myChatsRef = database.child("user_chats").child(currentUser.uid)
        myChatsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                val chatPartnerIds = snapshot.children.mapNotNull { it.key }
                chatPartnerIds.forEach { partnerId ->
                    database.child("users").child(partnerId)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(userSnapshot: DataSnapshot) {
                                val userName = userSnapshot.child("fullName").value?.toString() ?: "Unknown"
                                val profileImageUrl = userSnapshot.child("profileImageUrl").value?.toString() ?: ""
                                if (userList.none { it.uid == partnerId }) {
                                    userList.add(UserItemData(partnerId, userName, "Tap to view messages", profileImageUrl))
                                }
                            }
                            override fun onCancelled(error: DatabaseError) {}
                        })
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue) // Unified background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            // Header
            Text(
                text = "Messages",
                color = Color.White,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 28.sp,
                modifier = Modifier.padding(24.dp)
            )

            if (userList.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No conversations yet", color = Color.White.copy(alpha = 0.4f))
                }
            } else {
                // One large Glass Container for the whole list looks much cleaner
                GlassSurface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp),
                    containerColor = Color.White.copy(alpha = 0.08f)
                ) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(userList) { user ->
                            MessageRow(user) { onChatClick(user) }
                            // Subtle divider that doesn't scream for attention
                            HorizontalDivider(
                                modifier = Modifier.padding(horizontal = 16.dp),
                                thickness = 0.5.dp,
                                color = Color.White.copy(alpha = 0.1f)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MessageRow(user: UserItemData, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 20.dp), // Increased vertical padding for "air"
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box {
            AsyncImage(
                model = user.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(54.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.1f)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.baseline_person_24),
                error = painterResource(R.drawable.baseline_person_24)
            )
            // Tiny "online" dot indicator for the theme
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(Yellow)
                    .align(Alignment.BottomEnd)
                    .padding(2.dp)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = user.name,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = user.preview,
                style = TextStyle(
                    color = Color.White.copy(alpha = 0.5f),
                    fontSize = 13.sp
                )
            )
        }

        Icon(
            painter = painterResource(R.drawable.outline_keyboard_arrow_right_24),
            contentDescription = null,
            tint = Color.White.copy(alpha = 0.3f),
            modifier = Modifier.size(12.dp) // Subtle indicator
        )
    }
}

data class UserItemData(
    val uid: String,
    val name: String,
    val preview: String,
    val imageUrl: String
)