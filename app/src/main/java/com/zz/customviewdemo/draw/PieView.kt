package com.zz.customviewdemo.draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.zz.customviewdemo.dp
import kotlin.math.cos
import kotlin.math.sin

class PieView(context: Context?, attr: AttributeSet?) : View(context, attr) {
    private val OFFSET_LENGTH = 20f.dp
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val RADIUS = 150f.dp
    private val ANGLES = floatArrayOf(60f, 90f, 150f, 60f)
    private val COLORS = listOf(
        Color.parseColor("#C2185B"),
        Color.parseColor("#00ACC1"),
        Color.parseColor("#558B2F"),
        Color.parseColor("#5D4037")
    )

    init {
        paint.strokeWidth = 3f.dp
        paint.style = Paint.Style.FILL
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var startAngle = 0f
        for ((index, angle) in ANGLES.withIndex()) {
            paint.color = COLORS[index]
            if (index == 2) {
                canvas.save()
                canvas.translate(OFFSET_LENGTH * cos(Math.toRadians(startAngle + angle / 2f.toDouble())).toFloat(), OFFSET_LENGTH * sin(Math.toRadians(startAngle + angle / 2f.toDouble())).toFloat())
            }
            canvas.drawArc(
                width / 2 - RADIUS,
                height / 2 - RADIUS,
                width / 2 + RADIUS,
                height / 2 + RADIUS,
                startAngle,
                angle,
                true,
                paint
            )
            startAngle += angle
            if (index == 2) {
                canvas.restore()
            }
        }
    }
}