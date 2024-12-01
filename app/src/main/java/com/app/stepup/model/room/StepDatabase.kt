package com.app.stepup.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [
        StepData::class,
        TotalStepData::class,
        DailyStepData::class,
        MonthlyStepData::class,
        YearlyStepData::class
    ],
    version = 5
)
abstract class StepDatabase : RoomDatabase() {
    abstract fun stepDao(): StepDao
}

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS daily_steps (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                date TEXT NOT NULL,
                hour INTEGER NOT NULL,
                steps INTEGER NOT NULL
            )
        """
        )

        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS monthly_steps (
                month TEXT PRIMARY KEY NOT NULL,
                day INTEGER NOT NULL,
                steps INTEGER NOT NULL
            )
        """
        )

        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS yearly_steps (
                year TEXT PRIMARY KEY NOT NULL,
                month TEXT NOT NULL,
                steps INTEGER NOT NULL
            )
        """
        )

        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS total_steps (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                steps INTEGER NOT NULL
            )
        """
        )
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS `yearly_steps_new` (
                `year` TEXT PRIMARY KEY NOT NULL,
                `month` INTEGER NOT NULL,
                `steps` INTEGER NOT NULL
            )
        """
        )

        db.execSQL(
            """
            INSERT INTO `yearly_steps_new` (`year`, `month`, `steps`)
            SELECT `year`, CAST(`month` AS INTEGER), `steps` FROM `yearly_steps`
        """
        )

        db.execSQL("DROP TABLE `yearly_steps`")

        db.execSQL("ALTER TABLE `yearly_steps_new` RENAME TO `yearly_steps`")
    }
}

val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS new_monthly_steps (
                month TEXT NOT NULL,
                day INTEGER NOT NULL,
                steps INTEGER NOT NULL,
                PRIMARY KEY(month, day)
            )
        """
        )

        db.execSQL(
            """
            INSERT INTO new_monthly_steps (month, day, steps)
            SELECT month, day, steps FROM monthly_steps
        """
        )

        db.execSQL("DROP TABLE IF EXISTS monthly_steps")

        db.execSQL("ALTER TABLE new_monthly_steps RENAME TO monthly_steps")
    }
}

val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS yearly_steps_new (
                year TEXT NOT NULL,
                month INTEGER NOT NULL,
                steps INTEGER NOT NULL,
                PRIMARY KEY(year, month)
            )
        """
        )

        db.execSQL(
            """
            INSERT INTO yearly_steps_new (year, month, steps)
            SELECT year, month, steps FROM yearly_steps
        """
        )

        db.execSQL("DROP TABLE yearly_steps")

        db.execSQL("ALTER TABLE yearly_steps_new RENAME TO yearly_steps")
    }
}