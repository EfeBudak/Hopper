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
            .times(20)
            .hopDirection(HopDirection.LEFT_TO_RIGHT)
            .distance(50f)
            .duration(2000)
            .addHopperFinishedListener {
                Log.d("HopperBuilder", "Animation is finished")
            }
            .build()

        hopper.start()

        //hopper.end()

        Hopper.HopperBuilder(buttonLogin)
            .hopDirection(HopDirection.TOP_TO_BOTTOM)
            .distanceInDp(16f)
            .duration(750)
            .times(5)
            .addHopperFinishedListener {
                //Do what you want to do
            }
            .build()
            .start()

    }
}
