
package com.example.roomsathi.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomsathi.ProfileScreenBody
import com.example.roomsathi.R
import com.example.roomsathi.ui.theme.*

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
fun GlassSurface(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val shape = RoundedCornerShape(20.dp)

    Box(
        modifier = modifier
            .clip(shape)
            .border(1.dp, Color.White.copy(alpha = 0.2f), shape)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Color.White.copy(alpha = 0.12f))
                .blur(25.dp)
        )
        Box(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}

@Composable
fun DashboardBody() {
    var selectedIndex by rememberSaveable { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue)
    ) {
        Scaffold(
            containerColor = Color.Transparent,

            // Show TopBar only on Home
            topBar = {
                if (selectedIndex == 0) {
                    DashboardTopBar()
                }
            },
//            // Hide BottomBar on Messages
//            bottomBar = {
//                if (selectedIndex != 2) {
//                    DashboardBottomBar(
//                        selectedIndex = selectedIndex,
//                        onItemSelected = { selectedIndex = it }
//                    )
//                }
//            }
            // BottomBar for ALL screens (including Messages)
            bottomBar = {
                DashboardBottomBar(
                    selectedIndex = selectedIndex,
                    onItemSelected = { selectedIndex = it }
                )
            }
        ) { padding ->
            when (selectedIndex) {
                0 -> DashboardContent(padding)
                1 -> ListingScreen()          // Listing
                2 -> MessageBody()            // Messages
                3 -> ProfileScreenBody()          // Profile
            }
        }
    }
}

//top bar \

@Composable
fun DashboardTopBar() {
    GlassSurface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 40.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(R.drawable.parkbogum),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = "Hello! Park Bo Gum",
                modifier = Modifier.weight(1f),
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Icon(
                painter = painterResource(R.drawable.baseline_notifications_active_24),
                contentDescription = null,
                tint = Yellow,
                modifier = Modifier.size(26.dp)
            )
        }
    }
}

/* ---------------- Dashboard Content ---------------- */

@Composable
fun DashboardContent(padding: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentPadding = PaddingValues(bottom = 120.dp)
    ) {

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            GlassSurface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_search_24),
                        contentDescription = null,
                        tint = Color.White
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = "Search Place, Apartment, Room",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                }
            }
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }

        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    FeatureCard(R.drawable.apartment, "Rs 25,000")
                }
                item {
                    FeatureCard(R.drawable.room, "Rs 15,000")
                }
            }
        }


        item { Spacer(modifier = Modifier.height(20.dp)) }

        item {
            GlassSurface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Post your property here.",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Box(
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .clickable { },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_add_24),
                            contentDescription = null,
                            tint = DarkBlue,
                            modifier = Modifier.size(26.dp)
                        )
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Feature listing",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "See all", color = Blue, fontWeight = FontWeight.Bold)
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item { FeatureCard(R.drawable.apartment, "Rs 25,000") }
                item { FeatureCard(R.drawable.room, "Rs 15,000") }
            }
        }
    }
}

@Composable
fun FeatureCard(image: Int, price: String) {
    Column(modifier = Modifier.width(280.dp)) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(280.dp)
                .clip(RoundedCornerShape(20.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = price,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}


/* ---------------- Bottom Navigation ---------------- */

@Composable
fun DashboardBottomBar(
    selectedIndex: Int,
    onItemSelected: (Int) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .background(DarkBlue)
            .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.Bottom
        ) {
            BottomNavItem("Home", R.drawable.baseline_home_24, selectedIndex == 0) {
                onItemSelected(0)
            }
            BottomNavItem("Listing", R.drawable.baseline_business_24, selectedIndex == 1) {
                onItemSelected(1)
            }
            BottomNavItem("Messages", R.drawable.baseline_message_24, selectedIndex == 2) {
                onItemSelected(2)
            }
            BottomNavItem("Profile", R.drawable.baseline_person_24, selectedIndex == 3) {
                onItemSelected(3)
            }
        }
    }
}

@Composable
fun BottomNavItem(
    label: String,
    icon: Int,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
            .padding(vertical = 4.dp)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = if (selected) Yellow else Color.White,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            color = if (selected) Yellow else Color.White,
            fontSize = 12.sp
        )
    }
}



/* ---------------- Other Screens ---------------- */

@Composable
fun ListingScreen() = CenterText("Listing Page")

@Composable
fun MessageScreen() = CenterText("Message Box")

@Composable
fun ProfileScreen() = CenterText("Profile Page")

@Composable
fun CenterText(text: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = Color.White, fontSize = 20.sp)
    }
}

/* ---------------- Preview ---------------- */

@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    DashboardBody()
}
