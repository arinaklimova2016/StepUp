package com.app.stepup.utils

import android.util.Log
import com.app.stepup.constants.Constants.ACTIVE_MONTH
import com.app.stepup.constants.Constants.BILLION
import com.app.stepup.constants.Constants.BURNED_BIG_MAC
import com.app.stepup.constants.Constants.BURNED_CAKE
import com.app.stepup.constants.Constants.BURNED_PIZZA
import com.app.stepup.constants.Constants.CIRCLED_THE_WORLD
import com.app.stepup.constants.Constants.CIRCLED_THE_WORLD_TWICE
import com.app.stepup.constants.Constants.DAILY_NORM
import com.app.stepup.constants.Constants.EVEREST_SUMMIT
import com.app.stepup.constants.Constants.FIFTY
import com.app.stepup.constants.Constants.FIVE
import com.app.stepup.constants.Constants.FIVE_THOUSAND
import com.app.stepup.constants.Constants.MARATHON_DISTANCE
import com.app.stepup.constants.Constants.MILLION
import com.app.stepup.constants.Constants.MONTH_WORTH
import com.app.stepup.constants.Constants.ONE_HUNDRED
import com.app.stepup.constants.Constants.ONE_HUNDRED_THOUSAND
import com.app.stepup.constants.Constants.ONE_THOUSAND
import com.app.stepup.constants.Constants.TEN
import com.app.stepup.constants.Constants.TEN_THOUSAND
import com.app.stepup.constants.Constants.TRIP_TO_THE_MOON
import com.app.stepup.constants.Constants.TWO_HUNDRED
import com.app.stepup.model.StepRepository
import com.app.stepup.model.data.Achievement
import com.app.stepup.model.data.Name
import com.app.stepup.model.data.Status
import com.app.stepup.model.datastore.StepUpPreferencesRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AchievementsManager @Inject constructor(
    private val repository: StepUpPreferencesRepository,
    private val stepRepository: StepRepository,
    private val calculator: ActivityMetricsCalculator
) {
    private var allSteps: Long = 0
    private var stepsByToday: Long = 0
    private var stepsByWeek: Long = 0
    private var stepsByMonth: Long = 0
    private var stepsByYear: Long = 0
    private var userHeight: Double = 0.0

    suspend fun checkAchievements(achievements: List<Achievement>) {
        Log.d("qazasdedc", "checkAchievements")
        allSteps = stepRepository.getAllSteps().first()
        stepsByMonth =
            stepRepository.getAllStepsFromMonthByDays().first().values.sumOf { it.toLong() }
        stepsByWeek =
            stepRepository.getAllStepsFromWeekByDays().first().values.sumOf { it.toLong() }
        stepsByToday = stepRepository.getAllStepsFromToday().first()
        stepsByYear =
            stepRepository.getAlStepsFromYearByMonth().first().values.sumOf { it.toLong() }
        userHeight = repository.getUserData().first().height
        Log.d("qazasdedc", "allSteps $allSteps")
        Log.d("qazasdedc", "stepsByToday $stepsByToday")
        Log.d("qazasdedc", "stepsByWeek $stepsByWeek")
        Log.d("qazasdedc", "stepsByMonth $stepsByMonth")
        Log.d("qazasdedc", "stepsByYear $stepsByYear")
        Log.d("qazasdedc", "userHeight $userHeight")
        achievements.map {
            when (it.status) {
                Status.DONE -> {}
                Status.UNDONE -> checkAchievement(it)
            }
        }
    }

    private suspend fun checkAchievement(achievement: Achievement) {
        when (achievement.name) {
            Name.MARATHONER -> checkMarathoner(achievement)
            Name.SUPER_WALKER -> checkSuperWalker(achievement)
            Name.MILLION_STEPS -> checkMillionSteps(achievement)
            Name.CIRCLED_THE_EARTH -> checkCircledTheWorld(achievement)
            Name.ACTIVE_MONTH -> checkActiveMonth(achievement)
            Name.EVEREST_SUMMIT -> checkEverestSummit(achievement)
            Name.TRIP_TO_THE_MOON -> checkTripToTheMoon(achievement)
            Name.BILLION_STEPS -> checkBillionSteps(achievement)
            Name.BURNED_PIZZA -> checkBurnedPizza(achievement)
            Name.MINI_MARATHON -> checkMiniMarathon(achievement)
            Name.BURNED_CAKE -> checkBurnedCake(achievement)
            Name.BURNED_DAILY_NORM -> checkBurnedDailyNorm(achievement)
            Name.TEN_K_CALORIES -> checkTenKCalories(achievement)
            Name.MONTH_WORTH_CALORIES -> checkMonthWorth(achievement)
            Name.ONE_HUNDRED_THOUSAND_CALORIES -> checkOneHundredThousandCalories(achievement)
            Name.BURNED_BIG_MAC -> checkBurnedBigMac(achievement)
            Name.MILLION_CALORIES -> checkMillionCalories(achievement)
            Name.FIVE_KM_ON_FOOT -> checkFiveKmOnFoot(achievement)
            Name.GOLDEN_TEN -> checkGoldenTen(achievement)
            Name.FIFTY_KM_OF_JOURNEY -> checkFiftyKmOnJourney(achievement)
            Name.WALKING_TO_WORK -> checkWalkingToWork(achievement)
            Name.TWO_HUNDRED_KM_WEEKEND -> checkTwoHundredKmWeekend(achievement)
            Name.ONE_THOUSAND_KM_IN_YEAR -> checkOneThousandKmInYear(achievement)
            Name.CONTINENTAL_TRAVELER -> checkContinentalTraveler(achievement)
            Name.ULTRA_HIKE -> checkUltraHike(achievement)
            Name.CIRCLED_THE_EARTH_TWICE -> checkCircledTheWorldTwice(achievement)
        }
    }

    private suspend fun checkMarathoner(achievement: Achievement) {
        if (allSteps.toInt() >= MARATHON_DISTANCE) updateAchieve(achievement)
    }

    private suspend fun checkSuperWalker(achievement: Achievement) {
        if (allSteps.toInt() >= ONE_HUNDRED_THOUSAND) updateAchieve(achievement)
    }

    private suspend fun checkMillionSteps(achievement: Achievement) {
        if (allSteps.toInt() >= MILLION) updateAchieve(achievement)
    }

    private suspend fun checkCircledTheWorld(achievement: Achievement) {
        if (allSteps.toInt() >= CIRCLED_THE_WORLD) updateAchieve(achievement)
    }

    private suspend fun checkActiveMonth(achievement: Achievement) {
        if (stepsByMonth.toInt() >= ACTIVE_MONTH) updateAchieve(achievement)
    }

    private suspend fun checkEverestSummit(achievement: Achievement) {
        if (allSteps.toInt() >= EVEREST_SUMMIT) updateAchieve(achievement)
    }

    private suspend fun checkTripToTheMoon(achievement: Achievement) {
        if (allSteps.toInt() >= TRIP_TO_THE_MOON) updateAchieve(achievement)
    }

    private suspend fun checkBillionSteps(achievement: Achievement) {
        if (allSteps.toInt() >= BILLION) updateAchieve(achievement)
    }

    private suspend fun checkBurnedPizza(achievement: Achievement) {
        if (getCaloriesByToday() >= BURNED_PIZZA) updateAchieve(achievement)
    }

    private suspend fun checkMiniMarathon(achievement: Achievement) {
        if (getCaloriesByWeek() >= ONE_THOUSAND) updateAchieve(achievement)
    }

    private suspend fun checkBurnedCake(achievement: Achievement) {
        if (getAllCalories() >= BURNED_CAKE) updateAchieve(achievement)
    }

    private suspend fun checkBurnedDailyNorm(achievement: Achievement) {
        if (getCaloriesByToday() >= DAILY_NORM) updateAchieve(achievement)
    }

    private suspend fun checkTenKCalories(achievement: Achievement) {
        if (getAllCalories() >= TEN_THOUSAND) updateAchieve(achievement)
    }

    private suspend fun checkMonthWorth(achievement: Achievement) {
        if (getCaloriesByMonth() >= MONTH_WORTH) updateAchieve(achievement)
    }

    private suspend fun checkOneHundredThousandCalories(achievement: Achievement) {
        if (getAllCalories() >= ONE_HUNDRED_THOUSAND) updateAchieve(achievement)
    }

    private suspend fun checkBurnedBigMac(achievement: Achievement) {
        if (getAllCalories() >= BURNED_BIG_MAC) updateAchieve(achievement)
    }

    private suspend fun checkMillionCalories(achievement: Achievement) {
        if (getAllCalories() >= MILLION) updateAchieve(achievement)
    }

    private suspend fun checkFiveKmOnFoot(achievement: Achievement) {
        if (getDistanceByToday() >= FIVE) updateAchieve(achievement)
    }

    private suspend fun checkGoldenTen(achievement: Achievement) {
        if (getDistanceByToday() >= TEN) updateAchieve(achievement)
    }

    private suspend fun checkFiftyKmOnJourney(achievement: Achievement) {
        if (getAllDistance() >= FIFTY) updateAchieve(achievement)
    }

    private suspend fun checkWalkingToWork(achievement: Achievement) {
        if (getAllDistance() >= ONE_HUNDRED) updateAchieve(achievement)
    }

    private suspend fun checkTwoHundredKmWeekend(achievement: Achievement) {
        if (getDistanceByMonth() >= TWO_HUNDRED) updateAchieve(achievement)
    }

    private suspend fun checkOneThousandKmInYear(achievement: Achievement) {
        if (getDistanceByYear() >= ONE_THOUSAND) updateAchieve(achievement)
    }

    private suspend fun checkContinentalTraveler(achievement: Achievement) {
        if (getAllDistance() >= FIVE_THOUSAND) updateAchieve(achievement)
    }

    private suspend fun checkUltraHike(achievement: Achievement) {
        if (getAllDistance() >= TEN_THOUSAND) updateAchieve(achievement)
    }

    private suspend fun checkCircledTheWorldTwice(achievement: Achievement) {
        if (getAllDistance() >= CIRCLED_THE_WORLD_TWICE) updateAchieve(achievement)
    }

    private fun getCaloriesByToday(): Int = calculator.calculateCalories(stepsByToday).toInt()

    private fun getCaloriesByWeek(): Int = calculator.calculateCalories(stepsByWeek).toInt()

    private fun getCaloriesByMonth(): Int = calculator.calculateCalories(stepsByMonth).toInt()

    private fun getAllCalories(): Int = calculator.calculateCalories(allSteps).toInt()

    private fun getAllDistance(): Int =
        calculator.calculateDistanceInKm(userHeight, allSteps).toInt()

    private fun getDistanceByToday(): Int =
        calculator.calculateDistanceInKm(userHeight, stepsByToday).toInt()

    private fun getDistanceByMonth(): Int =
        calculator.calculateDistanceInKm(userHeight, stepsByMonth).toInt()

    private fun getDistanceByYear(): Int =
        calculator.calculateDistanceInKm(userHeight, stepsByYear).toInt()

    private suspend fun updateAchieve(achievement: Achievement) {
        val oldAchievements = repository.getAchievements().first()
        val updatedAchievements = oldAchievements.map {
            if (it.name == achievement.name) {
                it.copy(status = Status.DONE)
            } else {
                it
            }
        }
        Log.d("qazasdedc", "updateAchieve")
        Log.d("qazasdedc", "oldAchievements $oldAchievements")
        Log.d("qazasdedc", "achievement $achievement")
        Log.d("qazasdedc", "updatedAchievements $updatedAchievements")
        repository.setAchievements(updatedAchievements)
    }
}