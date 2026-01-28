package com.example.roomsathi.view

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocationOn
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

// External Library for Image Loading
import coil.compose.AsyncImage

import com.example.roomsathi.model.PropertyModel
import com.example.roomsathi.R
import com.example.roomsathi.ui.theme.Yellow
import com.example.roomsathi.ui.theme.LightBlue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PropertyDetailsScreen(
    property: PropertyModel,
    ownerName: String,
    ownerImageUrl: String, // Added this parameter
    isFavoriteInitially: Boolean,
    onFavoriteToggle: (String) -> Unit,
    onBack: () -> Unit,
    onMessageClick: (String) -> Unit){
    val context = LocalContext.current

    // Use the database value but allow local toggling for instant UI feedback
    // remember(property.propertyId) ensures state resets correctly if you switch properties
    var isFavorite by remember(property.propertyId) { mutableStateOf(isFavoriteInitially) }

    val images = property.imageUrls
    val pagerState = rememberPagerState(
        pageCount = { if (images.isEmpty()) 1 else images.size }
    )

    Scaffold(
        containerColor = LightBlue,
        bottomBar = {
            Surface(
                color = LightBlue,
                tonalElevation = 8.dp,
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Price", color = Color.White.copy(alpha = 0.6f), fontSize = 12.sp)
                        Text(
                            "Rs ${property.cost}",
                            color = Yellow,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
//
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            // 1. Image Swiper Section
            Box(modifier = Modifier.fillMaxWidth().height(350.dp)) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    if (images.isNotEmpty()) {
                        AsyncImage(
                            model = images[page],
                            contentDescription = "Property Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize(),
                            placeholder = painterResource(R.drawable.apartment),
                            error = painterResource(R.drawable.apartment)
                        )
                    } else {
                        Image(
                            painter = painterResource(R.drawable.apartment),
                            contentDescription = "Placeholder",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                // Pager Indicators
                if (images.size > 1) {
                    Row(
                        Modifier
                            .height(50.dp)
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(images.size) { iteration ->
                            val color = if (pagerState.currentPage == iteration) Yellow else Color.White.copy(alpha = 0.5f)
                            Box(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clip(CircleShape)
                                    .background(color)
                                    .size(8.dp)
                            )
                        }
                    }
                }

                // Back Button Overlay
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .padding(top = 16.dp, start = 16.dp)
                        .background(Color.Black.copy(alpha = 0.3f), CircleShape)
                ) {
                    Icon(painterResource(R.drawable.baseline_arrow_back_ios_24), null, tint = Color.White)
                }

                // --- UPDATED SAVE/BOOKMARK BUTTON ---
                IconButton(
                    onClick = {
                        isFavorite = !isFavorite // Immediate UI change
                        onFavoriteToggle(property.propertyId) // Update Firebase
                    },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 16.dp, end = 16.dp)
                        .background(Color.Black.copy(alpha = 0.3f), CircleShape)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Save",
                        // Dynamic tint based on favorites state
                        tint = if (isFavorite) Yellow else Color.White
                    )
                }
            }

            Column(modifier = Modifier.padding(20.dp)) {
                // 2. Title and Location
                Text(
                    text = property.title,
                    color = Color.White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = Yellow,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = property.location, color = Color.LightGray, fontSize = 14.sp)
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 3. Owner Profile Section
// 3. Owner Profile Section
                Text("Posted By", color = Color.White.copy(alpha = 0.6f), fontSize = 14.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(16.dp))
                        .padding(12.dp)
                ) {
                    // CHANGED: Using AsyncImage for the Owner's Profile Picture
                    AsyncImage(
                        model = ownerImageUrl,
                        contentDescription = "Owner Profile Picture",
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(Color.Gray.copy(alpha = 0.2f)), // Background while loading
                        contentScale = ContentScale.Crop,
                        // Fallback to a local person icon if the URL is empty or fails
                        placeholder = painterResource(R.drawable.baseline_person_24),
                        error = painterResource(R.drawable.baseline_person_24)
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = ownerName,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Text(
                            text = "Property Owner",
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                    }

                    // MESSAGE BUTTON
                    IconButton(
                        onClick = { onMessageClick(property.ownerId) },
                        modifier = Modifier.background(Yellow, CircleShape)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_message_24),
                            contentDescription = "Message",
                            tint = Color.Black
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 4. Description Section
                Text(
                    text = "Description",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = property.description,
                    color = Color.White.copy(alpha = 0.7f),
                    lineHeight = 22.sp,
                    fontSize = 15.sp
                )

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }
}