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

            SettingsBody(
                onBack = { finish() },
                onChangePassword = { sendResetEmail() },
                onDeleteAccount = { showDeleteDialog = true }
            )

            if (showDeleteDialog) {
                AlertDialog(
                    onDismissRequest = { showDeleteDialog = false },
                    containerColor = DarkBlue,
                    title = { Text("Delete Account?", color = White) },
                    text = { Text("This will permanently remove your profile and all your posted rooms. This cannot be undone.", color = Color.LightGray) },
                    confirmButton = {
                        TextButton(onClick = {
                            showDeleteDialog = false
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

        // 1. Fetch all properties owned by this user
        db.child("Properties").orderByChild("ownerId").equalTo(userId)
            .get().addOnSuccessListener { snapshot ->
                val batchUpdates = mutableMapOf<String, Any?>()

                snapshot.children.forEach { propSnapshot ->
                    val propId = propSnapshot.key ?: ""
                    val indexOfImages = propSnapshot.child("indexOfImages").getValue(Long::class.java) ?: -1L

                    // 2. Queue Property for deletion
                    batchUpdates["Properties/$propId"] = null

                    // 3. Queue 8 Image Slots for deletion
                    if (indexOfImages != -1L) {
                        for (i in 0..7) {
                            batchUpdates["Images/${indexOfImages + i}"] = null
                        }
                    }
                }

                // 4. Queue the User Profile for deletion
                batchUpdates["users/$userId"] = null
                // 5. Queue User Chats for deletion
                batchUpdates["user_chats/$userId"] = null

                // Execute all database deletions at once
                db.updateChildren(batchUpdates).addOnCompleteListener { dbTask ->
                    if (dbTask.isSuccessful) {
                        // 6. Finally, delete the Auth Account
                        user.delete().addOnCompleteListener { authTask ->
                            if (authTask.isSuccessful) {
                                Toast.makeText(this, "Account Deleted", Toast.LENGTH_LONG).show()
                                // Restart App to Login Screen
                                val intent = packageManager.getLaunchIntentForPackage(packageName)
                                intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                startActivity(intent)
                                finish()
                            } else {
                                // If this fails, it's usually because the user needs to re-login
                                Toast.makeText(this, "Please re-login to verify and try again.", Toast.LENGTH_LONG).show()
                                auth.signOut()
                                finish()
                            }
                        }
                    }
                }
            }
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
            modifier = Modifier.fillMaxSize().padding(padding).background(DarkBlue).padding(16.dp)
        ) {
            ProfileItem(R.drawable.outline_key_vertical_24, "Change Password", onChangePassword)
            HorizontalDivider(color = White.copy(0.1f))
            ProfileItem(R.drawable.baseline_delete_24, "Delete Account", onDeleteAccount, isLogout = true)
        }
    }
}