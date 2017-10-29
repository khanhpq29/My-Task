package ht.pq.khanh.service

import android.app.IntentService
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import ht.pq.khanh.multitask.R
import ht.pq.khanh.task.reminder.ReminderActivity
import java.util.*

/**
 * Created by khanh on 15/10/2017.
 */
class ReminderNotificationService(name: String) : IntentService(name) {
    private val TODOTEXT = "notification.content"
    private val TODOUUID = "notification.uuid"
    private var mTodoText: String? = null
    private var mTodoUUID: UUID? = null
    private val mContext: Context? = null

    override fun onHandleIntent(intent: Intent) {
        mTodoText = intent.getStringExtra(TODOTEXT)
        mTodoUUID = intent.getSerializableExtra(TODOUUID) as UUID

        Log.d("OskarSchindler", "onHandleIntent called")
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val i = Intent(this, ReminderActivity::class.java)
        i.putExtra(TODOUUID, mTodoUUID)
//        val deleteIntent = Intent(this, DeleteNotificationService::class.java)
//        deleteIntent.putExtra(TODOUUID, mTodoUUID)
        val notification = Notification.Builder(this)
                .setContentTitle(mTodoText)
                .setSmallIcon(R.drawable.ic_access_alarms_black)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND)
//                .setDeleteIntent(PendingIntent.getService(this, mTodoUUID!!.hashCode(), deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT))
                .setContentIntent(PendingIntent.getActivity(this, mTodoUUID!!.hashCode(), i, PendingIntent.FLAG_UPDATE_CURRENT))
                .build()

        manager.notify(100, notification)
    }


}