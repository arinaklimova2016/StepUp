package com.app.stepup.navigation

import com.app.stepup.constants.Constants.ACHIEVEMENT
import com.app.stepup.constants.Constants.ACHIEVEMENTS
import com.app.stepup.constants.Constants.ANALYTICS
import com.app.stepup.constants.Constants.HOME
import com.app.stepup.constants.Constants.MAIN
import com.app.stepup.constants.Constants.PROFILE
import com.app.stepup.constants.Constants.REGISTRATION

sealed class ScreenRoutes(val route: String) {
    data object MainScreen : ScreenRoutes(MAIN)
    data object RegistrationScreen : ScreenRoutes(REGISTRATION)
    data object HomeScreen : ScreenRoutes(HOME)
    data object ProfileScreen : ScreenRoutes(PROFILE)
    data object AnalyticsScreen : ScreenRoutes(ANALYTICS)
    data object AchievementsScreen : ScreenRoutes(ACHIEVEMENTS)
    data object AchievementScreen : ScreenRoutes(ACHIEVEMENT)
}
