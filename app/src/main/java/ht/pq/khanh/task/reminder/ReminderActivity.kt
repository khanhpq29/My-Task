package ht.pq.khanh.task.reminder

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import butterknife.BindView
import butterknife.ButterKnife
import ht.pq.khanh.multitask.R

class ReminderActivity : AppCompatActivity() {
    @BindView(R.id.btnRemove)
    lateinit var btnRemove: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)
        ButterKnife.bind(this)
        btnRemove.setOnClickListener { moveToReminderList() }
    }

    private fun moveToReminderList(){

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return super.onOptionsItemSelected(item)
    }
}
