package com.example.roomsathi

import android.net.Uri
import com.example.roomsathi.model.UserModel
import com.example.roomsathi.repository.UserRepo
import com.example.roomsathi.viewmodel.UserViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.*

class RegistrationUnitTest {

    @Test
    fun register_user_complete_success_test() {
        // 1. Setup Mocks
        val repo = mock<UserRepo>()
        val viewModel = UserViewModel(repo)
        val mockUri = mock<Uri>()

        val testEmail = "test@example.com"
        val testPass = "123456"
        val testUserId = "user_123"
        val testImageUrl = "https://image.com/profile.jpg"

        // 2. Mock register (Step 1)
        // Callback: (Boolean, String, String) -> Unit
        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String, String) -> Unit>(2)
            callback.invoke(true, "Success", testUserId)
            null
        }.`when`(repo).register(eq(testEmail), eq(testPass), any())

        // 3. Mock uploadProfilePicture (Step 2)
        // Interface says: (Boolean, String, String?)
        // THE FIX: We must pass a String (like "") for the second parameter, not null.
        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String, String?) -> Unit>(1)
            callback.invoke(true, "No Error", testImageUrl)
            null
        }.`when`(repo).uploadProfilePicture(eq(mockUri), any())

        // 4. Mock addUserToDatabase (Step 3)
        // Callback: (Boolean, String) -> Unit
        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(2)
            callback.invoke(true, "User added")
            null
        }.`when`(repo).addUserToDatabase(eq(testUserId), any(), any())

        // 5. Execution
        var isFinalSuccess = false
        var messageResult = ""

        viewModel.register(testEmail, testPass) { success, msg, userId ->
            if (success) {
                viewModel.uploadProfilePicture(mockUri) { uploadSuccess, uploadError, url ->
                    if (uploadSuccess) {
                        val model = UserModel(
                            userId = userId,
                            fullName = "Test User",
                            phoneNumber = "9800000000",
                            email = testEmail,
                            profileImageUrl = url ?: ""
                        )
                        viewModel.addUserToDatabase(userId, model) { dbSuccess, dbMsg ->
                            isFinalSuccess = dbSuccess
                            messageResult = dbMsg
                        }
                    }
                }
            }
        }

        // 6. Assertions
        assertTrue(isFinalSuccess)
        assertEquals("User added", messageResult)

        // 7. Verify calls
        verify(repo).register(eq(testEmail), eq(testPass), any())
        verify(repo).uploadProfilePicture(eq(mockUri), any())
        verify(repo).addUserToDatabase(eq(testUserId), any(), any())
    }
}