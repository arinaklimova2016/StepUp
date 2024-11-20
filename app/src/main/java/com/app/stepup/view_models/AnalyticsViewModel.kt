package com.app.stepup.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.stepup.model.StepRepository
import com.app.stepup.model.datastore.StepUpPreferencesRepository
import com.app.stepup.utils.ActivityMetricsCalculator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnalyticsViewModel @Inject constructor(
    private val stepRepository: StepRepository,
    private val calculator: ActivityMetricsCalculator,
    private val repository: StepUpPreferencesRepository
) : ViewModel() {
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

    val stepsByWeek = stepRepository.getAllStepsFromWeekByDays()
    val stepsByMonth = stepRepository.getAllStepsFromMonthByDays()
    val stepsByYear = stepRepository.getAlStepsFromYearByMonth()

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
}