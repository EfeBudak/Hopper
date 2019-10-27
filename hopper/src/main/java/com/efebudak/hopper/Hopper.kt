package com.efebudak.hopper

import android.os.Handler
import android.view.View
import android.view.ViewPropertyAnimator
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import java.lang.ref.WeakReference

class Hopper private constructor(
    view: View,
    private val times: Int,
    private val hopDirection: HopDirection
) {

    private val weakView: WeakReference<View> = WeakReference(view)
    private val animationHandler = Handler()
    private var hopperStarted = false
    private var currentHopIteration = 0

    fun start() {

        if (hopperStarted) return
        hopperStarted = true
        animationHandler.post(object : Runnable {
            override fun run() {

                currentHopIteration++
                val animation = hopAnimationWithDirection()

                animation?.withEndAction {
                    returnAnimationWithDirection()
                }
                if (times == 0 || currentHopIteration < times) {
                    animationHandler.postDelayed(this, 1000)
                } else {
                    hopperStarted = false
                }
            }
        })
    }

    fun end() {
        animationHandler.removeCallbacksAndMessages(null)
        hopperStarted = false
    }

    private fun hopAnimationWithDirection(): ViewPropertyAnimator? {

        val durationInMs = 300L
        return when (hopDirection) {

            HopDirection.BOTTOM_TO_TOP -> {
                weakView.get()?.animate()
                    ?.setInterpolator(DecelerateInterpolator())
                    ?.translationYBy(-100f)
                    ?.setDuration(durationInMs)
            }
            HopDirection.TOP_TO_BOTTOM -> {
                weakView.get()?.animate()
                    ?.setInterpolator(DecelerateInterpolator())
                    ?.translationYBy(100f)
                    ?.setDuration(durationInMs)
            }
            HopDirection.LEFT_TO_RIGHT -> {
                weakView.get()?.animate()
                    ?.setInterpolator(DecelerateInterpolator())
                    ?.translationXBy(100f)
                    ?.setDuration(durationInMs)
            }

            HopDirection.RIGHT_TO_LEFT -> {
                weakView.get()?.animate()
                    ?.setInterpolator(DecelerateInterpolator())
                    ?.translationXBy(-100f)
                    ?.setDuration(durationInMs)
            }

        }

    }

    private fun returnAnimationWithDirection(): ViewPropertyAnimator? {

        val durationInMs = 300L

        return when (hopDirection) {

            HopDirection.BOTTOM_TO_TOP -> {
                weakView.get()?.animate()
                    ?.setInterpolator(AccelerateInterpolator())
                    ?.translationYBy(100f)
                    ?.setDuration(durationInMs)
            }
            HopDirection.TOP_TO_BOTTOM -> {
                weakView.get()?.animate()
                    ?.setInterpolator(AccelerateInterpolator())
                    ?.translationYBy(-100f)
                    ?.setDuration(durationInMs)
            }
            HopDirection.LEFT_TO_RIGHT -> {
                weakView.get()?.animate()
                    ?.setInterpolator(AccelerateInterpolator())
                    ?.translationXBy(-100f)
                    ?.setDuration(durationInMs)
            }

            HopDirection.RIGHT_TO_LEFT -> {
                weakView.get()?.animate()
                    ?.setInterpolator(AccelerateInterpolator())
                    ?.translationXBy(100f)
                    ?.setDuration(durationInMs)
            }

        }
    }

    class HopperBuilder(private val view: View) {
        private var times: Int = 0
        private var hopDirection: HopDirection = HopDirection.BOTTOM_TO_TOP

        /**
         * Set how many times the view is going to hop
         *
         * Default is 0 which will hop the view endlessly
         *
         * Value cannot be negative
         */
        fun times(value: Int): HopperBuilder {
            if (value < 0) throw Exception("Hooper: Times value cannot be negative!")
            times = value
            return this
        }

        /**
         * Set the animation direction by using com.efebudak.hopper.HopDirection
         */
        fun hopDirection(direction: HopDirection): HopperBuilder {
            hopDirection = direction
            return this
        }

        fun build() = Hopper(view, times, hopDirection)
    }

}