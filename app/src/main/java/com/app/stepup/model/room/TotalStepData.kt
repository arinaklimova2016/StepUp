package com.app.stepup.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "total_steps")
data class TotalStepData(
    @PrimaryKey(autoGenerate = true) val id: Int = 1,
    @ColumnInfo(name = "steps") val steps: Long
)