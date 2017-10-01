package ht.pq.khanh.dialog

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import java.util.*

/**
 * Created by khanh on 30/09/2017.
 */
class DateDialogFragment : DialogFragment(){
    private val dateFormat = "dd/MM/yyyy"
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val date = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)
        val datePicker = DatePickerDialog(activity, activity as DatePickerDialog.OnDateSetListener, year, month, date)
        return datePicker
    }
}