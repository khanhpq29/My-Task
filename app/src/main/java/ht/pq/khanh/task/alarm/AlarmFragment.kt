package ht.pq.khanh.task.alarm

import android.app.Activity
import android.app.TimePickerDialog
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
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
import ht.pq.khanh.TaskApplication
import ht.pq.khanh.extension.inflateLayout
import ht.pq.khanh.extension.showToast
import ht.pq.khanh.model.alarm.Alarm
import ht.pq.khanh.multitask.R
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

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
    private var ringToneUri: String? = null
    private val RQS_RINGTONEPICKER = 111
    private var selectedPosition = 0
    private val disposal : CompositeDisposable by lazy { CompositeDisposable() }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = container!!.inflateLayout(R.layout.fragment_alarm)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        alarmAdapter.setOnChangeDate(this)
        alarmAdapter.handleListener(this)
    }

    override fun onPause() {
        super.onPause()

    }

    override fun onResume() {
        super.onResume()
    }

    @OnClick(R.id.fab_set_alarm)
    fun showTimeDialog() {
        val intent = IntentFor<AlarmEditActivity>(activity)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        d("on activity result")
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            val alarm = data?.getParcelableExtra<Alarm>("Alarm_parcel")
            if (alarm != null) alarms.add(alarm)
            alarmAdapter.notifyDataSetChanged()
        } else if (requestCode == RQS_RINGTONEPICKER && resultCode == android.app.Activity.RESULT_OK) {
            val uri = data?.getParcelableExtra<Uri>(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
            ringToneUri = uri?.toString()
            val alarmItem = alarms[selectedPosition]
            alarmItem.ringtoneUri = ringToneUri
            disposal.add(updateAlarm(alarmItem))
            alarmAdapter.notifyItemChanged(selectedPosition)
        }
    }

    override fun onChangeTime(position: Int) {
        selectedPosition = position
        val alarm = alarms[position]
        val time = alarm.time
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = time
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val timePicker = TimePickerDialog(activity, onTimeSet, hour, minute, DateFormat.is24HourFormat(activity))
        timePicker.show()
    }

    override fun onActivate(isActivation: Boolean, position: Int) {
        selectedPosition = position
        val alarm = alarms[selectedPosition]
        alarm.isActive = isActivation
        disposal.add(updateAlarm(alarm))
        alarmAdapter.notifyDataSetChanged()
    }

    override fun onDeleteAlarm(position: Int) {
        d("ondelete")
        selectedPosition = position
        val alarm = alarms[position]
        alarms.removeAt(position)
        alarmAdapter.notifyDataSetChanged()
        disposal.add(deleteAlarm(alarm))
        Snackbar.make(recyclerAlarm, "delete on item", Snackbar.LENGTH_LONG)
                .setAction("Undo", {
                    alarms.add(selectedPosition, alarm)
                    alarmAdapter.notifyDataSetChanged()
                    disposal.add(insertAlarm(alarm))
                }).show()
    }

    private val onTimeSet = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
        val time = Calendar.getInstance()
        time.set(Calendar.HOUR_OF_DAY, hourOfDay)
        time.set(Calendar.MINUTE, minute)
        val alarm = alarms[selectedPosition]
        alarm.time = time.timeInMillis
        disposal.add(updateAlarm(alarm))
        alarmAdapter.notifyItemChanged(selectedPosition)
    }

    override fun onChangeRingtone(position: Int) {
        selectedPosition = position
        val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER)
        startActivityForResult(intent, RQS_RINGTONEPICKER)
    }

    override fun onIsVibrate(isVibrate: Boolean, position: Int) {
        selectedPosition = position
        activity.showToast("vibrate")
        val alarm = alarms[selectedPosition]
        alarm.isVibrate = isVibrate
        disposal.add(updateAlarm(alarm))
        alarmAdapter.notifyDataSetChanged()
    }

    override fun display() {

    }

    private fun initData() {
        presenter = AlarmPresenter()
        Single.fromCallable { TaskApplication.db.alarmDao().getAllAlarm() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { alamList -> alarms = alamList }
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
    }

    private fun updateAlarm(alarm: Alarm) : Disposable{
        return Single.fromCallable { TaskApplication.db.alarmDao().updateAlarm(alarm) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    private fun insertAlarm(alarm: Alarm) : Disposable{
        return Single.fromCallable { TaskApplication.db.alarmDao().insertAlarm(alarm) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    private fun deleteAlarm(alarm: Alarm) : Disposable {
        return Single.fromCallable { TaskApplication.db.alarmDao().deleteAlarm(alarm) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }
}
