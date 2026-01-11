package com.example.roomsathi.view

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
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
                // Transition to the Inbox/Chat screen
                val intent = Intent(this, InboxActivity::class.java).apply {
                    putExtra("RECEIVER_ID", selectedUser.uid)
                    putExtra("RECEIVER_NAME", selectedUser.name)
                }
                startActivity(intent)
            }
        }
    }
}

@Composable
fun MessageBody(onChatClick: (UserItemData) -> Unit) {
    // 1. State for holding real users from Firebase
    val userList = remember { mutableStateListOf<UserItemData>() }
    val currentUser = FirebaseAuth.getInstance().currentUser

    // 2. Fetch users from Firebase Realtime Database
    LaunchedEffect(Unit) {
        val database = FirebaseDatabase.getInstance().getReference("users")
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (data in snapshot.children) {
                    val userId = data.child("userId").value.toString()
                    val userName = data.child("fullName").value.toString()

                    // Only add the user if it's NOT the person currently logged in
                    if (userId != currentUser?.uid) {
                        userList.add(
                            UserItemData(
                                uid = userId,
                                name = userName,
                                preview = "Click to chat...",
                                imageRes = R.drawable.parkbogum // Dummy image for everyone
                            )
                        )
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(DarkBlue)
        ) {
            // Top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_back_ios_24),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp).clickable { /* Handle back */ },
                    tint = White
                )
                Spacer(Modifier.width(8.dp))
                Text("Messages", color = White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }

            // Search bar (Visual only)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5EFE8))
            ) {
                Row(modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(painterResource(R.drawable.baseline_search_24), null, tint = DarkBlue)
                    Spacer(Modifier.width(8.dp))
                    Text("Search", color = Color.Gray, fontSize = 15.sp)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Real-time user list
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(userList) { user ->
                    MessageRow(user) { onChatClick(user) }
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
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(user.imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(55.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                user.name,
                style = TextStyle(color = White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            )
            Text(
                user.preview,
                style = TextStyle(color = White.copy(alpha = 0.7f), fontSize = 12.sp)
            )
        }
    }
}

// Data model for the UI list
data class UserItemData(
    val uid: String,
    val name: String,
    val preview: String,
    val imageRes: Int
)
