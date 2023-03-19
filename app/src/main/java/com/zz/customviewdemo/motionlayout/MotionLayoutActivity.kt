package com.zz.customviewdemo.motionlayout

import android.os.Bundle
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.zz.customviewdemo.R


class MotionLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)
//        findViewById<RatingBar>(R.id.rating_film_rating).rating = 4.5f
//        findViewById<TextView>(R.id.text_film_title).text = getString(R.string.film_title)
//        findViewById<TextView>(R.id.text_film_description).text =
//            getString(R.string.film_description)
    }

//    private var toggle = true

//    override fun onClick(v: View) {
//        val root = findViewById<ViewGroup>(R.id.root)
//        val startScene = Scene.getSceneForLayout(root, R.layout.go_start, this)
//        val endScene = Scene.getSceneForLayout(root, R.layout.go_end, this)
//        if (toggle) {
//            toggle = !toggle
//            TransitionManager.go(endScene)
//        }else{
//            toggle = true
//            TransitionManager.go(startScene)
//        }
//        bindData()
////        TransitionManager.beginDelayedTransition(v.parent as ViewGroup)
////        with(v.layoutParams as LinearLayout.LayoutParams) {
////            height *= 2
////            width *= 2
////        }
////        v.requestLayout()
//    }
//
//    private fun bindData() {
//        findViewById<ImageView>(R.id.image_film_cover).setOnClickListener(this)
//        findViewById<RatingBar>(R.id.rating_film_rating).rating = 4.5f
//        findViewById<TextView>(R.id.text_film_title).text = getString(R.string.film_title)
//        findViewById<TextView>(R.id.text_film_description).text = getString(R.string.film_description)
//    }
}