package com.efebudak.hopanimationsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.efebudak.hopper.Hopper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val hopper = Hopper.HopperBuilder(textView)
            .infinite(true)
            .build()
            .start()

    }
}
