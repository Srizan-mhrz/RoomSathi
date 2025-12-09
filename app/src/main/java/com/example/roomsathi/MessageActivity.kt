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

class MessageActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MessageBody()
        }
    }
}

@Composable
fun MessageBody() {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(LightBlue)
        ) {
            // Top bar row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_arrow_back_ios_24),
                    contentDescription = null,
                    modifier = Modifier.size(25.dp),
                    tint = White
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Search bar
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(45.dp)
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF5EFE8))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_search_24),
                        contentDescription = null,
                        tint = DarkBlue
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        "Search",
                        color = Blue.copy(alpha = 0.6f),
                        fontSize = 15.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Header row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Messages",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = TextStyle(
                        color = White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    "Requests",
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = TextStyle(
                        color = Blue,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // ✅ Scrollable list of messages
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(messageList) { msg ->
                    MessageRow(msg.name, msg.preview, msg.imageRes)
                }
            }
        }
    }
}

// ✅ Reusable row for each message
@Composable
fun MessageRow(name: String, preview: String, imageRes: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(imageRes),
            contentDescription = null,
            modifier = Modifier
                .size(55.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                name,
                style = TextStyle(
                    color = White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                preview,
                style = TextStyle(
                    color = White.copy(alpha = 0.8f),
                    fontSize = 12.sp
                )
            )
        }
    }
}

// Example data model
data class Message(val name: String, val preview: String, val imageRes: Int)

// Example list
val messageList = listOf(
    Message("Jeon Jungkook", "Hi, Can I get info...", R.drawable.jungkook),
    Message("Lee Ji Eun", "Hi, Can I get info...", R.drawable.leejieun),
    Message("Lin Yi", "Hi, Can I get info...", R.drawable.linyi),
    Message("Xing Fei", "Hi, Can I get info...", R.drawable.xingfei),
    Message("Zhao Lusi", "Hi, Can I get info...", R.drawable.zhaulusi),
    Message("Chen Zheyuan", "Hi, Can I get info...", R.drawable.chain),
    Message("Hwang In-Yeop", "Hi, Can I get info...", R.drawable.hang),
    Message("Park Bo Gum", "Looking forward to our meeting...", R.drawable.parkbogum),
    Message("Kim Taehyung", "Can you share the details...", R.drawable.parkbogum),
    Message("IU", "I’ll send the files soon...", R.drawable.jungkook),
    Message("Yang Yang", "Please confirm the schedule...", R.drawable.bogum),
    Message("Dilraba Dilmurat", "Thanks for the update...", R.drawable.cat)
    )

@Preview(showBackground = true)
@Composable
fun MessagePreview() {
    MessageBody()
}
