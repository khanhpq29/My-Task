package ht.pq.khanh.task.alarm

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.TimePicker
import butterknife.BindView
import butterknife.ButterKnife
import ht.pq.khanh.multitask.R

import kotlinx.android.synthetic.main.activity_alarm_edit.*

class AlarmEditActivity : AppCompatActivity() {
    @BindView(R.id.fab)
    lateinit var fab : FloatingActionButton
    @BindView(R.id.timeAlarm)
    lateinit var timeAlarm : TimePicker
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_edit)
        ButterKnife.bind(this)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

}
