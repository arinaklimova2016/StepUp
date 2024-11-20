package com.app.stepup.model.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.app.stepup.model.data.Achievement
import com.app.stepup.model.data.Gender
import com.app.stepup.model.data.User
import com.google.gson.Gson
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StepUpPreferencesRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : StepUpPreferencesRepository {

    private object PreferencesKeys {
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_AGE = intPreferencesKey("user_age")
        val USER_GENDER = stringPreferencesKey("user_gender")
        val USER_HEIGHT = doublePreferencesKey("user_height")
        val USER_WEIGHT = doublePreferencesKey("user_weight")
        val STEP_GOAL = intPreferencesKey("step_goal")
        val ACHIEVEMENTS = stringPreferencesKey("achievements")
    }

    override suspend fun setUserData(userData: User) {
        dataStore.edit { prefs ->
            prefs[PreferencesKeys.USER_NAME] = userData.name
            prefs[PreferencesKeys.USER_AGE] = userData.age
            prefs[PreferencesKeys.USER_GENDER] = userData.gender.name
            prefs[PreferencesKeys.USER_HEIGHT] = userData.height
            prefs[PreferencesKeys.USER_WEIGHT] = userData.weight
        }
    }

    override fun getUserData() = dataStore.data.map { prefs ->
        User(
            prefs[PreferencesKeys.USER_NAME] ?: "",
            prefs[PreferencesKeys.USER_AGE] ?: 0,
            Gender.valueOf(prefs[PreferencesKeys.USER_GENDER] ?: Gender.OTHER.name),
            prefs[PreferencesKeys.USER_HEIGHT] ?: 0.0,
            prefs[PreferencesKeys.USER_WEIGHT] ?: 0.0
        )
    }

    override suspend fun setStepGoal(stepGoal: Int) {
        dataStore.edit { prefs ->
            prefs[PreferencesKeys.STEP_GOAL] = stepGoal
        }
    }

    override fun getStepGoal() = dataStore.data.map { prefs ->
        prefs[PreferencesKeys.STEP_GOAL] ?: 0
    }

    override suspend fun setAchievements(list: List<Achievement>) {
        dataStore.edit { prefs ->
            prefs[PreferencesKeys.ACHIEVEMENTS] = Gson().toJson(list)
        }
    }

    override fun getAchievements() = dataStore.data.map { prefs ->
        val json = prefs[PreferencesKeys.ACHIEVEMENTS]
        if (json != null) {
            Gson().fromJson(json, Array<Achievement>::class.java).toList()
        } else {
            emptyList()
        }
    }
}