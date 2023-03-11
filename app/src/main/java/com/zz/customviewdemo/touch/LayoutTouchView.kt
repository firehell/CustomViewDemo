package com.zz.customviewdemo.touch

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.OverScroller
import androidx.core.view.ViewConfigurationCompat
import androidx.core.view.children
import kotlin.math.abs


class LayoutTouchView(context: Context, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    private val overScroller = OverScroller(context)
    private val velocityTracker = VelocityTracker.obtain()
    private val viewConfiguration = ViewConfiguration.get(context)

    private var downX = 0f
    private var downY = 0f
    private var downScrollX = 0f
    private var isScrolling = false
    private val minVelocityTracker = viewConfiguration.scaledMinimumFlingVelocity
    private val maxVelocityTracker = viewConfiguration.scaledMaximumFlingVelocity
    private val pageSlop = viewConfiguration.scaledPagingTouchSlop

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        measureChildren(widthMeasureSpec, heightMeasureSpec)
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var childLeft = 0
        val childTop = 0
        var childRight = width
        val childBottom = height
        for (child in children) {
            child.layout(childLeft, childTop, childRight, childBottom)
            childLeft += width
            childRight += width
        }
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(event)
        var result = false
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                isScrolling = false
                downX = event.x
                downY = event.y
                downScrollX = scrollX.toFloat()
            }
            MotionEvent.ACTION_MOVE -> {
                if (!isScrolling) {
                    val dx = downX - event.x
                    if (abs(dx) > pageSlop) {
                        isScrolling = true
                        result = true
                        parent.requestDisallowInterceptTouchEvent(true)
                    }
                }
            }
        }
        return result
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_DOWN) {
            velocityTracker.clear()
        }
        velocityTracker.addMovement(event)
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                isScrolling = false
                downX = event.x
                downY = event.y
                downScrollX = scrollX.toFloat()
            }
            MotionEvent.ACTION_MOVE -> {
                val dx =
                    (downX - event.x + downScrollX).toInt().coerceAtLeast(0).coerceAtMost(width)
                scrollTo(dx, 0)
            }
            MotionEvent.ACTION_UP -> {
                velocityTracker.computeCurrentVelocity(1000, maxVelocityTracker.toFloat())
                val vx = velocityTracker.xVelocity
                val scrollX = scrollX
                val targetPage = if (abs(vx) < minVelocityTracker) {
                    if (scrollX > width / 2) {
                        1
                    } else {
                        0
                    }
                } else {
                    if (vx < 0) 1 else 0
                }
                val scrollDistance = if (targetPage == 1) {
                    width - scrollX
                } else {
                    -scrollX
                }
                overScroller.startScroll(getScrollX(), 0, scrollDistance, 0)
                postInvalidateOnAnimation()
            }
        }
        return true
    }

    override fun computeScroll() {
        super.computeScroll()
        if (overScroller.computeScrollOffset()) {
            scrollTo(overScroller.currX, overScroller.currY)
            postInvalidateOnAnimation()
        }
    }
}