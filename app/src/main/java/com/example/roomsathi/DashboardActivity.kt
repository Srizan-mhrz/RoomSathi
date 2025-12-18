////package com.example.roomsathi
////
////import android.os.Bundle
////import androidx.activity.ComponentActivity
////import androidx.activity.compose.setContent
////import androidx.activity.enableEdgeToEdge
////import androidx.compose.foundation.Image
////import androidx.compose.foundation.background
////import androidx.compose.foundation.clickable
////import androidx.compose.foundation.layout.*
////import androidx.compose.foundation.lazy.LazyColumn
////import androidx.compose.foundation.lazy.LazyRow
////import androidx.compose.foundation.shape.CircleShape
////import androidx.compose.foundation.shape.RoundedCornerShape
////import androidx.compose.material3.*
////import androidx.compose.runtime.Composable
////import androidx.compose.ui.Alignment
////import androidx.compose.ui.Modifier
////import androidx.compose.ui.draw.clip
////import androidx.compose.ui.graphics.Color
////import androidx.compose.ui.layout.ContentScale
////import androidx.compose.ui.res.painterResource
////import androidx.compose.ui.text.TextStyle
////import androidx.compose.ui.text.font.FontWeight
////import androidx.compose.ui.tooling.preview.Preview
////import androidx.compose.ui.unit.dp
////import androidx.compose.ui.unit.sp
////import com.example.roomsathi.ui.theme.*
////import androidx.compose.ui.graphics.Color.Companion.LightGray
////
////class DashboardActivity : ComponentActivity() {
////    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////        enableEdgeToEdge()
////        setContent {
////            DashboardBody()
////        }
////    }
////}
////
////@Composable
////fun DashboardBody() {
////    Scaffold(
////        topBar = {
////            Column(
////                modifier = Modifier
////                    .fillMaxWidth()
////                    .background(LightBlue)
////                    .padding(top = 40.dp) // space above
////            ) {
////                Row(
////                    modifier = Modifier
////                        .fillMaxWidth()
////                        .padding(vertical = 8.dp, horizontal = 16.dp),
////                    verticalAlignment = Alignment.CenterVertically
////                ) {
////                    Image(
////                        painter = painterResource(R.drawable.parkbogum),
////                        contentDescription = null,
////                        modifier = Modifier
////                            .size(50.dp)
////                            .clip(CircleShape),
////                        contentScale = ContentScale.Crop
////                    )
////
////                    Spacer(modifier = Modifier.width(16.dp))
////
////                    Text(
////                        "Hello! Park Bo Gum",
////                        modifier = Modifier.weight(1f),
////                        style = TextStyle(
////                            color = White,
////                            fontSize = 16.sp,
////                            fontWeight = FontWeight.Bold
////                        )
////                    )
////
////                    Spacer(modifier = Modifier.width(16.dp))
////
////                    Icon(
////                        painter = painterResource(R.drawable.baseline_notifications_active_24),
////                        contentDescription = null,
////                        modifier = Modifier
////                            .size(30.dp)
////                            .clickable { /* Handle notification click */ },
////                        tint = Yellow
////                    )
////                }
////            }
////        },
////        bottomBar = {
////            Row(
////                modifier = Modifier
////                    .fillMaxWidth()
////                    .height(75.dp)
////                    .background(Color(0xFF212842)),
////                horizontalArrangement = Arrangement.SpaceEvenly,
////                verticalAlignment = Alignment.CenterVertically
////            ) {
////                BottomNavItem("Home", R.drawable.baseline_home_24) { }
////                BottomNavItem("Listing", R.drawable.baseline_business_24) { }
////                BottomNavItem("Messages", R.drawable.baseline_message_24) { }
////                BottomNavItem("Profile", R.drawable.baseline_person_24) { }
////            }
////        }
////    ) { padding ->
////        LazyColumn(
////            modifier = Modifier
////                .fillMaxSize()
////                .padding(padding)
////                .background(LightBlue)
////        ) {
////            item { Spacer(modifier = Modifier.height(16.dp)) }
////
////            // Search Card
////            item {
////                Card(
////                    modifier = Modifier
////                        .fillMaxWidth()
////                        .height(50.dp)
////                        .padding(horizontal = 16.dp)
////                        .clickable { },
////                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0E7D5))
////                ) {
////                    Row(
////                        modifier = Modifier
////                            .fillMaxSize()
////                            .padding(horizontal = 12.dp),
////                        verticalAlignment = Alignment.CenterVertically
////                    ) {
////                        Icon(
////                            painter = painterResource(R.drawable.baseline_search_24),
////                            contentDescription = null,
////                            modifier = Modifier.size(24.dp)
////                        )
////                        Spacer(modifier = Modifier.width(10.dp))
////                        Text(
////                            "Search Place, Apartment, Room",
////                            modifier = Modifier.weight(1f),
////                            color = Color(0xFF5C4433),
////                            fontSize = 15.sp
////                        )
////                    }
////                }
////            }
////
////            item { Spacer(modifier = Modifier.height(16.dp)) }
////
////            // Horizontal scroll for Room / Apartment / Location
////            item {
////                LazyRow(
////                    modifier = Modifier
////                        .fillMaxWidth()
////                        .padding(horizontal = 16.dp),
////                    horizontalArrangement = Arrangement.spacedBy(32.dp)
////                ) {
////                    item { RoomItem("Room", R.drawable.baseline_home_24) { } }
////                    item { RoomItem("Apartment", R.drawable.baseline_business_24) { } }
////                    item { RoomItem("Location", R.drawable.baseline_location_on_24) { } }
////                }
////            }
////
////            item { Spacer(modifier = Modifier.height(16.dp)) }
////
////            // Post Card
////            item {
////                Card(
////                    modifier = Modifier
////                        .fillMaxWidth()
////                        .height(120.dp)
////                        .padding(horizontal = 16.dp)
////                        .clickable { },
////                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE7DCCD))
////                ) {
////                    Row(
////                        modifier = Modifier
////                            .fillMaxSize()
////                            .padding(horizontal = 12.dp),
////                        verticalAlignment = Alignment.CenterVertically
////                    ) {
////                        Text(
////                            "Post your property here.",
////                            style = TextStyle(
////                                fontSize = 20.sp,
////                                fontWeight = FontWeight.Bold,
////                                color = DarkBlue
////                            )
////                        )
////                        Spacer(modifier = Modifier.width(55.dp))
////                        Icon(
////                            painter = painterResource(R.drawable.baseline_add_circle_24),
////                            contentDescription = null,
////                            modifier = Modifier.size(55.dp),
////                            tint = DarkBlue
////                        )
////                    }
////                }
////            }
////
////            item { Spacer(modifier = Modifier.height(16.dp)) }
////
////            // Feature List Title
////            item {
////                Row(
////                    modifier = Modifier
////                        .fillMaxWidth()
////                        .padding(horizontal = 16.dp),
////                    verticalAlignment = Alignment.CenterVertically
////                ) {
////                    Text(
////                        "Feature listing",
////                        modifier = Modifier.weight(5f),
////                        style = TextStyle(
////                            fontSize = 16.sp,
////                            color = White,
////                            fontWeight = FontWeight.Bold
////                        )
////                    )
////                    Text(
////                        "See All",
////                        modifier = Modifier
////                            .weight(1f)
////                            .clickable { },
////                        style = TextStyle(
////                            fontSize = 16.sp,
////                            color = Blue,
////                            fontWeight = FontWeight.Bold
////                        )
////                    )
////                }
////            }
////
////            item { Spacer(modifier = Modifier.height(16.dp)) }
////
////            // Horizontal scroll for feature images with price
////            item {
////                LazyRow(
////                    modifier = Modifier.fillMaxWidth(),
////                    horizontalArrangement = Arrangement.spacedBy(12.dp)
////                ) {
////                    item {
////                        Column(
////                            horizontalAlignment = Alignment.CenterHorizontally,
////                            modifier = Modifier.clickable { }
////                        ) {
////                            Image(
////                                painter = painterResource(R.drawable.apartment),
////                                contentDescription = null,
////                                contentScale = ContentScale.Crop,
////                                modifier = Modifier
////                                    .size(300.dp)
////                                    .clip(RoundedCornerShape(20.dp))
////                            )
////                            Spacer(modifier = Modifier.height(8.dp))
////                            Text(
////                                text = "Rs 25,000",
////                                color = White,
////                                fontSize = 16.sp,
////                                fontWeight = FontWeight.Bold
////                            )
////                            Spacer(modifier = Modifier.height(4.dp))
////                            // 👇 First line with ellipses
////                            Text(
////                                text = "Spacious apartment with balcony and great view...",
////                                color = White,
////                                fontSize = 14.sp
////                            )
////                        }
////                    }
////                    item {
////                        Column(
////                            horizontalAlignment = Alignment.CenterHorizontally,
////                            modifier = Modifier.clickable { }
////                        ) {
////                            Image(
////                                painter = painterResource(R.drawable.room),
////                                contentDescription = null,
////                                contentScale = ContentScale.Crop,
////                                modifier = Modifier
////                                    .size(300.dp)
////                                    .clip(RoundedCornerShape(20.dp))
////                            )
////                            Spacer(modifier = Modifier.height(8.dp))
////                            Text(
////                                text = "Rs 15,000",
////                                color = White,
////                                fontSize = 16.sp,
////                                fontWeight = FontWeight.Bold
////                            )
////                        }
////                    }
////                }
////            }
////        }
////    }
////}
////
////// Reusable item for Room/Apartment/Location
////@Composable
////fun RoomItem(label: String, iconRes: Int, onClick: () -> Unit) {
////    Column(
////        horizontalAlignment = Alignment.CenterHorizontally,
////        modifier = Modifier
////            .width(100.dp)
////            .padding(8.dp)
////            .clickable { onClick() }
////    ) {
////        Icon(
////            painter = painterResource(iconRes),
////            contentDescription = null,
////            modifier = Modifier.size(30.dp),
////            tint = White
////        )
////        Text(label, color = White)
////    }
////}
////
////// Reusable Bottom Navigation Item
////@Composable
////fun BottomNavItem(label: String, iconRes: Int, onClick: () -> Unit) {
////    Column(
////        horizontalAlignment = Alignment.CenterHorizontally,
////        modifier = Modifier.clickable { onClick() }
////    ) {
////        Icon(
////            painter = painterResource(iconRes),
////            contentDescription = null,
////            modifier = Modifier.size(30.dp),
////            tint = White
////        )
////        Text(label, fontSize = 12.sp, color = White)
////    }
////}
////    @Preview(showBackground = true)
////    @Composable
////    fun DashboardPreview() {
////        DashboardBody()
////    }
//
//
//package com.example.roomsathi
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.LazyRow
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.blur
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.roomsathi.ui.theme.*
//
///* ---------------------------------------------------
//   Main Activity
//--------------------------------------------------- */
//class DashboardActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        // Enables edge-to-edge layout (status bar transparent)
//        enableEdgeToEdge()
//
//        setContent {
//            DashboardBody()
//        }
//    }
//}
//
///* ---------------------------------------------------
//   Glassmorphism Surface (Reusable)
//--------------------------------------------------- */
//@Composable
//fun GlassSurface(
//    modifier: Modifier = Modifier,
//    content: @Composable () -> Unit
//) {
//    val shape = RoundedCornerShape(20.dp)
//
//    Box(
//        modifier = modifier
//            .clip(shape) // Round corners
//            .border( // Light border for glass look
//                1.dp,
//                Color.White.copy(alpha = 0.2f),
//                shape
//            )
//    ) {
//        // Blurred background layer
//        Box(
//            modifier = Modifier
//                .matchParentSize()
//                .background(Color.White.copy(alpha = 0.12f))
//                .blur(25.dp)
//        )
//
//        // Content layer
//        Box(modifier = Modifier.padding(16.dp)) {
//            content()
//        }
//    }
//}
//
///* ---------------------------------------------------
//   Main Dashboard Layout
//--------------------------------------------------- */
//@Composable
//fun DashboardBody() {
//
//    // Background container
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(LightBlue)
//    ) {
//        Scaffold(
//            containerColor = Color.Transparent, // Important for glass UI
//            topBar = { DashboardTopBar() },
//            bottomBar = { DashboardBottomBar() }
//        ) { padding ->
//            DashboardContent(padding)
//        }
//    }
//}
//
///* ---------------------------------------------------
//   Top Bar (Profile + Greeting)
//--------------------------------------------------- */
//@Composable
//fun DashboardTopBar() {
//    GlassSurface(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp, vertical = 40.dp)
//    ) {
//        Row(
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            // Profile Image
//            Image(
//                painter = painterResource(R.drawable.parkbogum),
//                contentDescription = null,
//                modifier = Modifier
//                    .size(48.dp)
//                    .clip(CircleShape),
//                contentScale = ContentScale.Crop
//            )
//
//            Spacer(modifier = Modifier.width(12.dp))
//
//            // Greeting Text
//            Text(
//                text = "Hello! Park Bo Gum",
//                modifier = Modifier.weight(1f),
//                color = Color.White,
//                fontSize = 16.sp,
//                fontWeight = FontWeight.Bold
//            )
//
//            // Notification Icon
//            Icon(
//                painter = painterResource(R.drawable.baseline_notifications_active_24),
//                contentDescription = null,
//                tint = Yellow,
//                modifier = Modifier.size(26.dp)
//            )
//        }
//    }
//}
//
///* ---------------------------------------------------
//   Dashboard Scrollable Content
//--------------------------------------------------- */
//@Composable
//fun DashboardContent(padding: PaddingValues) {
//
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(padding),
//        contentPadding = PaddingValues(bottom = 100.dp)
//    ) {
//
//        item { Spacer(modifier = Modifier.height(16.dp)) }
//
//        /* -------- Search Bar -------- */
//        item {
//            GlassSurface(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(50.dp)
//                    .padding(horizontal = 16.dp)
//            ) {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//                    Icon(
//                        painter = painterResource(R.drawable.baseline_search_24),
//                        contentDescription = null,
//                        tint = Color.White
//                    )
//                    Spacer(modifier = Modifier.width(10.dp))
//                    Text(
//                        text = "Search Place, Apartment, Room",
//                        color = Color.White.copy(alpha = 0.7f),
//                        fontSize = 14.sp
//                    )
//                }
//            }
//        }
//
//        item { Spacer(modifier = Modifier.height(20.dp)) }
//
//        /* -------- Category Section -------- */
//        item {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 32.dp),   // left & right space
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                CategoryItem("Room", R.drawable.baseline_home_24)
//                CategoryItem("Apartment", R.drawable.baseline_business_24)
//                CategoryItem("Location", R.drawable.baseline_location_on_24)
//            }
//        }
//
//
//        item { Spacer(modifier = Modifier.height(20.dp)) }
//
//        /* -------- Post Property Card -------- */
//        item {
//            GlassSurface(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(120.dp)
//                    .padding(horizontal = 16.dp)
//            ) {
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ) {
//                    Text(
//                        text = "Post your property here.",
//                        color = Color.White,
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//
//                    // GAP + CIRCULAR BUTTON
//                    Box(
//                        modifier = Modifier
//                            .size(46.dp)              // button size
//                            .clip(CircleShape)
//                            .background(Color.White)  // white background
//                            .clickable { },
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Icon(
//                            painter = painterResource(R.drawable.baseline_add_24),
//                            contentDescription = null,
//                            tint = DarkBlue,          // contrast color
//                            modifier = Modifier.size(26.dp)
//                        )
//                    }
//                }
//
//            }
//        }
//
//        item { Spacer(modifier = Modifier.height(20.dp)) }
//
//        /* -------- Feature Listing Title -------- */
//        item {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp),
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Text(
//                    text = "Feature listing",
//                    color = Color.White,
//                    fontSize = 18.sp,
//                    fontWeight = FontWeight.Bold
//                )
//                Text(
//                    text = "See all",
//                    color = Blue,
//                    fontWeight = FontWeight.Bold
//                )
//            }
//        }
//
//        item { Spacer(modifier = Modifier.height(16.dp)) }
//
//        /* -------- Feature Cards -------- */
//        item {
//            LazyRow(
//                contentPadding = PaddingValues(horizontal = 16.dp),
//                horizontalArrangement = Arrangement.spacedBy(12.dp)
//            ) {
//                item { FeatureCard(R.drawable.apartment, "Rs 25,000") }
//                item { FeatureCard(R.drawable.room, "Rs 15,000") }
//            }
//        }
//    }
//}
//
///* ---------------------------------------------------
//   Category Item (Room / Apartment / Location)
//--------------------------------------------------- */
//@Composable
//fun CategoryItem(title: String, icon: Int) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        modifier = Modifier.clickable { }
//    ) {
//        Icon(
//            painter = painterResource(icon),
//            contentDescription = null,
//            tint = Color.White,
//            modifier = Modifier.size(30.dp)
//        )
//        Spacer(modifier = Modifier.height(6.dp))
//        Text(text = title, color = Color.White)
//    }
//}
//
///* ---------------------------------------------------
//   Feature Card
//--------------------------------------------------- */
//@Composable
//fun FeatureCard(image: Int, price: String) {
//    Column(modifier = Modifier.width(280.dp)) {
//        Image(
//            painter = painterResource(image),
//            contentDescription = null,
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .height(180.dp)
//                .clip(RoundedCornerShape(20.dp))
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//        Text(
//            text = price,
//            color = Color.White,
//            fontWeight = FontWeight.Bold
//        )
//    }
//}
//
///* ---------------------------------------------------
//   Bottom Navigation Bar
//--------------------------------------------------- */
//@Composable
//fun DashboardBottomBar() {
//    GlassSurface(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(80.dp)
//            .padding(horizontal = 16.dp, vertical = 10.dp)
//    ) {
//        Row(
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            BottomNavItem("Home", R.drawable.baseline_home_24)
//            BottomNavItem("Listing", R.drawable.baseline_business_24)
//            BottomNavItem("Messages", R.drawable.baseline_message_24)
//            BottomNavItem("Profile", R.drawable.baseline_person_24)
//        }
//    }
//}
//
///* ---------------------------------------------------
//   Bottom Navigation Item
//--------------------------------------------------- */
//@Composable
//fun BottomNavItem(label: String, icon: Int) {
//    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//        Icon(
//            painter = painterResource(icon),
//            contentDescription = null,
//            tint = Color.White,
//            modifier = Modifier.size(26.dp)
//        )
//        Text(text = label, color = Color.White, fontSize = 12.sp)
//    }
//}
//
///* ---------------------------------------------------
//   Preview
//--------------------------------------------------- */
//@Preview(showBackground = true)
//@Composable
//fun DashboardPreview() {
//    DashboardBody()
//}

package com.example.roomsathi

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
import androidx.compose.runtime.Composable
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

//Glass surface
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

//Main Layout
@Composable
fun DashboardBody() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue)
    ) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = { DashboardTopBar() },
            bottomBar = { DashboardBottomBar() }
        ) { padding ->
            DashboardContent(padding)
        }
    }
}

//Top Bar
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

//Content
@Composable
fun DashboardContent(padding: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentPadding = PaddingValues(bottom = 120.dp)
    ) {

        item { Spacer(modifier = Modifier.height(16.dp)) }

//  Search

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

//        Categories
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CategoryItem("Room", R.drawable.baseline_home_24)
                CategoryItem("Apartment", R.drawable.baseline_business_24)
                CategoryItem("Location", R.drawable.baseline_location_on_24)
            }
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }

//       Post Property
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

//         Feature title
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

//  Feature cards
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

//Category Item
@Composable
fun CategoryItem(title: String, icon: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(text = title, color = Color.White)
    }
}


//Feature Card

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
        Text(text = price, color = Color.White, fontWeight = FontWeight.Bold)
    }
}

//Bottom Navigation Bar

@Composable
fun DashboardBottomBar() {
    GlassSurface(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp) // increased height
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.Bottom // align items at the bottom
        ) {
            BottomNavItem("Home", R.drawable.baseline_home_24)
            BottomNavItem("Listing", R.drawable.baseline_business_24)
            BottomNavItem("Messages", R.drawable.baseline_message_24)
            BottomNavItem("Profile", R.drawable.baseline_person_24)
        }
    }
}

@Composable
fun BottomNavItem(label: String, icon: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center, // center icon and text
        modifier = Modifier
            .clickable { }
            .padding(vertical = 4.dp)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            color = Color.White,
            fontSize = 12.sp
        )
    }
}


/* ---------------------------------------------------
   Preview
--------------------------------------------------- */
@Preview(showBackground = true)
@Composable
fun DashboardPreview() {
    DashboardBody()
}
