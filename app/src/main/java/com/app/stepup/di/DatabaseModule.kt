package com.app.stepup.di

import android.content.Context
import androidx.room.Room
import com.app.stepup.constants.Constants.DATABASE_NAME
import com.app.stepup.model.StepRepository
import com.app.stepup.model.room.StepDao
import com.app.stepup.model.room.StepDatabase
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
        ).build()
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
        stepDao: StepDao
    ): StepRepository {
        return StepRepository(appContext, stepDao)
    }
}