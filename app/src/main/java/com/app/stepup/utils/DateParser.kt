package com.app.stepup.utils

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DateParser @Inject constructor() {
    fun getDayFromDate(dateString: String): Int {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(dateString, formatter)
        return date.dayOfMonth
    }

    fun getMonthAndYearFromDate(dateString: String): Pair<Int, String> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM")
        val yearMonth = YearMonth.parse(dateString, formatter)
        return Pair(yearMonth.monthValue, yearMonth.year.toString())
    }
}