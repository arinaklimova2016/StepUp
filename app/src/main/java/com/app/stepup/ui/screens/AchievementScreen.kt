package com.app.stepup.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.SemiBold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.stepup.R
import com.app.stepup.constants.Constants.defaultAchievements
import com.app.stepup.model.data.Achievement
import com.app.stepup.model.data.Status
import com.app.stepup.ui.components.Background
import com.app.stepup.ui.theme.SoftGreen

@Composable
fun AchievementScreen(paddingValues: PaddingValues, achievement: Achievement?) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Background()
        if (achievement != null) {
            Column(
                Modifier.fillMaxSize().padding(horizontal = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = achievement.name.getNameRes()),
                    style = TextStyle(
                        fontSize = 32.sp,
                        color = White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(id = achievement.iconRes),
                    contentDescription = achievement.iconRes.toString(),
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .aspectRatio(1f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(id = achievement.descriptionRes),
                    style = TextStyle(
                        fontSize = 24.sp,
                        color = White,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = achievement.status.name,
                    style = TextStyle(
                        fontSize = 24.sp,
                        color = when (achievement.status) {
                            Status.DONE -> SoftGreen
                            Status.UNDONE -> Gray
                        },
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                )
            }
        } else {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(id = R.string.something_went_wrong),
                    fontSize = 24.sp,
                    color = White,
                    fontWeight = SemiBold
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewAchievement() {
    AchievementScreen(paddingValues = PaddingValues(), achievement = defaultAchievements[6])
}