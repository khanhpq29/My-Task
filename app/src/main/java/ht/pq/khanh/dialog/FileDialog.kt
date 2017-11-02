package ht.pq.khanh.dialog

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment

/**
 * Created by khanhpq on 10/26/17.
 */
class FileDialog : DialogFragment() {
    interface OnDialogListener{
        fun onOkClickListener()
        fun onCancleClickListener()
    }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
    }
}