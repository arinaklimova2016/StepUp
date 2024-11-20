package com.app.stepup.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StepDao {
    @Query("SELECT * FROM steps")
    fun getAll(): Flow<List<StepData>>

    @Query(
        "SELECT * FROM steps WHERE created_at >= date(:startDateTime) " +
                "AND created_at < date(:startDateTime, '+1 day')"
    )
    fun loadAllStepsFromToday(startDateTime: String): Flow<Array<StepData>>

    @Query(
        "SELECT * FROM steps WHERE created_at >= date(:startOfWeek) " +
                "AND created_at < date(:startOfWeek, '+7 days')"
    )
    fun loadAllStepsFromWeek(startOfWeek: String): Flow<Array<StepData>>

    @Query(
        "SELECT * FROM steps WHERE created_at >= date(:startOfMonth) " +
                "AND created_at < date(:startOfMonth, '+1 month')"
    )
    fun loadAllStepsFromMonth(startOfMonth: String): Flow<Array<StepData>>

    @Query(
        "SELECT * FROM steps WHERE created_at >= date(:startOfYear) " +
                "AND created_at < date(:startOfYear, '+1 year')"
    )
    fun loadAllStepsFromYear(startOfYear: String): Flow<Array<StepData>>

    @Insert
    suspend fun insert(steps: StepData)
}