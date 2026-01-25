package com.example.roomsathi.view

import android.net.Uri
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.roomsathi.ui.theme.*
import com.example.roomsathi.viewmodel.AddPropertyUiState
import com.example.roomsathi.viewmodel.AddingPropertyViewModel

@Composable
fun AddingPropertyScreen(
    onAddSuccess: (String) -> Unit = {}
) {
    val viewModel: AddingPropertyViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()
    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    var title by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }
    var selectedImageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris -> selectedImageUris = uris }

    LaunchedEffect(uiState) {
        when (val state = uiState) {
            is AddPropertyUiState.Error -> { errorMessage = state.message }
            is AddPropertyUiState.Success -> {
                successMessage = state.message
                onAddSuccess(state.propertyId)
                viewModel.resetState()
            }
            else -> { errorMessage = null; successMessage = null }
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(LightBlue)
            .imePadding() // CRITICAL: This pushes the UI up when keyboard opens
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(modifier = Modifier.fillMaxWidth().padding(top = 16.dp)) {
                IconButton(
                    onClick = { backDispatcher?.onBackPressed() },
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = androidx.compose.material.icons.Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
                Text(
                    text = "Add Property",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Text(
                text = "Fill in the details to list your room",
                color = Color.White.copy(alpha = 0.5f),
                fontSize = 14.sp,
                modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
            )

            // Form Fields
            // Replace your "Form Fields" Column with this:
            GlassSurface(
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.White.copy(alpha = 0.1f)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    // Using your app's custom ThemedInputField (pass R.drawable icons as needed)
                    PropertyInputField(title, { title = it }, "Property Title", icon = com.example.roomsathi.R.drawable.baseline_home_24)
                    PropertyInputField(location, { location = it }, "Location", icon = com.example.roomsathi.R.drawable.baseline_location_on_24)
                    PropertyInputField(description, { description = it }, "Description", icon = com.example.roomsathi.R.drawable.baseline_description_24)
                    PropertyInputField(cost, { if (it.all { c -> c.isDigit() }) cost = it }, "Cost (Monthly)", icon = com.example.roomsathi.R.drawable.outline_attach_money_24, keyboardType = KeyboardType.Number)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Pictures Section
            Button(
                onClick = { galleryLauncher.launch("image/*") },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.15f)),
                border = BorderStroke(1.dp, Color.White.copy(alpha = 0.2f))
            ) {
                Icon(painterResource(id = com.example.roomsathi.R.drawable.baseline_add_photo_alternate_24), null, tint = Yellow)
                Spacer(Modifier.width(12.dp))
                Text("Add Photos (${selectedImageUris.size}/8)", color = Color.White, fontWeight = FontWeight.Bold)
            }

            if (selectedImageUris.isNotEmpty()) {
                Spacer(Modifier.height(12.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    items(selectedImageUris) { uri ->
                        AsyncImage(
                            model = uri,
                            contentDescription = null,
                            modifier = Modifier.size(90.dp).clip(RoundedCornerShape(12.dp)).border(1.dp, Color.White.copy(alpha = 0.2f), RoundedCornerShape(12.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))

            // Status Messages
            if (uiState is AddPropertyUiState.Loading) {
                CircularProgressIndicator(color = Yellow)
                Spacer(modifier = Modifier.height(16.dp))
            } else {
                errorMessage?.let { Text(it, color = Color.Red, fontSize = 13.sp, modifier = Modifier.padding(bottom = 12.dp)) }
                successMessage?.let { Text(it, color = Yellow, fontSize = 13.sp, modifier = Modifier.padding(bottom = 12.dp)) }

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 32.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Cancel Button Fixed
                    OutlinedButton(
                        onClick = { backDispatcher?.onBackPressed() },
                        modifier = Modifier.weight(1f).height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
                    ) {
                        Text("CANCEL", color = Color.White, fontWeight = FontWeight.Bold)
                    }

                    // Add Button
                    Button(
                        onClick = {
                            val costDouble = cost.toDoubleOrNull() ?: 0.0
                            viewModel.addProperty(title, location, description, costDouble, selectedImageUris)
                        },
                        modifier = Modifier.weight(1.5f).height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Yellow),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp),
                        enabled = title.isNotBlank() && cost.isNotBlank() && selectedImageUris.isNotEmpty()
                    ) {
                        Text("ADD PROPERTY", color = Color.Black, fontWeight = FontWeight.ExtraBold)
                    }
                }
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyInputField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: Int,
    placeholder: String = "", isMultiline: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text, prefix: String? = null
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        leadingIcon = {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Yellow,
                modifier = Modifier.size(20.dp)
            )
        },
        label = { Text(label, color = Yellow, fontSize = 12.sp, fontWeight = FontWeight.SemiBold) },
        placeholder = { Text(placeholder, color = Color.White.copy(alpha = 0.3f), fontSize = 14.sp) },
        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(Color.White.copy(alpha = 0.07f))
            .then(if (isMultiline) Modifier.height(110.dp) else Modifier),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        prefix = prefix?.let { { Text(it, color = Color.White) } },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent, unfocusedContainerColor = Color.Transparent,
            focusedTextColor = Color.White, unfocusedTextColor = Color.White,
            focusedIndicatorColor = Yellow, unfocusedIndicatorColor = Color.Transparent, cursorColor = Yellow
        ),
        singleLine = !isMultiline
    )
}