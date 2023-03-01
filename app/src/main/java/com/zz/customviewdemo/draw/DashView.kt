package com.zz.customviewdemo.draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Paint.Cap
import android.graphics.Path
import android.graphics.PathDashPathEffect
import android.graphics.PathEffect
import android.graphics.PathMeasure
import android.util.AttributeSet
import android.view.View
import com.zz.customviewdemo.dp
import kotlin.math.cos
import kotlin.math.sin

private val RADIUS = 150f.dp
private val DASH_WIDTH = 2f.dp
private val DASH_LENGTH = 10f.dp
private const val OPEN_ANGLE = 120f
private const val MARK = 10
private val LENGTH = 120f.dp

class DashView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()
    private val dash = Path()
    private lateinit var pathEffect: PathEffect

    init {
        paint.strokeWidth = 3f.dp
        paint.strokeCap = Cap.ROUND
        paint.style = Paint.Style.STROKE

        dash.addRect(0f, 0f, DASH_WIDTH, DASH_LENGTH, Path.Direction.CCW)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        path.reset()
        path.addArc(
            w / 2 - RADIUS,
            h / 2 - RADIUS,
            width / 2 + RADIUS,
            h / 2 + RADIUS,
            90 + OPEN_ANGLE / 2f,
            360 - OPEN_ANGLE
        )

        val pathMeasure = PathMeasure(path, false)
        pathEffect = PathDashPathEffect(dash, (pathMeasure.length - DASH_WIDTH) / 20f, 0f, PathDashPathEffect.Style.ROTATE)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // 画弧
        canvas.drawPath(path, paint)

        // 画刻度
        paint.pathEffect = pathEffect
        canvas.drawPath(path, paint)
        paint.pathEffect = null

        canvas.drawLine(width / 2f, height / 2f,
            width / 2f + LENGTH * cos(markToRadians(MARK)).toFloat(),
            height / 2f + LENGTH * sin(markToRadians(MARK)).toFloat(), paint)

    }

    private fun markToRadians(mark: Int) =
        Math.toRadians((90 + OPEN_ANGLE / 2f + (360 - OPEN_ANGLE) / 20f * mark).toDouble())
}