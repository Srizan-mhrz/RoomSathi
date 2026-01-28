package com.example.roomsathi

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.roomsathi.view.RegistrationScreen
import com.example.roomsathi.ui.theme.RoomSathiTheme
import org.junit.Rule
import org.junit.Test

class RegistrationUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testRegistrationFieldsAndValidation() {
        // Start the Registration Screen
        composeTestRule.setContent {
            RoomSathiTheme {
                RegistrationScreen()
            }
        }

        // 1. Verify Screen Title exists
        composeTestRule.onNodeWithText("Create Account").assertIsDisplayed()

        // 2. Input data using the new Test Tags
        // This targets the OutlinedTextField directly, avoiding the label error
        composeTestRule.onNodeWithTag("fullNameField").performTextInput("John Doe")
        composeTestRule.onNodeWithTag("emailField").performTextInput("john@example.com")
        composeTestRule.onNodeWithTag("phoneField").performTextInput("9876543210")
        composeTestRule.onNodeWithTag("passwordField").performTextInput("password123")

        // 3. Click the Image Picker (Optional verification)
        composeTestRule.onNodeWithTag("profileImagePicker").assertHasClickAction()

        // 4. Click the SIGN UP button using its Tag
        composeTestRule.onNodeWithTag("signUpButton").performClick()

        // Note: Since clicking the button triggers Firebase/Toasts,
        // the test will finish here. In a real CI environment, you'd mock the VM.
    }

    @Test
    fun testEmptyFieldsShowValidationError() {
        composeTestRule.setContent {
            RoomSathiTheme {
                RegistrationScreen()
            }
        }

        // Click Sign Up without entering anything
        composeTestRule.onNodeWithTag("signUpButton").performClick()

        // Verify we are still on the registration screen (didn't finish the activity)
        composeTestRule.onNodeWithText("Create Account").assertIsDisplayed()
    }
}