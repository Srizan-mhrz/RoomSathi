package com.example.roomsathi.view

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomsathi.R
import com.example.roomsathi.ui.theme.Blue
import com.example.roomsathi.ui.theme.DarkBlue
import com.example.roomsathi.ui.theme.LightBlue
import com.example.roomsathi.ui.theme.White

data class RoomModel(
    val image: Int,
    val title: String,
    val price: String,
    val location: String,
    val postedTime: String
)

class SeeAllActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SeeBody()
        }
    }
}

@Composable
fun SeeBody() {
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp)
                    .background(LightBlue)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_arrow_back_ios_24),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp),
                        tint = White
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }
    ) { padding ->

        //  LIST DATA
        val roomList = listOf(
            RoomModel(R.drawable.room, "Single big Room", "Rs 6000", "New Road, Kathmandu", "Just now"),
            RoomModel(R.drawable.apartment, "Kitchen & bedroom attached", "Rs 20000", "Baneshwor, Kathmandu", "5 days ago"),
            RoomModel(R.drawable.apartment, "Kitchen, living & bedroom apartment", "Rs 30000", "Sanogau, Lalitpur", "2 months ago"),
            RoomModel(R.drawable.room2, "Apartment", "Rs 20000", "New Road, Kathmandu", "Just now"),
            RoomModel(R.drawable.room, "Room attach with kitchen", "Rs 15000", "Naxal, Kathmandu", "1 week ago"),

            RoomModel(R.drawable.room, "Single big Room", "Rs 6000", "New Road, Kathmandu", "Just now"),
            RoomModel(R.drawable.apartment, "Kitchen & bedroom attached", "Rs 20000", "Baneshwor, Kathmandu", "5 days ago"),
            RoomModel(R.drawable.apartment, "Kitchen, living & bedroom apartment", "Rs 30000", "Sanogau, Lalitpur", "2 months ago"),
            RoomModel(R.drawable.room2, "Apartment", "Rs 20000", "New Road, Kathmandu", "Just now"),
            RoomModel(R.drawable.room, "Room attach with kitchen", "Rs 15000", "Naxal, Kathmandu", "1 week ago"),
            
            RoomModel(R.drawable.room, "Single big Room", "Rs 6000", "New Road, Kathmandu", "Just now"),
            RoomModel(R.drawable.apartment, "Kitchen & bedroom attached", "Rs 20000", "Baneshwor, Kathmandu", "5 days ago"),
            RoomModel(R.drawable.apartment, "Kitchen, living & bedroom apartment", "Rs 30000", "Sanogau, Lalitpur", "2 months ago"),
            RoomModel(R.drawable.room2, "Apartment", "Rs 20000", "New Road, Kathmandu", "Just now"),
            RoomModel(R.drawable.room, "Room attach with kitchen", "Rs 15000", "Naxal, Kathmandu", "1 week ago"),


            RoomModel(R.drawable.room2, "Full Flat 2BHK", "Rs 35000", "Patan, Lalitpur", "3 days ago")
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(LightBlue)
        ) {

            // spacing top
            item { Spacer(modifier = Modifier.height(16.dp)) }

            //  Search Bar
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(horizontal = 16.dp)
                        .clickable { },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0E7D5))
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
                        Text("Search", color = Blue.copy(alpha = 0.6f), fontSize = 15.sp)
                    }
                }
            }

            // space under search
            item { Spacer(modifier = Modifier.height(18.dp)) }

            // ROOM LISTING ITEMS
            items(roomList) { room ->
                RoomListItem(room = room)
            }
        }
    }
}

//Single room  list
@Composable
fun RoomListItem(room: RoomModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(room.image),
            contentDescription = null,
            modifier = Modifier
                .size(85.dp)
                .clip(RoundedCornerShape(12.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = room.title, color = White, fontSize = 15.sp)
            Text(text = room.price, color = Color(0xFFEEC85A), fontSize = 14.sp)
            Text(text = room.location, color = Color.LightGray, fontSize = 13.sp)
        }

        Column(horizontalAlignment = Alignment.End) {
            Icon(
                painter = painterResource(R.drawable.baseline_more_vert_24),
                contentDescription = null,
                tint = White,
                modifier = Modifier.size(20.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))  // ✔ Fixed

            Icon(
                painter = painterResource(R.drawable.baseline_bookmark_border_24),
                contentDescription = null,
                tint = White,
                modifier = Modifier.size(22.dp)
            )
            Text(room.postedTime, color = Color.LightGray, fontSize = 12.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SeePreview() {
    SeeBody()
}
