package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.screens.*
import com.example.ui.theme.DeadTimeTheme
import com.example.ui.viewmodel.DeadTimeViewModel
import com.example.ui.viewmodel.Screen

enum class BottomTab(val label: String, val icon: ImageVector, val screen: Screen) {
    HOME("Home", Icons.Default.HourglassTop, Screen.Home),
    GOALS("Goals", Icons.Default.Flag, Screen.Goals),
    STATS("Stats", Icons.Default.BarChart, Screen.Statistics),
    HISTORY("History", Icons.Default.History, Screen.History),
    PROFILE("Profile", Icons.Default.Person, Screen.Profile)
}

@Composable
fun DeadTimeApp(viewModel: DeadTimeViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val profile by viewModel.userProfile.collectAsStateWithLifecycle()

    val themeMode = profile?.themeMode ?: "SYSTEM"

    DeadTimeTheme(themeMode = themeMode) {
        // Back navigation handler
        BackHandler(enabled = currentScreen != Screen.Home && currentScreen != Screen.Welcome && currentScreen != Screen.Splash) {
            viewModel.navigateBack()
        }

        val showBottomBar = currentScreen == Screen.Home ||
                currentScreen == Screen.Goals ||
                currentScreen == Screen.Statistics ||
                currentScreen == Screen.History ||
                currentScreen == Screen.Profile

        Scaffold(
            bottomBar = {
                if (showBottomBar) {
                    NavigationBar(
                        modifier = Modifier
                            .windowInsetsPadding(WindowInsets.navigationBars)
                            .testTag("deadtime_bottom_bar"),
                        containerColor = MaterialTheme.colorScheme.surface,
                        tonalElevation = 6.dp
                    ) {
                        BottomTab.entries.forEach { tab ->
                            val isSelected = currentScreen == tab.screen
                            NavigationBarItem(
                                selected = isSelected,
                                onClick = { viewModel.navigateTo(tab.screen) },
                                icon = {
                                    Icon(
                                        imageVector = tab.icon,
                                        contentDescription = tab.label
                                    )
                                },
                                label = { Text(tab.label) },
                                modifier = Modifier.testTag("nav_item_${tab.label.lowercase()}")
                            )
                        }
                    }
                }
            },
            contentWindowInsets = WindowInsets.safeDrawing
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                AnimatedContent(
                    targetState = currentScreen,
                    transitionSpec = {
                        fadeIn() togetherWith fadeOut()
                    },
                    label = "ScreenTransition"
                ) { target ->
                    when (target) {
                        is Screen.Splash -> SplashScreen()
                        is Screen.Welcome -> WelcomeScreen(viewModel)
                        is Screen.Auth -> AuthScreen(viewModel)
                        is Screen.Onboarding -> OnboardingScreen(viewModel)
                        is Screen.Home -> HomeScreen(viewModel)
                        is Screen.Recommendations -> RecommendationsScreen(viewModel)
                        is Screen.ActiveTimer -> ActiveTimerScreen(viewModel)
                        is Screen.Completion -> CompletionScreen(viewModel)
                        is Screen.Goals -> GoalsScreen(viewModel)
                        is Screen.CreateGoal -> GoalsScreen(viewModel)
                        is Screen.StudentMode -> StudentModeScreen(viewModel)
                        is Screen.CareerMode -> CareerModeScreen(viewModel)
                        is Screen.Statistics -> StatisticsScreen(viewModel)
                        is Screen.History -> HistoryScreen(viewModel)
                        is Screen.CustomActivity -> CustomActivityScreen(viewModel)
                        is Screen.CalendarSync -> CalendarSyncScreen(viewModel)
                        is Screen.PrivacyCenter -> PrivacyCenterScreen(viewModel)
                        is Screen.NotificationSettings -> NotificationSettingsScreen(viewModel)
                        is Screen.Profile -> ProfileScreen(viewModel)
                        is Screen.Settings -> SettingsScreen(viewModel)
                        is Screen.Premium -> PremiumScreen(viewModel)
                        is Screen.Help -> HelpScreen(viewModel)
                        is Screen.About -> AboutScreen(viewModel)
                    }
                }
            }
        }
    }
}
