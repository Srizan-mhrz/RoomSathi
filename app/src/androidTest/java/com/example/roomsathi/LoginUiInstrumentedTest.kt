package com.example.roomsathi

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.roomsathi.view.LoginScreen
import com.example.roomsathi.ui.theme.RoomSathiTheme
import org.junit.Rule
import org.junit.Test

class LoginUiTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testLoginInputAndValidation() {
        // 1. Start the Login Screen
        composeTestRule.setContent {
            RoomSathiTheme {
                LoginScreen()
            }
        }

        // 2. Check if header text exists
        composeTestRule.onNodeWithText("Welcome Back!").assertIsDisplayed()

        // 3. Enter Email
        composeTestRule.onNodeWithTag("loginEmailField")
            .performTextInput("test@example.com")

        // 4. Enter Password
        composeTestRule.onNodeWithTag("loginPasswordField")
            .performTextInput("password123")

        // 5. Click Sign In
        composeTestRule.onNodeWithTag("signInButton").performClick()

        // Verify the UI is responsive (button still exists if login is processing)
        composeTestRule.onNodeWithTag("signInButton").assertExists()
    }

    @Test
    fun testEmptyFieldsErrorHandling() {
        composeTestRule.setContent {
            RoomSathiTheme {
                LoginScreen()
            }
        }

        // Try to click sign in with empty fields
        composeTestRule.onNodeWithTag("signInButton").performClick()

        // Verify we are still on the login page
        composeTestRule.onNodeWithText("Welcome Back!").assertIsDisplayed()
    }

    @Test
    fun testNavigationToRegistration() {
        composeTestRule.setContent {
            RoomSathiTheme {
                LoginScreen()
            }
        }

        // Click the "Sign Up" text at the bottom
        composeTestRule.onNodeWithText("Sign Up").performClick()
    }
}