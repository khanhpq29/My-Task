package ht.pq.khanh.task.reminder

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.support.v4.app.NavUtils
import android.support.v7.widget.SwitchCompat
import android.text.format.DateFormat
import android.view.*
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnCheckedChanged
import butterknife.OnClick
import com.pawegio.kandroid.d
import ht.pq.khanh.extension.*
import ht.pq.khanh.model.reminder.Reminder
import ht.pq.khanh.multitask.R
import ht.pq.khanh.util.Common
import java.util.*

/**
 * Created by khanhpq on 10/5/17.
 */
class ReminderDetailFragment : Fragment(), TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    @BindView(R.id.tvDayReminder)
    lateinit var tvDateTime: TextView
    @BindView(R.id.edTitle)
    lateinit var edtTitle: TextInputEditText
    @BindView(R.id.textInputTitle)
    lateinit var layoutTitle: TextInputLayout
    @BindView(R.id.switch_reminder)
    lateinit var switchRemind: SwitchCompat
    @BindView(R.id.edtDate)
    lateinit var edtDate: EditText
    @BindView(R.id.edtTime)
    lateinit var edtTime: EditText
    private lateinit var item: Reminder
    private var time: Date? = null
    private val timeReminder: Calendar by lazy { Calendar.getInstance() }
    private val dateReminder: Calendar by lazy { Calendar.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item = arguments.getParcelable("reminder_detail")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = container!!.inflateLayout(R.layout.fragment_detail_reminder)
        ButterKnife.bind(this, view)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeData()
    }

    @OnCheckedChanged(R.id.switch_reminder)
    fun changeLayoutVisuality(isChecked: Boolean) {
        if (isChecked) {
            context.hideKeyBoard(edtTitle)
            edtDate.isShow()
            edtTime.isShow()
        } else {
            edtDate.isHide()
            edtTime.isHide()
        }
    }

    @OnClick(R.id.edtDate)
    fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        val datePicker = DatePickerDialog(context, this, year, month, day)
        datePicker.show()
    }

    @OnClick(R.id.edtTime)
    fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val timePicker = TimePickerDialog(activity, this, hour, minute, DateFormat.is24HourFormat(activity))
        timePicker.show()
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val calendar = Calendar.getInstance()
        if (time != null) {
            calendar.time = time
        }
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        edtDate.setText("${month + 1}/$dayOfMonth/$year")
        dateReminder.set(year, month, dayOfMonth, hour, minute, 0)
        time = dateReminder.time
        tvDateTime.text = "${month + 1}, $dayOfMonth $year, $hour : $minute "
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        if (time != null) {
            calendar.time = time
        }
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        edtTime.setText("$hourOfDay : $minute")
        timeReminder.set(year, month, day, hourOfDay, minute, 0)
        time = timeReminder.time
        tvDateTime.text = "${month + 1}, $day $year, $hourOfDay : $minute "

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_save, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> NavUtils.navigateUpFromSameTask(activity)
            R.id.m_save -> saveToData()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        d("destroy")
    }

    private fun initializeData() {
        edtTitle.setText(item.title)
        switchRemind.isChecked = item.isNotify
        val calendar = Calendar.getInstance()
        if (item.dateTime != null) {
            calendar.time = item.dateTime
            edtDate.setText("${calendar.get(Calendar.MONTH)}/${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.YEAR)}")
            edtTime.setText("${calendar.get(Calendar.HOUR_OF_DAY)} : ${calendar.get(Calendar.MINUTE)}")
        }
        changeLayoutVisuality(switchRemind.isChecked)
    }

    private fun saveToData() {
        val titleRemind = edtTitle.text.toString()
        if (titleRemind.trim().isEmpty()) {
            layoutTitle.error = getString(R.string.title_empty)
            return
        }
        if (time != null) {
            if (time!!.before(Date())) {
                context.showToast(getString(R.string.time_error))
                return
            }
            val isAlarm = switchRemind.isChecked
            val currentId = System.currentTimeMillis()
            var remind = if (item.id == 0.toLong()) {
                Reminder(currentId, titleRemind, time, Common.randomColor(), isAlarm)
            } else {
                Reminder(item.id, titleRemind, time, item.color, isAlarm)
            }
            val intentReminder = activity.intent
            intentReminder.putExtra("reminder_result", remind)
            activity.setResult(Activity.RESULT_OK, intentReminder)
            activity.finish()
        }
    }

    companion object {
        fun newInstance(remind: Reminder): ReminderDetailFragment {
            val myFragment = ReminderDetailFragment()
            val args = Bundle()
            args.putParcelable("reminder_detail", remind)
            myFragment.arguments = args
            return myFragment
        }
    }
}