package com.app.stepup.di

import android.content.Context
import androidx.room.Room
import com.app.stepup.constants.Constants.DATABASE_NAME
import com.app.stepup.model.StepRepository
import com.app.stepup.model.room.MIGRATION_1_2
import com.app.stepup.model.room.MIGRATION_2_3
import com.app.stepup.model.room.MIGRATION_3_4
import com.app.stepup.model.room.MIGRATION_4_5
import com.app.stepup.model.room.StepDao
import com.app.stepup.model.room.StepDatabase
import com.app.stepup.utils.DateParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideStepDatabase(@ApplicationContext appContext: Context): StepDatabase {
        return Room.databaseBuilder(
            appContext,
            StepDatabase::class.java,
            DATABASE_NAME
        )
            .addMigrations(MIGRATION_1_2)
            .addMigrations(MIGRATION_2_3)
            .addMigrations(MIGRATION_3_4)
            .addMigrations(MIGRATION_4_5)
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(stepDatabase: StepDatabase): StepDao {
        return stepDatabase.stepDao()
    }

    @Provides
    @Singleton
    fun provideStepRepository(
        @ApplicationContext appContext: Context,
        stepDao: StepDao,
        dateParser: DateParser
    ): StepRepository {
        return StepRepository(appContext, stepDao, dateParser)
    }
}