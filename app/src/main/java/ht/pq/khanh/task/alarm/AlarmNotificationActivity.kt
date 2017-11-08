package ht.pq.khanh.task.alarm

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import butterknife.OnClick
import ht.pq.khanh.multitask.R

class AlarmNotificationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_notification)
        ButterKnife.bind(this)
    }

    @OnClick(R.id.btn_alarm_close)
    fun closeAlarm() {
        finish()
    }

    @OnClick(R.id.btn_alarm_snooze)
    fun snoozeAlarm() {
        finish()
    }
}
