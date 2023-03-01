package com.zz.customviewdemo.animage

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.zz.customviewdemo.R
import com.zz.customviewdemo.dp

class MaterialEditText(context: Context, attrs: AttributeSet?) : AppCompatEditText(context, attrs) {

    private val TEXTSIZE = 12.dp
    private val TEXTMARGIN = 8.dp
    private val HOR_OFFSET = 5.dp
    private val VERTICAL_OFFSET = 23.dp
    private val EXTRAL_VERTICAL_OFFSET = 16.dp

    private var labelShowing = false

    var useLabel = false
        set(value) {
            if (field != value) {
                field = value
                if (field) {
                    setPadding(
                        paddingLeft,
                        (paddingTop + textSize + TEXTMARGIN).toInt(),
                        paddingRight,
                        paddingBottom
                    )
                } else {
                    setPadding(
                        paddingLeft,
                        (paddingTop - textSize - TEXTMARGIN).toInt(),
                        paddingRight,
                        paddingBottom
                    )
                }
            }
        }

    private val animator by lazy {
        ObjectAnimator.ofFloat(this, "labelFraction", 0f, 1f)
    }

    var labelFraction = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = TEXTSIZE
        setTextColor(Color.BLUE)
    }

    init {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialEditText)
        useLabel = typeArray.getBoolean(R.styleable.MaterialEditText_useLabel, true)
        typeArray.recycle()
    }

    override fun onTextChanged(
        text: CharSequence?, start: Int, lengthBefore: Int, lengthAfter: Int
    ) {
        if (labelShowing && text.isNullOrEmpty()) {
            labelShowing = false;
            animator.reverse()
        } else if (!labelShowing && !text.isNullOrEmpty()) {
            labelShowing = true
            animator.start()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.alpha = (labelFraction * 0xff).toInt()
        val currentVer = VERTICAL_OFFSET + EXTRAL_VERTICAL_OFFSET * (1 - labelFraction)
        canvas.drawText(hint.toString(), HOR_OFFSET, currentVer, paint)
    }
}