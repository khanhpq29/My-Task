package ht.pq.khanh.task.alarm

import android.app.Activity
import android.app.TimePickerDialog
import android.content.Intent
import android.media.RingtoneManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.format.DateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.pawegio.kandroid.IntentFor
import com.pawegio.kandroid.d
import ht.pq.khanh.extension.*
import ht.pq.khanh.model.Alarm
import ht.pq.khanh.multitask.R
import io.realm.Realm
import java.util.*
import android.widget.Toast
import ht.pq.khanh.multitask.MainActivity
import android.R.attr.data
import android.app.Activity.RESULT_OK
import android.media.Ringtone
import android.net.Uri


/**
 * Created by khanhpq on 9/25/17.
 */

class AlarmFragment : Fragment(), AlarmContract.View, AlarmCallback {
    @BindView(R.id.alarms)
    lateinit var recyclerAlarm: RecyclerView
    private lateinit var presenter: AlarmPresenter
    private lateinit var alarmAdapter: AlarmAdapter
    private var alarms: MutableList<Alarm> = arrayListOf()
    private val REQUEST_CODE = 116
    private var selectedPosition = 0
    private val realm: Realm by lazy { Realm.getDefaultInstance() }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = container!!.inflateLayout(R.layout.fragment_alarm)
        ButterKnife.bind(this, view)
        Realm.init(context)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        alarmAdapter?.setOnChangeDate(this)
        alarmAdapter?.handleListener(this)
    }

    @OnClick(R.id.fab_set_alarm)
    fun showTimeDialog() {
//        val calendar = Calendar.getInstance()
//        val hour = calendar.get(Calendar.HOUR_OF_DAY)
//        val minute = calendar.get(Calendar.MINUTE)
//        val timePicker = TimePickerDialog(activity, onTimeSet, hour, minute, DateFormat.is24HourFormat(activity))
//        timePicker.show()
        val intent = IntentFor<AlarmEditActivity>(activity)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        d("on activity result")
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            val alarm = data?.getParcelableExtra<Alarm>("Alarm_parcel")
            alarms.add(alarm!!)
            alarmAdapter?.notifyDataSetChanged()
        }
    }

    override fun onChangeTime(alarm: Alarm) {
        val timePicker = TimePickerDialog(activity, onTimeSet, 3, 5, DateFormat.is24HourFormat(activity))
        timePicker.show()
    }

    override fun onDeleteAlarm(position: Int) {
        d("ondelete")
        selectedPosition = position
        val alarm = alarms[position]
        alarms.removeAt(position)
        alarmAdapter?.notifyDataSetChanged()
        realm.deleteAlarm(selectedPosition)
        Snackbar.make(recyclerAlarm, "delete on item", Snackbar.LENGTH_LONG)
                .setAction("Undo", {
                    alarms.add(selectedPosition, alarm)
                    alarmAdapter?.notifyDataSetChanged()
                    realm.insertAlarm(alarm)
                }).show()
    }

    private val onTimeSet = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
        val time = Calendar.getInstance()
        time.set(Calendar.HOUR_OF_DAY, hourOfDay)
        time.set(Calendar.MINUTE, minute)

        val alarm = Alarm(time.timeInMillis, "alarm", false, true)
        alarms.add(alarm)
        alarmAdapter.addChange(alarms)
        Log.d("alarm size", "${alarms.size}")
        realm.insertAlarm(alarm)
    }

    override fun onChangeRington() {

    }

    override fun onChangeOnOff(isOff: Boolean) {
        activity.showToast("switch")

    }

    override fun onIsVibrate(isVibrate: Boolean) {
        activity.showToast("vibrate")
    }

    override fun display() {

    }

    private fun initData() {
        presenter = AlarmPresenter()
        alarms = realm.copyFromRealm(realm.findAllAlarm())
        val decorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        alarmAdapter = AlarmAdapter(context, alarms)
        recyclerAlarm.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = alarmAdapter
            addItemDecoration(decorator)
            setHasFixedSize(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("destroy alarm", "destroy")
        realm.close()
    }

    override fun onStop() {
        super.onStop()
        if (!realm.isClosed) {
            realm.close()
        }
    }
}
