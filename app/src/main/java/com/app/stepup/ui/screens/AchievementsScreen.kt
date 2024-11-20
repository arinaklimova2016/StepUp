package com.app.stepup.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.stepup.model.data.Status
import com.app.stepup.navigation.ScreenRoutes
import com.app.stepup.ui.components.AchievementView
import com.app.stepup.ui.components.Background
import com.app.stepup.ui.components.LoadingIndicator
import com.app.stepup.ui.theme.SoftGreen
import com.app.stepup.view_models.AchievementsViewModel
import com.google.gson.Gson

@Composable
fun AchievementsScreen(
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: AchievementsViewModel = hiltViewModel()
) {
    val achievements by viewModel.achievements.collectAsState(initial = emptyList())

    LaunchedEffect(achievements) {
        if (achievements.isNotEmpty())
            viewModel.checkAchievements(achievements)
    }

    if (achievements.isEmpty()) {
        LoadingIndicator()
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Background()
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize()
            ) {
                items(achievements) { achieve ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .aspectRatio(1f)
                            .padding(8.dp)
                            .background(
                                when (achieve.status) {
                                    Status.DONE -> SoftGreen
                                    Status.UNDONE -> Color.Gray
                                },
                                CircleShape
                            )
                            .clickable {
                                navController.navigate(
                                    buildString {
                                        append(ScreenRoutes.AchievementScreen.route)
                                        append("/${Gson().toJson(achieve)}")
                                    }
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        AchievementView(achieve)
                    }
                }
            }
        }
    }
}
