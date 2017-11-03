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
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnCheckedChanged
import butterknife.OnClick
import com.pawegio.kandroid.d
import ht.pq.khanh.extension.hideKeyBoard
import ht.pq.khanh.extension.inflateLayout
import ht.pq.khanh.model.Reminder
import ht.pq.khanh.multitask.R
import ht.pq.khanh.util.Common
import io.realm.Realm
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
    @BindView(R.id.btnDate)
    lateinit var btnDate: Button
    @BindView(R.id.btnTime)
    lateinit var btnTime: Button
    private val realm: Realm by lazy { Realm.getDefaultInstance() }
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
        Realm.init(context)
        edtTitle.setText(item.title)
        switchRemind.isChecked = item.isNotify
        changeLayoutVisuality(switchRemind.isChecked)
    }

    @OnCheckedChanged(R.id.switch_reminder)
    fun changeLayoutVisuality(isChecked: Boolean) {
        if (isChecked) {
            context.hideKeyBoard(edtTitle)
            btnDate.visibility = View.VISIBLE
            btnTime.visibility = View.VISIBLE
        } else {
            btnDate.visibility = View.GONE
            btnTime.visibility = View.GONE
        }
    }

    private fun saveToData() {
        val titleRemind = edtTitle.text.toString()
        if (titleRemind.trim().isEmpty()) {
            layoutTitle.error = getString(R.string.title_empty)
            return
        }
        var remind: Reminder
        val isAlarm = switchRemind.isChecked
        val currentId = System.currentTimeMillis()
        remind = if (item.id == 0.toLong()) {
            Reminder(currentId, titleRemind, time, Common.randomColor(), isAlarm)
        } else {
            Reminder(item.id, titleRemind, time, item.color, isAlarm)
        }
        val intentReminder = activity.intent
        intentReminder.putExtra("reminder_result", remind)
        activity.setResult(Activity.RESULT_OK, intentReminder)
        activity.finish()
    }

    @OnClick(R.id.btnDate)
    fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        val datePicker = DatePickerDialog(context, this, year, month, day)
        datePicker.show()
    }

    @OnClick(R.id.btnTime)
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
        realm.close()
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