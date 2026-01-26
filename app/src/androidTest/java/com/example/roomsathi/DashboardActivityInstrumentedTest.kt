package com.example.roomsathi

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import com.example.roomsathi.view.RegistrationActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DashboardInstrumentedTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<DashboardActivity>()

    @Test
    fun dashboard_logoutButton_isDisplayed() {
        composeRule
            .onNodeWithTag("logout")
            .assertIsDisplayed()
    }
}

