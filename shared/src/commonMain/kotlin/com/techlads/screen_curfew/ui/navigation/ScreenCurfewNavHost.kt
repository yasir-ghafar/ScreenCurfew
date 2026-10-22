package com.techlads.screen_curfew.ui.navigation

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.techlads.screen_curfew.ui.components.AppBottomBar
import com.techlads.screen_curfew.ui.home.HomeScreen
import com.techlads.screen_curfew.ui.onboarding.OnboardingScreen
import com.techlads.screen_curfew.ui.override.PauseLockScreen
import com.techlads.screen_curfew.ui.schedules.SchedulesScreen
import com.techlads.screen_curfew.ui.schedules.create_schedule.CreateScheduleScreen
import com.techlads.screen_curfew.ui.theme.ScreenCurfewTheme

@Composable
fun ScreenCurfewNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: Any = OnboardingRoute,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val selectedTab = BottomTab.entries.firstOrNull { tab ->
        currentDestination?.hierarchy?.any { it.hasRoute(tab.route::class) } == true
    }
    val showBottomBar = currentDestination?.hierarchy?.any {
        it.hasRoute(MainGraph::class)
    } == true

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
        ) {
            NavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = Modifier.fillMaxSize(),
            ) {
                composable<OnboardingRoute> {
                    OnboardingScreen(
                        onGetStarted = {
                            navController.navigate(MainGraph) {
                                popUpTo(OnboardingRoute) { inclusive = true }
                            }
                        },
                        onAlreadyHaveAccount = {
                            navController.navigate(MainGraph) {
                                popUpTo(OnboardingRoute) { inclusive = true }
                            }
                        },
                    )
                }

                navigation<MainGraph>(startDestination = TonightRoute) {
                    composable<TonightRoute> {
                        TabContent { HomeScreen() }
                    }
                    composable<SchedulesRoute> {
                        TabContent {
                            SchedulesScreen(
                                onAddSchedule = {
                                    navController.navigate(CreateScheduleRoute)
                                },
                            )
                        }
                    }
                    composable<InsightsRoute> {
                        TabContent {
                            PlaceholderTab(
                                title = "Insights",
                                body = "Progress without shame — streak, adherence, and calm trends.",
                            )
                        }
                    }
                    composable<FamilyRoute> {
                        TabContent {
                            PlaceholderTab(
                                title = "Family",
                                body = "Shared bedtime goals for the people you wind down with.",
                            )
                        }
                    }
                }

                composable<CreateScheduleRoute> {
                    TabContent {
                        CreateScheduleScreen(
                            onSave = { navController.popBackStack() },
                        )
                    }
                }

                composable<PauseLockRoute> {
                    PauseLockScreen(
                        onPinComplete = { navController.popBackStack() },
                        onCancel = { navController.popBackStack() },
                    )
                }
            }
        }

        if (showBottomBar && selectedTab != null) {
            AppBottomBar(
                selected = selectedTab,
                onSelect = { tab ->
                    navController.navigate(tab.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}

@Composable
private fun TabContent(
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .safeContentPadding(),
    ) {
        content()
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
