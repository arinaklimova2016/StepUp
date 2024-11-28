package com.app.stepup.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StepDao {
    @Query("SELECT * FROM steps")
    fun getAll(): List<StepData>

    @Query("DELETE FROM steps")
    suspend fun clearStepsDb()

    @Query("SELECT * FROM daily_steps WHERE date = :date AND hour = :hour")
    suspend fun getStepDataByDateAndHour(date: String, hour: Int): DailyStepData?

    @Query("SELECT * FROM daily_steps ORDER BY hour")
    fun getFlowStepsForDay(): Flow<List<DailyStepData>>

    @Query("SELECT * FROM daily_steps ORDER BY hour")
    suspend fun getStepsForDay(): List<DailyStepData>

    @Query("SELECT * FROM daily_steps WHERE date = :date ORDER BY hour")
    suspend fun getStepsForDayByDate(date: String): List<DailyStepData>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateDailySteps(dailyStep: DailyStepData)

    @Query("DELETE FROM daily_steps WHERE date = :date")
    suspend fun clearStepsForDay(date: String)

    @Query("SELECT * FROM monthly_steps WHERE month = :month")
    suspend fun getStepsByMonth(month: String): List<MonthlyStepData>?

    @Query("SELECT * FROM monthly_steps WHERE month = :month AND day = :day")
    suspend fun getStepsByMonthAndDay(month: String, day: Int): MonthlyStepData?

    @Query("SELECT * FROM monthly_steps ORDER BY day")
    suspend fun getStepsForMonth(): List<MonthlyStepData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateMonthlySteps(monthlyStep: MonthlyStepData)

    @Query("DELETE FROM monthly_steps WHERE month = :month")
    suspend fun clearStepsForMonth(month: String)

    @Query("SELECT * FROM yearly_steps WHERE month = :month AND year =:year")
    suspend fun getStepsFromYearByMonth(year: String, month: Int): YearlyStepData?

    @Query("SELECT * FROM yearly_steps WHERE year = :year ORDER BY month")
    suspend fun getStepsForYear(year: String): List<YearlyStepData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateYearlySteps(yearlyStep: YearlyStepData)

    @Query("SELECT steps FROM total_steps WHERE id = 1")
    fun getTotalSteps(): Flow<Long>

    @Query("UPDATE total_steps SET steps = steps + :newSteps WHERE id = 1")
    suspend fun updateTotalSteps(newSteps: Long)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInitial(totalSteps: TotalStepData)
}