package com.example.roomsathi.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.roomsathi.viewmodel.AddPropertyUiState
import com.example.roomsathi.viewmodel.AddingPropertyViewModel

@Composable
fun AddingPropertyScreen(
    onAddSuccess: (String) -> Unit = {}
) {
    val viewModel: AddingPropertyViewModel = viewModel()
    val uiState by viewModel.uiState.collectAsState()

    var title by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }


    var selectedImageUris by remember { mutableStateOf<List<Uri>>(emptyList()) }


    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents()
    ) { uris ->
        selectedImageUris = uris
    }

    when (val state = uiState) {
        is AddPropertyUiState.Error -> { errorMessage = state.message }
        is AddPropertyUiState.Success -> {
            successMessage = state.message
            onAddSuccess(state.propertyId)
            viewModel.resetState()
        }
        else -> {
            errorMessage = null
            successMessage = null
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Add a New Property",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 24.dp)
            )


            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth().height(120.dp),
                maxLines = 5
            )
            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = cost,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() }) {
                        cost = newValue
                    }
                },
                label = { Text("Cost per Month") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                prefix = { Text("Rs. ") }
            )

            Spacer(Modifier.height(16.dp))


            Button(
                onClick = { galleryLauncher.launch("image/*") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("Select Pictures from Gallery")
            }


            if (selectedImageUris.isNotEmpty()) {
                Spacer(Modifier.height(8.dp))
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(selectedImageUris) { uri ->
                        AsyncImage(
                            model = uri,
                            contentDescription = null,
                            modifier = Modifier
                                .size(90.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            if (uiState is AddPropertyUiState.Loading) {
                CircularProgressIndicator()
            } else {
                errorMessage?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }
                successMessage?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.primary)
                }

                Button(
                    onClick = {
                        val costDouble = cost.toDoubleOrNull() ?: 0.0
                        val ownerId = "abiabi"


                        viewModel.addPropertyWithHardcodedOwner(
                            ownerId = ownerId,
                            title = title,
                            location = location,
                            description = description,
                            cost = costDouble,
                            imageUris = selectedImageUris
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),

                    enabled = selectedImageUris.isNotEmpty()
                ) {
                    Text("Add Property")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddingPropertyScreenPreview() {
    AddingPropertyScreen()
}
