package com.app.stepup.model.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [StepData::class], version = 1)
abstract class StepDatabase : RoomDatabase() {
    abstract fun stepDao(): StepDao
}