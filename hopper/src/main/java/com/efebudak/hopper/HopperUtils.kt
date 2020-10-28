package com.efebudak.hopper

import android.view.View

fun View.hopToDirection(hopDirection: HopDirection) {
    Hopper.HopperBuilder(this)
        .hopDirection(hopDirection)
        .build()
        .start()
}