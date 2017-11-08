package ht.pq.khanh.multitask

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ToggleButton
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import ht.pq.khanh.extension.inflateLayout
import ht.pq.khanh.helper.NotificationHelper

/**
 * Created by khanhpq on 11/8/17.
 */

class Example : Fragment(){
    @BindView(R.id.editTextHH)
    lateinit var hours: EditText
    @BindView(R.id.editTextMM)
    lateinit var minutes: EditText
    @BindView(R.id.toggleButtonRTC)
    lateinit var toggleRTC : ToggleButton
    @BindView(R.id.toggleButtonElapsed)
    lateinit var toggleElapsed : ToggleButton
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = container!!.inflateLayout(R.layout.layout_example)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    @OnClick(R.id.toggleButtonRTC)
    fun clickToggleButtonRTC() {
        val isEnabled = toggleRTC.isEnabled

        if (isEnabled) {
//            NotificationHelper.scheduleRepeatingRTCNotification(activity, hours.text.toString(), minutes.text.toString())
            NotificationHelper.enableBootReceiver(activity)
        } else {
            NotificationHelper.cancelAlarmRTC()
            NotificationHelper.disableBootReceiver(activity)
        }
    }
    @OnClick(R.id.toggleButtonElapsed)
    fun clickToggleButtonElapsed() {
        val isEnabled = toggleElapsed.isEnabled

        if (isEnabled) {
            NotificationHelper.scheduleRepeatingElapsedNotification(activity)
            NotificationHelper.enableBootReceiver(activity)
        } else {
            NotificationHelper.cancelAlarmElapsed()
            NotificationHelper.disableBootReceiver(activity)
        }
    }
    @OnClick(R.id.cancel_action)
    fun cancelAlarms() {
        NotificationHelper.cancelAlarmRTC()
        NotificationHelper.cancelAlarmElapsed()
        NotificationHelper.disableBootReceiver(activity)
    }
}
