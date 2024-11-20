package com.app.stepup

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.core.app.ActivityCompat
import com.app.stepup.navigation.StepUpNavigation
import com.app.stepup.services.StepsService
import com.app.stepup.ui.theme.StepUpTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACTIVITY_RECOGNITION),
            1
        )
        startForegroundService(Intent(this, StepsService::class.java))
        setContent {
            StepUpTheme {
                Scaffold { paddingValues ->
                    StepUpNavigation(paddingValues)
                }
            }
        }
    }
}
