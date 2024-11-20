package com.app.stepup.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.tooling.preview.Preview
import com.app.stepup.ui.theme.Lavender
import com.app.stepup.ui.theme.LavenderBlue
import com.app.stepup.ui.theme.PastelBlue
import com.app.stepup.ui.theme.PastelPink
import com.app.stepup.ui.theme.PastelPurple
import com.app.stepup.ui.theme.SoftLilac

val pastelGradientColors = listOf(
    Lavender, SoftLilac, PastelPurple, LavenderBlue, PastelBlue, PastelPink
)

@Composable
fun Background() {
    Box(
        modifier = Modifier
            .background(
                brush = Brush.linearGradient(
                    colors = pastelGradientColors,
                    tileMode = TileMode.Repeated
                )
            )
            .fillMaxSize()
    )
}

@Preview
@Composable
fun PreviewBackground() {
    Background()
}