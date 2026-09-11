package com.techlads.screen_curfew.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.techlads.screen_curfew.ui.components.CrescentMoon
import com.techlads.screen_curfew.ui.components.GhostButton
import com.techlads.screen_curfew.ui.components.PrimaryButton
import com.techlads.screen_curfew.ui.theme.PillShape
import com.techlads.screen_curfew.ui.theme.ScreenCurfewTheme

@Composable
fun OnboardingScreen(
    onGetStarted: () -> Unit,
    onAlreadyHaveAccount: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .safeContentPadding()
            .padding(horizontal = 24.dp)
            .padding(top = 8.dp, bottom = 28.dp),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            CrescentMoon(size = 120.dp)
            Spacer(modifier = Modifier.height(28.dp))
            Text(
                text = "SCREENCURFEW",
                style = MaterialTheme.typography.labelSmall,
                color = ScreenCurfewTheme.colors.moon,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Protect the hours\nthat restore you",
                style = MaterialTheme.typography.displayLarge,
                color = ScreenCurfewTheme.colors.cream,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "A gentle bedtime lock that quiets the phone so sleep can start on time — without shame, without tricks.",
                style = MaterialTheme.typography.bodyLarge,
                color = ScreenCurfewTheme.colors.muted,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 8.dp),
            )
            Spacer(modifier = Modifier.height(28.dp))
            PlatformCapabilityCard()
            Spacer(modifier = Modifier.height(24.dp))
        }

        PrimaryButton(
            text = "Get started",
            onClick = onGetStarted,
        )
        Spacer(modifier = Modifier.height(8.dp))
        GhostButton(
            text = "I already have an account",
            onClick = onAlreadyHaveAccount,
        )
    }
}

@Composable
private fun PlatformCapabilityCard(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surface,
                shape = MaterialTheme.shapes.large,
            )
            .border(
                width = 1.dp,
                color = ScreenCurfewTheme.colors.line,
                shape = MaterialTheme.shapes.large,
            )
            .padding(horizontal = 18.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        CapabilityRow(
            chipLabel = "Android lock",
            chipTextColor = ScreenCurfewTheme.colors.sage,
            chipBackground = ScreenCurfewTheme.colors.chipAndroidBg,
            detail = "Overlay + DND",
        )
        CapabilityRow(
            chipLabel = "iOS companion",
            chipTextColor = ScreenCurfewTheme.colors.moon,
            chipBackground = ScreenCurfewTheme.colors.chipIosBg,
            detail = "Focus + tracking",
        )
    }
}

@Composable
private fun CapabilityRow(
    chipLabel: String,
    chipTextColor: Color,
    chipBackground: Color,
    detail: String,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = chipLabel,
            style = MaterialTheme.typography.labelMedium.copy(
                letterSpacing = MaterialTheme.typography.labelMedium.letterSpacing,
            ),
            color = chipTextColor,
            modifier = Modifier
                .background(chipBackground, PillShape)
                .padding(horizontal = 10.dp, vertical = 6.dp),
        )
        Text(
            text = detail,
            style = MaterialTheme.typography.bodyMedium,
            color = ScreenCurfewTheme.colors.muted,
        )
    }
}

@Preview
@Composable
private fun OnboardingScreenPreview() {
    ScreenCurfewTheme {
        OnboardingScreen(
            onGetStarted = {},
            onAlreadyHaveAccount = {},
        )
    }
}
