package com.techlads.screen_curfew.ui.schedules.create_schedule

data class ScheduleTime(
    val hour: Int,
    val minute: Int,
) {
    init {
        require(hour in 0..23) { "hour must be 0..23" }
        require(minute in 0..59) { "minute must be 0..59" }
    }

    fun format12Hour(): String {
        val period = if (hour < 12) "AM" else "PM"
        val hour12 = when {
            hour == 0 -> 12
            hour > 12 -> hour - 12
            else -> hour
        }
        return "$hour12:${minute.toString().padStart(2, '0')} $period"
    }
}

enum class LockType {
    OverlayBlock,
    SoftReminder,
}

enum class TimeField {
    Bedtime,
    Wake,
}

data class CreateScheduleUiState(
    val name: String = "",
    val bedtime: ScheduleTime? = null,
    val wake: ScheduleTime? = null,
    val activeDays: Set<Int> = emptySet(),
    val lockType: LockType? = null,
    val activeTimeField: TimeField? = null,
) {
    val canSave: Boolean
        get() = name.isNotBlank() &&
            bedtime != null &&
            wake != null &&
            activeDays.isNotEmpty() &&
            lockType != null
}

data class CreateScheduleResult(
    val name: String,
    val bedtime: ScheduleTime,
    val wake: ScheduleTime,
    val activeDays: Set<Int>,
    val lockType: LockType,
)
