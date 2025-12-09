package com.example.roomsathi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomsathi.ui.theme.Blue
import com.example.roomsathi.ui.theme.DarkBlue
import com.example.roomsathi.ui.theme.LightBlue
import com.example.roomsathi.ui.theme.White

class InboxActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InboxBody()
        }
    }
}

@Composable
fun InboxBody() {
    Scaffold(
        containerColor = LightBlue,
        topBar = {
            // Top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkBlue)
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_back_ios_24),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Image(
                    painter = painterResource(R.drawable.parkbogum),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Bo Gum",
                    color = White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        bottomBar = {
            // Message input box
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkBlue)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_add_24),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .background(Color(0xFFF0F0F0), RoundedCornerShape(20.dp))
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                ) {
                    Text("Type a message...", color = Color.Gray)
                }
                Spacer(modifier = Modifier.width(8.dp))
                Icon(
                    painter = painterResource(R.drawable.baseline_send_24),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = Blue
                )
            }
        }
    ) { padding ->
        // Chat messages
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(chatMessages) { msg ->
                ChatBubble(msg)
            }
        }
    }
}

@Composable
fun ChatBubble(message: ChatMessage) {
    val bubbleColor = if (message.isUser) Color.White else Color(0xFFE7DCCD) // white & cream

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.isUser) Arrangement.End else Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .background(bubbleColor, RoundedCornerShape(12.dp))
                .padding(12.dp)
        ) {
            Text(message.text, fontSize = 14.sp)
        }
    }
}

// Data model
data class ChatMessage(val text: String, val isUser: Boolean)

// Sample messages
val chatMessages = listOf(
    ChatMessage("Hi", false),
    ChatMessage("Hello", true),
    ChatMessage("What's Up", false),
    ChatMessage("..", true),
    ChatMessage(".......", false),
    ChatMessage(".........?", true),
    ChatMessage("Yes", true),
    ChatMessage("Hi again", false),
    ChatMessage("Hello again", true),
    ChatMessage("Hi", false),
    ChatMessage("Hello", true),
    ChatMessage("What's Up", false),
    ChatMessage("..", true),
    ChatMessage(".......", false),
    ChatMessage(".........?", true),
    ChatMessage("Yes", true),
    ChatMessage("Hi again", false),
    ChatMessage("Hello again", true),
    ChatMessage("Hi", false),
    ChatMessage("Hello", true),
    ChatMessage("What's Up", false),
    ChatMessage("..", true),
    ChatMessage(".......", false),
    ChatMessage(".........?", true),
    ChatMessage("Yes", true),
    ChatMessage("Hi again", false),
    ChatMessage("Hello again", true)
)

@Preview(showBackground = true)
@Composable
fun ChatPreview() {
    InboxBody()
}
