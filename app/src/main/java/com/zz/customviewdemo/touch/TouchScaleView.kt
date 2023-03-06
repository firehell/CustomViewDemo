package com.zz.customviewdemo.touch

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.GestureDetector.OnDoubleTapListener
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.widget.OverScroller
import androidx.core.animation.doOnEnd
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import com.zz.customviewdemo.dp
import com.zz.customviewdemo.getAvatar
import kotlin.math.max
import kotlin.math.min

private val BITMAP_WIDTH = 300.dp.toInt()
private const val EXTRA_SCALE_FACTOR = 1.5f

class TouchScaleView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatar(resources, BITMAP_WIDTH)
    private var originalOffsetX = 0f
    private var originalOffsetY = 0f
    private var offsetX = 0f
    private var offsetY = 0f
    private var big = false
    private var smallScale = 0f
    private var bigScale = 0f

    private val gestureListener = GestureListener()
    private val flingRun = FlingRunner()

    private val gestureDetector = GestureDetectorCompat(context, gestureListener)

    private val scroller = OverScroller(context)
    private var scaleFactor = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val animator: ObjectAnimator by lazy {
        ObjectAnimator.ofFloat(this, "scaleFactor", 0f, 1f)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        originalOffsetX = ((width - BITMAP_WIDTH) / 2).toFloat()
        originalOffsetY = ((height - BITMAP_WIDTH) / 2).toFloat()

        if (bitmap.width / bitmap.height.toFloat() > width / height.toFloat()) {
            smallScale = width / bitmap.width.toFloat()
            bigScale = height / bitmap.height.toFloat() * EXTRA_SCALE_FACTOR
        } else {
            smallScale = height / bitmap.height.toFloat()
            bigScale = width / bitmap.width.toFloat() * EXTRA_SCALE_FACTOR
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.translate(offsetX * scaleFactor, offsetY * scaleFactor)
        val scale = smallScale + (bigScale - smallScale) * scaleFactor
        canvas.scale(scale, scale, (width / 2).toFloat(), (height / 2).toFloat())
        canvas.drawBitmap(bitmap, originalOffsetX, originalOffsetY, paint)
    }

    private fun fixOffset() {
        offsetX = min(offsetX, (bitmap.width * bigScale - width) / 2)
        offsetX = max(offsetX, -(bitmap.width * bigScale - width) / 2)
        offsetY = min(offsetY, (bitmap.height * bigScale - height) / 2)
        offsetY = max(offsetY, -(bitmap.height * bigScale - height) / 2)
    }


    inner class GestureListener : SimpleOnGestureListener() {
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onScroll(
            e1: MotionEvent, e2: MotionEvent, distanceX: Float, distanceY: Float
        ): Boolean {
            if (big) {
                offsetX -= distanceX
                offsetY -= distanceY
                fixOffset()
                invalidate()
            }
            return false
        }

        override fun onFling(
            e1: MotionEvent, e2: MotionEvent, velocityX: Float, velocityY: Float
        ): Boolean {
            if (big) {
                scroller.fling(
                    offsetX.toInt(),
                    offsetY.toInt(),
                    velocityX.toInt(),
                    velocityY.toInt(),
                    (-(bitmap.width * bigScale - width) / 2).toInt(),
                    ((bitmap.width * bigScale - width) / 2).toInt(),
                    (-(bitmap.height * bigScale - height) / 2).toInt(),
                    ((bitmap.height * bigScale - height) / 2).toInt(),
                    40.dp.toInt(),
                    40.dp.toInt()
                )
                ViewCompat.postOnAnimation(this@TouchScaleView, flingRun)
            }
            return false
        }

        override fun onDoubleTap(e: MotionEvent): Boolean {
            if (!big) {
                offsetX = (e.x - width / 2) * (1 - bigScale / smallScale)
                offsetX = (e.y - height / 2) * (1 - bigScale / smallScale)
                fixOffset()
                animator.start()
            } else {
                animator.reverse()
            }
            big = !big
            invalidate()
            return true
        }
    }

    inner class FlingRunner : Runnable {
        override fun run() {
            if (scroller.computeScrollOffset()) {
                scroller.computeScrollOffset()
                offsetX = scroller.currX.toFloat()
                offsetY = scroller.currY.toFloat()
                invalidate()
                ViewCompat.postOnAnimation(this@TouchScaleView, this)
            }
        }
    }
}