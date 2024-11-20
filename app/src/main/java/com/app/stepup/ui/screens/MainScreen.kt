package com.app.stepup.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.stepup.navigation.ScreenRoutes
import com.app.stepup.view_models.MainViewModel

@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel = hiltViewModel()
) {
    var isSplashVisible by remember { mutableStateOf(true) }
    val isUserRegistered by viewModel.isUserRegistered.collectAsState()
    LaunchedEffect(isUserRegistered) {
        if (isSplashVisible) {
            isSplashVisible = false
        }
    }

    if (isSplashVisible) {
        SplashScreen()
    } else {
        if (isUserRegistered)
            navigateTo(navController, ScreenRoutes.HomeScreen.route)
        else
            navigateTo(navController, ScreenRoutes.RegistrationScreen.route)
    }
}

private fun navigateTo(navController: NavController, route: String) {
    navController.navigate(route) {
        popUpTo(navController.graph.startDestinationId) {
            inclusive = true
        }
    }
}
