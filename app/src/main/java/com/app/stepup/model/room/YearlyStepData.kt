package com.app.stepup.model.room

import androidx.room.Entity

@Entity(tableName = "yearly_steps", primaryKeys = ["year", "month"])
data class YearlyStepData(
    val year: String, // "YYYY"
    val month: Int,
    val steps: Long
)