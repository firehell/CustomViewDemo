package com.zz.customviewdemo.draw

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.zz.customviewdemo.R
import com.zz.customviewdemo.dp


class AvatarView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val IMAGE_WIDTH = 200f.dp
    private val IMAGE_PADDING = 20f.dp
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

    private val offset = 10f.dp
    private val bounds = RectF(
        IMAGE_PADDING,
        IMAGE_PADDING,
        IMAGE_PADDING + IMAGE_WIDTH,
        IMAGE_PADDING + IMAGE_WIDTH
    )

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawOval(
            IMAGE_PADDING - offset,
            IMAGE_PADDING - offset,
            IMAGE_PADDING + IMAGE_WIDTH + offset,
            IMAGE_PADDING + IMAGE_WIDTH + offset,
            paint
        )
        val count = canvas.saveLayer(bounds, null)
        canvas.drawOval(
            IMAGE_PADDING,
            IMAGE_PADDING,
            IMAGE_PADDING + IMAGE_WIDTH,
            IMAGE_PADDING + IMAGE_WIDTH,
            paint
        )
        paint.xfermode = xfermode
        canvas.drawBitmap(getAvatar(IMAGE_WIDTH.toInt()), IMAGE_PADDING, IMAGE_PADDING, paint)
        paint.xfermode = null
        canvas.restoreToCount(count)
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