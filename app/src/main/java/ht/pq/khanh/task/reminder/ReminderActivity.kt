package ht.pq.khanh.task.reminder

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import ht.pq.khanh.extension.setUpTheme
import ht.pq.khanh.multitask.R
import ht.pq.khanh.util.Common

class ReminderActivity : AppCompatActivity() {
    @BindView(R.id.txtContent)
    lateinit var tvContent : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setUpTheme()
        setContentView(R.layout.activity_reminder)
        ButterKnife.bind(this)
        val title = intent.getStringExtra(Common.TODOTEXT)
        tvContent.text = title
    }

    @OnClick(R.id.btnRemove)
    fun removeReminder(){
        moveToReminderList()
    }

    @OnClick(R.id.btnSnooze)
    fun snoozeMore(){

    }

    private fun moveToReminderList(){

    }
}
