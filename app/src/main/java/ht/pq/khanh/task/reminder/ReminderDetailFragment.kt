package ht.pq.khanh.task.reminder

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.support.v4.app.NavUtils
import android.support.v7.widget.SwitchCompat
import android.text.format.DateFormat
import android.util.Log
import android.view.*
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import ht.pq.khanh.extension.inflateLayout
import ht.pq.khanh.extension.insertRemind
import ht.pq.khanh.model.Reminder
import ht.pq.khanh.multitask.R
import ht.pq.khanh.util.Common
import io.realm.Realm
import java.util.*
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.inputmethod.InputMethodManager


/**
 * Created by khanhpq on 10/5/17.
 */
class ReminderDetailFragment : Fragment(), TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    @BindView(R.id.tvDayReminder)
    lateinit var tvDateTime: TextView
    @BindView(R.id.edMsg)
    lateinit var edtContent: TextInputEditText
    @BindView(R.id.edTitle)
    lateinit var edtTitle: TextInputEditText
    @BindView(R.id.switch_reminder)
    lateinit var switchRemind: SwitchCompat
    private val realm: Realm by lazy { Realm.getDefaultInstance() }
    private var item: Reminder? = null
    private val timeReminder : Calendar by lazy { Calendar.getInstance() }
    private val dateReminder : Calendar by lazy { Calendar.getInstance() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        item = arguments.getParcelable("reminder_detail")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = container!!.inflateLayout(R.layout.fragment_detail_reminder)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Realm.init(context)
        edtTitle.setText(item?.title)
        edtContent.setText(item?.message)
    }

    @OnClick(R.id.fab)
    fun saveToData() {
        var remind: Reminder
        val titleRemind = edtTitle.text.toString()
            val contentRemind = edtContent.text.toString()
            val isAlarm = switchRemind.isChecked
            val timeAlert = timeReminder.timeInMillis
            val dateAlert = dateReminder.timeInMillis
        remind = if (item!!.id == 0.toLong()) {
            Reminder(timeAlert, titleRemind, contentRemind, timeAlert, dateAlert, Common.randomColor(), isAlarm)
        }else {
            Reminder(item!!.id, titleRemind, contentRemind, timeAlert, dateAlert, Common.randomColor(), isAlarm)
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
        tvDateTime.text = "$dayOfMonth : ${month + 1} : $year "
        dateReminder.set(Calendar.YEAR, year)
        dateReminder.set(Calendar.MONTH, month)
        dateReminder.set(Calendar.DAY_OF_MONTH, dayOfMonth)
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        tvDateTime.text = "$hourOfDay : $minute"
        timeReminder.set(Calendar.HOUR_OF_DAY, hourOfDay)
        timeReminder.set(Calendar.MINUTE, minute)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(activity)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("destroy detailactivity", "destroy")
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