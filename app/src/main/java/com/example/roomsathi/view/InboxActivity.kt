package com.example.roomsathi.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomsathi.R
import com.example.roomsathi.ui.theme.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class InboxActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve current user and the person you are chatting with
        val senderId = FirebaseAuth.getInstance().currentUser?.uid ?: "user_1"
        val receiverId = intent.getStringExtra("RECEIVER_ID") ?: "user_2"
        val receiverName = intent.getStringExtra("RECEIVER_NAME") ?: "Bo Gum"

        enableEdgeToEdge()
        setContent {
            InboxBody(senderId, receiverId, receiverName)
        }
    }
}

@Composable
fun ChatTopBar(name: String) {
    Row(
        modifier = Modifier.fillMaxWidth().background(DarkBlue).padding(top = 32.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painterResource(R.drawable.baseline_arrow_back_ios_24), null, Modifier.size(24.dp), tint = White)
        Spacer(Modifier.width(8.dp))
        Image(
            painterResource(R.drawable.parkbogum), null,
            Modifier.size(40.dp).clip(CircleShape), contentScale = ContentScale.Crop
        )
        Spacer(Modifier.width(8.dp))
        Text(name, color = White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}


@Composable
fun InboxBody(senderId: String, receiverId: String, receiverName: String) {
    val database = FirebaseDatabase.getInstance().reference
    val chatRoomId = if (senderId < receiverId) senderId + receiverId else receiverId + senderId

    val chatMessages = remember { mutableStateListOf<ChatMessage>() }
    var messageText by remember { mutableStateOf("") }
    val listState = rememberLazyListState() // State for the scroll

    // SYNC DATA
    LaunchedEffect(chatRoomId) {
        val messageRef = database.child("chats").child(chatRoomId).child("messages")
        messageRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                chatMessages.clear()
                for (postSnapshot in snapshot.children) {
                    postSnapshot.getValue(ChatMessage::class.java)?.let { chatMessages.add(it) }
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    // AUTO-SCROLL TO BOTTOM
    LaunchedEffect(chatMessages.size) {
        if (chatMessages.size > 0) {
            listState.animateScrollToItem(chatMessages.size - 1)
        }
    }

    Scaffold(
        containerColor = LightBlue,
        topBar = { ChatTopBar(receiverName) },
        bottomBar = {
            ChatInputBar(
                text = messageText,
                onValueChange = { messageText = it },
                onSendClick = {
                    if (messageText.isNotBlank()) {
                        val msg = ChatMessage(messageText, senderId, System.currentTimeMillis())

                        // 1. Save the actual message
                        database.child("chats").child(chatRoomId).child("messages").push().setValue(msg)

                        // 2. Register the chat for BOTH users so it appears in their MessageBody
                        // This tells the app: "User A and User B now have a history"
                        database.child("user_chats").child(senderId).child(receiverId).setValue(true)
                        database.child("user_chats").child(receiverId).child(senderId).setValue(true)

                        messageText = ""
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            state = listState, // Attach the scroll state here
            modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 10.dp)
        ) {
            items(chatMessages) { msg ->
                ChatBubble(msg, isUser = msg.senderId == senderId)
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatInputBar(text: String, onValueChange: (String) -> Unit, onSendClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkBlue)
            .padding(8.dp)
            .navigationBarsPadding()
            .imePadding(),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(painterResource(R.drawable.baseline_add_24), null, Modifier.size(24.dp), tint = White)
        Spacer(Modifier.width(8.dp))

        TextField(
            value = text,
            onValueChange = onValueChange,
            placeholder = { Text("Type a message...", color = Color.Gray) },
            modifier = Modifier.weight(1f).clip(RoundedCornerShape(20.dp)),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = White,
                unfocusedContainerColor = White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Spacer(Modifier.width(8.dp))
        IconButton(onClick = onSendClick) {
            Icon(painterResource(R.drawable.baseline_send_24), null, Modifier.size(24.dp), tint = Blue)
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage, isUser: Boolean) {
    val bubbleColor = if (isUser) Color.White else Color(0xFFE7DCCD)
    val alignment = if (isUser) Alignment.End else Alignment.Start
    val shape = if (isUser) {
        RoundedCornerShape(12.dp, 12.dp, 0.dp, 12.dp)
    } else {
        RoundedCornerShape(12.dp, 12.dp, 12.dp, 0.dp)
    }

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = alignment) {
        Box(
            modifier = Modifier
                .background(bubbleColor, shape)
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            Text(message.text, fontSize = 15.sp, color = Color.Black)
        }
    }
}
data class ChatMessage(
    val text: String = "",
    val senderId: String = "",
    val timestamp: Long = 0L
)

