package com.zz.customviewdemo.touch

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.zz.customviewdemo.dp
import com.zz.customviewdemo.getAvatar

class MultiTouchView1(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bitmap = getAvatar(resources, 200.dp.toInt())

    private var downX = 0f
    private var downY = 0f
    private var offsetX = 0f
    private var offsetY = 0f
    private var originalX = 0f
    private var originalY = 0f
    private var trackPointerId = 0

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(bitmap, offsetX, offsetY, paint)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                trackPointerId = event.getPointerId(0)
                downX = event.x
                downY = event.y
                originalX = offsetX
                originalY = offsetY
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                val actionIndex = event.actionIndex
                trackPointerId = event.getPointerId(actionIndex)
                downX = event.getX(event.actionIndex)
                downY = event.getY(event.actionIndex)
                originalX = offsetX
                originalY = offsetY
            }
            MotionEvent.ACTION_POINTER_UP -> {
                val actionIndex = event.actionIndex
                val pointerId = event.getPointerId(actionIndex)
                if (pointerId == trackPointerId) {
                    val newIndex = if (actionIndex == event.pointerCount - 1) {
                        event.pointerCount - 2
                    } else {
                        event.pointerCount - 1
                    }
                    trackPointerId = event.getPointerId(newIndex)
                    downX = event.getX(newIndex)
                    downY = event.getY(newIndex)
                    originalX = offsetX
                    originalY = offsetY
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val index = event.findPointerIndex(trackPointerId)
                offsetX = event.getX(index) - downX + originalX
                offsetY = event.getY(index) - downY + originalY
                invalidate()
            }
        }
        return true
    }
}