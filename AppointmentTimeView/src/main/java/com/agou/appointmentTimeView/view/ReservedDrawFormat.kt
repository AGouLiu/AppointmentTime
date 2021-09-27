package com.agou.appointmentTimeView.view

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import com.agou.appointmentTimeView.entry.Cell
import com.agou.appointmentTimeView.imp.IDrawFormat

abstract class ReservedDrawFormat : IDrawFormat {

    override fun onClick(calendarView: CalendarView, cell: Cell?) {}
    override fun getDateType(calendarView: CalendarView?, cell: Cell?): Int {
        if (calendarView == null||cell ==null) {
            return 0
        }

        if (cell.col == 0 && cell.row != 0) {  //
            return TimeFrame
        }
        if (cell.row == 0 && cell.col != 0) { // 第一行的时候是日期
            return DateTime
        }
        return if (cell.row > 0 && cell.col > 0) {
            if (cell.date.status!!.isEmpty()) {
                Reserved
            } else {
                if (cell.date.isReservedOver) {
                    ReservedOver
                } else {
                    ReservedLeisure
                }
            }
        } else 0
    }

    override fun onDraw(
        calendarView: CalendarView?,
        canvas: Canvas?,
        cell: Cell?,
        rect: Rect?,
        paint: Paint?
    ) {
        val type = getDateType(calendarView, cell)
        onDrawBackground(canvas, type, rect, paint)
        onDrawDateText(canvas, type, rect, paint, cell)
        onDrawTimeFrame(canvas, type, rect, paint, cell)
        onDrawReserved(canvas, type, rect, paint, cell)
        onDrawReservedOver(canvas, type, rect, paint, cell)
    }

    //绘制背景  在有
    abstract fun onDrawBackground(canvas: Canvas?, type: Int, rect: Rect?, paint: Paint?)

    //绘制 日期
    abstract fun onDrawDateText(canvas: Canvas?, type: Int, rect: Rect?, paint: Paint?, cell: Cell?)

    //绘制 时段
    abstract fun onDrawTimeFrame(
        canvas: Canvas?,
        type: Int,
        rect: Rect?,
        paint: Paint?,
        cell: Cell?
    )

    /**
     * 绘制预约
     *
     * @param canvas
     * @param type
     * @param rect
     * @param paint
     * @param cell
     */
    abstract fun onDrawReserved(canvas: Canvas?, type: Int, rect: Rect?, paint: Paint?, cell: Cell?)

    /**
     * 绘制月满
     *
     * @param canvas
     * @param type
     * @param rect
     * @param paint
     * @param cell
     */
    abstract fun onDrawReservedOver(
        canvas: Canvas?,
        type: Int,
        rect: Rect?,
        paint: Paint?,
        cell: Cell?
    )

    override fun onDraw(calendarView: CalendarView) {}

    companion object {
        const val ALL_DAY = 0 //日期
        const val AM = 1 // 上午
        const val PM = 2 //  下午
        const val NIGHT = 3 // 晚上
        const val TimeFrame = 3 // 时段
        const val DateTime = 4 // 日期
        const val Reserved = 5 // 预约
        const val ReservedLeisure = 6 // 有预
        const val ReservedOver = 7 // 预满
    }
}