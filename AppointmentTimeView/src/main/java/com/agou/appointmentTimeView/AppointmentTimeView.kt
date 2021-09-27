package com.agou.appointmentTimeView

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AbsListView
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.agou.appointmentTimeView.entry.Cell
import com.agou.appointmentTimeView.entry.CustomDate
import com.agou.appointmentTimeView.tool.DateUtil
import com.agou.appointmentTimeView.view.CalendarView
import java.util.HashMap


class AppointmentTimeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private var recyclerView: RecyclerView? = null
    private var customCalendarAdapter: ReservedCalendarAdapter? = null
    private var manager: LinearLayoutManager? = null
    private var position: Int = 0

    init {
        initView()
    }

    lateinit var onPageChanged2: (String, String) -> Unit
    lateinit var onClickListener: (CalendarView, Cell) -> Unit
    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.view_appointment_time, this)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        customCalendarAdapter = ReservedCalendarAdapter()
        customCalendarAdapter!!.listener  = CalendarView.OnClickListener { calendarView, cell ->
            onClickListener.invoke(calendarView,cell)
        }
        recyclerView?.adapter = customCalendarAdapter
        manager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView?.layoutManager = manager
        manager?.scrollToPosition(position)
        recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    val calendarView: CalendarView = recyclerView.getChildAt(0) as CalendarView
                    val showDate: CustomDate = calendarView.showDate
                    onPageChanged2.invoke(DateUtil.getCustomDateString(showDate),DateUtil.addCustomDateDay(showDate,6))
                }
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
        PagerSnapHelper().attachToRecyclerView(recyclerView)
    }
    fun  setOnPageChanged(onPageChanged2: (String, String) -> Unit){
        this.onPageChanged2 = onPageChanged2
        val nowDate=  CustomDate()
        onPageChanged2.invoke(DateUtil.getCustomDateString(nowDate),DateUtil.addCustomDateDay(nowDate,6))
    }
    @JvmName("setOnClickListener1")
    fun  setOnClickListener(onClickListener: (CalendarView, Cell) -> Unit){
        this.onClickListener = onClickListener
    }

    fun updateStatus(reservedStatus: Map<String,  List<String>>) {
        customCalendarAdapter?.setReservedStatus(reservedStatus)
    }

    fun nextPage() {
        if ( position < getPageCount()!!){
            position ++
        }
        manager?.scrollToPosition(position)
    }
    fun upPage() {
        if ( position >0){
            position --
        }
        manager?.scrollToPosition(position)
    }

    private fun getPageCount(): Int? =  customCalendarAdapter?.itemCount


}