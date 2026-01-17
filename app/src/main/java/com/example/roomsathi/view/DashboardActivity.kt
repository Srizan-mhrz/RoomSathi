package com.example.roomsathi.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.roomsathi.ProfileScreenBody
import com.example.roomsathi.R
import com.example.roomsathi.model.PropertyModel
import com.example.roomsathi.repository.PropertyRepoImpl
import com.example.roomsathi.ui.theme.*
import com.example.roomsathi.viewmodel.DashboardViewModel

class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DashboardBody()
        }
    }
}

/* ---------------- UI Components ---------------- */

@Composable
fun GlassSurface(
    modifier: Modifier = Modifier,
    containerColor: Color = Color.White.copy(alpha = 0.2f),
    content: @Composable () -> Unit
) {
    val shape = RoundedCornerShape(20.dp)
    Box(
        modifier = modifier
            .clip(shape)
            .border(1.dp, Color.White.copy(alpha = 0.3f), shape)
    ) {
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(containerColor)
                .blur(50.dp)
        )
        Box(modifier = Modifier.padding(16.dp)) {
            content()
        }
    }
}

@Composable
fun DashboardBody() {
    val context = LocalContext.current

    // ViewModels initialization
    val dashboardViewModel: DashboardViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DashboardViewModel(PropertyRepoImpl()) as T
            }
        }
    )

    val userViewModel: com.example.roomsathi.viewmodel.UserViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return com.example.roomsathi.viewmodel.UserViewModel(com.example.roomsathi.repository.UserRepoImpl()) as T
            }
        }
    )

    // --- ADDED: Favorite ViewModel Initialization ---
    val favoriteViewModel: com.example.roomsathi.viewmodel.FavoriteViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return com.example.roomsathi.viewmodel.FavoriteViewModel(com.example.roomsathi.repository.FavoritesRepoImpl()) as T
            }
        }
    )

    var selectedIndex by rememberSaveable { mutableStateOf(0) }
    var selectedProperty by remember { mutableStateOf<PropertyModel?>(null) }

    val propertyOwnerState by userViewModel.propertyOwner.observeAsState()

    // --- ADDED: Observe Favorite IDs and Current User ---
    val favoriteIds by favoriteViewModel.favoriteIds.observeAsState(emptyList())
    val currentUser = userViewModel.getCurrentUser()

    // Fetch favorites once the user is known
    LaunchedEffect(currentUser) {
        currentUser?.uid?.let { uid ->
            favoriteViewModel.getFavorites(uid)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue)
    ) {
        if (selectedProperty != null) {
            LaunchedEffect(selectedProperty) {
                selectedProperty?.ownerId?.let { id ->
                    userViewModel.getPropertyOwnerById(id)
                }
            }

            val ownerName = propertyOwnerState?.fullName ?: "Loading..."
            val ownerImageUrl = propertyOwnerState?.profileImageUrl ?: ""
            PropertyDetailsScreen(
                property = selectedProperty!!,
                ownerName = ownerName,
                ownerImageUrl = ownerImageUrl,
                // --- ADDED: Pass Favorite state and toggle logic ---
                isFavoriteInitially = favoriteIds.contains(selectedProperty!!.propertyId),
                onFavoriteToggle = { propId ->
                    currentUser?.uid?.let { uid ->
                        favoriteViewModel.toggleFavorite(uid, propId) { message ->
                            android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                onBack = { selectedProperty = null },
                onMessageClick = { ownerId ->
                    val intent = android.content.Intent(context, InboxActivity::class.java).apply {
                        putExtra("RECEIVER_ID", ownerId)
                        putExtra("RECEIVER_NAME", ownerName)
                        putExtra("RECEIVER_IMAGE", ownerImageUrl)
                    }
                    context.startActivity(intent)
                }
            )
        } else {
            Scaffold(
                containerColor = Color.Transparent,
                bottomBar = {
                    if (selectedIndex != 4) {
                        DashboardBottomBar(
                            selectedIndex = selectedIndex,
                            onItemSelected = { selectedIndex = it }
                        )
                    }
                },
                floatingActionButton = {
                    if (selectedIndex != 4) {
                        FloatingActionButton(
                            onClick = { selectedIndex = 4 },
                            shape = CircleShape,
                            containerColor = Yellow,
                            contentColor = DarkBlue,
                            modifier = Modifier.size(64.dp).offset(y = 55.dp)
                        ) {
                            Icon(painterResource(R.drawable.baseline_add_24), "Post")
                        }
                    }
                },
                floatingActionButtonPosition = FabPosition.Center,
            ) { innerPadding ->
                Box(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
                    when (selectedIndex) {
                        0 -> HomeScreen(
                            padding = innerPadding,
                            dashboardViewModel = dashboardViewModel,
                            userViewModel = userViewModel,
                            onPropertyClick = { clickedProperty ->
                                selectedProperty = clickedProperty
                            }
                        )
                        1 -> MessageBody { selectedUser ->
                            val intent = android.content.Intent(context, InboxActivity::class.java).apply {
                                putExtra("RECEIVER_ID", selectedUser.uid)
                                putExtra("RECEIVER_NAME", selectedUser.name)
                            }
                            context.startActivity(intent)
                        }
                        // --- CHANGED: Pass data to SavedScreen ---
                        2 -> SavedScreen(
                            favoriteViewModel = favoriteViewModel,
                            dashboardViewModel = dashboardViewModel,
                            onPropertyClick = { clickedProperty ->
                                selectedProperty = clickedProperty
                            }
                        )
                        3 -> ProfileScreenBody(
                            userViewModel = userViewModel,
                            dashboardViewModel = dashboardViewModel
                        )
                        4 -> AddingPropertyScreen(
                            onAddSuccess = {
                                selectedIndex = 0
                                dashboardViewModel.fetchAllProperties()
                            }
                        )
                    }
                }
            }
        }
    }
}

/* ---------------- Dashboard Tab (Home) ---------------- */

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DashboardContent(padding: PaddingValues) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 120.dp)
    ) {
        // 1. Top Bar - inside LazyColumn so it scrolls away
        item {
//            DashboardTopBar()
        }

        // 2. Sticky Search Bar - ADDED TOP PADDING FOR CLEAN UI
        stickyHeader {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LightBlue)
                    .padding(top = 54.dp, bottom = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 1. THE SEARCH BAR (Flexible width)
                    GlassSurface(
                        modifier = Modifier
                            .weight(1f) // Takes up remaining space
                            .height(56.dp),
                        containerColor = Color.White.copy(alpha = 0.5f)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_search_24),
                                contentDescription = null,
                                tint = Color.White
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Search Place...",
                                color = Color.White.copy(alpha = 0.9f),
                                fontSize = 14.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    // 2. THE FILTER BUTTON (Square glass)
                    GlassSurface(
                        modifier = Modifier
                            .size(56.dp) // Makes it a perfect square
                            .clickable { /* TODO: Open Filter */ },
                        containerColor = Yellow.copy(alpha = 0.8f) // Yellow glass makes it pop
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.baseline_filter_list_24), // Use your filter icon
                                contentDescription = "Filter",
                                tint = DarkBlue, // Dark contrast on yellow
                                modifier = Modifier.size(26.dp)
                            )
                        }
                    }
                }
            }
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        // ... Rest of your items (Featured Title, LazyRow, etc.) stay the same
        item {
            SectionHeader(title = "Featured Properties", actionText = "See all")
        }

        item { Spacer(modifier = Modifier.height(16.dp)) }

        item {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item { FeatureCard(R.drawable.apartment, "Rs 25,000", "Luxury Apartment") }
                item { FeatureCard(R.drawable.room, "Rs 15,000", "Cozy Room") }
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }

        item {
            Row(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CategoryItem("Room", R.drawable.baseline_home_24)
                CategoryItem("Flat", R.drawable.baseline_business_24)
                CategoryItem("Hostel", R.drawable.baseline_location_on_24)
            }
        }

        item { Spacer(modifier = Modifier.height(24.dp)) }

        item {
            SectionHeader(title = "Available Near You", actionText = "")
        }

        items(10) { index ->
            Box(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                FeatureCard(R.drawable.apartment, "Rs 18,000", "Available Room #$index")
            }
        }
    }
}
@Composable
fun DashboardTopBar(userName: String, profileImageUrl: String) {
    Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 60.dp, bottom = 10.dp)) {
        GlassSurface(modifier = Modifier.fillMaxWidth()) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(12.dp)
            ) {
                // CHANGED: Using AsyncImage for Cloudinary URL
                AsyncImage(
                    model = profileImageUrl,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.Gray.copy(alpha = 0.2f)), // Background while loading
                    contentScale = ContentScale.Crop,
                    // Use a person icon if the URL is empty or fails
                    placeholder = painterResource(R.drawable.baseline_person_24),
                    error = painterResource(R.drawable.baseline_person_24)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Hello!",
                        color = Color.White.copy(alpha = 0.6f),
                        fontSize = 12.sp
                    )
                    Text(
                        text = userName,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Notification Icon
                Icon(
                    painter = painterResource(R.drawable.baseline_notifications_active_24),
                    contentDescription = null,
                    tint = Yellow,
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    }
}
/* ---------------- Reusable Helpers ---------------- */

@Composable
fun SectionHeader(title: String, actionText: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(title, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        if (actionText.isNotEmpty()) {
            Text(actionText, color = Yellow, fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun FeatureCard(image: Int, price: String, title: String) {
    Column(modifier = Modifier.width(280.dp)) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.height(200.dp).clip(RoundedCornerShape(20.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = title, color = Color.White, fontSize = 14.sp)
        Text(text = price, color = Yellow, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}

@Composable
fun CategoryItem(title: String, icon: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier.size(50.dp).clip(CircleShape).background(Color.White.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(painter = painterResource(icon), contentDescription = null, tint = Color.White, modifier = Modifier.size(24.dp))
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = title, color = Color.White, fontSize = 12.sp)
    }
}

/* ---------------- Bottom Navigation ---------------- */

@Composable
fun DashboardBottomBar(selectedIndex: Int, onItemSelected: (Int) -> Unit) {
    GlassSurface(
        modifier = Modifier.fillMaxWidth().height(100.dp).padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.SpaceAround) {
                BottomNavItem("Home", R.drawable.baseline_home_24, selectedIndex == 0) { onItemSelected(0) }
                BottomNavItem("Chat", R.drawable.baseline_message_24, selectedIndex == 1) { onItemSelected(1) }
            }
            Spacer(modifier = Modifier.width(72.dp))
            Row(modifier = Modifier.weight(1f), horizontalArrangement = Arrangement.SpaceAround) {
                BottomNavItem("Saved", R.drawable.baseline_bookmark_24, selectedIndex == 2) { onItemSelected(2) }
                BottomNavItem("Profile", R.drawable.baseline_person_24, selectedIndex == 3) { onItemSelected(3) }
            }
        }
    }
}

@Composable
fun BottomNavItem(label: String, icon: Int, selected: Boolean, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.clickable { onClick() }) {
        Icon(
            painter = painterResource(icon),
            contentDescription = label,
            tint = if (selected) Yellow else Color.White,
            modifier = Modifier.size(26.dp)
        )
        Text(text = label, color = if (selected) Yellow else Color.White, fontSize = 10.sp)
    }
}

/* ---------------- Screen Placeholders ---------------- */

@Composable
fun MessageScreen() = CenterText("Messages / Inbox")
@Composable
fun SavedScreen() = CenterText("Favorites & Saved Rooms")
@Composable
fun ProfileScreen() = CenterText("User Profile & My Ads")
@Composable
fun CenterText(text: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text, color = Color.White, fontSize = 20.sp)
    }
}