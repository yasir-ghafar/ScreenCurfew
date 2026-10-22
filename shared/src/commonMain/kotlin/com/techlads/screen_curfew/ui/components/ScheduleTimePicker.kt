package com.techlads.screen_curfew.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerDialog
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.techlads.screen_curfew.ui.schedules.create_schedule.ScheduleTime
import com.techlads.screen_curfew.ui.theme.ScreenCurfewTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScheduleTimePicker(
    title: String,
    initialTime: ScheduleTime?,
    onConfirm: (ScheduleTime) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val pickerState = rememberTimePickerState(
        initialHour = initialTime?.hour ?: 22,
        initialMinute = initialTime?.minute ?: 30,
        is24Hour = false,
    )

    val colors = TimePickerDefaults.colors(
        clockDialColor = ScreenCurfewTheme.colors.elevated,
        clockDialSelectedContentColor = ScreenCurfewTheme.colors.ink,
        clockDialUnselectedContentColor = ScreenCurfewTheme.colors.cream,
        selectorColor = ScreenCurfewTheme.colors.moon,
        containerColor = MaterialTheme.colorScheme.surface,
        periodSelectorBorderColor = ScreenCurfewTheme.colors.line,
        periodSelectorSelectedContainerColor = ScreenCurfewTheme.colors.moon,
        periodSelectorUnselectedContainerColor = ScreenCurfewTheme.colors.elevated,
        periodSelectorSelectedContentColor = ScreenCurfewTheme.colors.ink,
        periodSelectorUnselectedContentColor = ScreenCurfewTheme.colors.muted,
        timeSelectorSelectedContainerColor = ScreenCurfewTheme.colors.moon,
        timeSelectorUnselectedContainerColor = ScreenCurfewTheme.colors.elevated,
        timeSelectorSelectedContentColor = ScreenCurfewTheme.colors.ink,
        timeSelectorUnselectedContentColor = ScreenCurfewTheme.colors.cream,
    )

    TimePickerDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = ScreenCurfewTheme.colors.cream,
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(
                        ScheduleTime(
                            hour = pickerState.hour,
                            minute = pickerState.minute,
                        ),
                    )
                },
            ) {
                Text(
                    text = "OK",
                    color = ScreenCurfewTheme.colors.moon,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(
                    text = "Cancel",
                    color = ScreenCurfewTheme.colors.muted,
                    style = MaterialTheme.typography.labelLarge,
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
    ) {
        TimePicker(
            state = pickerState,
            colors = colors,
        )
    }
}
