package ht.pq.khanh.task.alarm

import android.app.TimePickerDialog
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
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
import ht.pq.khanh.extension.findAllAlarm
import ht.pq.khanh.extension.inflateLayout
import ht.pq.khanh.extension.insertAlarm
import ht.pq.khanh.model.Alarm
import ht.pq.khanh.multitask.R
import io.realm.Realm
import java.util.*

/**
 * Created by khanhpq on 9/25/17.
 */

class AlarmFragment : Fragment(), AlarmContract.View {
    @BindView(R.id.fab_set_alarm)
    lateinit var fabAlarm: FloatingActionButton
    @BindView(R.id.alarms)
    lateinit var recyclerAlarm: RecyclerView
    private lateinit var presenter: AlarmPresenter
    private lateinit var alarmAdapter: AlarmAdapter
    private var alarms: MutableList<Alarm> = arrayListOf()
    private val realm : Realm by lazy { Realm.getDefaultInstance() }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = container!!.inflateLayout(R.layout.fragment_alarm)
        ButterKnife.bind(this, view)
        Realm.init(context)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    @OnClick(R.id.fab_set_alarm)
    fun showTimeDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val timePicker = TimePickerDialog(activity, onTimeSet, hour, minute, DateFormat.is24HourFormat(activity))
        timePicker.show()
    }

    private val onTimeSet = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
        val alarm = Alarm(hourOfDay, minute, false, true)
        alarms.add(alarm)
        alarmAdapter.notifyDataSetChanged()
        Log.d("alarm size", "${alarms.size}")
        realm.insertAlarm(alarm)
    }

    override fun display() {

    }

    private fun initData() {
        presenter = AlarmPresenter()
        alarms = realm.copyFromRealm(realm.findAllAlarm())
        alarmAdapter = AlarmAdapter(alarms)
        val linearManager = LinearLayoutManager(activity)
        recyclerAlarm.layoutManager = linearManager
        recyclerAlarm.adapter = AlarmAdapter(alarms)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        realm.close()
    }

}
