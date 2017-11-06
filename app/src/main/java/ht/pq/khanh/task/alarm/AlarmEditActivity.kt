package ht.pq.khanh.task.alarm

import android.app.Activity
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.TextView
import android.widget.TimePicker
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import ht.pq.khanh.extension.insertAlarm
import ht.pq.khanh.extension.setUpTheme
import ht.pq.khanh.model.Alarm
import ht.pq.khanh.multitask.R
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_alarm_edit.*
import java.util.*

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
    lateinit var tvRingtone: TextView
    @BindView(R.id.cbVib)
    lateinit var cbVibrate: CheckBox
    private val realm by lazy { Realm.getDefaultInstance() }
    private var ringToneUri: String? = null
    private val RQS_RINGTONEPICKER = 111
    private var idAlarm : Long = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setUpTheme()
        setContentView(R.layout.activity_alarm_edit)
        ButterKnife.bind(this)
        Realm.init(applicationContext)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return true
    }

    @OnClick(R.id.fab)
    fun saveAlarm() {
        val time = Calendar.getInstance()
        val hour = timeAlarm.currentHour
        val minute = timeAlarm.currentMinute
        time.set(Calendar.HOUR_OF_DAY, hour)
        time.set(Calendar.MINUTE, minute)
//        alarmDay.put(2, cbMon.isChecked)
//        alarmDay.put(3, cbTue.isChecked)
//        alarmDay.put(4, cbWed.isChecked)
//        alarmDay.put(5, cbThur.isChecked)
//        alarmDay.put(6, cbFri.isChecked)
//        alarmDay.put(7, cbSat.isChecked)
//        alarmDay.put(8, cbSun.isChecked)
        val isVibration = cbVibrate.isChecked
        val alarmId = System.currentTimeMillis()
        val alarm = Alarm(alarmId, time.timeInMillis, "alarm", true, isVibration, ringToneUri)
        realm.insertAlarm(alarm)
        intent.putExtra("Alarm_parcel", alarm)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    @OnClick(R.id.tvRington)
    fun chooseRingtone() {
        val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER)
        startActivityForResult(intent, RQS_RINGTONEPICKER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RQS_RINGTONEPICKER && resultCode == android.app.Activity.RESULT_OK) {
            val uri = data?.getParcelableExtra<Uri>(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
            val ringTone = RingtoneManager.getRingtone(applicationContext, uri)
            ringToneUri = uri?.toString()
            tvRingtone.text = ringTone.getTitle(this@AlarmEditActivity)
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
}
