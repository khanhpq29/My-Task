package ht.pq.khanh.notification

import android.app.IntentService
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import ht.pq.khanh.multitask.R
import ht.pq.khanh.task.reminder.ReminderActivity
import ht.pq.khanh.util.Common

/**
 * Created by khanh on 15/10/2017.
 */
class ReminderNotificationService : IntentService("reminder_notification") {

    override fun onHandleIntent(intent: Intent) {
        val mTodoText = intent.getStringExtra(Common.TODOTEXT)
        val mTodoUUID = intent.getLongExtra(Common.TODOUUID, 0)
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val i = Intent(this, ReminderActivity::class.java)
        i.putExtra(Common.TODOUUID, mTodoUUID)
        i.putExtra(Common.TODOTEXT, mTodoText)
//        val deleteIntent = Intent(this, DeleteNotificationService::class.java)
//        deleteIntent.putExtra(Common.TODOUUID, mTodoUUID)
        val notification = Notification.Builder(this)
                .setContentTitle(mTodoText)
                .setSmallIcon(R.mipmap.ic_launcher_round)
//                .setSmallIcon(R.drawable.ic_access_alarms_black)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND)
//                .setDeleteIntent(PendingIntent.getService(this, mTodoUUID!!.hashCode(), deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setContentIntent(PendingIntent.getActivity(this, mTodoUUID.hashCode(), i, PendingIntent.FLAG_UPDATE_CURRENT))
                .build()

        manager.notify(100, notification)
    }


}