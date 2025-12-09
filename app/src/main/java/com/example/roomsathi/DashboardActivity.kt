

package com.example.roomsathi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import com.example.roomsathi.ui.theme.*
import androidx.compose.ui.graphics.Color.Companion.LightGray

class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DashboardBody()
        }
    }
}

@Composable
fun DashboardBody() {
    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LightBlue)
                    .padding(top = 40.dp) // space above
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.parkbogum),
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        "Hello! Park Bo Gum",
                        modifier = Modifier.weight(1f),
                        style = TextStyle(
                            color = White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Icon(
                        painter = painterResource(R.drawable.baseline_notifications_active_24),
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp)
                            .clickable { /* Handle notification click */ },
                        tint = Yellow
                    )
                }
            }
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)
                    .background(Color(0xFF212842)),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                BottomNavItem("Home", R.drawable.baseline_home_24) { }
                BottomNavItem("Listing", R.drawable.baseline_business_24) { }
                BottomNavItem("Messages", R.drawable.baseline_message_24) { }
                BottomNavItem("Profile", R.drawable.baseline_person_24) { }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(LightBlue)
        ) {
            item { Spacer(modifier = Modifier.height(16.dp)) }

            // Search Card
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
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            "Search Place, Apartment, Room",
                            modifier = Modifier.weight(1f),
                            color = Color(0xFF5C4433),
                            fontSize = 15.sp
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            // Horizontal scroll for Room / Apartment / Location
            item {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    item { RoomItem("Room", R.drawable.baseline_home_24) { } }
                    item { RoomItem("Apartment", R.drawable.baseline_business_24) { } }
                    item { RoomItem("Location", R.drawable.baseline_location_on_24) { } }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            // Post Card
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(horizontal = 16.dp)
                        .clickable { },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE7DCCD))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Post your property here.",
                            style = TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkBlue
                            )
                        )
                        Spacer(modifier = Modifier.width(55.dp))
                        Icon(
                            painter = painterResource(R.drawable.baseline_add_circle_24),
                            contentDescription = null,
                            modifier = Modifier.size(55.dp),
                            tint = DarkBlue
                        )
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            // Feature List Title
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Feature listing",
                        modifier = Modifier.weight(5f),
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        "See All",
                        modifier = Modifier
                            .weight(1f)
                            .clickable { },
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Blue,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            item { Spacer(modifier = Modifier.height(16.dp)) }

            // Horizontal scroll for feature images with price
            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clickable { }
                        ) {
                            Image(
                                painter = painterResource(R.drawable.apartment),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(300.dp)
                                    .clip(RoundedCornerShape(20.dp))
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Rs 25,000",
                                color = White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            // 👇 First line with ellipses
                            Text(
                                text = "Spacious apartment with balcony and great view...",
                                color = White,
                                fontSize = 14.sp
                            )
                        }
                    }
                    item {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clickable { }
                        ) {
                            Image(
                                painter = painterResource(R.drawable.room),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(300.dp)
                                    .clip(RoundedCornerShape(20.dp))
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Rs 15,000",
                                color = White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

// ✅ Reusable item for Room/Apartment/Location
@Composable
fun RoomItem(label: String, iconRes: Int, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(100.dp)
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier.size(30.dp),
            tint = White
        )
        Text(label, color = White)
    }
}

// ✅ Reusable Bottom Navigation Item
@Composable
fun BottomNavItem(label: String, iconRes: Int, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier.size(30.dp),
            tint = White
        )
        Text(label, fontSize = 12.sp, color = White)
    }
}
    @Preview(showBackground = true)
    @Composable
    fun DashboardPreview() {
        DashboardBody()
    }
