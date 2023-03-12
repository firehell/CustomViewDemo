package com.zz.customviewdemo.touch

import android.content.ClipData
import android.content.Context
import android.util.AttributeSet
import android.view.DragEvent
import android.view.View
import android.view.View.OnLongClickListener
import android.widget.LinearLayout
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.children

class DragToCollectLayout(context: Context, attrs: AttributeSet?) :
    ConstraintLayout(context, attrs) {

    private var dragStarter = OnLongClickListener { v ->
        val imageData = ClipData.newPlainText("name", v.contentDescription)
        ViewCompat.startDragAndDrop(v, imageData, DragShadowBuilder(v), null, 0)
    }
    private val dragListener = HenDragListener()

    override fun onFinishInflate() {
        super.onFinishInflate()
        for (child in children) {
            if (child is LinearLayout) {
                child.setOnDragListener(dragListener)
            } else{
                child.setOnLongClickListener(dragStarter)
            }
        }
    }

    private inner class HenDragListener : OnDragListener {
        override fun onDrag(v: View, event: DragEvent): Boolean {
            when (event.action) {
                DragEvent.ACTION_DROP -> {
                    if (v is LinearLayout) {
                        Toast.makeText(
                            v.context, event.clipData.getItemAt(0).text, Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
            return true
        }
    }
}