package ht.pq.khanh.dialog

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.format.DateFormat
import android.widget.TimePicker
import ht.pq.khanh.task.alarm.TimePickerCallback
import java.util.*

/**
 * Created by khanhpq on 9/25/17.
 */
class TimePickerDialogFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    private var callBack : TimePickerCallback? = null
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        return TimePickerDialog(context, this, hour, minute, DateFormat.is24HourFormat(context))
    }
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        if (callBack != null){
            callBack!!.setTimeAlarm(hourOfDay, minute)
        }
    }
}