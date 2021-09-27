package com.agou.appointmentTimeView.view

import android.content.Context
import android.util.AttributeSet
import com.agou.appointmentTimeView.entry.Cell
import com.agou.appointmentTimeView.entry.CustomDate
import com.agou.appointmentTimeView.tool.DateUtil

class CalendarWeekView(context: Context?, attrs: AttributeSet?) : CalendarView(context, attrs) {


    fun setShowDate(showDate: CustomDate, status: Map<String,  List<String>>) {
        mShowDate = showDate
        fillWeekDate(status)
        invalidate()
    }

    public override fun measureClickCell(col: Int, row: Int) {
        if (mShowDate != null) {
            if (col >= TOTAL_COL || row >= TOTAL_ROW) return
            val cell = cells[row][col]
            if (cell?.date == null) {
                return
            }
            geCalendarDraw().onClick(this, cell)
            if (onClickListener != null) {
                onClickListener.onClick(this, cell)
            }
            /* fillWeekDate();
            invalidate();*/
        }
    }

    /**
     * 只显示最新一个月的
     */
    override fun getTotalRow(): Int {
        return 4
    }

   private fun fillWeekDate(status: Map<String, List<String>>) {
        val currentMonthDays = DateUtil.getMonthDays(mShowDate.year, mShowDate.month)

        for (i in 0 until TOTAL_ROW) {
            if (cells[i] == null) cells[i] = arrayOfNulls(TOTAL_COL)
            if (cells[i][0] == null) cells[i][0] = Cell(CustomDate(), i, 0)
            for (j in 1 until TOTAL_COL) {
                val year = mShowDate.year
                var month = mShowDate.month
                var monthDay = mShowDate.day + j - 1
                if (monthDay > currentMonthDays) {
                    month += 1
                    monthDay -= currentMonthDays
                }
                val cell = cells[i][j]
                if (cell?.date != null) {
                    cell.date.update(year, month, monthDay, j, i)
                    cell.update(cell.date, i, j)
                    update(status, cell)

                } else {
                    val date = CustomDate(year, month, monthDay, j, i)
                    cells[i][j] = Cell(date, i, j)
                    update(status, cells[i][j])
                }
            }
        }
    }

    private fun update(status: Map<String, List<String>>?, cell: Cell) {
        if (!status.isNullOrEmpty()) {
            val let = status[cell.date.getKey()]
            if (!let.isNullOrEmpty()) {
                cell.date.status = let as MutableList<String>
            } else {
                cell.date.status?.clear()
            }
        } else {
            cell.date.status?.clear()
        }
    }
}
