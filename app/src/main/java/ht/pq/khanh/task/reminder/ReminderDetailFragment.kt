package ht.pq.khanh.task.reminder

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.support.v4.app.NavUtils
import android.support.v7.widget.SwitchCompat
import android.util.Log
import android.view.*
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import ht.pq.khanh.dialog.DateDialogFragment
import ht.pq.khanh.dialog.TimePickerDialogFragment
import ht.pq.khanh.extension.inflateLayout
import ht.pq.khanh.extension.insertRemind
import ht.pq.khanh.model.Reminder
import ht.pq.khanh.multitask.R
import io.realm.Realm

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
        edtContent.setText(item?.title)
    }

    @OnClick(R.id.fab)
    fun saveToData() {
        val titleRemind = edtTitle.text.toString()
        val contentRemind = edtContent.text.toString()
        val isAlarm = switchRemind.isChecked
        val remind = Reminder(titleRemind, contentRemind, null, isAlarm)
        realm.insertRemind(remind)
        activity.intent.putExtra("reminder", remind)
        activity.setResult(Activity.RESULT_OK, activity.intent)
        activity.finish()
    }

    @OnClick(R.id.btnDate)
    fun showDatePickerDialog() {
        val dateDialog = DateDialogFragment()
        dateDialog.show(childFragmentManager, "date")
    }

    @OnClick(R.id.btnTime)
    fun showTimePickerDialog() {
        val timeDialog = TimePickerDialogFragment()
        timeDialog.show(childFragmentManager, "time picker")
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        tvDateTime.text = "$dayOfMonth : ${month + 1} : $year "
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        tvDateTime.text = "$hourOfDay : $minute"
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(activity)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("destroy detailactivity", "destroy")
        realm.close()
    }

    private fun getData() {

    }
}