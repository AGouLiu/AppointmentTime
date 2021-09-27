package com.agou.appointmentTimeView.tool

import android.annotation.SuppressLint
import com.agou.appointmentTimeView.entry.CustomDate
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*





object DateUtil {
    var simpleDateFormat = SimpleDateFormat("yyyyMMdd")

    @JvmStatic
    fun getMonthDays(year: Int, month: Int): Int {
        var year = year
        var month = month
        if (month > 12) {
            month = 1
            year += 1
        } else if (month < 1) {
            month = 12
            year -= 1
        }
        val arr = intArrayOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
        var days = 0
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            arr[1] = 29
        }
        try {
            days = arr[month - 1]
        } catch (e: Exception) {
            e.stackTrace
        }
        return days
    }

    @JvmStatic
    val year: Int
        get() = Calendar.getInstance()[Calendar.YEAR]

    @JvmStatic
    val month: Int
        get() = Calendar.getInstance()[Calendar.MONTH] + 1

    @JvmStatic
    val currentMonthDay: Int
        get() = Calendar.getInstance()[Calendar.DAY_OF_MONTH]

    @JvmStatic
    val weekDay: Int
        get() = Calendar.getInstance()[Calendar.DAY_OF_WEEK]
    val hour: Int
        get() = Calendar.getInstance()[Calendar.HOUR_OF_DAY]
    val minute: Int
        get() = Calendar.getInstance()[Calendar.MINUTE]

    fun getWeekDayFromDate(year: Int, month: Int): Int {
        val cal = Calendar.getInstance()
        cal.time = getDateFromString(year, month)
        var week_index = cal[Calendar.DAY_OF_WEEK] - 1
        if (week_index < 0) {
            week_index = 0
        }
        return week_index
    }

    @SuppressLint("SimpleDateFormat")
    fun getDateFromString(year: Int, month: Int): Date? {
        val dateString = (year.toString() + "-" + (if (month > 9) month else "0$month")
                + "-01")
        var date: Date? = null
        try {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            date = sdf.parse(dateString)
        } catch (e: ParseException) {
            println(e.message)
        }
        return date
    }

    fun getCustomDateString(date: CustomDate): String {
        return "" + (if (date.month > 9) date.month else "0${date.month}") + "月" + (if (date.day > 9) date.day else "0${date.day}")+"日"
    }
    fun addCustomDateDay(date: CustomDate,day: Int): String {
        val sdf = SimpleDateFormat("MM月dd日")
        val cd = Calendar.getInstance()
        cd.time = sdf.parse(   getCustomDateString(date))
        cd.add(Calendar.DATE, day);
        return sdf.format(cd.time);
    }


    fun isToday(date: CustomDate): Boolean {
        return date.year == year && date.month == month && date.day == currentMonthDay
    }

    fun isCurrentMonth(date: CustomDate): Boolean {
        return date.year == year &&
                date.month == month
    }

    fun betweenDays(date: CustomDate, compareDate: CustomDate): Int {
        var dayCount = 0
        try {
            val date1 =
                simpleDateFormat.parse(date.year.toString() + (if (date.month < 10) "0" + date.month else "" + date.month) + "" + if (date.day < 10) "0" + date.day else "" + date.day)
            val compareDate1 =
                simpleDateFormat.parse(compareDate.year.toString() + (if (compareDate.month < 10) "0" + compareDate.month else "" + compareDate.month) + "" + if (compareDate.day < 10) "0" + compareDate.day else "" + compareDate.day)
            dayCount = ((date1.time - compareDate1.time) / (3600000 * 24)).toInt()
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dayCount
    }
}