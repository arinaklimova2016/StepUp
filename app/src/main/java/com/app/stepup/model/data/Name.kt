package com.app.stepup.model.data

import com.app.stepup.R

enum class Name(private val nameRes: Int) {
    MARATHONER(R.string.marathoner),
    SUPER_WALKER(R.string.super_walker),
    MILLION_STEPS(R.string.million_steps),
    CIRCLED_THE_EARTH(R.string.circled_the_earth),
    ACTIVE_MONTH(R.string.active_month),
    EVEREST_SUMMIT(R.string.everest_summit),
    TRIP_TO_THE_MOON(R.string.trip_to_the_moon),
    BILLION_STEPS(R.string.billion_steps),
    BURNED_PIZZA(R.string.burned_pizza),
    MINI_MARATHON(R.string.mini_marathon_of_calories),
    BURNED_CAKE(R.string.burned_cake),
    BURNED_DAILY_NORM(R.string.burned_daily_norm),
    TEN_K_CALORIES(R.string.ten_k_calories),
    MONTH_WORTH_CALORIES(R.string.burned_month_worth_calories),
    ONE_HUNDRED_THOUSAND_CALORIES(R.string.one_hundred_thousand_calories),
    BURNED_BIG_MAC(R.string.burned_big_mac),
    MILLION_CALORIES(R.string.million_calories),
    FIVE_KM_ON_FOOT(R.string.five_km_on_foot),
    GOLDEN_TEN(R.string.golden_ten),
    FIFTY_KM_OF_JOURNEY(R.string.fifty_km_journey),
    WALKING_TO_WORK(R.string.walking_to_work),
    TWO_HUNDRED_KM_WEEKEND(R.string.two_hundred_km_weekend),
    ONE_THOUSAND_KM_IN_YEAR(R.string.one_thousand_km_in_year),
    CONTINENTAL_TRAVELER(R.string.continental_traveler),
    ULTRA_HIKE(R.string.ultra_hike),
    CIRCLED_THE_EARTH_TWICE(R.string.circled_the_earth_twice);

    fun getNameRes(): Int {
        return nameRes
    }
}