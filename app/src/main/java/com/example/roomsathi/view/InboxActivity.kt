package com.example.roomsathi.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
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
        val receiverImageUrl = intent.getStringExtra("RECEIVER_IMAGE") ?: ""
        enableEdgeToEdge()
        setContent {
            InboxBody(senderId, receiverId, receiverName, receiverImageUrl)
        }
    }
}

@Composable
fun ChatTopBar(name: String, imageUrl: String, onBack: () -> Unit) {
    // THEMED: Using GlassSurface to match Dashboard and fix top gap
    GlassSurface(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 12.dp, vertical = 8.dp),
        containerColor = Color.White.copy(alpha = 0.15f)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    painterResource(R.drawable.baseline_arrow_back_ios_24),
                    contentDescription = "Back",
                    tint = Color.White
                )
            }

            AsyncImage(
                model = if (imageUrl.isNotEmpty()) imageUrl else R.drawable.baseline_person_24,
                contentDescription = "Profile Picture",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.2f)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.baseline_person_24),
                error = painterResource(R.drawable.baseline_person_24)
            )

            Spacer(Modifier.width(12.dp))
            Text(name, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        }
    }
}


@Composable
fun InboxBody(senderId: String, receiverId: String, receiverName: String, receiverImageUrl: String) {
    val database = FirebaseDatabase.getInstance().reference
    val chatRoomId = if (senderId < receiverId) senderId + receiverId else receiverId + senderId
    val context = LocalContext.current
    val chatMessages = remember { mutableStateListOf<ChatMessage>() }
    var messageText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

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

    LaunchedEffect(chatMessages.size) {
        if (chatMessages.size > 0) {
            listState.animateScrollToItem(chatMessages.size - 1)
        }
    }

    // THEMED: LightBlue background Box fixes the white gaps
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue)
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                ChatTopBar(
                    name = receiverName,
                    imageUrl = receiverImageUrl,
                    onBack = { (context as? ComponentActivity)?.finish() }
                )
            },
            bottomBar = {
                ChatInputBar(
                    text = messageText,
                    onValueChange = { messageText = it },
                    onSendClick = {
                        if (messageText.isNotBlank()) {
                            val msg = ChatMessage(messageText, senderId, System.currentTimeMillis())
                            database.child("chats").child(chatRoomId).child("messages").push().setValue(msg)
                            database.child("user_chats").child(senderId).child(receiverId).setValue(true)
                            database.child("user_chats").child(receiverId).child(senderId).setValue(true)
                            messageText = ""
                        }
                    }
                )
            }
        ) { padding ->
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(vertical = 10.dp)
            ) {
                items(chatMessages) { msg ->
                    ChatBubble(msg, isUser = msg.senderId == senderId)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatInputBar(text: String, onValueChange: (String) -> Unit, onSendClick: () -> Unit) {
    // THEMED: Glass Input Bar with no white gaps
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding()
            .imePadding()
            .padding(12.dp)
    ) {
        GlassSurface(
            modifier = Modifier.fillMaxWidth(),
            containerColor = Color.White.copy(alpha = 0.15f)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(horizontal = 4.dp)
            ) {
                Icon(
                    painterResource(R.drawable.baseline_add_24),
                    null,
                    modifier = Modifier.size(24.dp),
                    tint = Color.White.copy(alpha = 0.7f)
                )

                Spacer(Modifier.width(8.dp))

                TextField(
                    value = text,
                    onValueChange = onValueChange,
                    placeholder = { Text("Type a message...", color = Color.White.copy(alpha = 0.5f)) },
                    modifier = Modifier.weight(1f),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    )
                )

                Spacer(Modifier.width(8.dp))

                IconButton(
                    onClick = onSendClick,
                    modifier = Modifier
                        .background(Yellow, CircleShape)
                        .size(40.dp)
                ) {
                    Icon(
                        painterResource(R.drawable.baseline_send_24),
                        null,
                        modifier = Modifier.size(20.dp),
                        tint = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage, isUser: Boolean) {
    // THEMED: User = Yellow, Receiver = Transparent White
    val bubbleColor = if (isUser) Yellow else Color.White.copy(alpha = 0.15f)
    val textColor = if (isUser) Color.Black else Color.White
    val alignment = if (isUser) Alignment.End else Alignment.Start
    val shape = if (isUser) {
        RoundedCornerShape(16.dp, 16.dp, 2.dp, 16.dp)
    } else {
        RoundedCornerShape(16.dp, 16.dp, 16.dp, 2.dp)
    }

    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = alignment) {
        Surface(
            color = bubbleColor,
            shape = shape,
            modifier = Modifier.padding(vertical = 2.dp)
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                fontSize = 15.sp,
                color = textColor
            )
        }
    }
}

data class ChatMessage(
    val text: String = "",
    val senderId: String = "",
    val timestamp: Long = 0L
)