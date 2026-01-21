package com.example.roomsathi.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.roomsathi.R
import com.example.roomsathi.ProfileItem
import com.example.roomsathi.ui.theme.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Added to ensure color goes behind status bars
        setContent {
            var showDeleteDialog by remember { mutableStateOf(false) }
            var isDeleting by remember { mutableStateOf(false) }

            // Root container to fix all white gaps
            Box(modifier = Modifier.fillMaxSize().background(LightBlue)) {
                SettingsBody(
                    onBack = { finish() },
                    onChangePassword = { sendResetEmail() },
                    onDeleteAccount = { showDeleteDialog = true }
                )

                // 1. Loading Overlay
                if (isDeleting) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.7f)),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Yellow)
                    }
                }

                // 2. Confirmation Dialog (Themed)
                if (showDeleteDialog) {
                    AlertDialog(
                        onDismissRequest = { showDeleteDialog = false },
                        containerColor = DarkBlue, // Keeping your color or swap to LightBlue
                        title = { Text("Delete Account?", color = White, fontWeight = FontWeight.Bold) },
                        text = { Text("This will permanently remove your profile and all your posted rooms. This cannot be undone.", color = Color.LightGray) },
                        confirmButton = {
                            TextButton(onClick = {
                                showDeleteDialog = false
                                isDeleting = true
                                performFullDelete()
                            }) {
                                Text("Delete Everything", color = Color.Red, fontWeight = FontWeight.ExtraBold)
                            }
                        },
                        dismissButton = {
                            TextButton(onClick = { showDeleteDialog = false }) {
                                Text("Cancel", color = White)
                            }
                        }
                    )
                }
            }
        }
    }

    private fun sendResetEmail() {
        val userEmail = FirebaseAuth.getInstance().currentUser?.email
        if (userEmail != null) {
            FirebaseAuth.getInstance().sendPasswordResetEmail(userEmail)
                .addOnCompleteListener { task ->
                    val msg = if (task.isSuccessful) "Reset email sent!" else "Failed to send email."
                    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun performFullDelete() {
        val auth = FirebaseAuth.getInstance()
        val user = auth.currentUser ?: return
        val userId = user.uid
        val db = FirebaseDatabase.getInstance().reference

        db.child("Properties").orderByChild("ownerId").equalTo(userId)
            .get().addOnSuccessListener { snapshot ->
                val batchUpdates = mutableMapOf<String, Any?>()
                snapshot.children.forEach { propSnapshot ->
                    val propId = propSnapshot.key ?: ""
                    val indexOfImages = propSnapshot.child("indexOfImages").getValue(Long::class.java) ?: -1L
                    batchUpdates["Properties/$propId"] = null
                    if (indexOfImages != -1L) {
                        for (i in 0..7) {
                            batchUpdates["Images/${indexOfImages + i}"] = null
                        }
                    }
                }
                batchUpdates["users/$userId"] = null
                batchUpdates["user_chats/$userId"] = null

                db.updateChildren(batchUpdates).addOnCompleteListener { dbTask ->
                    if (dbTask.isSuccessful) {
                        user.delete().addOnCompleteListener { authTask ->
                            if (authTask.isSuccessful) {
                                Toast.makeText(this, "Account Deleted", Toast.LENGTH_LONG).show()
                                navigateToLogin()
                            } else {
                                Toast.makeText(this, "Security: Please log in again to delete account", Toast.LENGTH_SHORT).show()
                                auth.signOut()
                                navigateToLogin()
                            }
                        }
                    } else {
                        Toast.makeText(this, "Error clearing data", Toast.LENGTH_SHORT).show()
                    }
                }
            }.addOnFailureListener {
                Toast.makeText(this, "Failed to connect to database", Toast.LENGTH_SHORT).show()
            }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginUi::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}

@Composable
fun SettingsBody(onBack: () -> Unit, onChangePassword: () -> Unit, onDeleteAccount: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding() // Eliminates top gap
    ) {
        // Custom Themed Top Bar
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(painterResource(R.drawable.baseline_arrow_back_ios_24), null, tint = White, modifier = Modifier.size(20.dp))
            }
            Text(
                "Account Settings",
                color = White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // UI UPDATED: Using GlassSurface to wrap items according to theme
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            GlassSurface(
                modifier = Modifier.fillMaxWidth(),
                containerColor = Color.White.copy(alpha = 0.1f)
            ) {
                Column {
                    ProfileItem(R.drawable.outline_key_vertical_24, "Change Password", onChangePassword)

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = White.copy(0.1f),
                        thickness = 0.5.dp
                    )

                    ProfileItem(
                        R.drawable.baseline_delete_24,
                        "Delete Account",
                        onDeleteAccount,
                        isLogout = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "Room Sathi v1.0.2",
                color = White.copy(alpha = 0.3f),
                fontSize = 12.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}