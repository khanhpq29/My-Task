package ht.pq.khanh.notification

import android.app.Notification
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Created by khanhpq on 10/31/17.
 */
class NotificationPublisher : BroadcastReceiver() {
    public val NOTIFICATION_ID = "notification-id"
    public val NOTIFICATION = "notification"
    override fun onReceive(p0: Context, p1: Intent?) {
        val notificationService = p0.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification = p1?.getParcelableExtra<Notification>(NOTIFICATION)
        val notificationId = p1?.getIntExtra(NOTIFICATION_ID, 0)
        notificationService.notify(notificationId!!, notification)
    }
}