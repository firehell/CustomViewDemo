package com.zz.customviewdemo.layout

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children
import kotlin.math.max

class TabLayoutView(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    private val childrenBounds = mutableListOf<Rect>()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var widthUsed = 0
        var heightUsed = 0
        var linWidthUsed = 0
        var lineMaxHeight = 0

        val specWidthSize = MeasureSpec.getSize(widthMeasureSpec)
        val specWidthMode = MeasureSpec.getMode(widthMeasureSpec)
        children.forEachIndexed { index, view ->
            measureChildWithMargins(
                view, widthMeasureSpec, 0, heightMeasureSpec, heightUsed
            )
            if (specWidthMode != MeasureSpec.UNSPECIFIED && linWidthUsed + view.measuredWidth > specWidthSize) {
                linWidthUsed = 0
                heightUsed += lineMaxHeight
                lineMaxHeight = 0
                measureChildWithMargins(view, widthMeasureSpec, 0, heightMeasureSpec, heightUsed)
            }

            if (index >= childrenBounds.size) {
                childrenBounds.add(Rect())
            }

            val childBounds = childrenBounds[index]
            childBounds.set(
                linWidthUsed,
                heightUsed,
                linWidthUsed + view.measuredWidth,
                heightUsed + view.measuredHeight
            )

            linWidthUsed += view.measuredWidth
            widthUsed = max(linWidthUsed, widthUsed)
            lineMaxHeight = max(lineMaxHeight, view.measuredHeight)

        }
        var selfWidth = widthUsed
        var selfHeight = heightUsed + lineMaxHeight
        setMeasuredDimension(selfWidth, selfHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        children.forEachIndexed { index, view ->
            val childBounds = childrenBounds[index]
            view.layout(childBounds.left, childBounds.top, childBounds.right, childBounds.bottom)
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }
}