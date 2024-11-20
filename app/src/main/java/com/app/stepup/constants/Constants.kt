package com.app.stepup.constants

import com.app.stepup.R
import com.app.stepup.model.data.Achievement
import com.app.stepup.model.data.Gender
import com.app.stepup.model.data.Name
import com.app.stepup.model.data.Status
import com.app.stepup.model.data.User

object Constants {
    const val MAIN = "MAIN"
    const val REGISTRATION = "REGISTRATION"
    const val HOME = "HOME"
    const val PROFILE = "PROFILE"
    const val ANALYTICS = "ANALYTICS"
    const val ACHIEVEMENTS = "ACHIEVEMENTS"
    const val ACHIEVEMENT = "ACHIEVEMENT"

    const val STEPS_COUNTER_CHANNEL_ID = "step_counter_channel"
    const val STEPS_COUNTER_CHANNEL_NAME = "Step Counter"
    const val STEPS_COUNTER_NOTIFICATION_ID = 1

    const val DATABASE_NAME = "step_database"
    const val DATA_STORE_NAME = "step_up_preferences"

    const val KEY_ACHIEVEMENT = "key_achievement"

    const val MARATHON_DISTANCE = 42195
    const val ONE_HUNDRED_THOUSAND = 100000
    const val MILLION = 1000000
    const val CIRCLED_THE_WORLD = 50000000
    const val ACTIVE_MONTH = 300000
    const val EVEREST_SUMMIT = 58070
    const val TRIP_TO_THE_MOON = 478000000
    const val BILLION = 1000000000
    const val BURNED_PIZZA = 300
    const val BURNED_CAKE = 2000
    const val DAILY_NORM = 2500
    const val MONTH_WORTH = 75000
    const val BURNED_BIG_MAC = 550
    const val CIRCLED_THE_WORLD_TWICE = 80000
    const val FIVE = 5
    const val TEN = 10
    const val FIFTY = 50
    const val ONE_HUNDRED = 100
    const val TWO_HUNDRED = 200
    const val ONE_THOUSAND = 1000
    const val FIVE_THOUSAND = 5000
    const val TEN_THOUSAND = 10000

    val emptyUser = User(
        "", 0, Gender.OTHER, 0.0, 0.0
    )

    val defaultAchievements = listOf(
        Achievement(
            R.drawable.ic_running,
            Name.MARATHONER,
            R.string.marathoner_descr,
            Status.UNDONE
        ),
        Achievement(
            R.drawable.ic_footprints,
            Name.SUPER_WALKER,
            R.string.super_walker_descr,
            Status.UNDONE
        ),
        Achievement(
            R.drawable.ic_medal,
            Name.MILLION_STEPS,
            R.string.million_steps_descr,
            Status.UNDONE
        ),
        Achievement(
            R.drawable.ic_globe,
            Name.CIRCLED_THE_EARTH,
            R.string.circled_the_earth_descr,
            Status.UNDONE
        ),
        Achievement(
            R.drawable.ic_calendar,
            Name.ACTIVE_MONTH,
            R.string.active_month_descr,
            Status.UNDONE
        ),
        Achievement(
            R.drawable.ic_mountain,
            Name.EVEREST_SUMMIT,
            R.string.everest_summit_descr,
            Status.UNDONE
        ),
        Achievement(
            R.drawable.ic_astronaut,
            Name.TRIP_TO_THE_MOON,
            R.string.trip_to_the_moon_descr,
            Status.UNDONE
        ),
        Achievement(
            R.drawable.ic_trophy,
            Name.BILLION_STEPS,
            R.string.billion_steps_descr,
            Status.UNDONE
        ),
        Achievement(
            R.drawable.ic_pizza,
            Name.BURNED_PIZZA,
            R.string.burned_pizza_descr,
            Status.UNDONE
        ),
        Achievement(
            R.drawable.ic_torch,
            Name.MINI_MARATHON,
            R.string.mini_marathon_of_calories_descr,
            Status.UNDONE
        ),
        Achievement(
            R.drawable.ic_cake,
            Name.BURNED_CAKE,
            R.string.burned_cake_descr,
            Status.UNDONE
        ),
        Achievement(
            R.drawable.ic_eat,
            Name.BURNED_DAILY_NORM,
            R.string.burned_daily_norm_descr,
            Status.UNDONE
        ),
        Achievement(
            R.drawable.ic_metabolism,
            Name.TEN_K_CALORIES,
            R.string.ten_k_calories_descr,
            Status.UNDONE
        ),
        Achievement(
            R.drawable.ic_calendar,
            Name.MONTH_WORTH_CALORIES,
            R.string.burned_month_worth_calories_descr,
            Status.UNDONE
        ),
        Achievement(
            R.drawable.ic_metabolism,
            Name.ONE_HUNDRED_THOUSAND_CALORIES,
            R.string.one_hundred_thousand_calories_descr,
            Status.UNDONE
        ),
        Achievement(
            R.drawable.ic_burger,
            Name.BURNED_BIG_MAC,
            R.string.burned_big_mac_descr,
            Status.UNDONE
        ),
        Achievement(
            R.drawable.ic_trophy,
            Name.MILLION_CALORIES,
            R.string.million_calories_descr,
            Status.UNDONE
        ),
        Achievement(
            R.drawable.ic_footprints,
            Name.FIVE_KM_ON_FOOT,
            R.string.five_km_on_foot_descr,
            Status.UNDONE
        ),
        Achievement(
            R.drawable.ic_footprints,
            Name.GOLDEN_TEN,
            R.string.golden_ten_descr,
            Status.UNDONE
        ),
        Achievement(
            R.drawable.ic_compass,
            Name.FIFTY_KM_OF_JOURNEY,
            R.string.fifty_km_journey_descr,
            Status.UNDONE
        ),
        Achievement(
            R.drawable.ic_office,
            Name.WALKING_TO_WORK,
            R.string.walking_to_work_descr,
            Status.UNDONE
        ),
        Achievement(
            R.drawable.ic_map,
            Name.TWO_HUNDRED_KM_WEEKEND,
            R.string.two_hundred_km_weekend_descr,
            Status.UNDONE
        ),
        Achievement(
            R.drawable.ic_distance,
            Name.ONE_THOUSAND_KM_IN_YEAR,
            R.string.one_thousand_km_in_year_descr,
            Status.UNDONE
        ),
        Achievement(
            R.drawable.ic_airplane,
            Name.CONTINENTAL_TRAVELER,
            R.string.continental_traveler_descr,
            Status.UNDONE
        ),
        Achievement(
            R.drawable.ic_pedometer,
            Name.ULTRA_HIKE,
            R.string.ultra_hike_descr,
            Status.UNDONE
        ),
        Achievement(
            R.drawable.ic_global,
            Name.CIRCLED_THE_EARTH_TWICE,
            R.string.circled_the_earth_twice_descr,
            Status.UNDONE
        )
    )
}
