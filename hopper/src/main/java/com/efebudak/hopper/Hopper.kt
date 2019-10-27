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
    private val hopDirection: HopDirection,
    private val distance: Float,
    private val duration: Long,
    private val animationFinishedCallback: (() -> Unit)?
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
                    returnAnimationWithDirection()?.withEndAction {

                        if (!hopperStarted) {
                            animationFinishedCallback?.invoke()
                        }
                    }
                }
                if (times == 0 || currentHopIteration < times) {
                    animationHandler.postDelayed(this, duration)
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

        val durationInMs = getHalfAnimationDuration()
        return when (hopDirection) {

            HopDirection.BOTTOM_TO_TOP -> {
                weakView.get()?.animate()
                    ?.setInterpolator(DecelerateInterpolator())
                    ?.translationYBy(-1 * distance)
                    ?.setDuration(durationInMs)
            }
            HopDirection.TOP_TO_BOTTOM -> {
                weakView.get()?.animate()
                    ?.setInterpolator(DecelerateInterpolator())
                    ?.translationYBy(distance)
                    ?.setDuration(durationInMs)
            }
            HopDirection.LEFT_TO_RIGHT -> {
                weakView.get()?.animate()
                    ?.setInterpolator(DecelerateInterpolator())
                    ?.translationXBy(distance)
                    ?.setDuration(durationInMs)
            }
            HopDirection.RIGHT_TO_LEFT -> {
                weakView.get()?.animate()
                    ?.setInterpolator(DecelerateInterpolator())
                    ?.translationXBy(-1 * distance)
                    ?.setDuration(durationInMs)
            }
        }
    }

    private fun returnAnimationWithDirection(): ViewPropertyAnimator? {

        val durationInMs = getHalfAnimationDuration()

        return when (hopDirection) {

            HopDirection.BOTTOM_TO_TOP -> {
                weakView.get()?.animate()
                    ?.setInterpolator(AccelerateInterpolator())
                    ?.translationYBy(distance)
                    ?.setDuration(durationInMs)
            }
            HopDirection.TOP_TO_BOTTOM -> {
                weakView.get()?.animate()
                    ?.setInterpolator(AccelerateInterpolator())
                    ?.translationYBy(-1 * distance)
                    ?.setDuration(durationInMs)
            }
            HopDirection.LEFT_TO_RIGHT -> {
                weakView.get()?.animate()
                    ?.setInterpolator(AccelerateInterpolator())
                    ?.translationXBy(-1 * distance)
                    ?.setDuration(durationInMs)
            }

            HopDirection.RIGHT_TO_LEFT -> {
                weakView.get()?.animate()
                    ?.setInterpolator(AccelerateInterpolator())
                    ?.translationXBy(distance)
                    ?.setDuration(durationInMs)
            }

        }
    }

    private fun getHalfAnimationDuration() = duration / 2 - 100

    class HopperBuilder(private val view: View) {

        private var times: Int = 0
        private var hopDirection: HopDirection = HopDirection.BOTTOM_TO_TOP
        private var distance = 100f
        private var duration = 1000L
        private var animationFinishedCallback: (() -> Unit)? = null

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

        /**
         * The distance you want your view to hop
         * It is in pixel. If you want to use dp us #distanceInDp
         *
         * It CANNOT be less than 50f
         */
        fun distance(value: Float): HopperBuilder {
            if (value < 50) throw Exception("Hooper: Distance value cannot be less than 50f!")
            distance = value
            return this
        }

        /**
         * The distance you want your view to hop
         * It is in dp.
         *
         * It CANNOT be less than 4 dp
         */
        fun distanceInDp(dpValue: Float): HopperBuilder {
            if (dpValue < 4) throw Exception("Hooper: Distance value cannot be less than 4 dp!")
            val density = view.context.resources.displayMetrics.density
            distance = dpValue * density

            return this
        }

        /**
         * Total animation duration
         * It is in MS
         *
         * It CANNOT be less than 500
         */
        fun duration(value: Long): HopperBuilder {
            if (value < 500) throw Exception("Hooper: Duration value cannot be less than 500ms!")
            duration = value
            return this
        }

        /**
         * Called at the end of the last hop
         *
         * times value must be set something finite before setting this listener
         */
        fun addAnimationFinishedListener(animationFinishedListener: (() -> Unit)): HopperBuilder {
            if (times == 0)
                throw Exception(
                    "Hooper: Times value must be finite to add an animation finished listener!"
                )
            animationFinishedCallback = animationFinishedListener
            return this
        }

        fun build() =
            Hopper(view, times, hopDirection, distance, duration, animationFinishedCallback)
    }

}