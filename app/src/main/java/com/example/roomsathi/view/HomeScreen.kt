package com.example.roomsathi.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomsathi.R // Correct R import
import com.example.roomsathi.ui.theme.Yellow
import com.example.roomsathi.ui.theme.LightBlue // Assuming this is in your theme
import com.example.roomsathi.viewmodel.DashboardViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    padding: PaddingValues,
    viewModel: DashboardViewModel
) {
    val properties by viewModel.properties.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 120.dp)
    ) {
        // 1. Dashboard Top Bar (Profile/Notif)
        item {
            DashboardTopBar()
        }

        // 2. Sticky Search & Filter Bar
        stickyHeader {
            // Re-using the logic from DashboardActivity
            SearchAndFilterSection()
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            SectionHeader(title = "Available Near You", actionText = "")
        }

        if (isLoading) {
            item {
                Box(Modifier.fillMaxWidth().padding(50.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Yellow)
                }
            }
        }

        // DYNAMIC LIST from Repo
        items(properties) { property ->
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                PropertyCard(
                    title = property.title,
                    price = "Rs ${property.cost}",
                    location = property.location,
                    propertyId = property.propertyId
                )
            }
        }
    }
}

@Composable
fun PropertyCard(
    title: String,
    price: String,
    location: String,
    propertyId: String
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(R.drawable.apartment),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(20.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = title,
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = Yellow,
                modifier = Modifier.size(16.dp)
            )
            Text(
                text = location,
                color = Color.White.copy(alpha = 0.7f),
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = price,
                color = Yellow,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun SearchAndFilterSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(LightBlue)
            .padding(top = 54.dp, bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Search Bar Glass
            GlassSurface(
                modifier = Modifier.weight(1f).height(56.dp),
                containerColor = Color.White.copy(alpha = 0.5f)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(painterResource(R.drawable.baseline_search_24), null, tint = Color.White)
                    Spacer(Modifier.width(10.dp))
                    Text("Search Place...", color = Color.White.copy(alpha = 0.9f))
                }
            }
            Spacer(Modifier.width(10.dp))
            // Filter Button Glass
            GlassSurface(
                modifier = Modifier.size(56.dp),
                containerColor = Yellow.copy(alpha = 0.8f)
            ) {
                Icon(painterResource(R.drawable.baseline_location_on_24), null, tint = Color.Black)
            }
        }
    }
}