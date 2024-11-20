package com.app.stepup.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.app.stepup.R
import com.app.stepup.model.data.AnalyticsType
import com.app.stepup.ui.components.AnalyticsColumnChart
import com.app.stepup.ui.components.Background
import com.app.stepup.ui.components.LoadingIndicator
import com.app.stepup.ui.theme.SoftGreen
import com.app.stepup.ui.theme.SoftTeal
import com.app.stepup.ui.theme.WarmCoral
import com.app.stepup.view_models.AnalyticsViewModel

@Composable
fun AnalyticsScreen(
    paddingValues: PaddingValues,
    viewModel: AnalyticsViewModel = hiltViewModel()
) {
    var showInfo by rememberSaveable { mutableStateOf(AnalyticsType.STEPS) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Background()
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = stringResource(id = R.string.steps),
                    style = TextStyle(
                        color = White,
                        fontSize = 16.sp,
                        fontWeight = Bold,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .background(
                            if (showInfo == AnalyticsType.STEPS) MaterialTheme.colorScheme.primary else Transparent,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp)
                        .weight(1f)
                        .clickable {
                            showInfo = AnalyticsType.STEPS
                        }
                )
                Text(
                    text = stringResource(id = R.string.calories),
                    style = TextStyle(
                        color = White,
                        fontSize = 16.sp,
                        fontWeight = Bold,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .background(
                            if (showInfo == AnalyticsType.CALORIES) MaterialTheme.colorScheme.primary else Transparent,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp)
                        .weight(1f)
                        .clickable {
                            showInfo = AnalyticsType.CALORIES
                        }
                )
                Text(
                    text = stringResource(id = R.string.distance),
                    style = TextStyle(
                        color = White,
                        fontSize = 16.sp,
                        fontWeight = Bold,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier
                        .background(
                            if (showInfo == AnalyticsType.DISTANCE) MaterialTheme.colorScheme.primary else Transparent,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp)
                        .weight(1f)
                        .clickable {
                            showInfo = AnalyticsType.DISTANCE
                        }
                )
            }
            when (showInfo) {
                AnalyticsType.STEPS -> {
                    val byWeek by viewModel.stepsByWeek.collectAsState(initial = emptyMap())
                    val byMonth by viewModel.stepsByMonth.collectAsState(initial = emptyMap())
                    val byYear by viewModel.stepsByYear.collectAsState(initial = emptyMap())
                    if (byWeek.isEmpty() && byMonth.isEmpty() && byYear.isEmpty())
                        LoadingIndicator()
                    else
                        AnalyticsCharts(
                            byWeek,
                            byMonth,
                            byYear,
                            SoftGreen
                        )
                }

                AnalyticsType.CALORIES -> {
                    viewModel.calculateCalories()
                    val byWeek by viewModel.caloriesByWeek.collectAsState()
                    val byMonth by viewModel.caloriesByMonth.collectAsState()
                    val byYear by viewModel.caloriesByYear.collectAsState()
                    if (byWeek.isEmpty() && byMonth.isEmpty() && byYear.isEmpty())
                        LoadingIndicator()
                    else
                        AnalyticsCharts(
                            byWeek,
                            byMonth,
                            byYear,
                            WarmCoral
                        )
                }

                AnalyticsType.DISTANCE -> {
                    viewModel.calculateDistance()
                    val byWeek by viewModel.distanceByWeek.collectAsState()
                    val byMonth by viewModel.distanceByMonth.collectAsState()
                    val byYear by viewModel.distanceByYear.collectAsState()
                    if (byWeek.isEmpty() && byMonth.isEmpty() && byYear.isEmpty())
                        LoadingIndicator()
                    else
                        AnalyticsCharts(
                            byWeek,
                            byMonth,
                            byYear,
                            SoftTeal
                        )
                }
            }
        }
    }
}

@Composable
fun AnalyticsCharts(
    dataFromWeek: Map<String, Double>,
    dataFromMonth: Map<Int, Double>,
    dataFromYear: Map<String, Double>,
    barColor: Color
) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        AnalyticsColumnChart(
            R.string.analytics_week,
            dataFromWeek,
            Modifier.weight(1f),
            barWidth = 18,
            barColor = barColor,
        )
        Spacer(modifier = Modifier.height(16.dp))
        AnalyticsColumnChart(
            R.string.analytics_month,
            dataFromMonth.mapKeys { it.key.toString() },
            Modifier.weight(1f),
            8,
            isMakeShortly = true,
            barColor = barColor
        )
        Spacer(modifier = Modifier.height(16.dp))
        AnalyticsColumnChart(
            R.string.analytics_year,
            dataFromYear,
            Modifier.weight(1f),
            barColor = barColor,
            barWidth = 12,
            rotation = -45f
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}

fun checkIfNeedToMakeShortly(isMakeShortly: Boolean, index: Int, name: String, size: Int): String {
    return if (isMakeShortly) when {
        index == 0 || index == size - 1 || index % 5 == 0 -> name
        else -> " "
    } else name
}
