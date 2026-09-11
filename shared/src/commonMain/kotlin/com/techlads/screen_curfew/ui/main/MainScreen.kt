package com.techlads.screen_curfew.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.techlads.screen_curfew.ui.components.AppBottomBar
import com.techlads.screen_curfew.ui.home.HomeScreen
import com.techlads.screen_curfew.ui.navigation.AppDestination
import com.techlads.screen_curfew.ui.theme.ScreenCurfewTheme

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    initialDestination: AppDestination = AppDestination.Tonight,
) {
    var selected by remember { mutableStateOf(initialDestination) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .safeContentPadding(),
        ) {
            when (selected) {
                AppDestination.Tonight -> HomeScreen()
                AppDestination.Schedules -> PlaceholderTab(
                    title = "Schedules",
                    body = "Weekday and weekend profiles. One tap to pause.",
                )
                AppDestination.Insights -> PlaceholderTab(
                    title = "Insights",
                    body = "Progress without shame — streak, adherence, and calm trends.",
                )
                AppDestination.Family -> PlaceholderTab(
                    title = "Family",
                    body = "Shared bedtime goals for the people you wind down with.",
                )
            }
        }
        AppBottomBar(
            selected = selected,
            onSelect = { selected = it },
        )
    }
}

@Composable
private fun PlaceholderTab(
    title: String,
    body: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
            .padding(top = 8.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.displayLarge,
            color = ScreenCurfewTheme.colors.cream,
        )
        Text(
            text = body,
            style = MaterialTheme.typography.bodyLarge,
            color = ScreenCurfewTheme.colors.muted,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(top = 8.dp),
        )
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    ScreenCurfewTheme {
        MainScreen()
    }
}
