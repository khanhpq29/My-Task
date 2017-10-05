package ht.pq.khanh.task.reminder

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import butterknife.ButterKnife
import ht.pq.khanh.multitask.R
import io.realm.Realm

class RemindDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
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
