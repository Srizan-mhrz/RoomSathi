//package com.example.roomsathi.view
//
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.activity.enableEdgeToEdge
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.BasicTextField
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.*
//import androidx.compose.material.icons.outlined.DoorFront
//import androidx.compose.material.icons.outlined.LocationOn
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.shadow
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.SolidColor
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import com.example.roomsathi.ui.theme.RoomSathiTheme
//
//class SearchActivity1 : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContent {
//            RoomSathiTheme(darkTheme = true) {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = Color(0xFF1E1E1E) // Dark background
//                ) {
//                    SearchScreen()
//                }
//            }
//        }
//    }
//}
//
//@OptIn(ExperimentalLayoutApi::class)
//@Composable
//fun SearchScreen() {
//    var destination by remember { mutableStateOf("Bali, Indonesia") }
//    var rooms by remember { mutableStateOf("1") }
//    var minPrice by remember { mutableStateOf("100") }
//    var maxPrice by remember { mutableStateOf("400") }
//    var selectedRating by remember { mutableIntStateOf(4) }
//    var selectedAmenities by remember { mutableStateOf(setOf("Wi-Fi", "Swimming Pool", "Fitness Center")) }
//
//    val darkCardColor = Color(0xFF2D2D2D)
//
//    Scaffold(
//        containerColor = Color(0xFF1E1E1E),
//        bottomBar = {
//            Button(
//                onClick = { /* TODO: Search */ },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//                    .height(56.dp),
//                shape = RoundedCornerShape(16.dp),
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color.White,
//                    contentColor = Color.Black
//                )
//            ) {
//                Text(text = "Search", fontSize = 18.sp, fontWeight = FontWeight.Bold)
//            }
//        }
//    ) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(innerPadding)
//                .padding(horizontal = 16.dp)
//        ) {
//            // Header
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 16.dp),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = "Search Filter",
//                    style = MaterialTheme.typography.titleLarge,
//                    color = Color.White,
//                    fontWeight = FontWeight.Bold
//                )
//                IconButton(onClick = { /* TODO: Close */ }) {
//                    Icon(Icons.Default.Close, contentDescription = "Close", tint = Color.White)
//                }
//            }
//
//            LazyColumn(
//                modifier = Modifier.fillMaxSize(),
//                verticalArrangement = Arrangement.spacedBy(20.dp)
//            ) {
//                item {
//                    FilterSection(label = "Destination") {
//                        SearchInputField(
//                            value = destination,
//                            onValueChange = { destination = it },
//                            icon = Icons.Outlined.LocationOn,
//                            backgroundColor = darkCardColor
//                        )
//                    }
//                }
//
//                item {
//                    FilterSection(label = "Room") {
//                        SearchInputField(
//                            value = rooms,
//                            onValueChange = { rooms = it },
//                            icon = Icons.Outlined.DoorFront,
//                            backgroundColor = darkCardColor
//                        )
//                    }
//                }
//
//                item {
//                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
//                        FilterSection(label = "Price Minimum", modifier = Modifier.weight(1f)) {
//                            SearchInputField(
//                                value = minPrice,
//                                onValueChange = { minPrice = it },
//                                prefix = "$ ",
//                                backgroundColor = darkCardColor
//                            )
//                        }
//                        FilterSection(label = "Price Maximum", modifier = Modifier.weight(1f)) {
//                            SearchInputField(
//                                value = maxPrice,
//                                onValueChange = { maxPrice = it },
//                                prefix = "$ ",
//                                backgroundColor = darkCardColor
//                            )
//                        }
//                    }
//                }
//
//                item {
//                    FilterSection(label = "Rating") {
//                        Row(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.spacedBy(8.dp)
//                        ) {
//                            (1..5).forEach { rating ->
//                                RatingButton(
//                                    rating = rating,
//                                    isSelected = rating == selectedRating,
//                                    onClick = { selectedRating = rating },
//                                    modifier = Modifier.weight(1f)
//                                )
//                            }
//                        }
//                    }
//                }
//
//                item {
//                    FilterSection(label = "History") {
//                        FlowRow(
//                            modifier = Modifier.fillMaxWidth(),
//                            horizontalArrangement = Arrangement.spacedBy(8.dp),
//                            verticalArrangement = Arrangement.spacedBy(8.dp)
//                        ) {
//                            val amenities = listOf(
//                                "Air Conditioner" to Icons.Default.Air,
//                                "Wi-Fi" to Icons.Default.Wifi,
//                                "Swimming Pool" to Icons.Default.Pool,
//                                "Restaurant" to Icons.Default.Restaurant,
//                                "Minibar" to Icons.Default.LocalBar,
//                                "Fitness Center" to Icons.Default.FitnessCenter
//                            )
//                            amenities.forEach { (name, icon) ->
//                                AmenityChip(
//                                    name = name,
//                                    icon = icon,
//                                    isSelected = name in selectedAmenities,
//                                    onClick = {
//                                        selectedAmenities = if (name in selectedAmenities) {
//                                            selectedAmenities - name
//                                        } else {
//                                            selectedAmenities + name
//                                        }
//                                    }
//                                )
//                            }
//                        }
//                    }
//                }
//
//                item { Spacer(modifier = Modifier.height(16.dp)) }
//            }
//        }
//    }
//}
//
//@Composable
//fun FilterSection(
//    label: String,
//    modifier: Modifier = Modifier,
//    content: @Composable () -> Unit
//) {
//    Column(modifier = modifier) {
//        Text(
//            text = label,
//            color = Color.White,
//            fontSize = 14.sp,
//            fontWeight = FontWeight.Medium,
//            modifier = Modifier.padding(bottom = 8.dp)
//        )
//        content()
//    }
//}
//
//@Composable
//fun SearchInputField(
//    value: String,
//    onValueChange: (String) -> Unit,
//    icon: ImageVector? = null,
//    prefix: String? = null,
//    backgroundColor: Color
//) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(56.dp)
//            .background(backgroundColor, RoundedCornerShape(12.dp))
//            .padding(horizontal = 16.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        if (icon != null) {
//            Icon(icon, contentDescription = null, tint = Color.White, modifier = Modifier.size(20.dp))
//            Spacer(modifier = Modifier.width(12.dp))
//        }
//        if (prefix != null) {
//            Text(text = prefix, color = Color.White, fontSize = 16.sp)
//        }
//        BasicTextField(
//            value = value,
//            onValueChange = onValueChange,
//            textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
//            modifier = Modifier.weight(1f),
//            singleLine = true,
//            cursorBrush = SolidColor(Color.White)
//        )
//    }
//}
//
//@Composable
//fun RatingButton(
//    rating: Int,
//    isSelected: Boolean,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    val backgroundColor = if (isSelected) Color.White else Color(0xFF2D2D2D)
//    val contentColor = if (isSelected) Color.Black else Color.Gray
//
//    Box(
//        modifier = modifier
//            .height(44.dp)
//            .shadow(if (isSelected) 8.dp else 0.dp, RoundedCornerShape(8.dp))
//            .background(backgroundColor, RoundedCornerShape(8.dp))
//            .clickable { onClick() },
//        contentAlignment = Alignment.Center
//    ) {
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            Icon(
//                imageVector = if (isSelected) Icons.Default.Star else Icons.Default.StarBorder,
//                contentDescription = null,
//                tint = contentColor,
//                modifier = Modifier.size(16.dp)
//            )
//            Spacer(modifier = Modifier.width(4.dp))
//            Text(text = rating.toString(), color = contentColor, fontSize = 14.sp, fontWeight = FontWeight.Bold)
//        }
//    }
//}
//
//@Composable
//fun AmenityChip(
//    name: String,
//    icon: ImageVector,
//    isSelected: Boolean,
//    onClick: () -> Unit
//) {
//    val backgroundColor = if (isSelected) Color.White else Color.Transparent
//    val contentColor = if (isSelected) Color.Black else Color.Gray
//    val borderColor = if (isSelected) Color.White else Color(0xFF444444)
//
//    Row(
//        modifier = Modifier
//            .border(1.dp, borderColor, RoundedCornerShape(12.dp))
//            .shadow(if (isSelected) 4.dp else 0.dp, RoundedCornerShape(12.dp))
//            .background(backgroundColor, RoundedCornerShape(12.dp))
//            .clickable { onClick() }
//            .padding(horizontal = 16.dp, vertical = 10.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Icon(icon, contentDescription = null, tint = contentColor, modifier = Modifier.size(18.dp))
//        Spacer(modifier = Modifier.width(8.dp))
//        Text(text = name, color = contentColor, fontSize = 14.sp, fontWeight = FontWeight.Medium)
//    }
//}
//
//@Preview
//@Composable
//fun SearchScreenPreview() {
//    RoomSathiTheme(darkTheme = true) {
//        Surface(color = Color(0xFF1E1E1E)) {
//            SearchScreen()
//        }
//    }
//}
