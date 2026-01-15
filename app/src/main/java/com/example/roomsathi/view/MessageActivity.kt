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
    val userList = remember { mutableStateListOf<UserItemData>() }
    val currentUser = FirebaseAuth.getInstance().currentUser
    val database = FirebaseDatabase.getInstance().reference

    // 1. Fetch only CHATTED users
    LaunchedEffect(currentUser?.uid) {
        if (currentUser == null) return@LaunchedEffect

        val myChatsRef = database.child("user_chats").child(currentUser.uid)

        myChatsRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                // Get all User IDs I have a history with
                val chatPartnerIds = snapshot.children.mapNotNull { it.key }

                chatPartnerIds.forEach { partnerId ->
                    // Fetch details for each specific partner
                    database.child("users").child(partnerId)
                        .addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(userSnapshot: DataSnapshot) {
                                val userName = userSnapshot.child("fullName").value.toString()

                                userList.add(
                                    UserItemData(
                                        uid = partnerId,
                                        name = userName,
                                        preview = "Tap to view messages",
                                        imageRes = R.drawable.parkbogum
                                    )
                                )
                            }
                            override fun onCancelled(error: DatabaseError) {}
                        })
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
                Text("Messages", color = White, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }

            // Real-time filtered user list
            if (userList.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No conversations yet", color = White.copy(alpha = 0.5f))
                }
            } else {
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
