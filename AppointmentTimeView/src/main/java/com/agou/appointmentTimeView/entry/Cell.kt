package com.agou.appointmentTimeView.entry

import android.graphics.Rect

class Cell(
    var date: CustomDate, //行 //时段 上午 下午 晚上 。全天
    var row: Int, //列
    var col: Int
) {
    private val rect: Rect = Rect()
    fun update(date: CustomDate, row: Int, col: Int) {
        this.date = date
        this.col = col
        this.row = row
    }

    fun getRect(perW: Int, perH: Int, leftW: Int): Rect {
        var left = 0
        var right = 0
        if (col == 0) {
            left = 0
            right = leftW
        } else {
            left = perW * (col - 1) + leftW
            right = left + perW
        }
        val top = row * perH
        val bottom = top + perH
        rect[left, top, right] = bottom
        return rect
    }

}