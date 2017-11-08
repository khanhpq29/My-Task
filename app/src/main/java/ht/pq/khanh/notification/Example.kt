package ht.pq.khanh.notification

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ToggleButton
import butterknife.BindView
import butterknife.ButterKnife
import ht.pq.khanh.extension.inflateLayout
import ht.pq.khanh.multitask.R
import ht.pq.khanh.service.NotificationHelper

/**
 * Created by khanhpq on 11/8/17.
 */

class Example : Fragment(){
    @BindView(R.id.editTextHH)
    lateinit var hours: EditText
    @BindView(R.id.editTextMM)
    lateinit var minutes: EditText
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = container!!.inflateLayout(R.layout.layout_example)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    fun clickToggleButtonRTC(view: View) {
        val isEnabled = (view as ToggleButton).isEnabled

        if (isEnabled) {
            NotificationHelper.scheduleRepeatingRTCNotification(activity, hours.text.toString(), minutes.text.toString())
            NotificationHelper.enableBootReceiver(activity)
        } else {
            NotificationHelper.cancelAlarmRTC()
            NotificationHelper.disableBootReceiver(activity)
        }
    }

    fun clickToggleButtonElapsed(view: View) {
        val isEnabled = (view as ToggleButton).isEnabled

        if (isEnabled) {
            NotificationHelper.scheduleRepeatingElapsedNotification(activity)
            NotificationHelper.enableBootReceiver(activity)
        } else {
            NotificationHelper.cancelAlarmElapsed()
            NotificationHelper.disableBootReceiver(activity)
        }
    }

    fun cancelAlarms(view: View) {
        NotificationHelper.cancelAlarmRTC()
        NotificationHelper.cancelAlarmElapsed()
        NotificationHelper.disableBootReceiver(activity)
    }
}
