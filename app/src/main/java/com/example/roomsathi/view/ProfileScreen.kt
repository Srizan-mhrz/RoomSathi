package com.example.roomsathi

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.roomsathi.model.PropertyModel
import com.example.roomsathi.ui.theme.*
import com.example.roomsathi.view.EditProfileActivity
import com.example.roomsathi.viewmodel.DashboardViewModel
import com.example.roomsathi.viewmodel.UserViewModel

@Composable
fun GlassSurface(
    modifier: Modifier = Modifier,
    containerColor: Color = White.copy(alpha = 0.15f),
    content: @Composable () -> Unit
) {
    val cornerShape = RoundedCornerShape(16.dp)
    Box(
        modifier = modifier
            .clip(cornerShape)
            .border(width = 1.dp, color = White.copy(alpha = 0.3f), shape = cornerShape)
    ) {
        Box(modifier = Modifier.matchParentSize().background(containerColor).blur(20.dp))
        Box(modifier = Modifier.padding(16.dp)) { content() }
    }
}

@Composable
fun ProfileScreenBody(userViewModel: UserViewModel, dashboardViewModel: DashboardViewModel) {
    val context = LocalContext.current
    val backgroundColor = LightBlue

    val userModel by userViewModel.users.observeAsState()
    val myPosts by dashboardViewModel.myPosts.collectAsState()

    var showEditDialog by remember { mutableStateOf(false) }
    var selectedProperty by remember { mutableStateOf<PropertyModel?>(null) }

    val profileName = userModel?.fullName ?: "Loading..."
    val profileEmail = userModel?.email ?: "user@example.com"
    val profileImageUrl = userModel?.profileImageUrl ?: ""

    LaunchedEffect(Unit) {
        userViewModel.getCurrentUser()?.uid?.let { uid ->
            userViewModel.getUserById(uid)
        }
    }

    Box(modifier = Modifier.fillMaxSize().background(backgroundColor)) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
            ) {
                GlassSurface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 24.dp)
                ) {
                    ProfileHeaderContent(profileName, profileEmail, profileImageUrl)
                }

                Text(
                    text = "My Posts",
                    color = White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
                )

                if (myPosts.isEmpty()) {
                    Text(
                        text = "You haven't posted any rooms yet.",
                        color = Color.LightGray.copy(alpha = 0.6f),
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 10.dp),
                        fontSize = 14.sp
                    )
                } else {
                    myPosts.forEach { property ->
                        MyPostCard(
                            property = property,
                            onEdit = {
                                selectedProperty = property
                                showEditDialog = true
                            },
                            onDelete = {
                                dashboardViewModel.deleteProperty(property.propertyId) { success, msg ->
                                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                GlassSurface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 24.dp)
                ) {
                    Column(modifier = Modifier.padding(vertical = 4.dp)) {
                        ProfileItem(R.drawable.outline_location_on_24, "Location", {})
                        HorizontalDivider(color = White.copy(alpha = 0.2f), thickness = 0.5.dp)
                        ProfileItem(R.drawable.baseline_history_24, "History", {})
                        HorizontalDivider(color = White.copy(alpha = 0.2f), thickness = 0.5.dp)
                        ProfileItem(R.drawable.baseline_settings_24, "Account settings", {})
                        HorizontalDivider(color = White.copy(alpha = 0.2f), thickness = 0.5.dp)
                        ProfileItem(
                            iconRes = R.drawable.baseline_logout_24,
                            label = "Log Out",
                            onClick = {
                                userViewModel.logOut { success, _ ->
                                    if(success) (context as? android.app.Activity)?.finish()
                                }
                            },
                            isLogout = true
                        )
                    }
                }
            }
        }

        if (showEditDialog && selectedProperty != null) {
            EditPostDialog(
                property = selectedProperty!!,
                onDismiss = { showEditDialog = false },
                onSave = { updatedProperty ->
                    // To be connected to ViewModel: dashboardViewModel.updateProperty(updatedProperty)
                    showEditDialog = false
                    Toast.makeText(context, "Changes Saved Locally", Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}

@Composable
fun EditPostDialog(
    property: PropertyModel,
    onDismiss: () -> Unit,
    onSave: (PropertyModel) -> Unit
) {
    var title by remember { mutableStateOf(property.title) }

    // FIX: Convert Double to String for the TextField
    var priceStr by remember { mutableStateOf(property.cost.toString()) }

    var description by remember { mutableStateOf(property.description) }
    var location by remember { mutableStateOf(property.location) }
    var imageUrls by remember { mutableStateOf(property.imageUrls.toMutableList()) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .wrapContentHeight()
                .clip(RoundedCornerShape(24.dp)),
            color = DarkBlue
        ) {
            Column(
                modifier = Modifier
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Text("Edit Room Details", color = White, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(16.dp))

                Text("Room Photos", color = Yellow, fontSize = 14.sp)
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier.horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    imageUrls.forEachIndexed { index, url ->
                        Box(modifier = Modifier.size(100.dp)) {
                            AsyncImage(
                                model = url,
                                contentDescription = null,
                                modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(12.dp)),
                                contentScale = ContentScale.Crop
                            )
                            IconButton(
                                onClick = {
                                    val newList = imageUrls.toMutableList()
                                    newList.removeAt(index)
                                    imageUrls = newList
                                },
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .size(24.dp)
                                    .background(Color.Black.copy(0.6f), CircleShape)
                            ) {
                                Icon(painterResource(R.drawable.baseline_delete_24), null, tint = Color.Red, modifier = Modifier.size(14.dp))
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                EditField(label = "Title", value = title, onValueChange = { title = it })

                // Price field now handles String correctly
                EditField(label = "Price (Rs)", value = priceStr, onValueChange = { priceStr = it })

                EditField(label = "Location", value = location, onValueChange = { location = it })
                EditField(
                    label = "Description",
                    value = description,
                    onValueChange = { description = it },
                    isMultiLine = true
                )

                Spacer(Modifier.height(24.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel", color = Color.LightGray)
                    }
                    Spacer(Modifier.width(8.dp))
                    Button(
                        onClick = {
                            // Convert price back to Double when saving
                            val priceDouble = priceStr.toDoubleOrNull() ?: 0.0
                            onSave(property.copy(
                                title = title,
                                cost = priceDouble,
                                description = description,
                                imageUrls = imageUrls,
                                location = location
                            ))
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Yellow),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Save Changes", color = Color.Black, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
fun EditField(label: String, value: String, onValueChange: (String) -> Unit, isMultiLine: Boolean = false) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(label, color = Color.Gray, fontSize = 12.sp)
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = White.copy(alpha = 0.05f),
                unfocusedContainerColor = White.copy(alpha = 0.05f),
                focusedTextColor = White,
                unfocusedTextColor = White,
                cursorColor = Yellow,
                focusedIndicatorColor = Yellow
            ),
            maxLines = if (isMultiLine) 4 else 1,
            singleLine = !isMultiLine
        )
    }
}

@Composable
fun MyPostCard(property: PropertyModel, onEdit: () -> Unit, onDelete: () -> Unit) {
    var showMenu by remember { mutableStateOf(false) }

    GlassSurface(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 6.dp),
        containerColor = White.copy(alpha = 0.05f)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = property.imageUrls.firstOrNull(),
                contentDescription = null,
                modifier = Modifier.size(60.dp).clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.apartment)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(property.title, color = White, fontWeight = FontWeight.Bold, fontSize = 15.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text("Rs ${property.cost}", color = Yellow, fontSize = 13.sp, fontWeight = FontWeight.Medium)
            }
            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(painterResource(R.drawable.baseline_more_vert_24), "Options", tint = White)
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false },
                    modifier = Modifier.background(DarkBlue)
                ) {
                    DropdownMenuItem(
                        text = { Text("Edit", color = White) },
                        leadingIcon = { Icon(painterResource(R.drawable.baseline_edit_24), null, tint = White) },
                        onClick = { showMenu = false; onEdit() }
                    )
                    DropdownMenuItem(
                        text = { Text("Delete", color = Color.Red) },
                        leadingIcon = { Icon(painterResource(R.drawable.baseline_delete_24), null, tint = Color.Red) },
                        onClick = { showMenu = false; onDelete() }
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileHeaderContent(profileName: String, profileHandle: String, profileImageUrl: String) {
    val context = LocalContext.current
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            model = profileImageUrl,
            contentDescription = null,
            modifier = Modifier.size(70.dp).clip(CircleShape).background(White.copy(alpha = 0.1f)),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.baseline_person_24)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(profileName, style = TextStyle(color = White, fontSize = 20.sp, fontWeight = FontWeight.Bold), maxLines = 1)
            Text(profileHandle, style = TextStyle(color = Color.LightGray.copy(alpha = 0.8f), fontSize = 14.sp), maxLines = 1)
        }
        Button(
            onClick = { context.startActivity(Intent(context, EditProfileActivity::class.java)) },
            colors = ButtonDefaults.buttonColors(containerColor = DarkBlue),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text("Edit Profile", color = White)
        }
    }
}

@Composable
fun ProfileItem(iconRes: Int, label: String, onClick: () -> Unit, isLogout: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable { onClick() }.padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(painter = painterResource(iconRes), null, modifier = Modifier.size(24.dp), tint = if (isLogout) Color(0xFFFF4444) else White)
        Spacer(Modifier.width(20.dp))
        Text(label, modifier = Modifier.weight(1f), style = TextStyle(color = White, fontSize = 16.sp))
        Icon(painterResource(R.drawable.baseline_arrow_forward_ios_24), null, modifier = Modifier.size(14.dp), tint = White.copy(alpha = 0.5f))
    }
}