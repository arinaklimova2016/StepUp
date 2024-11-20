package com.app.stepup.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.stepup.model.data.User
import com.app.stepup.model.datastore.StepUpPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val repository: StepUpPreferencesRepository
) : ViewModel() {
    fun setUserData(user: User, stepGoal: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.setUserData(user)
            repository.setStepGoal(stepGoal)
        }
    }
}
