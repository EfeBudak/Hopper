package com.efebudak.hopanimationsample

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.efebudak.hopper.HopDirection
import com.efebudak.hopper.Hopper
import com.efebudak.hopper.hopToDirection
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    val hopper = Hopper.HopperBuilder(textView)
      .hopDirection(HopDirection.LEFT_TO_RIGHT)
      .build()
    hopper.start()
    //hopper.end()

    Hopper.HopperBuilder(buttonLogin)
      .distanceInDp(16f)
      .duration(750)
      .times(25)
      .addHopperFinishedListener {
        //Do what you want to do
      }
      .build()
      .start()

    buttonCancelValueAnimator.hopToDirection(HopDirection.RIGHT_TO_LEFT)

    val animator = ValueAnimator.ofInt(0, 10)

    animator.duration = 10000
    animator.addUpdateListener { valueAnimator ->

      textViewValueAnimator.text = valueAnimator.animatedValue.toString()
    }

    buttonStartValueAnimation.setOnClickListener {
      animator.start()
    }

    buttonEndValueAnimator.setOnClickListener {
      animator.end()
    }

    buttonCancelValueAnimator.setOnClickListener {
      animator.cancel()
    }

  }
}
