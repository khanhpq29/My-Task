package ht.pq.khanh.util

import android.os.CountDownTimer

/**
 * Created by khanhpq on 11/6/17.
 */
class Countdown(val time: Long, val interval : Long, val onTick : (String) -> Boolean ) : CountDownTimer(time, interval){
    override fun onFinish() {
        onTick
    }

    override fun onTick(millisUntilFinished: Long) {
    }
}