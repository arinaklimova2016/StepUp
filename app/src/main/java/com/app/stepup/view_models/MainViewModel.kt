package com.app.stepup.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.stepup.constants.Constants.emptyUser
import com.app.stepup.model.data.User
import com.app.stepup.model.datastore.StepUpPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: StepUpPreferencesRepository
) : ViewModel() {

    init {
        checkUserRegistration()
    }

    private val _isUserRegistered = MutableStateFlow(false)
    val isUserRegistered: StateFlow<Boolean> = _isUserRegistered

    private fun checkUserRegistration() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUserData().collect {
                _isUserRegistered.value = isUserRegistered(it)
            }
        }
    }

    private fun isUserRegistered(user: User): Boolean {
        return user != emptyUser
    }
}
