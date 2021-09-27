package com.agou.appointmentTimeView.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.agou.appointmentTimeView.view.CalendarView
import com.agou.appointmentTimeView.view.CalendarWeekView
import com.agou.appointmentTimeView.R
import com.agou.appointmentTimeView.entry.CustomDate
import java.util.*

abstract class ReservedAdapter : RecyclerView.Adapter<ReservedAdapter.ViewHolder>() {
    protected var mContext: Context? = null

     var listener: CalendarView.OnClickListener ? = null;

    private var reservedStatus: Map<String,  List<String>> = HashMap()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val mLayoutInflater = LayoutInflater.from(mContext)
        val calendarView: CalendarView
        calendarView =
            mLayoutInflater.inflate(R.layout.item_week_calendar, parent, false) as CalendarWeekView
        decorateCalendarView(calendarView)
        calendarView.setOnClickListener { calendarView, cell ->
            listener?.onClick(calendarView,cell)
        }
        return ViewHolder(calendarView)
    }

    //负责装饰日历
    protected abstract fun decorateCalendarView(calendarView: CalendarView?)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val calendar = Calendar.getInstance()
        //根据当天和 位置 计算 将当前的的星期1 复制到里面做游标
        val weekView = holder.itemView as CalendarWeekView
        val week = calendar[Calendar.DAY_OF_WEEK]-1
        calendar.add(Calendar.DATE, position * 7 - week+1) // 这里得到周1
        val customDate = CustomDate(
            calendar[Calendar.YEAR],
            calendar[Calendar.MONTH] + 1,
            calendar[Calendar.DAY_OF_MONTH]
        )
        weekView.setShowDate(customDate, reservedStatus)
    }

    override fun getItemCount(): Int {
        return 4
    }

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

    fun setReservedStatus(reservedStatus: Map<String,  List<String>>) {
        this.reservedStatus = reservedStatus
        notifyDataSetChanged()
    }

}