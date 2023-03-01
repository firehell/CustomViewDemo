package com.zz.customviewdemo.draw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.zz.customviewdemo.R
import com.zz.customviewdemo.dp

class SportView(context: Context, attr: AttributeSet?) : View(context, attr) {
    private val CIRCLE_COLOR = Color.parseColor("#90A4AE")
    private val HIGHLIGHT_COLOR = Color.parseColor("#FF4081")
    private val radius = 150f.dp
    private val bound = Rect()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 100f.dp
        textAlign = Paint.Align.CENTER
        typeface = ResourcesCompat.getFont(context, R.font.font)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 20f.dp
        paint.color = CIRCLE_COLOR
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, paint)

        paint.strokeCap = Paint.Cap.ROUND
        paint.color = HIGHLIGHT_COLOR
        canvas.drawArc(
            width / 2 - radius,
            height / 2 - radius,
            width / 2 + radius,
            height / 2 + radius,
            220f,
            200f,
            false,
            paint
        )

        paint.style = Paint.Style.FILL
        paint.getTextBounds("abab", 0, "abab".length, bound)
//        canvas.drawText(
//            "abab",
//            (width / 2).toFloat(),
//            (height / 2).toFloat() + (bound.bottom - bound.top) / 2,
//            paint
//        )


        paint.textSize = 150f.dp
        paint.textAlign = Paint.Align.LEFT
        val fontMetrics = paint.fontMetrics
        paint.getTextBounds("abab", 0, "abab".length, bound)
        canvas.drawText("abab", (-bound.left).toFloat(), 150f.dp, paint)

        paint.textSize = 15f.dp
        paint.getTextBounds("abab", 0, "abab".length, bound)
        canvas.drawText("abab", -bound.left.toFloat(), -bound.top.toFloat(), paint)
    }
}