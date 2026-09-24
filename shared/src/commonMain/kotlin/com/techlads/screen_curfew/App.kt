package com.techlads.screen_curfew

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.techlads.screen_curfew.ui.main.MainScreen
import com.techlads.screen_curfew.ui.navigation.AppDestination
import com.techlads.screen_curfew.ui.onboarding.OnboardingScreen
import com.techlads.screen_curfew.ui.theme.ScreenCurfewTheme

@Composable
@Preview
fun App() {
    ScreenCurfewTheme {
        var showOnboarding by remember { mutableStateOf(false) }

        if (showOnboarding) {
            OnboardingScreen(
                onGetStarted = { showOnboarding = false },
                onAlreadyHaveAccount = { showOnboarding = false },
            )
        } else {
            MainScreen(initialDestination = AppDestination.Schedules)
        }
    }
}
