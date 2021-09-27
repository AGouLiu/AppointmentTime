package com.agou.appointmentTimeView.entry

import com.agou.appointmentTimeView.tool.DateUtil.year
import com.agou.appointmentTimeView.tool.DateUtil.month
import com.agou.appointmentTimeView.tool.DateUtil.currentMonthDay
import com.agou.appointmentTimeView.tool.DateUtil.weekDay
import com.agou.appointmentTimeView.tool.DateUtil.getMonthDays
import com.agou.appointmentTimeView.entry.CustomDate
import android.os.Build
import com.agou.appointmentTimeView.tool.DateUtil
import java.io.Serializable
import java.util.ArrayList

class CustomDate : Serializable, Comparable<CustomDate?> {
    var year: Int
    var month: Int
    var day: Int
    var week = 0

    //{8:00-15:00-true }
    var timeFrame // 时段
            = 0

    //{8:00-15:00-true }
    @JvmField
    var status: MutableList<String>? = ArrayList()

    constructor(year: Int, month: Int, day: Int, week: Int) {
        var year = year
        var month = month
        if (month > 12) {
            month = 1
            year++
        } else if (month < 1) {
            month = 12
            year--
        }
        this.year = year
        this.month = month
        this.day = day
        this.week = week
    }

    constructor(year: Int, month: Int, day: Int, week: Int, timeFrame: Int) {
        var year = year
        var month = month
        if (month > 12) {
            month = 1
            year++
        } else if (month < 1) {
            month = 12
            year--
        }
        this.year = year
        this.month = month
        this.day = day
        this.week = week
        this.timeFrame = timeFrame
    }

    val isReservedOver: Boolean
        get() {
            var isReserved = true
            if (status != null && !status!!.isEmpty()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    status!!.stream().forEach { s ->
                        val split = s.split("-").toTypedArray()
                        isReserved= split != null && split.size >= 3 && split[2] == "true"
                    }
                }
            }
            return isReserved
        }

    fun update(year: Int, month: Int, day: Int, week: Int, timeFrame: Int) {
        update(year, month, day, week)
        this.timeFrame = timeFrame
    }

    fun update(year: Int, month: Int, day: Int, week: Int) {
        var year = year
        var month = month
        if (month > 12) {
            month = 1
            year++
        } else if (month < 1) {
            month = 12
            year--
        }
        this.year = year
        this.month = month
        this.day = day
        this.week = week
    }

    constructor(year: Int, month: Int, day: Int) {
        var year = year
        var month = month
        if (month > 12) {
            month = 1
            year++
        } else if (month < 1) {
            month = 12
            year--
        }
        this.year = year
        this.month = month
        this.day = day
    }

    constructor() {
        this.year = DateUtil.year
        this.month = DateUtil.month
        day = currentMonthDay
        week = weekDay
    }

    fun isSameDay(customDate: CustomDate): Boolean {
        return year == customDate.year && customDate.month == month && day == customDate.day
    }

    fun isSameMonth(customDate: CustomDate): Boolean {
        return year == customDate.year && customDate.month == month
    }

    fun isSameYear(customDate: CustomDate): Boolean {
        return year == customDate.year
    }
    fun getKey(): String {
        return "$year-$month-$day-$timeFrame"
    }
    override fun toString(): String {
        return "$year-$month-$day"
    }

    override fun compareTo(another: CustomDate?): Int {
        if (another != null) {
            if (this.year > another.year) {
                return 1
            } else if (this.year == another.year && this.month > another.month) {
                return 1
            } else if (this.year == another.year && this.month == another.month && day > another.day) {
                return 1
            } else if (this.year == another.year && this.month == another.month && day == another.day) {
                return 0
            }
            return -1
        }
        return -1
    }

    fun compareDays(another: CustomDate): Int {
        return if (this.month == another.month) {
            day - another.day
        } else {
            val days = getMonthDays(another.year, another.month)
            days - another.day + day
        }
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}