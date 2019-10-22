package com.efebudak.hopper

import android.os.Handler
import android.view.View
import java.lang.ref.WeakReference

class Hopper private constructor(
    view: View,
    private val infinite: Boolean
) {

    private val weakView: WeakReference<View> = WeakReference(view)
    private val animationHandler = Handler()

    fun start() {

        animationHandler.post(object : Runnable {
            override fun run() {

                val animation = weakView.get()?.animate()
                    ?.translationYBy(-40f)
                    ?.setDuration(200)

                animation?.withEndAction {
                    weakView.get()?.animate()?.translationYBy(40f)?.duration = 200
                }
                animationHandler.postDelayed(this, 1000)
            }
        })
    }


    class HopperBuilder(private val view: View) {
        var infinite: Boolean = true

        fun infinite(value:Boolean):HopperBuilder{
            infinite = value
            return this
        }

        fun build() = Hopper(view, infinite)
    }

}