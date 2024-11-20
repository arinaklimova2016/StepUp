package com.app.stepup.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.app.stepup.constants.Constants.KEY_ACHIEVEMENT
import com.app.stepup.model.data.Achievement
import com.app.stepup.ui.screens.AchievementScreen
import com.app.stepup.ui.screens.AchievementsScreen
import com.app.stepup.ui.screens.AnalyticsScreen
import com.app.stepup.ui.screens.HomeScreen
import com.app.stepup.ui.screens.MainScreen
import com.app.stepup.ui.screens.ProfileScreen
import com.app.stepup.ui.screens.RegistrationScreen
import com.google.gson.Gson

@Composable
fun StepUpNavigation(paddingValues: PaddingValues) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ScreenRoutes.MainScreen.route) {
        composable(ScreenRoutes.MainScreen.route) {
            MainScreen(navController)
        }
        composable(ScreenRoutes.RegistrationScreen.route) {
            RegistrationScreen(paddingValues, navController)
        }
        composable(ScreenRoutes.HomeScreen.route) {
            HomeScreen(paddingValues, navController)
        }
        composable(ScreenRoutes.AnalyticsScreen.route) {
            AnalyticsScreen(paddingValues)
        }
        composable(ScreenRoutes.ProfileScreen.route) {
            ProfileScreen(paddingValues, navController)
        }
        composable(ScreenRoutes.AchievementsScreen.route) {
            AchievementsScreen(paddingValues, navController)
        }
        composable(
            route = buildString {
                append(ScreenRoutes.AchievementScreen.route)
                append("/{$KEY_ACHIEVEMENT}")
            },
            arguments = listOf(navArgument(KEY_ACHIEVEMENT) { type = NavType.StringType })
        ) {
            val json = it.arguments?.getString(KEY_ACHIEVEMENT)
            AchievementScreen(
                paddingValues,
                if (json != null) {
                    Gson().fromJson(json, Achievement::class.java)
                } else {
                    null
                }
            )
        }
    }
}
