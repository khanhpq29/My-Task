package ht.pq.khanh.task.alarm

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import ht.pq.khanh.bus.RxBus
import ht.pq.khanh.bus.TimeEvent
import ht.pq.khanh.dialog.TimePickerDialogFragment
import ht.pq.khanh.extension.inflateLayout
import ht.pq.khanh.model.Alarm

import ht.pq.khanh.multitask.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by khanhpq on 9/25/17.
 */

class AlarmFragment : Fragment(), AlarmContract.View, TimePickerCallback {
    override fun setTimeAlarm(hour: Int, minute: Int) {
        Log.e("time", hour.toString() + minute.toString())
    }

    @BindView(R.id.fab_set_alarm)
    lateinit var fabAlarm: FloatingActionButton
    @BindView(R.id.alarms)
    lateinit var recyclerAlarm: RecyclerView
    private lateinit var presenter: AlarmPresenter
    private lateinit var alarmAdapter: AlarmAdapter
    private var alarms: List<Alarm> = arrayListOf()
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = container!!.inflateLayout(R.layout.fragment_alarm)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    @OnClick(R.id.fab_set_alarm)
    fun showTimeDialog() {
        val timeDialog = TimePickerDialogFragment()
        timeDialog.show(childFragmentManager, "timepicker")
    }

    override fun display() {

    }

    private fun initData() {
        presenter = AlarmPresenter()
        alarmAdapter = AlarmAdapter()
    }

}
