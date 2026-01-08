package com.example.roomsathi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.FileUpload
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Color constants for the dark theme

val DarkBackground = Color(0xFF121212)
val CardBackground = Color(0xFF2C2C2C)
val AccentBlue = Color(0xFF3D9AF0)


@Composable
fun SetupAccountScreen() {
    var selectedRole by remember { mutableStateOf("Home Owner") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Setup Your Account",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = "Complete your account to start your journey",
            fontSize = 14.sp,
            color = Color.Gray,
            modifier = Modifier.padding(top = 8.dp, bottom = 24.dp)
        )

        // Profile Picture Section
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.Gray)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("Profile Pictures", color = Color.White, fontWeight = FontWeight.Medium)
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { /* Action */ },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.FileUpload, contentDescription = null, tint = Color.Black)
                    Text("Upload Photo", color = Color.Black, modifier = Modifier.padding(start = 4.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            CustomInputField("First Name", "Kate", Modifier.weight(1f))
            Spacer(modifier = Modifier.width(12.dp))
            CustomInputField("Last Name", "Mepal", Modifier.weight(1f))
        }

        CustomInputField("Birth Date", "23/07/1996", trailingIcon = {
            Icon(Icons.Default.DateRange, contentDescription = null, tint = Color.Gray)
        })

        CustomInputField("Country", "Nepal", trailingIcon = {
            Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.Gray)
        })

        CustomInputField("City", "Suryabinayak")

        // Role Selection
        Spacer(modifier = Modifier.height(16.dp))
        Text("I am a:", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Medium)
        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            RoleCard(
                label = "Home Owner",
                isSelected = selectedRole == "Home Owner",
                modifier = Modifier.weight(1f),
                onClick = { selectedRole = "Home Owner" }
            )
            Spacer(modifier = Modifier.width(12.dp))
            RoleCard(
                label = "Home Seeker",
                isSelected = selectedRole == "Home Seeker",
                modifier = Modifier.weight(1f),
                onClick = { selectedRole = "Home Seeker" }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { /* Action */ },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.White),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Start Your Journey", color = Color.Black, fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = { /* Action */ },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Gray),
            border = null
        ) {
            Text("Skip", color = Color.Gray)
        }
    }
}

@Composable
fun CustomInputField(label: String, value: String, modifier: Modifier = Modifier, trailingIcon: @Composable (() -> Unit)? = null) {
    Column(modifier = modifier.padding(vertical = 8.dp)) {
        Text(label, color = Color.White, fontSize = 14.sp, modifier = Modifier.padding(bottom = 8.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .background(CardBackground, RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = value, color = Color.LightGray, modifier = Modifier.weight(1f))
                trailingIcon?.invoke()
            }
        }
    }
}

@Composable
fun RoleCard(label: String, isSelected: Boolean, modifier: Modifier, onClick: () -> Unit) {
    Box(
        modifier = modifier
            .height(60.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(CardBackground)
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) AccentBlue else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(
                selected = isSelected,
                onClick = null,
                colors = RadioButtonDefaults.colors(selectedColor = AccentBlue)
            )
            Text(label, color = Color.White, fontSize = 14.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewSetupAccount() {
    SetupAccountScreen()
}