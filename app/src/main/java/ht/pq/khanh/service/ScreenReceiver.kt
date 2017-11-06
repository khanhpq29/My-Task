package ht.pq.khanh.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by khanhpq on 11/6/17.
 */
class ScreenReceiver : BroadcastReceiver(){
    private var screenOn = true
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_SCREEN_OFF){
            screenOn = false
        }else if (intent.action == Intent.ACTION_SCREEN_ON){
            screenOn = true
        }
    }
}