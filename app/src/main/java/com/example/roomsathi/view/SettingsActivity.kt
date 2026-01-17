package com.example.roomsathi.view

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
        setContent {
            var showDeleteDialog by remember { mutableStateOf(false) }
            var isDeleting by remember { mutableStateOf(false) }

            Box(modifier = Modifier.fillMaxSize()) {
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
                            .background(Color.Black.copy(alpha = 0.6f)),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Yellow)
                    }
                }

                // 2. Confirmation Dialog
                if (showDeleteDialog) {
                    AlertDialog(
                        onDismissRequest = { showDeleteDialog = false },
                        containerColor = DarkBlue,
                        title = { Text("Delete Account?", color = White) },
                        text = { Text("This will permanently remove your profile and all your posted rooms. This cannot be undone.", color = Color.LightGray) },
                        confirmButton = {
                            TextButton(onClick = {
                                showDeleteDialog = false
                                isDeleting = true
                                performFullDelete()
                            }) {
                                Text("Delete Everything", color = Color.Red)
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

        // Step 1: Find all properties to delete their associated image slots
        db.child("Properties").orderByChild("ownerId").equalTo(userId)
            .get().addOnSuccessListener { snapshot ->
                val batchUpdates = mutableMapOf<String, Any?>()

                snapshot.children.forEach { propSnapshot ->
                    val propId = propSnapshot.key ?: ""
                    val indexOfImages = propSnapshot.child("indexOfImages").getValue(Long::class.java) ?: -1L

                    // Queue Property removal
                    batchUpdates["Properties/$propId"] = null

                    // Queue 8 Image Slots removal
                    if (indexOfImages != -1L) {
                        for (i in 0..7) {
                            batchUpdates["Images/${indexOfImages + i}"] = null
                        }
                    }
                }

                // Step 2: Queue User Profile and Chat metadata removal
                batchUpdates["users/$userId"] = null
                batchUpdates["user_chats/$userId"] = null

                // Step 3: Atomic database update
                db.updateChildren(batchUpdates).addOnCompleteListener { dbTask ->
                    if (dbTask.isSuccessful) {
                        // Step 4: Finally, delete the Auth Account
                        user.delete().addOnCompleteListener { authTask ->
                            if (authTask.isSuccessful) {
                                Toast.makeText(this, "Account Deleted", Toast.LENGTH_LONG).show()
                                navigateToLogin()
                            } else {
                                // Security: Firebase needs a recent login to delete an account
                                Toast.makeText(this, "Security: Please log in again to delete account", Toast.LENGTH_LONG).show()
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
        // This clears the entire app history so the user can't press 'Back'
        val intent = Intent(this, LoginUi::class.java) // Ensure this matches your Login Activity name
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsBody(onBack: () -> Unit, onChangePassword: () -> Unit, onDeleteAccount: () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Account Settings", color = White, fontSize = 18.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(painterResource(R.drawable.baseline_arrow_back_ios_24), null, tint = White)
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = DarkBlue)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(DarkBlue)
                .padding(16.dp)
        ) {
            ProfileItem(R.drawable.outline_key_vertical_24, "Change Password", onChangePassword)
            HorizontalDivider(color = White.copy(0.1f))
            ProfileItem(R.drawable.baseline_delete_24, "Delete Account", onDeleteAccount, isLogout = true)
        }
    }
}