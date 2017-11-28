package ht.pq.khanh.task.sleepawake

import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.TimePicker
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnCheckedChanged
import butterknife.OnClick
import ht.pq.khanh.extension.inflateLayout
import ht.pq.khanh.extension.showToast
import ht.pq.khanh.multitask.R
import ht.pq.khanh.util.CountDown
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by khanh on 15/10/2017.
 */
class AwakeFragment : Fragment(), TimePickerDialog.OnTimeSetListener {
    @BindView(R.id.tv_awake_hour)
    lateinit var tvAwakeHour: TextView
    @BindView(R.id.tv_countdown)
    lateinit var tvCountDown: TextView
    private val TIME_FORMAT = "hh:mm a"
    private val simpleTimeFormat by lazy { SimpleDateFormat(TIME_FORMAT) }
    private val timeReminder: Calendar by lazy { Calendar.getInstance() }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = container.inflateLayout(R.layout.fragment_awake)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        countdown.start()
    }

    @OnClick(R.id.tv_awake_hour)
    fun changeAwakeHour() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val timePicker = TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
        timePicker.show()
    }

    @OnCheckedChanged(R.id.tgCountdown)
    fun changeCountdown(isChecked: Boolean) {
        if (isChecked) {
            countdown.cancel()
        } else {
            countdown.start()
        }
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        timeReminder.set(Calendar.HOUR_OF_DAY, hourOfDay)
        timeReminder.set(Calendar.MINUTE, minute)
        tvAwakeHour.text = simpleTimeFormat.format(timeReminder.timeInMillis)
    }

    private val countdown = CountDown(1, { limit ->
        tvCountDown.text = limit
        true
    }) {
        activity.showToast("finish")
        activity.finish()
        true
    }
}