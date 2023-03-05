package com.zz.customviewdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zz.customviewdemo.animage.MaterialEditText

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab)

//        val animateView = findViewById<MaterialEditText>(R.id.animate_view)
//        animateView.useLabel = false

//        val circleObjectAnimator = ObjectAnimator.ofFloat(animateView, "radius",50.dp, 150.dp)
//        circleObjectAnimator.duration = 1000
//        circleObjectAnimator.start()

//        val circleObjectAnimator = ObjectAnimator.ofObject(
//            animateView, "point", PointEvaluator(), PointF(200.dp, 100.dp)
//        )
//        circleObjectAnimator.duration = 1000
//        circleObjectAnimator.start()

//        val bottomAnimator = ObjectAnimator.ofFloat(animateView, "bottomRotate", 60f)
//        bottomAnimator.startDelay = 1000
//        bottomAnimator.duration = 1000
//
//        val cameraAnimator = ObjectAnimator.ofFloat(animateView, "rotate", 270f)
//        cameraAnimator.startDelay = 200
//        cameraAnimator.duration = 1000
//
//        val topAnimator = ObjectAnimator.ofFloat(animateView, "topRotate", -60f)
//        topAnimator.startDelay = 200
//        topAnimator.duration = 1000
//
//        val animatorSet = AnimatorSet()
//        animatorSet.playTogether(bottomAnimator, cameraAnimator, topAnimator)
//        animatorSet.start()

//        val topHolder = PropertyValuesHolder.ofFloat("topRotate", -60f)
//        val bottomHolder = PropertyValuesHolder.ofFloat("bottomRotate", 60f)
//        val rotateHolder = PropertyValuesHolder.ofFloat("rotate", 270f)
//        val holder = ObjectAnimator.ofPropertyValuesHolder(animateView, topHolder, bottomHolder, rotateHolder)
//        holder.startDelay = 1000
//        holder.duration = 2000
//        holder.start()

//        val province = ObjectAnimator.ofObject(animateView, "province", ProvinceEvaluator(), "澳门特别行政区")
//        province.duration = 2000
//        province.start()
    }

//    class PointEvaluator : TypeEvaluator<PointF> {
//        override fun evaluate(fraction: Float, startValue: PointF, endValue: PointF): PointF {
//            val x = (endValue.x - startValue.x) * fraction
//            val y = (endValue.y - startValue.y) * fraction
//            return PointF(x, y)
//        }
//    }
}