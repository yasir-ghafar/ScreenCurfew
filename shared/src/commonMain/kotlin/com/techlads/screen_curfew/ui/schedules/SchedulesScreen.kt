package com.techlads.screen_curfew.ui.schedules

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.techlads.screen_curfew.ui.theme.ScreenCurfewTheme

private val DayLabels = listOf("S", "M", "T", "W", "T", "F", "S")

data class ScheduleItem(
    val id: String,
    val name: String,
    val detail: String,
    val enabled: Boolean,
    val activeDays: Set<Int>? = null,
    val footer: String? = null,
)

@Composable
fun SchedulesScreen(
    modifier: Modifier = Modifier,
    schedules: List<ScheduleItem> = sampleSchedules,
    onToggleSchedule: (String, Boolean) -> Unit = { _, _ -> },
    onAddSchedule: () -> Unit = {},
) {
    var items by remember(schedules) { mutableStateOf(schedules) }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .padding(top = 8.dp, bottom = 96.dp),
        ) {
            Text(
                text = "Schedules",
                style = MaterialTheme.typography.displayLarge,
                color = ScreenCurfewTheme.colors.cream,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Weekday and weekend profiles. One tap to pause.",
                style = MaterialTheme.typography.bodyLarge,
                color = ScreenCurfewTheme.colors.muted,
            )
            Spacer(modifier = Modifier.height(28.dp))

            items.forEachIndexed { index, schedule ->
                ScheduleCard(
                    schedule = schedule,
                    onEnabledChange = { enabled ->
                        items = items.map {
                            if (it.id == schedule.id) it.copy(enabled = enabled) else it
                        }
                        onToggleSchedule(schedule.id, enabled)
                    },
                )
                if (index != items.lastIndex) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

        AddScheduleFab(
            onClick = onAddSchedule,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 24.dp, bottom = 24.dp),
        )
    }
}

@Composable
private fun ScheduleCard(
    schedule: ScheduleItem,
    onEnabledChange: (Boolean) -> Unit,
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
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = schedule.name,
                    style = MaterialTheme.typography.titleLarge,
                    color = ScreenCurfewTheme.colors.cream,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = schedule.detail,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                    color = ScreenCurfewTheme.colors.muted,
                )
            }
            ScheduleSwitch(
                checked = schedule.enabled,
                onCheckedChange = onEnabledChange,
            )
        }

        val days = schedule.activeDays
        if (days != null) {
            Spacer(modifier = Modifier.height(16.dp))
            DaySelector(activeDays = days)
            if (schedule.footer != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = schedule.footer,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                    color = ScreenCurfewTheme.colors.muted,
                )
            }
        }
    }
}

@Composable
private fun DaySelector(
    activeDays: Set<Int>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        DayLabels.forEachIndexed { index, label ->
            val active = index in activeDays
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .background(
                        color = if (active) {
                            ScreenCurfewTheme.colors.moon
                        } else {
                            ScreenCurfewTheme.colors.elevated
                        },
                        shape = CircleShape,
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.sp,
                    ),
                    color = if (active) {
                        ScreenCurfewTheme.colors.ink
                    } else {
                        ScreenCurfewTheme.colors.muted
                    },
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
private fun ScheduleSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Switch(
        checked = checked,
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

@Composable
private fun AddScheduleFab(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(56.dp)
            .background(
                color = ScreenCurfewTheme.colors.moon,
                shape = RoundedCornerShape(18.dp),
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "+",
            style = MaterialTheme.typography.displayMedium.copy(
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 28.sp,
            ),
            color = ScreenCurfewTheme.colors.ink,
        )
    }
}

private val sampleSchedules = listOf(
    ScheduleItem(
        id = "weeknights",
        name = "Weeknights",
        detail = "10:30 PM – 7:00 AM",
        enabled = true,
        activeDays = setOf(1, 2, 3, 4, 5),
        footer = "Overlay lock · DND on",
    ),
    ScheduleItem(
        id = "weekends",
        name = "Weekends",
        detail = "11:30 PM – 8:00 AM",
        enabled = true,
        activeDays = setOf(0, 6),
        footer = "Soft reminder",
    ),
    ScheduleItem(
        id = "vacation",
        name = "Vacation",
        detail = "Paused until Apr 20",
        enabled = false,
    ),
)

@Preview
@Composable
private fun SchedulesScreenPreview() {
    ScreenCurfewTheme {
        SchedulesScreen(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
        )
    }
}
