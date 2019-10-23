package com.efebudak.hopper

import android.os.Handler
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import java.lang.ref.WeakReference

class Hopper private constructor(
    view: View,
    private val times: Int
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
                val animation = weakView.get()?.animate()
                    ?.setInterpolator(DecelerateInterpolator())
                    ?.translationYBy(-100f)
                    ?.setDuration(300)

                animation?.withEndAction {
                    weakView.get()?.animate()
                        ?.setInterpolator(AccelerateInterpolator())
                        ?.translationYBy(100f)?.duration = 300
                }
                if (times == 0 || currentHopIteration < times) {
                    animationHandler.postDelayed(this, 2000)
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

    class HopperBuilder(private val view: View) {
        private var times: Int = 0

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

        fun build() = Hopper(view, times)
    }

}