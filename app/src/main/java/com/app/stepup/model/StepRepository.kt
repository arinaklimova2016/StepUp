package com.app.stepup.model

import android.content.Context
import com.app.stepup.R
import com.app.stepup.model.room.DailyStepData
import com.app.stepup.model.room.MonthlyStepData
import com.app.stepup.model.room.StepDao
import com.app.stepup.model.room.StepData
import com.app.stepup.model.room.TotalStepData
import com.app.stepup.model.room.YearlyStepData
import com.app.stepup.utils.DateParser
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject

class StepRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val stepDao: StepDao,
    private val dateParser: DateParser
) {
    suspend fun insert(steps: StepData) {
        addToTotalDb(steps)
        processDailySteps(steps)
    }

    fun getTotalSteps() = stepDao.getTotalSteps()

    fun getFlowDailySteps(): Flow<List<Long>> {
        val steps = stepDao.getFlowStepsForDay()
        return steps.map { hourlySteps ->
            val stepsList = MutableList(24) { 0L }
            hourlySteps.forEach { stepData ->
                if (stepData.hour in 0..23) {
                    stepsList[stepData.hour] = stepData.steps
                }
            }
            stepsList
        }
    }

    suspend fun getDailySteps(): Long {
        return stepDao.getStepsForDay().sumOf { it.steps }
    }

    suspend fun getMonthlySteps() = stepDao.getStepsForMonth()

    suspend fun getYearlySteps(year: String) = stepDao.getStepsForYear(year)

    suspend fun insertTotalDb() {
        val stepsTotal = stepDao.getTotalSteps().firstOrNull()
        if (stepsTotal == null)
            stepDao.insertInitial(TotalStepData(steps = 0L))
    }

    private suspend fun addToTotalDb(steps: StepData) {
        stepDao.updateTotalSteps(steps.steps)
    }

    private suspend fun processDailySteps(steps: StepData) {
        val dateInDb = stepDao.getFlowStepsForDay().firstOrNull()?.firstOrNull()?.date
        val currentDate = LocalDateTime.now().toLocalDate().toString()
        updateDailySteps(steps, currentDate)
        if (!dateInDb.isNullOrEmpty() && dateInDb != currentDate)
            clearDailySteps(dateInDb)
    }

    private suspend fun updateDailySteps(steps: StepData, currentDate: String) {
        val currentHour = LocalDateTime.now().hour
        val existingHourData = stepDao.getStepDataByDateAndHour(currentDate, currentHour)
        stepDao.insertOrUpdateDailySteps(
            existingHourData?.copy(
                steps = existingHourData.steps + steps.steps
            ) ?: DailyStepData(date = currentDate, hour = currentHour, steps = steps.steps)
        )
    }

    private suspend fun clearDailySteps(previousDate: String) {
        val dailySteps = stepDao.getStepsForDayByDate(previousDate)
        val totalStepsForDay = dailySteps?.sumOf { it.steps } ?: 0
        processMonthlySteps(totalStepsForDay, previousDate)
        stepDao.clearStepsForDay(previousDate)
    }

    private suspend fun processMonthlySteps(totalStepsForDay: Long, previousDate: String) {
        val monthInDb = stepDao.getStepsForMonth().firstOrNull()?.month
        val currentMonth = LocalDate.now().toString().substring(0, 7) // "YYYY-MM"
        updateMonthlySteps(totalStepsForDay, previousDate)
        if (!monthInDb.isNullOrEmpty() && monthInDb != currentMonth) {
            clearMonthlySteps(monthInDb)
        }
    }

    private suspend fun updateMonthlySteps(totalStepsForDay: Long, previousDate: String) {
        val previousMonth = previousDate.substring(0, 7)  // "YYYY-MM"
        val day = dateParser.getDayFromDate(previousDate)
        val existingMonthData = stepDao.getStepsByMonthAndDay(previousMonth, day)

        stepDao.insertOrUpdateMonthlySteps(
            existingMonthData?.copy(
                steps = existingMonthData.steps + totalStepsForDay
            ) ?: MonthlyStepData(
                previousMonth, day, totalStepsForDay
            )
        )
    }

    private suspend fun clearMonthlySteps(previousData: String) {
        val monthlySteps = stepDao.getStepsByMonth(previousData)
        val totalStepsForMonth = monthlySteps?.sumOf { it.steps } ?: 0
        updateYearlySteps(totalStepsForMonth, previousData)
        stepDao.clearStepsForMonth(previousData)
    }

    private suspend fun updateYearlySteps(totalStepsForMonth: Long, previousData: String) {
        val monthAndYear = dateParser.getMonthAndYearFromDate(previousData)
        val existingYearlyStepData =
            stepDao.getStepsFromYearByMonth(monthAndYear.second, monthAndYear.first)
        stepDao.insertOrUpdateYearlySteps(
            existingYearlyStepData?.copy(
                steps = existingYearlyStepData.steps + totalStepsForMonth
            ) ?: YearlyStepData(
                monthAndYear.second, monthAndYear.first, totalStepsForMonth
            )
        )
    }

    fun getStepsByWeek(
        dailySteps: Long,
        weeklySteps: List<MonthlyStepData>
    ): Map<String, Double> {
        val stepsByDay = weeklySteps.associate { stepData ->
            LocalDate.parse("${stepData.month}-${"%02d".format(stepData.day)}").dayOfWeek.value to stepData.steps.toDouble()
        }

        val daysOfWeek = context.resources.getStringArray(R.array.days_of_week).toList()

        return daysOfWeek.associateWith { day ->
            val dayIndex = daysOfWeek.indexOf(day) + 1
            if (dayIndex == LocalDate.now().dayOfWeek.value) {
                dailySteps.toDouble()
            } else {
                stepsByDay[dayIndex] ?: 0.0
            }
        }
    }

    fun getStepsByMonth(
        monthlySteps: List<MonthlyStepData>,
        currentDaySteps: Long
    ): Map<Int, Double> {
        val stepsByDay = monthlySteps.associate { it.day to it.steps.toDouble() }

        val daysInMonth = LocalDate.now().lengthOfMonth()

        return (1..daysInMonth).associateWith { day ->
            if (day == LocalDate.now().dayOfMonth) {
                currentDaySteps.toDouble()
            } else {
                stepsByDay[day] ?: 0.0
            }
        }
    }

    fun getStepsByYear(
        yearlySteps: List<YearlyStepData>,
        currentMonthSteps: Long
    ): Map<String, Double> {
        val stepsByMonth = yearlySteps.associate { it.month to it.steps.toDouble() }

        val monthsOfYear = context.resources.getStringArray(R.array.months_of_year).toList()

        return monthsOfYear.associateWith { month ->
            val monthIndex = monthsOfYear.indexOf(month) + 1
            if (monthIndex == LocalDate.now().monthValue) {
                currentMonthSteps.toDouble()
            } else {
                stepsByMonth[monthIndex] ?: 0.0
            }
        }
    }
}