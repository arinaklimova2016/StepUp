package com.app.stepup.model.room

import androidx.room.Entity

@Entity(tableName = "monthly_steps", primaryKeys = ["month", "day"])
data class MonthlyStepData(
    val month: String, //"YYYY-MM"
    val day: Int,
    val steps: Long
)
