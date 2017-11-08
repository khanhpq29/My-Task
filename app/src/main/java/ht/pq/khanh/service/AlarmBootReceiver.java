package ht.pq.khanh.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by khanhpq on 11/8/17.
 */

class AlarmBootReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            //only enabling one type of notifications for demo purposes
            NotificationHelper.scheduleRepeatingElapsedNotification(context);
        }
    }
}
