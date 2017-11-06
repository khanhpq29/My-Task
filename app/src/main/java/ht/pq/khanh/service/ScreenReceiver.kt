package ht.pq.khanh.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.pawegio.kandroid.d
import ht.pq.khanh.extension.showToast

/**
 * Created by khanhpq on 11/6/17.
 */
class ScreenReceiver : BroadcastReceiver(){
    var screenOn = true
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_SCREEN_OFF){
            screenOn = false
            d("screen off")
        }else if (intent.action == Intent.ACTION_SCREEN_ON){
            screenOn = true
            d("screen on")
        }
    }
}