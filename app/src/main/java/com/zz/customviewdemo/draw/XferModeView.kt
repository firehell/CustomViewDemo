package com.zz.customviewdemo.draw

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.zz.customviewdemo.dp


class XferModeView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private val IMAGE_WIDTH = 150f.dp
    private val xfermode = PorterDuffXfermode(PorterDuff.Mode.DST)
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bounds = RectF(150f.dp, 50f.dp, 300f.dp, 300f.dp)

    private val circleBitmap =
        Bitmap.createBitmap(IMAGE_WIDTH.toInt(), IMAGE_WIDTH.toInt(), Bitmap.Config.ARGB_8888)
    private val squareBitmap =
        Bitmap.createBitmap(IMAGE_WIDTH.toInt(), IMAGE_WIDTH.toInt(), Bitmap.Config.ARGB_8888)

    init {
        var canvas = Canvas(circleBitmap)
        paint.color = Color.RED
        canvas.drawOval(50f.dp, 0f, 150f.dp, 100f.dp, paint)
        paint.color = Color.BLUE
        canvas = Canvas(squareBitmap)
        canvas.drawRect(0f, 50f.dp, 100f.dp, 150f.dp, paint)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val count = canvas.saveLayer(bounds, null)
        canvas.drawBitmap(circleBitmap, 150f.dp, 50f.dp, paint)
        paint.xfermode = xfermode
        canvas.drawBitmap(squareBitmap, 150f.dp, 50f.dp, paint)
        paint.xfermode = null
        canvas.restoreToCount(count)
    }
}