package com.efebudak.hopanimationsample

import android.animation.ValueAnimator
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.efebudak.hopanimationsample.databinding.ActivityMainBinding
import com.efebudak.hopper.HopDirection
import com.efebudak.hopper.Hopper
import com.efebudak.hopper.hopToDirection

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val hopper = Hopper.HopperBuilder(binding.textView)
            .hopDirection(HopDirection.LEFT_TO_RIGHT)
            .build()
        hopper.start()
        //hopper.end()

        Hopper.HopperBuilder(binding.buttonLogin)
            .distanceInDp(16f)
            .duration(750)
            .times(25)
            .addHopperFinishedListener {
                //Do what you want to do
            }
            .build()
            .start()

        binding.buttonCancelValueAnimator.hopToDirection(HopDirection.RIGHT_TO_LEFT)

        val animator = ValueAnimator.ofInt(0, 10)

        animator.duration = 10000
        animator.addUpdateListener { valueAnimator ->

            binding.textViewValueAnimator.text = valueAnimator.animatedValue.toString()
        }

        binding.buttonStartValueAnimation.setOnClickListener {
            animator.start()
        }

        binding.buttonEndValueAnimator.setOnClickListener {
            animator.end()
        }

        binding.buttonCancelValueAnimator.setOnClickListener {
            animator.cancel()
        }
    }
}
