package com.app.stepup.utils

import com.app.stepup.model.room.MonthlyStepData
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.WeekFields
import java.util.Locale
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

    fun getCurrentWeekSteps(data: List<MonthlyStepData>): List<MonthlyStepData> {
        val today = LocalDate.now()
        val currentWeekFields = WeekFields.of(Locale.getDefault())

        val startOfWeek = today.with(currentWeekFields.dayOfWeek(), 1)
        val endOfWeek = today.with(currentWeekFields.dayOfWeek(), 7)

        return data.filter { stepData ->
            val date = LocalDate.parse(
                "${stepData.month}-${stepData.day.toString().padStart(2, '0')}",
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
            )
            date in startOfWeek..endOfWeek
        }
    }

    companion object {
        private const val STEP_LENGTH_RATIO = 0.413
        private const val CALORIES_PER_STEP = 0.04
    }
}
