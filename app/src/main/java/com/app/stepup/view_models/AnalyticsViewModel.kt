package com.app.stepup.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.stepup.model.StepRepository
import com.app.stepup.model.datastore.StepUpPreferencesRepository
import com.app.stepup.model.room.MonthlyStepData
import com.app.stepup.model.room.YearlyStepData
import com.app.stepup.utils.ActivityMetricsCalculator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val stepRepository: StepRepository,
    private val calculator: ActivityMetricsCalculator,
    private val repository: StepUpPreferencesRepository
) : ViewModel() {

    init {
        calculateSteps()
    }

    private var _stepsByWeek: MutableStateFlow<Map<String, Double>> = MutableStateFlow(emptyMap())
    private var _stepsByMonth: MutableStateFlow<Map<Int, Double>> = MutableStateFlow(emptyMap())
    private var _stepsByYear: MutableStateFlow<Map<String, Double>> = MutableStateFlow(emptyMap())

    private var _caloriesByWeek: MutableStateFlow<Map<String, Double>> =
        MutableStateFlow(emptyMap())
    private var _caloriesByMonth: MutableStateFlow<Map<Int, Double>> = MutableStateFlow(emptyMap())
    private var _caloriesByYear: MutableStateFlow<Map<String, Double>> =
        MutableStateFlow(emptyMap())

    private var _distanceByWeek: MutableStateFlow<Map<String, Double>> =
        MutableStateFlow(emptyMap())
    private var _distanceByMonth: MutableStateFlow<Map<Int, Double>> = MutableStateFlow(emptyMap())
    private var _distanceByYear: MutableStateFlow<Map<String, Double>> =
        MutableStateFlow(emptyMap())

    val stepsByWeek = _stepsByWeek
    val stepsByMonth = _stepsByMonth
    val stepsByYear = _stepsByYear

    val caloriesByWeek = _caloriesByWeek
    val caloriesByMonth = _caloriesByMonth
    val caloriesByYear = _caloriesByYear

    val distanceByWeek = _distanceByWeek
    val distanceByMonth = _distanceByMonth
    val distanceByYear = _distanceByYear

    fun calculateCalories() {
        viewModelScope.launch(Dispatchers.IO) {
            _caloriesByWeek.emit(
                stepsByWeek.first().mapValues { (_, steps) ->
                    calculator.calculateCalories(steps.toLong())
                }
            )
            _caloriesByMonth.emit(
                stepsByMonth.first().mapValues { (_, steps) ->
                    calculator.calculateCalories(steps.toLong())
                }
            )
            _caloriesByYear.emit(
                stepsByYear.first().mapValues { (_, steps) ->
                    calculator.calculateCalories(steps.toLong())
                }
            )
        }
    }

    fun calculateDistance() {
        viewModelScope.launch(Dispatchers.IO) {
            val userHeight = repository.getUserData().first().height
            _distanceByWeek.emit(
                stepsByWeek.first().mapValues { (_, steps) ->
                    calculator.calculateDistanceInKm(userHeight, steps.toLong())
                }
            )
            _distanceByMonth.emit(
                stepsByMonth.first().mapValues { (_, steps) ->
                    calculator.calculateDistanceInKm(userHeight, steps.toLong())
                }
            )
            _distanceByYear.emit(
                stepsByYear.first().mapValues { (_, steps) ->
                    calculator.calculateDistanceInKm(userHeight, steps.toLong())
                }
            )
        }
    }

    private fun calculateSteps() {
        viewModelScope.launch(Dispatchers.IO) {
            val dailySteps = stepRepository.getDailySteps()
            val monthlyStepData: List<MonthlyStepData> = stepRepository.getMonthlySteps()
            val weeklySteps: List<MonthlyStepData> = calculator.getCurrentWeekSteps(monthlyStepData)
            val yearlySteps: List<YearlyStepData> =
                stepRepository.getYearlySteps(LocalDate.now().year.toString())
            _stepsByWeek.emit(stepRepository.getStepsByWeek(dailySteps, weeklySteps))
            _stepsByMonth.emit(stepRepository.getStepsByMonth(monthlyStepData, dailySteps))
            _stepsByYear.emit(
                stepRepository.getStepsByYear(
                    yearlySteps,
                    (monthlyStepData.sumOf { it.steps } + dailySteps)
                )
            )
        }
    }
}