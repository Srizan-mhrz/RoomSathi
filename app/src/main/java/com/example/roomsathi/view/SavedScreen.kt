package com.example.roomsathi.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState // Use this for StateFlow
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState // Use this for LiveData
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomsathi.model.PropertyModel
import com.example.roomsathi.ui.theme.LightBlue
import com.example.roomsathi.viewmodel.DashboardViewModel
import com.example.roomsathi.viewmodel.FavoriteViewModel


@Composable
fun SavedScreen(
    favoriteViewModel: FavoriteViewModel,
    dashboardViewModel: DashboardViewModel,
    onPropertyClick: (PropertyModel) -> Unit
) {
    // 1. Observe Favorite IDs (This is LiveData, so we use observeAsState)
    val favoriteIds by favoriteViewModel.favoriteIds.observeAsState(emptyList())

    // 2. Observe Properties (This is StateFlow, so we MUST use collectAsState)
    // Note: Use 'properties' (the public variable in your ViewModel)
    val allProperties by dashboardViewModel.properties.collectAsState()

    // 3. Filter the list
    val savedProperties = remember(favoriteIds, allProperties) {
        allProperties.filter { property ->
            favoriteIds.contains(property.propertyId)
        }
    }

    // Inside SavedScreen replace the main Column and Header text with:
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue)
            .statusBarsPadding() // IMMERSIVE SUPPORT
            .padding(horizontal = 24.dp) // UNIFIED PADDING
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Saved Properties",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(vertical = 12.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // ... logic for filtering remains the same

        if (savedProperties.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No saved properties yet",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            }
        } else {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                items(
                    items = savedProperties,
                    key = { it.propertyId }
                ) { property ->
                    PropertyCard(
                        property = property,
                        onClick = { onPropertyClick(property) }
                    )
                }
            }
        }
    }
}