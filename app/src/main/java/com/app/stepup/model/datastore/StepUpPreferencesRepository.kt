package com.app.stepup.model.datastore

import com.app.stepup.model.data.Achievement
import com.app.stepup.model.data.User
import kotlinx.coroutines.flow.Flow

interface StepUpPreferencesRepository {
    suspend fun setUserData(userData: User)
    fun getUserData(): Flow<User>
    suspend fun setStepGoal(stepGoal: Int)
    fun getStepGoal(): Flow<Int>
    suspend fun setAchievements(list: List<Achievement>)
    fun getAchievements(): Flow<List<Achievement>>
}