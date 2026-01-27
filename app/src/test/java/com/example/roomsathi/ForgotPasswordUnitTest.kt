package com.example.roomsathi

import com.example.roomsathi.repository.UserRepo
import com.example.roomsathi.viewmodel.UserViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mockito.kotlin.*

class ForgotPasswordUnitTest {

    @Test
    fun forgotPassword_Success_Test() {
        // 1. Setup
        val repo = mock<UserRepo>()
        val viewModel = UserViewModel(repo)
        val testEmail = "reset@example.com"

        // 2. Mock the behavior
        // Callback signature is (Boolean, String)
        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(1)
            callback.invoke(true, "Reset link sent successfully")
            null
        }.`when`(repo).forgetPassword(eq(testEmail), any())

        // 3. Execution
        var actualSuccess = false
        var actualMessage = ""

        viewModel.forgetPassword(testEmail) { success, msg ->
            actualSuccess = success
            actualMessage = msg
        }

        // 4. Assertions
        assertTrue(actualSuccess)
        assertEquals("Reset link sent successfully", actualMessage)

        // 5. Verification
        verify(repo).forgetPassword(eq(testEmail), any())
    }

    @Test
    fun forgotPassword_Failure_Test() {
        // 1. Setup
        val repo = mock<UserRepo>()
        val viewModel = UserViewModel(repo)
        val testEmail = "wrong@example.com"

        // 2. Mock the behavior for failure (e.g., user not found)
        doAnswer { invocation ->
            val callback = invocation.getArgument<(Boolean, String) -> Unit>(1)
            callback.invoke(false, "User not found")
            null
        }.`when`(repo).forgetPassword(eq(testEmail), any())

        // 3. Execution
        var actualSuccess = true // Start with true to ensure it flips to false
        var actualMessage = ""

        viewModel.forgetPassword(testEmail) { success, msg ->
            actualSuccess = success
            actualMessage = msg
        }

        // 4. Assertions
        assertTrue(!actualSuccess) // Should be false
        assertEquals("User not found", actualMessage)

        // 5. Verification
        verify(repo).forgetPassword(eq(testEmail), any())
    }
}