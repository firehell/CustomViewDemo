package com.zz.customviewdemo.draw

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Camera
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.zz.customviewdemo.R
import com.zz.customviewdemo.dp

class CameraView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val IMAGE_WIDTH = 150.dp
    private val IMAGE_PADDING = 100.dp
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatar(IMAGE_WIDTH.toInt())
    private val camera = Camera().apply {
        rotateX(30f)
        setLocation(0f, 0f, -6 * resources.displayMetrics.density)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //上半部分
        canvas.save()
        canvas.translate(IMAGE_PADDING + IMAGE_WIDTH / 2, IMAGE_PADDING + IMAGE_WIDTH / 2)
        canvas.rotate(-30f)
        canvas.clipRect(
            -IMAGE_WIDTH,
            -IMAGE_WIDTH,
            IMAGE_WIDTH,
            0f
        )
        canvas.rotate(30f)
        canvas.translate(-(IMAGE_PADDING + IMAGE_WIDTH / 2), -(IMAGE_PADDING + IMAGE_WIDTH / 2))
        canvas.drawBitmap(bitmap, IMAGE_PADDING, IMAGE_PADDING, paint)
        canvas.restore()

        //下半部分
        canvas.translate(IMAGE_PADDING + IMAGE_WIDTH / 2, IMAGE_PADDING + IMAGE_WIDTH / 2)
        canvas.rotate(-30f)
        camera.applyToCanvas(canvas)
        canvas.clipRect(
            -IMAGE_WIDTH,
            0f,
            IMAGE_WIDTH,
            IMAGE_WIDTH
        )
        canvas.rotate(30f)
        canvas.translate(-(IMAGE_PADDING + IMAGE_WIDTH / 2), -(IMAGE_PADDING + IMAGE_WIDTH / 2))
        canvas.drawBitmap(bitmap, IMAGE_PADDING, IMAGE_PADDING, paint)
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