package com.example.roomsathi.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.roomsathi.R
import com.example.roomsathi.model.PropertyModel
import com.example.roomsathi.ui.theme.Yellow
import com.example.roomsathi.ui.theme.LightBlue
import com.example.roomsathi.viewmodel.DashboardViewModel
import com.example.roomsathi.viewmodel.UserViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    padding: PaddingValues,
    dashboardViewModel: DashboardViewModel,
    userViewModel: UserViewModel,
    onPropertyClick: (PropertyModel) -> Unit
) {
    val properties by dashboardViewModel.properties.collectAsState()
    val isLoadingProperties by dashboardViewModel.isLoading.collectAsState()
    val userModel by userViewModel.users.observeAsState()

    LaunchedEffect(Unit) {
        val currentFirebaseUser = userViewModel.getCurrentUser()
        currentFirebaseUser?.uid?.let { uid ->
            userViewModel.getUserById(uid)
        }
    }

    val displayName = userModel?.fullName ?: "Guest"
    val featuredProperties = properties.take(5)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 120.dp)
    ) {
        item {
            DashboardTopBar(userName = displayName)
        }

        stickyHeader {
            SearchAndFilterSection()
        }

        item { Spacer(modifier = Modifier.height(20.dp)) }

        // --- 3. Featured Section ---
        if (featuredProperties.isNotEmpty()) {
            item {
                SectionHeader(title = "Featured Properties", actionText = "See All")
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(featuredProperties) { property ->
                        FeaturedPropertyCard(
                            title = property.title,
                            price = "Rs ${property.cost}",
                            location = property.location,
                            // CHANGE: Pass the first image URL from the list
                            imageUrl = property.imageUrls.firstOrNull() ?: ""
                        )
                    }
                }
                Spacer(modifier = Modifier.height(28.dp))
            }
        }

        item {
            SectionHeader(title = "Available Near You", actionText = "")
            Spacer(modifier = Modifier.height(8.dp))
        }

        if (isLoadingProperties) {
            item {
                Box(Modifier.fillMaxWidth().padding(50.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Yellow)
                }
            }
        }

        // --- 5. Compact List ---
        items(properties) { property ->
            CompactPropertyCard(
                title = property.title,
                price = "Rs ${property.cost}",
                location = property.location,
                // CHANGE: Pass the first image URL from the list
                imageUrl = property.imageUrls.firstOrNull() ?: "",
                onClick = { onPropertyClick(property) }
            )
        }
    }
}

@Composable
fun CompactPropertyCard(
    title: String,
    price: String,
    location: String,
    imageUrl: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onClick() },
        color = Color.White.copy(alpha = 0.05f),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(8.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.apartment),
                error = painterResource(R.drawable.apartment),
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(12.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.Center) {
                Text(text = title, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.LocationOn, contentDescription = null, tint = Yellow, modifier = Modifier.size(14.dp))
                    Text(text = location, color = Color.LightGray, fontSize = 13.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = price, color = Yellow, fontSize = 15.sp, fontWeight = FontWeight.ExtraBold)
            }
        }
    }
}

@Composable
fun FeaturedPropertyCard(title: String, price: String, location: String, imageUrl: String) {
    Column(modifier = Modifier.width(240.dp)) {
        Box {
            AsyncImage(
                model = imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.apartment),
                error = painterResource(R.drawable.apartment),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(20.dp))
            )
            Surface(
                modifier = Modifier.padding(10.dp).align(Alignment.TopEnd),
                color = Color.Black.copy(alpha = 0.6f),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = price, color = Yellow, modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp), fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = title, color = Color.White, fontWeight = FontWeight.Bold, maxLines = 1)
        Text(text = location, color = Color.Gray, fontSize = 12.sp, maxLines = 1)
    }
}

@Composable
fun SearchAndFilterSection() {
    var searchQuery by remember { mutableStateOf("") }
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
            GlassSurface(
                modifier = Modifier.weight(1f).height(56.dp),
                containerColor = Color.White.copy(alpha = 0.15f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxSize()
                ) {
                    Icon(painter = painterResource(R.drawable.baseline_search_24), contentDescription = null, tint = Color.White)
                    Spacer(Modifier.width(10.dp))
                    androidx.compose.foundation.text.BasicTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier.fillMaxWidth(),
                        textStyle = androidx.compose.ui.text.TextStyle(color = Color.White, fontSize = 16.sp),
                        singleLine = true,
                        cursorBrush = androidx.compose.ui.graphics.SolidColor(Color.White),
                        decorationBox = { innerTextField ->
                            if (searchQuery.isEmpty()) {
                                Text("Search Place...", color = Color.White.copy(alpha = 0.7f), fontSize = 16.sp)
                            }
                            innerTextField()
                        }
                    )
                }
            }
            Spacer(Modifier.width(10.dp))
            GlassSurface(
                modifier = Modifier.size(56.dp),
                containerColor = Yellow.copy(alpha = 0.9f)
            ) {
                Icon(painterResource(R.drawable.baseline_location_on_24), null, tint = Color.Black)
            }
        }
    }
}