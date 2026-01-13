import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.autofill.contentType
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.roomsathi.model.PropertyModel
import com.example.roomsathi.viewmodel.UpdatePropertyViewModel

@Composable
@Preview(showBackground = true)
fun UpdatePropertyScreen(existingProperty: PropertyModel) {
    val viewModel: UpdatePropertyViewModel = viewModel()

    var title by remember { mutableStateOf(existingProperty.title) }
    var cost by remember { mutableStateOf(existingProperty.cost.toString()) }
    var isRented by remember { mutableStateOf(existingProperty.status) }
    var imageList by remember { mutableStateOf<List<String>>(emptyList()) }
    LaunchedEffect(Unit) {
        viewModel.fetchPropertyImages(existingProperty.indexOfImages.toInt(), existingProperty.noOfImages) { urls ->
            if (urls != null) imageList = urls
        }
    }

    var selectedSlot by remember { mutableIntStateOf(-1) }
    val imagePicker = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let { viewModel.updateImageSlot(existingProperty, selectedSlot, it) }
    }

    Column(Modifier
        .fillMaxSize()
        .padding(16.dp)
        .verticalScroll(rememberScrollState())) {
        Text("Edit Listing", style = MaterialTheme.typography.headlineSmall)

        // Status Toggle (Landlord can mark as Rented)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Mark as Rented")
            Switch(checked = isRented, onCheckedChange = { isRented = it })
        }

        OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Title") })
        OutlinedTextField(value = cost, onValueChange = { cost = it }, label = { Text("Price") })

        Spacer(Modifier.height(16.dp))
        Text("Images (8 Slots Available)")

        // Grid of 8 Slots
        Column {
            for (row in 0..1) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (col in 0..3) {
                        val slotIndex = (row * 4) + col
                        val imageUrl = imageList.getOrNull(slotIndex)
                        Box(
                            Modifier
                                .size(80.dp)
                                .background(
                                    Color.LightGray,
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable {
                                    selectedSlot = slotIndex
                                    imagePicker.launch("image/*")
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            if (!imageUrl.isNullOrEmpty()){
                                AsyncImage(
                                    model = imageUrl,
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }
                            else {
                                Text("Slot $slotIndex", fontSize = 10.sp, color = Color.Gray)

                            }
                        }
                    }
                }
                Spacer(Modifier.height(8.dp))
            }
        }

        Button(
            onClick = {

                val updatedProperty = existingProperty.copy(
                    title = title,
                    cost = cost.toDoubleOrNull() ?: existingProperty.cost,
                    status = isRented

                )


                viewModel.updatePropertyDetails(
                    propertyId = existingProperty.propertyId,
                    updatedProperty = updatedProperty
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),

            enabled = title.isNotBlank()
        ) {
            Text("Save All Changes")
        }
    }
}
