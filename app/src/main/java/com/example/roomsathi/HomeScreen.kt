////package com.example.roomsathi
////
////import android.os.Bundle
////import android.widget.Toast
////import androidx.activity.ComponentActivity
////import androidx.activity.compose.setContent
////import androidx.compose.foundation.background
////import androidx.compose.foundation.layout.*
////import androidx.compose.foundation.lazy.LazyColumn
////import androidx.compose.material.icons.Icons
////import androidx.compose.material.icons.filled.*
////import androidx.compose.material3.*
////import androidx.compose.runtime.*
//////import androidx.compose.runtime.livedata.observeAsState
////import androidx.compose.ui.Alignment
////import androidx.compose.ui.Modifier
////import androidx.compose.ui.graphics.Color
////import androidx.compose.ui.platform.LocalContext
////import androidx.compose.ui.tooling.preview.Preview
////import androidx.compose.ui.unit.dp
////import androidx.navigation.NavController
////import androidx.navigation.compose.*
////
////
////
////
////class MainActivity : ComponentActivity() {
////    override fun onCreate(savedInstanceState: Bundle?) {
////        super.onCreate(savedInstanceState)
////        setContent {
////            HomepageApp()
////        }
////    }
////}
////
//////APP ROOT
////@Composable
////fun HomepageApp() {
////    val navController = rememberNavController()
////
////    Scaffold(
////        bottomBar = {
////            BottomBar(navController)
////        }
////    ) { padding ->
////        NavHost(
////            navController = navController,
////            startDestination = "home",
////            modifier = Modifier.padding(padding)
////        ) {
////            composable("home") { HomeScreen() }
////            composable("listing") { ListingScreen() }
////            composable("messages") { MessageScreen() }
////            composable("profile") { ProfileScreenBody() }
////        }
////    }
////}
////
/////* ---------------- BOTTOM BAR ---------------- */
////
////@Composable
////fun BottomBar(navController: NavController) {
////    BottomAppBar {
////        IconButton(onClick = { navController.navigate("home") }) {
////            Icon(Icons.Default.Home, null)
////        }
////        IconButton(onClick = { navController.navigate("listing") }) {
////            Icon(Icons.Default.List, null)
////        }
////        IconButton(onClick = { navController.navigate("messages") }) {
////            Icon(Icons.Default.Email, null)
////        }
////        IconButton(onClick = { navController.navigate("profile") }) {
////            Icon(Icons.Default.Person, null)
////        }
////    }
////}
////
/////* ---------------- HOME SCREEN ---------------- */
////
////@Composable
////fun HomeScreen() {
////    val context = LocalContext.current
////
////    LazyColumn(
////        modifier = Modifier
////            .fillMaxSize()
////            .background(Color.White)
////            .padding(16.dp)
////    ) {
////        items(5) { index ->
////            Card(
////                modifier = Modifier
////                    .fillMaxWidth()
////                    .padding(vertical = 8.dp)
////            ) {
////                Column(modifier = Modifier.padding(16.dp)) {
////                    Text("Product $index")
////                    Text("Rs ${(index + 1) * 5000}")
////
////                    Row {
////                        IconButton(onClick = {
////                            Toast.makeText(context, "Edit clicked", Toast.LENGTH_SHORT).show()
////                        }) {
////                            Icon(Icons.Default.Edit, null)
////                        }
////                        IconButton(onClick = {
////                            Toast.makeText(context, "Delete clicked", Toast.LENGTH_SHORT).show()
////                        }) {
////                            Icon(Icons.Default.Delete, null)
////                        }
////                    }
////                }
////            }
////        }
////    }
////}
////
/////* ---------------- OTHER SCREENS ---------------- */
////
////@Composable
////fun ListingScreen() {
////    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
////        Text("Listing Screen")
////    }
////}
////
////@Composable
////fun MessageScreen() {
////    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
////        Text("Messages Screen")
////    }
////}
////
////
////@Preview(showBackground = true)
////@Composable
////fun GreetingPreview() {
////
////}
//
//
//package com.example.roomsathi
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.compose.material3.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.navigation.compose.*
//
//class MainActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            HomepageApp()
//        }
//    }
//}
//
//@Composable
//fun HomepageApp() {
//    val navController = rememberNavController()
//
//    NavHost(
//        navController = navController,
//        startDestination = "dashboard"
//    ) {
//        composable("dashboard") {
//            DashboardBody(navController)
//        }
//        composable("listing") {
//            ListingScreen()
//        }
//        composable("messages") {
//            MessageScreen()
//        }
//        composable("profile") {
//            ProfileScreenBody()
//        }
//    }
//}
