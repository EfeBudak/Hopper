package com.efebudak.hopanimationsample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.efebudak.hopper.HopDirection
import com.efebudak.hopper.Hopper
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val hopper = Hopper.HopperBuilder(textView)
            .times(8)
            .hopDirection(HopDirection.LEFT_TO_RIGHT)
            .distance(50f)
            .duration(2000)
            .addAnimationFinishedListener {
                Log.d("HopperBuilder", "Animation is finished")
            }
            .build()

        hopper.start()

        //hopper.end()

    }
}
