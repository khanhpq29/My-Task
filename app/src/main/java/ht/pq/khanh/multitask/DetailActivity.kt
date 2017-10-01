package ht.pq.khanh.multitask

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SwitchCompat
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import ht.pq.khanh.dialog.DateDialogFragment
import ht.pq.khanh.dialog.TimePickerDialogFragment
import ht.pq.khanh.task.alarm.TimePickerCallback

class DetailActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        tvDateTime.text = "$dayOfMonth : ${month+1} : $year "
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        tvDateTime.text = "$hourOfDay : $minute"
    }

    @BindView(R.id.tvDayReminder)
    lateinit var tvDateTime : TextView
    @BindView(R.id.edMsg)
    lateinit var edtContent : EditText
    @BindView(R.id.edTitle)
    lateinit var edtTitle : EditText
    @BindView(R.id.switch_reminder)
    lateinit var switchRemind : SwitchCompat
    private var isAlarm = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        ButterKnife.bind(this)
    }
    @OnClick(R.id.fab)
    fun saveToData(){
        val titleRemind = edtTitle.text
        val contentRemind = edtContent.text

    }

    @OnClick(R.id.btnDate)
    fun showDatePickerDialog(){
        val dateDialog = DateDialogFragment()
        dateDialog.show(supportFragmentManager, "date")
    }
    @OnClick(R.id.btnTime)
    fun showTimePickerDialog(){
        val timeDialog = TimePickerDialogFragment()
        timeDialog.show(supportFragmentManager, "timepicker")
    }
}
