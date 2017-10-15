package ht.pq.khanh.task.sleepawake

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.SwitchCompat
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import ht.pq.khanh.extension.inflateLayout
import ht.pq.khanh.extension.showToast
import ht.pq.khanh.multitask.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by khanh on 15/10/2017.
 */
class AwakeFragment : Fragment(), TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    @BindView(R.id.tv_awake_day)
    lateinit var tvAwakeDay: TextView
    @BindView(R.id.tv_awake_hour)
    lateinit var tvAwakeHour: TextView
    @BindView(R.id.switch_awake)
    lateinit var switchAwake: SwitchCompat
    private val DATE_FORMAT = "MMM, dd yyyy"
    private val TIME_FORMAT = "hh:mm a"
    private val simpleDateFormat by lazy { SimpleDateFormat(DATE_FORMAT) }
    private val simpleTimeFormat by lazy { SimpleDateFormat(TIME_FORMAT) }
    private val timeReminder: Calendar by lazy { Calendar.getInstance() }
    private val dateReminder: Calendar by lazy { Calendar.getInstance() }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = container!!.inflateLayout(R.layout.fragment_awake)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        switchAwake.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                activity.showToast("on")
            }else {
                activity.showToast("off")
            }
        }
    }
    @OnClick(R.id.tv_awake_hour)
    fun changeAwakeHour() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val timePicker = TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
        timePicker.show()
    }

    @OnClick(R.id.btnAwakeDay)
    fun addAwakeDay() {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        val datePicker = DatePickerDialog(context, this, year, month, day)
        datePicker.show()
    }
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        timeReminder.set(Calendar.HOUR_OF_DAY, hourOfDay)
        timeReminder.set(Calendar.MINUTE, minute)
        tvAwakeHour.text = simpleTimeFormat.format(timeReminder.timeInMillis)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        dateReminder.set(Calendar.YEAR, year)
        dateReminder.set(Calendar.MONTH, month)
        dateReminder.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        tvAwakeDay.text = simpleDateFormat.format(dateReminder.timeInMillis)
    }
}