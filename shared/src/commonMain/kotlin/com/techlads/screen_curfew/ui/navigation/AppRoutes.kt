package com.techlads.screen_curfew.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
data object OnboardingRoute

@Serializable
data object MainGraph

@Serializable
data object TonightRoute

@Serializable
data object SchedulesRoute

@Serializable
data object InsightsRoute

@Serializable
data object FamilyRoute

@Serializable
data object CreateScheduleRoute

@Serializable
data object PauseLockRoute

enum class BottomTab(
    val label: String,
    val route: Any,
) {
    Tonight(label = "Tonight", route = TonightRoute),
    Schedules(label = "Schedules", route = SchedulesRoute),
    Insights(label = "Insights", route = InsightsRoute),
    Family(label = "Family", route = FamilyRoute),
}
