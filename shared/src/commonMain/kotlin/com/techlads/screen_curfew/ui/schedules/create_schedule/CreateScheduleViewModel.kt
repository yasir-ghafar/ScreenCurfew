package com.techlads.screen_curfew.ui.schedules.create_schedule

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CreateScheduleViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CreateScheduleUiState())
    val uiState: StateFlow<CreateScheduleUiState> = _uiState.asStateFlow()

    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name) }
    }

    fun onToggleDay(dayIndex: Int) {
        _uiState.update { state ->
            val days = if (dayIndex in state.activeDays) {
                state.activeDays - dayIndex
            } else {
                state.activeDays + dayIndex
            }
            state.copy(activeDays = days)
        }
    }

    fun onLockTypeSelect(lockType: LockType) {
        _uiState.update { it.copy(lockType = lockType) }
    }

    fun onTimeFieldClick(field: TimeField) {
        _uiState.update { it.copy(activeTimeField = field) }
    }

    fun onTimePickerDismiss() {
        _uiState.update { it.copy(activeTimeField = null) }
    }

    fun onTimeSelected(hour: Int, minute: Int) {
        val time = ScheduleTime(hour = hour, minute = minute)
        _uiState.update { state ->
            when (state.activeTimeField) {
                TimeField.Bedtime -> state.copy(bedtime = time, activeTimeField = null)
                TimeField.Wake -> state.copy(wake = time, activeTimeField = null)
                null -> state
            }
        }
    }

    fun buildResult(): CreateScheduleResult? {
        val state = _uiState.value
        if (!state.canSave) return null
        return CreateScheduleResult(
            name = state.name.trim(),
            bedtime = state.bedtime!!,
            wake = state.wake!!,
            activeDays = state.activeDays,
            lockType = state.lockType!!,
        )
    }
}
