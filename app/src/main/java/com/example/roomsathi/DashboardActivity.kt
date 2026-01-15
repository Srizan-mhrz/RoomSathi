//package com.example.roomsathi
//
////import android.os.Bundle
////import androidx.activity.ComponentActivity
////import androidx.activity.compose.setContent
////import androidx.activity.enableEdgeToEdge
////import androidx.compose.foundation.layout.fillMaxSize
////import androidx.compose.foundation.layout.padding
////import androidx.compose.material3.Scaffold
////import androidx.compose.material3.Text
////import androidx.compose.runtime.Composable
////import androidx.compose.ui.Modifier
////import androidx.compose.ui.tooling.preview.Preview
////import com.example.roomsathi.ui.theme.RoomSathiTheme
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
////fun DashboardBody(){
////
////}
////
////
////@Preview(showBackground = true)
////@Composable
////fun DashboardPreview() {
////    DashboardBody()
////}
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.tooling.preview.Preview
//
//import androidx.compose.foundation.Image
////import Cant.commit.project.R
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Color.Companion.LightGray
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.roomsathi.ui.theme.Blue
//import com.example.roomsathi.ui.theme.DarkBlue
//import com.example.roomsathi.ui.theme.DarkBrown
//import com.example.roomsathi.ui.theme.LightBlue
////import com.example.roomsathi.ui.theme.Green
//import com.example.roomsathi.ui.theme.White
//import com.example.roomsathi.ui.theme.Yellow
//
//
//class HomepageActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            DashboardBody()
//        }
//    }
//}
//
//@Composable
//fun DashboardBody() {
//    Scaffold { padding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(padding)
//                .background(LightBlue)
////                .background(Coffee)
//        ) {
//            //Top back button
////            Row(
////                modifier = Modifier
////                    .fillMaxWidth()
////                    .padding(horizontal = 16.dp, vertical = 8.dp),
////                verticalAlignment = Alignment.CenterVertically,
////                horizontalArrangement = Arrangement.SpaceBetween
////            ) {
////                Icon( painter = painterResource(R.drawable.baseline_arrow_back_ios_24),
////                    contentDescription = null,
////                    modifier = Modifier.size(25.dp),
////                    tint = White
////                )
////            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            //This of is for profile picture
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 8.dp, horizontal = 16.dp),
//                verticalAlignment = Alignment.CenterVertically,
////                horizontalArrangement = Arrangement.SpaceAround
//            ) {
//                Image(
//                    painter = painterResource(R.drawable.parkbogum),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .size(70.dp)
//                        .clip(CircleShape),
//                    contentScale = ContentScale.Crop
//                )
//
//                Spacer(modifier = Modifier.width(16.dp))
//                    Text("Hello! Park Bo Gum",
//                        modifier = Modifier.weight(14f),
//                        style = TextStyle(
//                            color = White,
//                            fontSize = 16.sp,
//                            fontWeight = FontWeight.Bold
//                        )
//                    )
//                Spacer(modifier = Modifier.width(16.dp))
//
//                //putting notification icon
//                Icon(painter = painterResource(R.drawable.baseline_notifications_active_24),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .size(30.dp)
//                        .weight(7f),
//                    tint = Yellow
//                )
//            }
////            Spacer(modifier = Modifier.width(30.dp))
//
//
//            //Card starts here
//            Card (
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(50.dp)
//                    .padding(horizontal = 16.dp)
//                    .border(2.dp, color= LightGray,RoundedCornerShape(15.dp)),
//                colors = CardDefaults.cardColors(containerColor = Color(0xFFF0E7D5))
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(horizontal = 12.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Icon(painter = painterResource(R.drawable.baseline_search_24),
//                        contentDescription = null,
//                        modifier = Modifier.size(24.dp),
////                        tint = Color.LightGray
//                    )
//                    Spacer(modifier = Modifier.width(10.dp))
//
//                    Text("Search Place, Apartment, Room",
//                        modifier = Modifier.weight(1f),
//                        color = Color(0xFF5C4433),
//                        fontSize = 16.sp)
//
//                }
//            }
//            Spacer(modifier = Modifier.height(16.dp))
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//
//                // Room
//                Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                    Icon(
//                        painter = painterResource(R.drawable.baseline_home_24),
//                        contentDescription = null,
//                        modifier = Modifier.size(30.dp),
//                        tint = White
//                    )
//                    Text("Room", color = Color(0xFFFFFFFF))
//                }
//
//                // Apartment
//                Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                    Icon(
//                        painter = painterResource(R.drawable.baseline_business_24),
//                        contentDescription = null,
//                        modifier = Modifier.size(30.dp),
//                        tint = White
//                    )
//                    Text("Apartment",color = Color(0xFFFFFFFF))
//                }
//
//                // Location
//                Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                    Icon(
//                        painter = painterResource(R.drawable.baseline_location_on_24),
//                        contentDescription = null,
//                        modifier = Modifier.size(30.dp),
//                        tint = White
//                    )
//                    Text("Location",color = Color(0xFFFFFFFF))
//                }
//            }
//
//
//
//            Spacer(modifier = Modifier.height(16.dp))
//            //card for post
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(120.dp)
//                    .padding(horizontal = 16.dp)
//                    .border(2.dp, color = LightGray,
//                        RoundedCornerShape(15.dp)),
//                colors = CardDefaults.cardColors(containerColor = Color(0xFFE7DCCD))
//            ) {
//                Row(
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .padding(horizontal = 12.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text("Post your property here.",
//                        style = TextStyle(
//                        fontSize = 20.sp,
//                            fontWeight = FontWeight.Bold,
//                            color = DarkBlue
//
//                        )
//                    )
//                    Spacer(modifier = Modifier.width(55.dp))
//                    Icon(painter = painterResource(R.drawable.baseline_add_circle_24),
//                        contentDescription = null,
//                        modifier = Modifier.size(55.dp),
//                        tint = DarkBlue
//                    )
//                    Spacer(modifier = Modifier.width(16.dp))
//
//                }
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    "Feature listing",
//                    modifier = Modifier.weight(5f),
//                    style = TextStyle(
//                        fontSize = 16.sp,
//                        color = White,
//                        fontWeight = FontWeight.Bold
//                    )
//                )
//                Text("See All",
//                    modifier = Modifier.weight(1f),
//                    style = TextStyle(
//                    fontSize = 16.sp,
//                        color = Blue,
//                    fontWeight = FontWeight.Bold
//                ))
//            }
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            //Crad for photo post
//            Card(
//                modifier = Modifier.fillMaxWidth()
//                    .height(350.dp)
//                    .padding(horizontal = 16.dp),
//                colors = CardDefaults.cardColors(containerColor = Color(0xFF212842))
////                    .weight(3f)
//            ){
//                Row(
//                    modifier = Modifier.fillMaxWidth()
//                        .clip(RoundedCornerShape(10.dp)),
////                    verticalAlignment = Alignment.CenterVertically
//                ){
//                    Image(painter =  painterResource(R.drawable.apartment),
//                        contentDescription = null,
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier .weight(2f)
//                            .fillMaxHeight()
//                            .clip(RoundedCornerShape(20.dp)),
//
//                    )
//                    Spacer(modifier = Modifier.width(8.dp))
//                    Image(painter =  painterResource(R.drawable.room),
//                        contentDescription = null,
//                        contentScale = ContentScale.Crop,
//                        modifier = Modifier .weight(1f)
//                            .fillMaxHeight()
//                            .clip(RoundedCornerShape(20.dp)),
//                    )
//                }
//            }
//            Spacer(modifier =Modifier.height(16.dp))
//            //for home card
//            Card(
//                modifier= Modifier
//                    .fillMaxWidth()
//                    .height(75.dp),
////                    .padding(horizontal = 16.dp)
////                    .border(2.dp, color= LightGray,),
//                colors = CardDefaults.cardColors(containerColor = Color(0xFF212842))
//            ){
//                Row(modifier = Modifier
//                    .fillMaxSize()
//                    .padding(horizontal = 20.dp),
//                    verticalAlignment = Alignment.CenterVertically,
//                    horizontalArrangement = Arrangement.SpaceBetween
//                ){
//                    Column (horizontalAlignment = Alignment.CenterHorizontally){
//                        Icon(
//                            painter = painterResource(R.drawable.baseline_home_24),
//                            contentDescription = null,
//                            modifier = Modifier.size(30.dp),
//                            tint = White
//                        )
//                        Text("Home", fontSize = 12.sp, color = White)
//                    }
//                    //listing
//                    Column (horizontalAlignment = Alignment.CenterHorizontally){
//                        Icon(
//                            painter = painterResource(R.drawable.baseline_business_24),
//                            contentDescription = null,
//                            modifier = Modifier.size(30.dp),
//                            tint = White
//                        )
//                        Text("listing", fontSize = 12.sp, color = White)
//                    }
//                    //message
//                    Column (horizontalAlignment = Alignment.CenterHorizontally){
//                        Icon(
//                            painter = painterResource(R.drawable.baseline_message_24),
//                            contentDescription = null,
//                            modifier = Modifier.size(30.dp),
//                            tint = White
//                        )
//                        Text("Messages", fontSize = 12.sp, color = White)
//                    }
//                    //profile
//                    Column (horizontalAlignment = Alignment.CenterHorizontally){
//                        Icon(
//                            painter = painterResource(R.drawable.baseline_person_24),
//                            contentDescription = null,
//                            modifier = Modifier.size(30.dp),
//                            tint = White
//                        )
//                        Text("Profile", fontSize = 12.sp, color = White)
//                    }
//
//
//
//                }
//
//            }
//
//
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun DashboardPreview() {
//    DashboardBody()
//}

//package com.example.roomsathi
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.material3.Card
//import androidx.compose.material3.CardDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.roomsathi.ui.theme.Blue
//import com.example.roomsathi.ui.theme.DarkBlue
//import com.example.roomsathi.ui.theme.LightBlue
//import com.example.roomsathi.ui.theme.White
//import com.example.roomsathi.ui.theme.Yellow
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.ui.graphics.Color.Companion.LightGray
//
//class DashboardActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            DashboardBody()
//        }
//    }
//}
//
//@Composable
//fun DashboardBody() {
//    Scaffold { padding ->
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(padding)
//                .background(LightBlue)
//        ) {
//
//            item { Spacer(modifier = Modifier.height(16.dp)) }
//
//            // Profile Row
//            item {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 8.dp, horizontal = 16.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Image(
//                        painter = painterResource(R.drawable.parkbogum),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .size(70.dp)
//                            .clip(CircleShape),
//                        contentScale = ContentScale.Crop
//                    )
//
//                    Spacer(modifier = Modifier.width(16.dp))
//
//                    Text(
//                        "Hello! Park Bo Gum",
//                        modifier = Modifier.weight(14f),
//                        style = TextStyle(
//                            color = White,
//                            fontSize = 16.sp,
//                            fontWeight = FontWeight.Bold
//                        )
//                    )
//                    Spacer(modifier = Modifier.width(16.dp))
//
//                    Icon(
//                        painter = painterResource(R.drawable.baseline_notifications_active_24),
//                        contentDescription = null,
//                        modifier = Modifier
//                            .size(30.dp)
//                            .weight(7f),
//                        tint = Yellow
//                    )
//                }
//            }
//
//            item { Spacer(modifier = Modifier.height(16.dp)) }
//
//            // Search Card
//            item {
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(50.dp)
//                        .padding(horizontal = 16.dp)
//                        .border(2.dp, color = LightGray, RoundedCornerShape(15.dp)),
//                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF0E7D5))
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(horizontal = 12.dp),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Icon(
//                            painter = painterResource(R.drawable.baseline_search_24),
//                            contentDescription = null,
//                            modifier = Modifier.size(24.dp)
//                        )
//                        Spacer(modifier = Modifier.width(10.dp))
//                        Text(
//                            "Search Place, Apartment, Room",
//                            modifier = Modifier.weight(1f),
//                            color = Color(0xFF5C4433),
//                            fontSize = 16.sp
//                        )
//                    }
//                }
//            }
//
//            item { Spacer(modifier = Modifier.height(16.dp)) }
//
//            // Room / Apartment / Location
//            item {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 16.dp),
//                    horizontalArrangement = Arrangement.SpaceBetween,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                        Icon(
//                            painter = painterResource(R.drawable.baseline_home_24),
//                            contentDescription = null,
//                            modifier = Modifier.size(30.dp),
//                            tint = White
//                        )
//                        Text("Room", color = White)
//                    }
//
//                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                        Icon(
//                            painter = painterResource(R.drawable.baseline_business_24),
//                            contentDescription = null,
//                            modifier = Modifier.size(30.dp),
//                            tint = White
//                        )
//                        Text("Apartment", color = White)
//                    }
//
//                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                        Icon(
//                            painter = painterResource(R.drawable.baseline_location_on_24),
//                            contentDescription = null,
//                            modifier = Modifier.size(30.dp),
//                            tint = White
//                        )
//                        Text("Location", color = White)
//                    }
//                }
//            }
//
//            item { Spacer(modifier = Modifier.height(16.dp)) }
//
//            // Post Card
//            item {
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(120.dp)
//                        .padding(horizontal = 16.dp)
//                        .border(2.dp, color = LightGray, RoundedCornerShape(15.dp)),
//                    colors = CardDefaults.cardColors(containerColor = Color(0xFFE7DCCD))
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(horizontal = 12.dp),
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text(
//                            "Post your property here.",
//                            style = TextStyle(
//                                fontSize = 20.sp,
//                                fontWeight = FontWeight.Bold,
//                                color = DarkBlue
//                            )
//                        )
//                        Spacer(modifier = Modifier.width(55.dp))
//                        Icon(
//                            painter = painterResource(R.drawable.baseline_add_circle_24),
//                            contentDescription = null,
//                            modifier = Modifier.size(55.dp),
//                            tint = DarkBlue
//                        )
//                        Spacer(modifier = Modifier.width(16.dp))
//                    }
//                }
//            }
//
//            item { Spacer(modifier = Modifier.height(16.dp)) }
//
//            // Feature List Title
//            item {
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 16.dp),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Text(
//                        "Feature listing",
//                        modifier = Modifier.weight(5f),
//                        style = TextStyle(
//                            fontSize = 16.sp,
//                            color = White,
//                            fontWeight = FontWeight.Bold
//                        )
//                    )
//                    Text(
//                        "See All",
//                        modifier = Modifier.weight(1f),
//                        style = TextStyle(
//                            fontSize = 16.sp,
//                            color = Blue,
//                            fontWeight = FontWeight.Bold
//                        )
//                    )
//                }
//            }
//
//            item { Spacer(modifier = Modifier.height(16.dp)) }
//
//            // Image Card
//            item {
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(350.dp)
//                        .padding(horizontal = 16.dp),
//                    colors = CardDefaults.cardColors(containerColor = Color(0xFF212842))
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .clip(RoundedCornerShape(10.dp)),
//                    ) {
//                        Image(
//                            painter = painterResource(R.drawable.apartment),
//                            contentDescription = null,
//                            contentScale = ContentScale.Crop,
//                            modifier = Modifier
//                                .weight(2f)
//                                .fillMaxHeight()
//                                .clip(RoundedCornerShape(20.dp)),
//                        )
//
//                        Spacer(modifier = Modifier.width(8.dp))
//
//                        Image(
//                            painter = painterResource(R.drawable.room),
//                            contentDescription = null,
//                            contentScale = ContentScale.Crop,
//                            modifier = Modifier
//                                .weight(1f)
//                                .fillMaxHeight()
//                                .clip(RoundedCornerShape(20.dp)),
//                        )
//                    }
//                }
//            }
//
//            item { Spacer(modifier = Modifier.height(16.dp)) }
//
//            // Bottom Navigation
//            item {
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(75.dp),
//                    colors = CardDefaults.cardColors(containerColor = Color(0xFF212842))
//                ) {
//                    Row(
//                        modifier = Modifier
//                            .fillMaxSize()
//                            .padding(horizontal = 20.dp),
//                        verticalAlignment = Alignment.CenterVertically,
//                        horizontalArrangement = Arrangement.SpaceBetween
//                    ) {
//                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                            Icon(
//                                painter = painterResource(R.drawable.baseline_home_24),
//                                contentDescription = null,
//                                modifier = Modifier.size(30.dp),
//                                tint = White
//                            )
//                            Text("Home", fontSize = 12.sp, color = White)
//                        }
//
//                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                            Icon(
//                                painter = painterResource(R.drawable.baseline_business_24),
//                                contentDescription = null,
//                                modifier = Modifier.size(30.dp),
//                                tint = White
//                            )
//                            Text("listing", fontSize = 12.sp, color = White)
//                        }
//
//                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                            Icon(
//                                painter = painterResource(R.drawable.baseline_message_24),
//                                contentDescription = null,
//                                modifier = Modifier.size(30.dp),
//                                tint = White
//                            )
//                            Text("Messages", fontSize = 12.sp, color = White)
//                        }
//
//                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                            Icon(
//                                painter = painterResource(R.drawable.baseline_person_24),
//                                contentDescription = null,
//                                modifier = Modifier.size(30.dp),
//                                tint = White
//                            )
//                            Text("Profile", fontSize = 12.sp, color = White)
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
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
