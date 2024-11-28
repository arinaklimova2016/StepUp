package com.app.stepup.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Black
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.stepup.R
import com.app.stepup.constants.Constants.emptyUser
import com.app.stepup.navigation.ScreenRoutes
import com.app.stepup.ui.components.AchievementView
import com.app.stepup.ui.components.Background
import com.app.stepup.ui.theme.SoftGreen
import com.app.stepup.ui.theme.TransparentWhite
import com.app.stepup.view_models.HomeViewModel
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import ir.ehsannarmani.compose_charts.models.DividerProperties
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.LineProperties
import ir.ehsannarmani.compose_charts.models.PopupProperties
import java.util.Locale

@Composable
fun HomeScreen(
    paddingValues: PaddingValues,
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val sumSteps by viewModel.sumSteps.collectAsState(initial = 0)
    val dailySteps by viewModel.dailySteps.collectAsState(initial = emptyList())
    val user by viewModel.user.collectAsState(initial = emptyUser)
    val stepGoal by viewModel.stepGoal.collectAsState(initial = 0)
    val calories by viewModel.calories.collectAsState()
    val distance by viewModel.distance.collectAsState()
    val achievement by viewModel.achievement.collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        viewModel.calculateActivityMetrics()
    }

    Background()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Title(user.name)
            IconButton(onClick = { navController.navigate(ScreenRoutes.ProfileScreen.route) }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = R.drawable.ic_profile.toString(),
                    tint = White,
                    modifier = Modifier
                        .fillMaxHeight(0.9f)
                        .aspectRatio(1f)
                )
            }
        }
        CurrentStepsIndicator(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .aspectRatio(1f),
            sumSteps,
            stepGoal,
            calories,
            distance
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            AnalyticsView(
                dailySteps.map { it.toDouble() },
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f)
                    .padding(bottom = 8.dp)
                    .background(TransparentWhite, shape = RoundedCornerShape(8.dp))
                    .clickable {
                        navController.navigate(ScreenRoutes.AnalyticsScreen.route)
                    }
            )
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp)
                    .background(TransparentWhite, shape = RoundedCornerShape(8.dp))
                    .clickable {
                        navController.navigate(ScreenRoutes.AchievementsScreen.route)
                    }
                    .padding(16.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.achievements),
                        color = White,
                        fontSize = 20.sp,
                        fontWeight = Bold
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_right),
                        contentDescription = R.drawable.ic_arrow_right.toString(),
                        tint = White
                    )
                }
                LazyRow(Modifier.fillMaxSize()) {
                    items(achievement.take(3)) {
                        AchievementView(achieve = it)
                    }
                }
            }
        }
    }
}

@Composable
fun Title(name: String) {
    Text(
        text = buildString {
            append(stringResource(id = R.string.hello))
            append(name)
            append(stringResource(id = R.string.exclamation_mark))
        },
        color = White,
        fontSize = 30.sp,
        fontWeight = Black,
        textAlign = TextAlign.Start
    )
}

@Composable
fun CurrentStepsIndicator(
    modifier: Modifier,
    stepsCount: Long,
    stepGoal: Int,
    calories: Double,
    distance: Double
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            strokeWidth = 20.dp,
            strokeCap = StrokeCap.Round,
            trackColor = White,
            modifier = modifier.fillMaxSize(),
            progress = {
                (stepsCount.toFloat() / stepGoal)
            }
        )
        CurrentSteps(stepsCount, stepGoal, calories, distance)
    }
}

@Composable
fun CurrentSteps(stepsCount: Long, stepGoal: Int, calories: Double, distance: Double) {
    Column {
        Image(
            painter = painterResource(id = R.drawable.ic_steps),
            contentDescription = R.drawable.ic_steps.toString(),
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .aspectRatio(1f)
        )
        Text(
            text = buildString {
                append(stepsCount)
                append(" / ")
                append(stepGoal)
            },
            modifier = Modifier.fillMaxWidth(0.4f),
            style = TextStyle(
                color = White,
                fontWeight = Black,
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                shadow = Shadow(
                    color = MaterialTheme.colorScheme.primary, blurRadius = 20f
                )
            ),
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(0.4f)
                .padding(top = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = buildString {
                    append(calories.toInt())
                    append("\n")
                    append(stringResource(id = R.string.calories_abbreviation))
                },
                style = TextStyle(
                    color = White,
                    fontSize = 12.sp,
                    fontWeight = Bold,
                    textAlign = TextAlign.Center,
                    shadow = Shadow(
                        color = MaterialTheme.colorScheme.primary, blurRadius = 20f
                    )
                )
            )
            Text(
                text = buildString {
                    append(String.format(Locale.getDefault(), "%.2f", distance))
                    append("\n")
                    append(stringResource(id = R.string.distance_abbreviation))
                },
                style = TextStyle(
                    color = White,
                    fontSize = 12.sp,
                    fontWeight = Bold,
                    textAlign = TextAlign.Center,
                    shadow = Shadow(
                        color = MaterialTheme.colorScheme.primary, blurRadius = 20f
                    )
                )
            )
        }

    }
}

@Composable
fun AnalyticsView(data: List<Double>, modifier: Modifier) {
    Column(modifier.padding(16.dp)) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(id = R.string.analytics),
                color = White,
                fontSize = 20.sp,
                fontWeight = Bold
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right),
                contentDescription = R.drawable.ic_arrow_right.toString(),
                tint = White
            )
        }
        if (data.isEmpty() or data.all { it == 0.0 }) {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.no_data),
                    fontSize = 14.sp,
                    color = White,
                    fontWeight = SemiBold
                )
                Text(
                    text = stringResource(id = R.string.no_data_description),
                    color = White,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            ColumnChart(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp),
                data = rememberSaveable {
                    data.mapIndexed { index, steps ->
                        Bars(
                            label = when {
                                index == 0 || index % 4 == 0 -> index.toString()
                                index == data.size - 1 -> (index + 1).toString()
                                else -> " "
                            },
                            values = listOf(
                                Bars.Data(
                                    value = steps,
                                    color = SolidColor(SoftGreen)
                                )
                            ),
                        )
                    }
                },
                indicatorProperties = HorizontalIndicatorProperties(enabled = false),
                gridProperties = GridProperties(enabled = false),
                labelHelperProperties = LabelHelperProperties(enabled = false),
                dividerProperties = DividerProperties(
                    xAxisProperties = LineProperties(color = SolidColor(White)),
                    yAxisProperties = LineProperties(color = SolidColor(White))
                ),
                popupProperties = PopupProperties(
                    textStyle = TextStyle(color = White),
                    containerColor = MaterialTheme.colorScheme.primary.copy(0.5f)
                ),
                labelProperties = LabelProperties(
                    enabled = true,
                    textStyle = TextStyle(
                        color = White,
                        fontSize = 10.sp
                    ),
                    rotationDegreeOnSizeConflict = 0f,
                    padding = 6.dp
                ),
                barProperties = BarProperties(thickness = 10.dp),
            )
        }
    }
}
