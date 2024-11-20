package com.app.stepup.model

import android.content.Context
import com.app.stepup.R
import com.app.stepup.model.room.StepDao
import com.app.stepup.model.room.StepData
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.LocalDate
import java.time.Month
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField
import javax.inject.Inject

class StepRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val stepDao: StepDao
) {
    suspend fun insert(steps: StepData) {
        stepDao.insert(steps)
    }

    fun getAllStepsFromToday(): Flow<Long> {
        return loadAllStepsFromToday().map { steps ->
            steps.sumOf { it.steps }
        }
    }

    fun getAllStepsFromTodayByHours(): Flow<List<Double>> {
        return loadAllStepsFromToday().map { steps ->
            val stepsByHour = steps.groupBy { stepData ->
                ZonedDateTime.ofInstant(
                    Instant.parse(stepData.createdAt),
                    ZoneId.systemDefault()
                ).hour
            }.mapValues { (_, steps) ->
                steps.sumOf { it.steps.toDouble() }
            }
            List(24) { hour ->
                stepsByHour[hour] ?: 0.0
            }
        }
    }

    fun getAllSteps(): Flow<Long> {
        return loadAllSteps().map { steps ->
            steps.sumOf { it.steps }
        }
    }

    fun getAllStepsFromWeekByDays(): Flow<Map<String, Double>> {
        return loadAllStepsFromWeek().map { steps ->
            val stepsByDay = steps.groupBy { stepData ->
                ZonedDateTime.ofInstant(Instant.parse(stepData.createdAt), ZoneId.systemDefault())
                    .toLocalDate()
            }.mapValues { (_, steps) -> steps.sumOf { it.steps.toDouble() } }

            val daysOfWeek = context.resources.getStringArray(R.array.days_of_week).toList()

            daysOfWeek.associateWith { day ->
                stepsByDay[ZonedDateTime.now().with(
                    ChronoField.DAY_OF_WEEK,
                    (daysOfWeek.indexOf(day) + 1).toLong()
                ).toLocalDate()] ?: 0.0
            }
        }
    }

    fun getAllStepsFromMonthByDays(): Flow<Map<Int, Double>> {
        return loadAllStepsFromMonth().map { steps ->
            val stepsByDay = steps.groupBy { stepData ->
                ZonedDateTime.ofInstant(Instant.parse(stepData.createdAt), ZoneId.systemDefault())
                    .toLocalDate().dayOfMonth
            }.mapValues { (_, steps) -> steps.sumOf { it.steps.toDouble() } }

            (1..LocalDate.now().lengthOfMonth()).associateWith { day ->
                stepsByDay[day] ?: 0.0
            }
        }
    }

    fun getAlStepsFromYearByMonth(): Flow<Map<String, Double>> {
        return loadAllStepsFromYearByMonths().map { steps ->
            val stepsByMonth = steps.groupBy { stepData ->
                ZonedDateTime.ofInstant(
                    Instant.parse(stepData.createdAt),
                    ZoneId.systemDefault()
                ).month
            }.mapValues { (_, steps) -> steps.sumOf { it.steps.toDouble() } }

            val monthsOfYear = context.resources.getStringArray(R.array.months_of_year).toList()

            monthsOfYear.associateWith { month ->
                val monthIndex = monthsOfYear.indexOf(month) + 1
                stepsByMonth[Month.of(monthIndex)] ?: 0.0
            }
        }
    }

    private fun loadAllSteps(): Flow<List<StepData>> {
        return stepDao.getAll()
    }

    private fun loadAllStepsFromToday(): Flow<Array<StepData>> {
        val startOfDay = LocalDate.now()
            .atStartOfDay()
            .format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        return stepDao.loadAllStepsFromToday(startOfDay)
    }

    private fun loadAllStepsFromWeek(): Flow<Array<StepData>> {
        val startOfWeek = LocalDate.now()
            .with(ChronoField.DAY_OF_WEEK, 1)
            .format(DateTimeFormatter.ISO_LOCAL_DATE)
        return stepDao.loadAllStepsFromWeek(startOfWeek)
    }

    private fun loadAllStepsFromMonth(): Flow<Array<StepData>> {
        val startMonth = LocalDate.now()
            .withDayOfMonth(1)
            .format(DateTimeFormatter.ISO_LOCAL_DATE)
        return stepDao.loadAllStepsFromMonth(startMonth)
    }

    private fun loadAllStepsFromYearByMonths(): Flow<Array<StepData>> {
        val startOfYear = LocalDate.now()
            .withDayOfYear(1)
            .format(DateTimeFormatter.ISO_LOCAL_DATE)
        return stepDao.loadAllStepsFromYear(startOfYear)
    }
}