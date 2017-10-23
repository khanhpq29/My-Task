package ht.pq.khanh.task.reminder

import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
