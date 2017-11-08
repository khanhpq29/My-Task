package ht.pq.khanh.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import ht.pq.khanh.helper.NotificationHelper;

/**
 * Created by khanhpq on 11/8/17.
 */

public class AlarmBootReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
            //only enabling one type of notifications for demo purposes
            NotificationHelper.scheduleRepeatingElapsedNotification(context);
        }
    }
}
