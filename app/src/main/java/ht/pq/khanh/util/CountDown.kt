package ht.pq.khanh.util

import android.os.CountDownTimer
import java.util.*

/**
 * Created by khanhpq on 11/6/17.
 */
class CountDown(val time: Int, private val onTick: (String) -> Boolean, private val onFinish: () -> Boolean) :
        CountDownTimer(time.times(60).times(1000).toLong(), 1000) {
    override fun onFinish() {
        onFinish
    }

    override fun onTick(millisUntilFinished: Long) {
        onTick(String.format(Locale.JAPAN,
                "%1\$d:%2$02d",
                millisUntilFinished / 1000 / 60,
                millisUntilFinished / 1000 % 60))
    }
}