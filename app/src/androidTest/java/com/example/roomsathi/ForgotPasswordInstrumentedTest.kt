package com.example.roomsathi

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.example.roomsathi.view.ForgotPasswordScreen
import com.example.roomsathi.ui.theme.RoomSathiTheme
import org.junit.Rule
import org.junit.Test

class ForgotPasswordUITest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testForgotPasswordFlow() {
        // Start the Forgot Password Screen
        composeTestRule.setContent {
            RoomSathiTheme {
                ForgotPasswordScreen()
            }
        }

        // 1. Verify static text is displayed
        composeTestRule.onNodeWithText("Reset Your Password").assertIsDisplayed()

        // 2. Verify button is initially disabled (since email is blank)
        composeTestRule.onNodeWithTag("resetButton").assertIsNotEnabled()

        // 3. Enter an email address
        composeTestRule.onNodeWithTag("forgotEmailField")
            .performTextInput("user@example.com")

        // 4. Verify button is now enabled
        composeTestRule.onNodeWithTag("resetButton").assertIsEnabled()

        // 5. Click the Send Reset Link button
        composeTestRule.onNodeWithTag("resetButton").performClick()
    }

    @Test
    fun testBackButtonFunctionality() {
        composeTestRule.setContent {
            RoomSathiTheme {
                ForgotPasswordScreen()
            }
        }

        // Find the back button (IconButton with contentDescription "Back")
        composeTestRule.onNodeWithContentDescription("Back").assertHasClickAction()
        composeTestRule.onNodeWithContentDescription("Back").performClick()
    }
}