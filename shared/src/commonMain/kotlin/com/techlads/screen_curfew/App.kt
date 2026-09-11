package com.techlads.screen_curfew

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.techlads.screen_curfew.ui.onboarding.OnboardingScreen
import com.techlads.screen_curfew.ui.theme.ScreenCurfewTheme

@Composable
@Preview
fun App() {
    ScreenCurfewTheme {
        OnboardingScreen(
            onGetStarted = {
                // Navigate to permissions / setup in a later step
            },
            onAlreadyHaveAccount = {
                // Sign-in flow will be wired later
            },
        )
    }
}
