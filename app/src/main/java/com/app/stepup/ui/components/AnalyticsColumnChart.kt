package com.app.stepup.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.stepup.R
import com.app.stepup.ui.screens.checkIfNeedToMakeShortly
import com.app.stepup.ui.theme.TransparentWhite
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

@Composable
fun AnalyticsColumnChart(
    title: Int,
    data: Map<String, Double>,
    modifier: Modifier,
    barWidth: Int,
    barColor: Color,
    rotation: Float = 0f,
    isMakeShortly: Boolean = false
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(TransparentWhite, shape = RoundedCornerShape(8.dp))
    ) {
        if (data.values.isEmpty() or data.values.all { it == 0.0 }) {
            NoData()
        } else {
            Title(string = title)
            ColumnChart(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp),
                data = rememberSaveable {
                    data.toList().mapIndexed { index, pair ->
                        Bars(
                            label = checkIfNeedToMakeShortly(
                                isMakeShortly,
                                index,
                                pair.first,
                                data.size
                            ),
                            values = listOf(
                                Bars.Data(
                                    value = pair.second,
                                    color = SolidColor(barColor)
                                )
                            ),
                        )
                    }
                },
                barProperties = BarProperties(thickness = barWidth.dp, spacing = 1.dp),
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
                    labels = data.keys.toList(),
                    enabled = true,
                    textStyle = TextStyle(
                        color = White,
                        fontSize = 12.sp
                    ),
                    rotationDegreeOnSizeConflict = rotation
                ),
                indicatorProperties = HorizontalIndicatorProperties(enabled = false)
            )
        }
    }
}

@Composable
fun NoData() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.no_data),
            fontSize = 18.sp,
            color = White,
            fontWeight = SemiBold
        )
        Text(
            text = stringResource(id = R.string.no_data_description),
            color = White,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Title(string: Int) {
    Text(
        text = stringResource(id = string),
        color = White,
        fontSize = 20.sp,
        fontWeight = Bold,
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    )
}