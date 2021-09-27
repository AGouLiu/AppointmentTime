package com.agou.appointmentTimeView.imp

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import com.agou.appointmentTimeView.view.CalendarView
import com.agou.appointmentTimeView.entry.Cell

interface IDrawFormat {
    fun onDraw(calendarView: CalendarView)
    fun getDateType(calendarView: CalendarView?, cell: Cell?): Int
    fun onDraw(
        calendarView: CalendarView?,
        canvas: Canvas?,
        cell: Cell?,
        rect: Rect?,
        paint: Paint?
    )

    fun onClick(calendarView: CalendarView, cell: Cell?)
}