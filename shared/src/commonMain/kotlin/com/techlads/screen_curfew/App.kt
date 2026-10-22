package com.techlads.screen_curfew

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.techlads.screen_curfew.ui.navigation.ScreenCurfewNavHost
import com.techlads.screen_curfew.ui.theme.ScreenCurfewTheme

@Composable
@Preview
fun App() {
    ScreenCurfewTheme {
        ScreenCurfewNavHost()
    }
}
