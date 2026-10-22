package com.techlads.screen_curfew.ui.schedules.create_schedule

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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.techlads.screen_curfew.ui.components.PrimaryButton
import com.techlads.screen_curfew.ui.components.ScheduleTimePicker
import com.techlads.screen_curfew.ui.theme.ScreenCurfewTheme

private val DayLabels = listOf("S", "M", "T", "W", "T", "F", "S")
private val FieldShape = RoundedCornerShape(14.dp)

@Composable
fun CreateScheduleScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateScheduleViewModel = viewModel { CreateScheduleViewModel() },
    onSave: (CreateScheduleResult) -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    CreateScheduleContent(
        uiState = uiState,
        onNameChange = viewModel::onNameChange,
        onToggleDay = viewModel::onToggleDay,
        onLockTypeSelect = viewModel::onLockTypeSelect,
        onTimeFieldClick = viewModel::onTimeFieldClick,
        onTimeSelected = viewModel::onTimeSelected,
        onTimePickerDismiss = viewModel::onTimePickerDismiss,
        onSaveClick = {
            viewModel.buildResult()?.let(onSave)
        },
        modifier = modifier,
    )
}

@Composable
private fun CreateScheduleContent(
    uiState: CreateScheduleUiState,
    onNameChange: (String) -> Unit,
    onToggleDay: (Int) -> Unit,
    onLockTypeSelect: (LockType) -> Unit,
    onTimeFieldClick: (TimeField) -> Unit,
    onTimeSelected: (Int, Int) -> Unit,
    onTimePickerDismiss: () -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 24.dp)
            .padding(top = 8.dp, bottom = 28.dp),
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
        ) {
            Text(
                text = "NEW SCHEDULE",
                style = MaterialTheme.typography.labelMedium,
                color = ScreenCurfewTheme.colors.moon,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "When should the phone rest?",
                style = MaterialTheme.typography.displayLarge,
                color = ScreenCurfewTheme.colors.cream,
            )

            Spacer(modifier = Modifier.height(28.dp))

            FieldCard(label = "Name") {
                Box(modifier = Modifier.fillMaxWidth()) {
                    if (uiState.name.isEmpty()) {
                        Text(
                            text = "Schedule name",
                            style = MaterialTheme.typography.titleLarge,
                            color = ScreenCurfewTheme.colors.muted,
                        )
                    }
                    BasicTextField(
                        value = uiState.name,
                        onValueChange = onNameChange,
                        singleLine = true,
                        textStyle = MaterialTheme.typography.titleLarge.copy(
                            color = ScreenCurfewTheme.colors.cream,
                        ),
                        cursorBrush = SolidColor(ScreenCurfewTheme.colors.moon),
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                TimeFieldCard(
                    label = "Bedtime",
                    value = uiState.bedtime?.format12Hour(),
                    placeholder = "Select time",
                    onClick = { onTimeFieldClick(TimeField.Bedtime) },
                    modifier = Modifier.weight(1f),
                )
                TimeFieldCard(
                    label = "Wake",
                    value = uiState.wake?.format12Hour(),
                    placeholder = "Select time",
                    onClick = { onTimeFieldClick(TimeField.Wake) },
                    modifier = Modifier.weight(1f),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Days",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                color = ScreenCurfewTheme.colors.muted,
            )
            Spacer(modifier = Modifier.height(8.dp))
            DaySelector(
                activeDays = uiState.activeDays,
                onToggleDay = onToggleDay,
            )

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "Lock type",
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                color = ScreenCurfewTheme.colors.muted,
            )
            Spacer(modifier = Modifier.height(8.dp))
            LockTypeOption(
                title = "Overlay block",
                detail = "Full-screen rest screen. Emergency PIN still works.",
                selected = uiState.lockType == LockType.OverlayBlock,
                onClick = { onLockTypeSelect(LockType.OverlayBlock) },
            )
            Spacer(modifier = Modifier.height(8.dp))
            LockTypeOption(
                title = "Soft reminder",
                detail = "Warns first. No overlay until you ignore it.",
                selected = uiState.lockType == LockType.SoftReminder,
                onClick = { onLockTypeSelect(LockType.SoftReminder) },
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        PrimaryButton(
            text = "Save schedule",
            onClick = onSaveClick,
            enabled = uiState.canSave,
        )
    }

    when (uiState.activeTimeField) {
        TimeField.Bedtime -> ScheduleTimePicker(
            title = "Bedtime",
            initialTime = uiState.bedtime,
            onConfirm = { time -> onTimeSelected(time.hour, time.minute) },
            onDismiss = onTimePickerDismiss,
        )
        TimeField.Wake -> ScheduleTimePicker(
            title = "Wake",
            initialTime = uiState.wake,
            onConfirm = { time -> onTimeSelected(time.hour, time.minute) },
            onDismiss = onTimePickerDismiss,
        )
        null -> Unit
    }
}

@Composable
private fun FieldCard(
    label: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .background(
                color = ScreenCurfewTheme.colors.elevated,
                shape = FieldShape,
            )
            .border(
                width = 1.dp,
                color = ScreenCurfewTheme.colors.line,
                shape = FieldShape,
            )
            .padding(horizontal = 16.dp, vertical = 14.dp),
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
            color = ScreenCurfewTheme.colors.muted,
        )
        Spacer(modifier = Modifier.height(4.dp))
        content()
    }
}

@Composable
private fun TimeFieldCard(
    label: String,
    value: String?,
    placeholder: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FieldCard(
        label = label,
        modifier = modifier.clickable(onClick = onClick),
    ) {
        Text(
            text = value ?: placeholder,
            style = MaterialTheme.typography.titleLarge,
            color = if (value != null) {
                ScreenCurfewTheme.colors.cream
            } else {
                ScreenCurfewTheme.colors.muted
            },
        )
    }
}

@Composable
private fun DaySelector(
    activeDays: Set<Int>,
    onToggleDay: (Int) -> Unit,
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
                    )
                    .clickable { onToggleDay(index) },
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
private fun LockTypeOption(
    title: String,
    detail: String,
    selected: Boolean,
    onClick: () -> Unit,
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
                color = if (selected) {
                    ScreenCurfewTheme.colors.moon
                } else {
                    ScreenCurfewTheme.colors.line
                },
                shape = MaterialTheme.shapes.large,
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 18.dp, vertical = 16.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleLarge,
            color = ScreenCurfewTheme.colors.cream,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = detail,
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
            color = ScreenCurfewTheme.colors.muted,
        )
    }
}

@Preview
@Composable
private fun CreateScheduleScreenPreview() {
    ScreenCurfewTheme {
        CreateScheduleContent(
            uiState = CreateScheduleUiState(),
            onNameChange = {},
            onToggleDay = {},
            onLockTypeSelect = {},
            onTimeFieldClick = {},
            onTimeSelected = { _, _ -> },
            onTimePickerDismiss = {},
            onSaveClick = {},
        )
    }
}
