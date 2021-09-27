package com.agou.appointmentTimeView

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import androidx.core.content.ContextCompat
import com.agou.appointmentTimeView.entry.Cell
import com.agou.appointmentTimeView.entry.CustomDate
import com.agou.appointmentTimeView.tool.DensityUtils
import com.agou.appointmentTimeView.view.CalendarView
import com.agou.appointmentTimeView.view.ReservedDrawFormat

class ReservedTestDrawFormat(private val mContext: Context) : ReservedDrawFormat() {
    private var progress = 100
    private var calendarView: CalendarView? = null
    private var clickDate: CustomDate? = null
    var interval = 7 // 间距
    var corner = 10 // 圆角
    override fun onDrawBackground(canvas: Canvas?, type: Int, rect: Rect?, paint: Paint?) {
        if (type == Reserved) {
            paint!!.style = Paint.Style.FILL
            paint.color = ContextCompat.getColor(mContext, R.color.reserved_bg_color)
            val rect1 = RectF(
                (rect!!.left + interval).toFloat(),
                (rect.top + interval).toFloat(),
                (rect.right - interval).toFloat(),
                (rect.bottom - interval).toFloat()
            )
            canvas!!.drawRoundRect(rect1, corner.toFloat(), corner.toFloat(), paint)
        }
    }

    //绘制 日期
    override fun onDrawDateText(
        canvas: Canvas?,
        type: Int,
        rect: Rect?,
        paint: Paint?,
        cell: Cell?
    ) {
        if (type == DateTime) {
            paint!!.textSize = DensityUtils.sp2px(mContext, 13f).toFloat()
            paint.color = ContextCompat.getColor(mContext, R.color.reserved_text_color)
            paint.style = Paint.Style.FILL
            paint.textAlign = Paint.Align.CENTER
            val date = cell!!.date
            canvas!!.drawText(
                getWeek(cell), rect!!.centerX().toFloat(),
                CalendarView.getTextCenterY(
                    rect.centerY() - DensityUtils.dp2px(mContext, 9f),
                    paint
                ), paint
            )
            val textHeight = CalendarView.getTextHeight(paint)
            canvas.drawText(
                getDateTime(cell),
                rect.centerX().toFloat(),
                CalendarView.getTextCenterY(rect.centerY() + textHeight / 2, paint), paint
            )
        }
    }

    //绘制 时段
    override fun onDrawTimeFrame(
        canvas: Canvas?,
        type: Int,
        rect: Rect?,
        paint: Paint?,
        cell: Cell?
    ) {
        if (type == TimeFrame) {
            paint!!.textSize = DensityUtils.sp2px(mContext, 13f).toFloat()
            paint.color = ContextCompat.getColor(mContext, R.color.reserved_text_color)
            paint.style = Paint.Style.FILL
            paint.textAlign = Paint.Align.CENTER
            val timeFrame = getTimeFrame(cell)
            val split = timeFrame.split(",").toTypedArray()
            canvas!!.drawText(
                split[0], rect!!.centerX().toFloat(),
                CalendarView.getTextCenterY(
                    rect.centerY() - DensityUtils.dp2px(mContext, 9f),
                    paint
                ), paint
            )
            val textHeight = CalendarView.getTextHeight(paint)
            canvas.drawText(
                split[1],
                rect.centerX().toFloat(),
                CalendarView.getTextCenterY(rect.centerY() + textHeight / 2, paint), paint
            )
        }
    }

    override fun onDrawReserved(
        canvas: Canvas?,
        type: Int,
        rect: Rect?,
        paint: Paint?,
        cell: Cell?
    ) {
        if (type == ReservedLeisure) {
            paint!!.style = Paint.Style.FILL
            paint.color = ContextCompat.getColor(mContext, R.color.reserved_text_color)
            val rect1 = RectF(
                (rect!!.left + interval).toFloat(),
                (rect.top + interval).toFloat(),
                (rect.right - interval).toFloat(),
                (rect.bottom - interval).toFloat()
            )
            canvas!!.drawRoundRect(rect1, corner.toFloat(), corner.toFloat(), paint)
            paint.textSize = DensityUtils.sp2px(mContext, 12f).toFloat()
            paint.color = ContextCompat.getColor(mContext, R.color.reserved_r_text_color)
            paint.style = Paint.Style.FILL
            paint.textAlign = Paint.Align.CENTER
            canvas.drawText(
                "点击", rect.centerX().toFloat(),
                CalendarView.getTextCenterY(
                    rect.centerY() - DensityUtils.dp2px(mContext, 9f),
                    paint
                ), paint
            )
            val textHeight = CalendarView.getTextHeight(paint)
            canvas.drawText(
                "预约",
                rect.centerX().toFloat(),
                CalendarView.getTextCenterY(rect.centerY() + textHeight / 2, paint), paint
            )
        }
    }

    override fun onDrawReservedOver(
        canvas: Canvas?,
        type: Int,
        rect: Rect?,
        paint: Paint?,
        cell: Cell?
    ) {
        if (type == ReservedOver) {
            paint!!.style = Paint.Style.FILL
            paint.color = ContextCompat.getColor(mContext, R.color.reserved_bg_waring_color)
            val rect1 = RectF(
                (rect!!.left + interval).toFloat(),
                (rect.top + interval).toFloat(),
                (rect.right - interval).toFloat(),
                (rect.bottom - interval).toFloat()
            )
            canvas!!.drawRoundRect(rect1, corner.toFloat(), corner.toFloat(), paint)
            paint.textSize = DensityUtils.sp2px(mContext, 12f).toFloat()
            paint.color = ContextCompat.getColor(mContext, R.color.reserved_text_waring_color)
            paint.style = Paint.Style.FILL
            paint.textAlign = Paint.Align.CENTER
            canvas.drawText(
                "预约", rect.centerX().toFloat(),
                CalendarView.getTextCenterY(
                    rect.centerY() - DensityUtils.dp2px(mContext, 9f),
                    paint
                ), paint
            )
            val textHeight = CalendarView.getTextHeight(paint)
            canvas.drawText(
                "已满",
                rect.centerX().toFloat(),
                CalendarView.getTextCenterY(rect.centerY() + textHeight / 2, paint), paint
            )
        }
    }

    override fun onDraw(calendarView: CalendarView) {
        if (this.calendarView === calendarView && progress == 0) {
            val animator = ValueAnimator.ofInt(30, 100).setDuration(200)
            animator.addUpdateListener { animation ->
                progress = animation.animatedValue as Int
                calendarView.invalidate()
            }
            animator.start()
        }
    }

    override fun onClick(calendarView: CalendarView, cell: Cell?) {
        super.onClick(calendarView, cell)
        this.calendarView = calendarView
        progress = 0
        clickDate = cell!!.date
        //   Log.d("ReservedTestDrawFormat ", "onClick: " +  "row " + cell.row + "  col  = " + cell.col + clickDate.toString() );
    }

    fun getDateTime(cell: Cell?): String {
        return cell!!.date.month.toString() + "-" + cell.date.day
    }

    fun getWeek(cell: Cell?): String {
        val stringArray = mContext.resources.getStringArray(R.array.week)
        return stringArray[cell!!.date.week - 1]
    }

    fun getTimeFrame(cell: Cell?): String {
        val stringArray = mContext.resources.getStringArray(R.array.time_frame)
        return stringArray[cell!!.row - 1]
    }
}