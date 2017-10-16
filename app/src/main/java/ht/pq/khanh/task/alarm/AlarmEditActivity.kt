package ht.pq.khanh.task.alarm

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.NavUtils
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import ht.pq.khanh.extension.insertAlarm
import ht.pq.khanh.model.Alarm
import ht.pq.khanh.multitask.R
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_alarm_edit.*
import java.util.*
import android.content.Intent
import android.widget.*


class AlarmEditActivity : AppCompatActivity() {
    @BindView(R.id.timeAlarm)
    lateinit var timeAlarm: TimePicker
    @BindView(R.id.cbMon)
    lateinit var cbMon: CheckBox
    @BindView(R.id.cbTue)
    lateinit var cbTue: CheckBox
    @BindView(R.id.cbWed)
    lateinit var cbWed: CheckBox
    @BindView(R.id.cbThur)
    lateinit var cbThur: CheckBox
    @BindView(R.id.cbFri)
    lateinit var cbFri: CheckBox
    @BindView(R.id.cbSat)
    lateinit var cbSat: CheckBox
    @BindView(R.id.cbSun)
    lateinit var cbSun: CheckBox
    @BindView(R.id.tvRington)
    lateinit var tvRingtone : TextView
    private val realm by lazy { Realm.getDefaultInstance() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm_edit)
        ButterKnife.bind(this)
        Realm.init(applicationContext)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this)
        }
        return super.onOptionsItemSelected(item)
    }

    @OnClick(R.id.fab)
    fun saveAlarm() {
        val time = Calendar.getInstance()
        val hour = timeAlarm.currentHour
        val minute = timeAlarm.currentMinute
        time.set(Calendar.HOUR_OF_DAY, hour)
        time.set(Calendar.MINUTE, minute)
        val alarm = Alarm(time.timeInMillis, "alarm", false, true)
        realm.insertAlarm(alarm)
        intent.putExtra("Alarm_parcel", alarm)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    @OnClick(R.id.tvRington)
    fun chooseRingtone() {
//        val audioIntent = Intent(Intent.ACTION_VIEW)
//        audioIntent.type = "audio/*"
//        startActivityForResult(audioIntent, 111)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 111 && resultCode == Activity.RESULT_OK){
            val path = data?.data
            tvRingtone.text = path?.host
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
