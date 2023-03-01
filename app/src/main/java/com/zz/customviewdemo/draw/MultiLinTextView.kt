package com.zz.customviewdemo.draw

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.view.ViewConfiguration
import com.zz.customviewdemo.R
import com.zz.customviewdemo.dp

class MultiLinTextView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val content =
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam et augue eros. Nam maximus " + "tempor mattis. Proin quis massa consectetur, dapibus erat sit amet, aliquet risus." + " Cras eget efficitur sem. Praesent sed congue eros. Nulla facilisi. Donec blandit " + "dignissim erat nec tincidunt. Nunc varius ipsum pulvinar mauris pulvinar, congue " + "convallis tortor fringilla. Nunc hendrerit aliquam mi sed convallis. Cras vulputate " + "leo ut condimentum fermentum. Vestibulum mollis sapien a enim cursus, at porttitor enim" + " porta. Nullam maximus sem vel lacus fermentum, et ultrices erat tincidunt. Nam eu" + " tortor a dui vestibulum fermentum."

    private val paint = TextPaint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 16.dp
    }

    private val fontMetrics = Paint.FontMetrics()

    private var image_width = 150.dp
    private var image_padding = 50.dp
    private val bitmap = getAvatar(image_width.toInt())

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        val staticLayout = StaticLayout(
//            content, paint, width, Layout.Alignment.ALIGN_NORMAL, 1f, 0f, false
//        )
//        staticLayout.draw(canvas)
        canvas.drawBitmap(bitmap, width - image_width, image_padding, paint)
        paint.getFontMetrics(fontMetrics)

        val measureArray = floatArrayOf(0f)
        var start = 0
        var count: Int
        var verticalOffset = -fontMetrics.top
        var maxWidth: Float
        while (start < content.length) {
            maxWidth = if (verticalOffset + fontMetrics.bottom < image_padding ||
                verticalOffset + fontMetrics.top > image_padding + image_width
            ) {
                width.toFloat()
            } else {
                width.toFloat() - image_width
            }
            count = paint.breakText(
                content,
                start,
                content.length,
                true,
                maxWidth,
                measureArray
            )
            canvas.drawText(content, start, start + count, 0f, verticalOffset, paint)
            start += count
            verticalOffset += paint.fontSpacing
        }
    }

    /**
     * 获取指定宽度的头像
     */
    private fun getAvatar(width: Int): Bitmap {
        val option = BitmapFactory.Options()
        option.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, option)
        option.inJustDecodeBounds = false
        option.inDensity = option.outWidth
        option.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.drawable.avatar_rengwuxian, option)
    }
}