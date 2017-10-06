package ht.pq.khanh.task.reminder

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ht.pq.khanh.multitask.R

class ReminderEditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder_edit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        if (intent.hasExtra("reminder_data")) {
            val bundle = Bundle()
            bundle.putParcelable("reminder_detail", intent.getParcelableExtra("reminder_data"))
            val reminderDetailFragment = ReminderDetailFragment()
            reminderDetailFragment.arguments = bundle
            supportFragmentManager.beginTransaction().replace(R.id.containRemind, reminderDetailFragment).commit()
        }
    }
}
