package com.example.chess

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect


class Cases(private val id: Int) {

    private val paint = Paint()
    private val col = (((id % 8) + (id / 8)) % 2) * 255
    private var color = Color.argb(255, col, col, col)
    private var renders = 0

    fun setcolor(res: Int) {
        color = res
    }

    fun clear() {
        color = if (col == 0) Color.argb(255, 121, 68, 59)
        else Color.argb(255, 255, 242, 230)
    }

    fun draw(canvas: Canvas?, cote: Int) {
        if (renders == 0) clear(); renders++
        val x1 = (id % 8) * cote
        val y1 = cote * 11 - cote * (id / 8)
        val r = Rect(x1, y1, x1 + cote, y1 - cote)
        paint.color = color
        canvas?.drawRect(r, paint)
    }

}