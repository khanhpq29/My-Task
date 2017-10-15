package ht.pq.khanh.task.reminder

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ht.pq.khanh.model.Reminder
import ht.pq.khanh.multitask.R

class ReminderEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder_edit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val reminderItem = intent.getParcelableExtra<Reminder>("reminder_data")
        val reminderDetailFragment = ReminderDetailFragment.newInstance(reminderItem)
        supportFragmentManager.beginTransaction().replace(R.id.containRemind, reminderDetailFragment).commit()

    }
}
