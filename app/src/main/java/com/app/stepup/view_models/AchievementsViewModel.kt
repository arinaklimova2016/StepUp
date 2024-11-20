package com.app.stepup.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.stepup.model.data.Achievement
import com.app.stepup.model.datastore.StepUpPreferencesRepository
import com.app.stepup.utils.AchievementsManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AchievementsViewModel @Inject constructor(
    private val repository: StepUpPreferencesRepository,
    private val achievementsManager: AchievementsManager
) : ViewModel() {
    val achievements = repository.getAchievements()

    fun checkAchievements(achievements: List<Achievement>) {
        viewModelScope.launch(Dispatchers.IO) {
            achievementsManager.checkAchievements(achievements)
        }
    }
}