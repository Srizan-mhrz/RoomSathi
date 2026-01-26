package com.example.roomsathi.view
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.IntrinsicSize
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.BasicTextField
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.automirrored.filled.ArrowBack
//import androidx.compose.material.icons.filled.Check
//import androidx.compose.material.icons.filled.KeyboardArrowDown
//import androidx.compose.material.icons.filled.Visibility
//import androidx.compose.material.icons.filled.VisibilityOff
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.LocalTextStyle
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.OutlinedTextFieldDefaults
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.saveable.rememberSaveable
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.text.input.VisualTransformation
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import coil.compose.rememberAsyncImagePainter
//import com.example.roomsathi.R
//import com.example.roomsathi.repository.UserRepoImpl
//import com.example.roomsathi.viewmodel.UserViewModel
//
//fun EditProfileScreens(onBack: () -> Unit) {
//
//    // State variables for each text field
////    val userViewModel= remember { UserViewModel(UserRepoImpl()) }
////    val user =userViewModel.users.observeAsState(initial=null)
////    var name by rememberSaveable { mutableStateOf("") }
////    var email by rememberSaveable { mutableStateOf("") }
////    var username by rememberSaveable { mutableStateOf("") }
////    var password by rememberSaveable { mutableStateOf("") }
////    var phoneNumber by rememberSaveable { mutableStateOf("") }
////    var passwordVisible by rememberSaveable { mutableStateOf(false) }
//
//    Scaffold(
//        topBar = {
//            EditProfileTopAppBar(onBackClicked = { onBack() }, onSaveClicked = { /* Handle save action */ })
//        }
//    ) { paddingValues ->
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(paddingValues)
//                .verticalScroll(rememberScrollState()) // Make the column scrollable
//                .padding(horizontal = 24.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = "Edit Profile",
//                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
//                modifier = Modifier
//                    .padding(vertical = 16.dp)
//                    .align(Alignment.Start)
//            )
//
////            ProfileImageWithEditor()
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            // Input Fields
////            EditProfileTextField(label = "Name", value = name, onValueChange = { name = it })
////            EditProfileTextField(label = "Email Address", value = email, onValueChange = { email = it }, keyboardType = KeyboardType.Email)
////            EditProfileTextField(label = "User name", value = username, onValueChange = { username = it })
////            PasswordTextField(label = "Password", password = password, onPasswordChange = { password = it }, passwordVisible = passwordVisible, onPasswordVisibilityChange = { passwordVisible = it })
////            PhoneNumberField(label = "Phone number", phoneNumber = phoneNumber, onPhoneNumberChange = { phoneNumber = it })
//        }
//    }
//}
//
//@Composable
//fun EditProfileTopAppBars(onBackClicked: () -> Unit, onSaveClicked: () -> Unit) {
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(60.dp)
//            .background(
//                brush = Brush.linearGradient(
//                    colors = listOf(Color(0xFF3A60F5), Color(0xFF5D85F6))
//                )
//            )
//    ) {
//        Row(
//            modifier = Modifier.fillMaxSize(),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            IconButton(onClick = onBackClicked) {
//                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
//            }
//            Spacer(modifier = Modifier.weight(1f))
//            // The checkmark is positioned absolutely in the original design,
//            // but for simplicity, placing it here. You can adjust as needed.
//        }
//    }
//}
//
//@Composable
//fun ProfileImageWithEditors() {
//    Box(contentAlignment = Alignment.Center) {
//
//        Image(
//            painter = rememberAsyncImagePainter("https://i.insider.com/655e4e2c57f2723c21a316e6?width=700"),
//            contentDescription = "Profile Image",
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .size(120.dp)
//                .clip(CircleShape)
//        )
//        Box(
//            modifier = Modifier
//                .align(Alignment.BottomEnd)
//                .offset(x = (-8).dp, y = (-8).dp)
//                .size(32.dp)
//                .clip(CircleShape)
//                .background(Color.White)
//                .padding(4.dp)
//                .clickable { /* TODO: Handle image change */ }
//        ) {
//            Icon(
//                painter = painterResource(id = R.drawable.baseline_photo_camera_24), // You need to add a camera icon to your drawables
//                contentDescription = "Change profile picture",
//                modifier = Modifier
//                    .size(20.dp)
//                    .align(Alignment.Center),
//                tint = Color.Black
//            )
//        }
//
//        Icon(
//            imageVector = Icons.Default.Check,
//            contentDescription = "Save Changes",
//            modifier = Modifier
//                .align(Alignment.TopEnd)
//                .offset(x = 100.dp, y = (-50).dp)
//                .size(36.dp)
//                .clickable { /* TODO: Handle save action */ },
//            tint = Color.Black
//        )
//    }
//}
//
//
//@Composable
//fun EditProfileTextFields(
//    label: String,
//    value: String,
//    onValueChange: (String) -> Unit,
//    keyboardType: KeyboardType = KeyboardType.Text
//) {
//    Column(modifier = Modifier.fillMaxWidth()) {
//        Text(text = label, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
//
//        OutlinedTextField(
//            value = value,
//            onValueChange = onValueChange,
//            modifier = Modifier.fillMaxWidth(),
//            shape = RoundedCornerShape(16.dp),
//
//            colors = OutlinedTextFieldDefaults.colors(
//                focusedContainerColor = Color.Gray.copy(alpha = 0.15f),
//                unfocusedContainerColor = Color.Gray.copy(alpha = 0.15f),
//                focusedBorderColor = Color.Transparent,
//                unfocusedBorderColor = Color.Transparent,
//                cursorColor = MaterialTheme.colorScheme.primary
//            ),
//            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
//            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
//            singleLine = true
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//    }
//}
//
//@Composable
//fun PasswordTextField(
//    label: String,
//    password: String,
//    onPasswordChange: (String) -> Unit,
//    passwordVisible: Boolean,
//    onPasswordVisibilityChange: (Boolean) -> Unit
//) {
//    Column(modifier = Modifier.fillMaxWidth()) {
//        Text(text = label, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
//        OutlinedTextField(
//            value = password,
//            onValueChange = onPasswordChange,
//            modifier = Modifier.fillMaxWidth(),
//            shape = RoundedCornerShape(16.dp),
//            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
//            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
//            trailingIcon = {
//
//                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
//                val description = if (passwordVisible) "Hide password" else "Show password"
//                IconButton(onClick = { onPasswordVisibilityChange(!passwordVisible) }) {
//                    Icon(imageVector = image, description)
//                }
//            },
//
//            colors = OutlinedTextFieldDefaults.colors(
//                focusedContainerColor = Color.Gray.copy(alpha = 0.15f),
//                unfocusedContainerColor = Color.Gray.copy(alpha = 0.15f),
//                focusedBorderColor = Color.Transparent,
//                unfocusedBorderColor = Color.Transparent,
//                cursorColor = MaterialTheme.colorScheme.primary,
//                focusedTrailingIconColor = Color.Black,
//                unfocusedTrailingIconColor = Color.Gray
//            ),
//            textStyle = LocalTextStyle.current.copy(fontSize = 16.sp),
//            singleLine = true
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//    }
//}
//
//@Composable
//fun PhoneNumberField(
//    label: String,
//    phoneNumber: String,
//    onPhoneNumberChange: (String) -> Unit
//) {
//    Column(modifier = Modifier.fillMaxWidth()) {
//        Text(text = label, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(bottom = 8.dp))
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(IntrinsicSize.Min)
//                .background(Color.Gray.copy(alpha = 0.15f), RoundedCornerShape(16.dp)),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//
//            Row(
//                modifier = Modifier
//                    .padding(horizontal = 16.dp, vertical = 14.dp)
//                    .clickable { /* TODO: Show country code picker */ },
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text("+977", fontSize = 16.sp)
//                Icon(Icons.Default.KeyboardArrowDown, contentDescription = "Select country code")
//            }
//
//
//            Box(
//                modifier = Modifier
//                    .width(1.dp)
//                    .fillMaxHeight()
//                    .padding(vertical = 8.dp)
//                    .background(Color.Gray.copy(alpha = 0.5f))
//            )
//
//
//            BasicTextField(
//                value = phoneNumber,
//                onValueChange = onPhoneNumberChange,
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
//                modifier = Modifier
//                    .padding(horizontal = 16.dp, vertical = 14.dp)
//                    .fillMaxWidth(),
//                textStyle = LocalTextStyle.current.copy(fontSize = 16.sp, color = MaterialTheme.colorScheme.onSurface),
//                singleLine = true
//            )
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//    }
//}