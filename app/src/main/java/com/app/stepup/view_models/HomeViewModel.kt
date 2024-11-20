package com.app.stepup.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.stepup.constants.Constants.defaultAchievements
import com.app.stepup.model.StepRepository
import com.app.stepup.model.data.Achievement
import com.app.stepup.model.datastore.StepUpPreferencesRepository
import com.app.stepup.utils.ActivityMetricsCalculator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val stepRepository: StepRepository,
    private val repository: StepUpPreferencesRepository,
    private val calculator: ActivityMetricsCalculator
) : ViewModel() {

    init {
        saveDefaultAchievements()
    }

    private var _calories = MutableStateFlow(0.0)

    private var _distance = MutableStateFlow(0.0)
    val actualSteps = stepRepository.getAllStepsFromToday()

    val stepsByHour = stepRepository.getAllStepsFromTodayByHours()
    val stepGoal = repository.getStepGoal()
    val user = repository.getUserData()
    val achievement = repository.getAchievements()
    val calories: StateFlow<Double> = _calories
    val distance: StateFlow<Double> = _distance
    fun calculateActivityMetrics() {
        viewModelScope.launch {
            _calories.value = calculator.calculateCalories(actualSteps.first())
            _distance.value =
                calculator.calculateDistanceInKm(user.first().height, actualSteps.first())
        }
    }

    private fun saveDefaultAchievements() {
        viewModelScope.launch(Dispatchers.IO) {
            if (repository.getAchievements().first() == emptyList<Achievement>())
                repository.setAchievements(defaultAchievements)
        }
    }
}