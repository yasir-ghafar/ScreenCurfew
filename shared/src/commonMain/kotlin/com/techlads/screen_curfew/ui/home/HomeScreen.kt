package com.techlads.screen_curfew.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techlads.screen_curfew.ui.theme.PillShape
import com.techlads.screen_curfew.ui.theme.ScreenCurfewTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    hoursUntilLock: String = "2:14",
    scheduleSummary: String = "hours until lock · Weeknights 10:30 PM – 7:00 AM",
    streakLabel: String = "12-night streak",
    adherencePercent: String = "86%",
    overrideCount: String = "1",
    allowedAppsSummary: String = "Phone, Clock, Alarms",
    softReminderEnabled: Boolean = true,
    onSoftReminderChange: (Boolean) -> Unit = {},
    onEditAllowedApps: () -> Unit = {},
) {
    var reminderOn by remember(softReminderEnabled) { mutableStateOf(softReminderEnabled) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
            .padding(top = 8.dp, bottom = 28.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "TONIGHT",
                    style = MaterialTheme.typography.labelMedium,
                    color = ScreenCurfewTheme.colors.moon,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Wind-down in",
                    style = MaterialTheme.typography.displayLarge,
                    color = ScreenCurfewTheme.colors.cream,
                )
            }
            Text(
                text = streakLabel,
                style = MaterialTheme.typography.labelMedium.copy(
                    letterSpacing = 0.sp,
                    fontSize = 12.sp,
                ),
                color = ScreenCurfewTheme.colors.sage,
                modifier = Modifier
                    .background(ScreenCurfewTheme.colors.chipAndroidBg, PillShape)
                    .padding(horizontal = 10.dp, vertical = 6.dp),
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = hoursUntilLock,
            style = MaterialTheme.typography.displayLarge.copy(
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Medium,
                fontSize = 48.sp,
                lineHeight = 52.sp,
                letterSpacing = (-1).sp,
            ),
            color = ScreenCurfewTheme.colors.cream,
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = scheduleSummary,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
            color = ScreenCurfewTheme.colors.muted,
        )

        Spacer(modifier = Modifier.height(28.dp))

        SoftReminderCard(
            enabled = reminderOn,
            onCheckedChange = { checked ->
                reminderOn = checked
                onSoftReminderChange(checked)
            },
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            StatCard(
                label = "Adherence",
                value = adherencePercent,
                detail = "this week",
                modifier = Modifier.weight(1f),
            )
            StatCard(
                label = "Overrides",
                value = overrideCount,
                detail = "15 min pause",
                modifier = Modifier.weight(1f),
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        AllowedAppsCard(
            summary = allowedAppsSummary,
            onEdit = onEditAllowedApps,
        )
    }
}

@Composable
private fun SoftReminderCard(
    enabled: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        ScreenCurfewTheme.colors.elevated,
                        MaterialTheme.colorScheme.surface,
                    ),
                ),
                shape = MaterialTheme.shapes.large,
            )
            .border(
                width = 1.dp,
                color = ScreenCurfewTheme.colors.line,
                shape = MaterialTheme.shapes.large,
            )
            .padding(horizontal = 18.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = if (enabled) "Soft reminder is on" else "Soft reminder is off",
                style = MaterialTheme.typography.titleLarge,
                color = ScreenCurfewTheme.colors.cream,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "A 15-minute warning before overlay lock",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                color = ScreenCurfewTheme.colors.muted,
            )
        }
        Switch(
            checked = enabled,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.White,
                checkedTrackColor = ScreenCurfewTheme.colors.moon,
                uncheckedThumbColor = Color.White,
                uncheckedTrackColor = Color(0xFF3A4460),
                uncheckedBorderColor = Color.Transparent,
                checkedBorderColor = Color.Transparent,
            ),
        )
    }
}

@Composable
private fun StatCard(
    label: String,
    value: String,
    detail: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
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
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
            color = ScreenCurfewTheme.colors.muted,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.displayMedium,
            color = ScreenCurfewTheme.colors.cream,
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = detail,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
            color = ScreenCurfewTheme.colors.muted,
        )
    }
}

@Composable
private fun AllowedAppsCard(
    summary: String,
    onEdit: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
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
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Allowed at bedtime",
                style = MaterialTheme.typography.titleLarge,
                color = ScreenCurfewTheme.colors.cream,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = summary,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                color = ScreenCurfewTheme.colors.muted,
            )
        }
        Text(
            text = "Edit",
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
            color = ScreenCurfewTheme.colors.muted,
            modifier = Modifier.clickable(onClick = onEdit),
        )
    }
}

@Preview
@Composable
private fun HomeScreenPreview() {
    ScreenCurfewTheme {
        HomeScreen(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
        )
    }
}
