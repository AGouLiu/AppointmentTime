package com.agou.appointmentTimeView

import com.agou.appointmentTimeView.adapter.ReservedAdapter
import com.agou.appointmentTimeView.view.CalendarView

class ReservedCalendarAdapter : ReservedAdapter() {
    private var format: ReservedTestDrawFormat? = null
    override fun decorateCalendarView(calendarView: CalendarView?) {
        if (format == null) {
            format = mContext?.let { ReservedTestDrawFormat(it) }
        }
        calendarView!!.addDrawFormat(format)
    }

    fun updateReservedStatus(status: Map<String?, String?>?) {}
}