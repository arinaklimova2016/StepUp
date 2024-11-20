package com.app.stepup.utils

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ActivityMetricsCalculator @Inject constructor() {
    fun calculateCalories(steps: Long): Double {
        return steps * CALORIES_PER_STEP
    }

    fun calculateDistanceInKm(height: Double, steps: Long): Double {
        val stepLength = (height / 100.0) * STEP_LENGTH_RATIO
        return (steps * stepLength) / 1000
    }

    companion object {
        private const val STEP_LENGTH_RATIO = 0.413
        private const val CALORIES_PER_STEP = 0.04
    }
}
