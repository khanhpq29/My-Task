package ht.pq.khanh.model

import android.media.Ringtone
import java.util.*

/**
 * Created by khanhpq on 9/25/17.
 */
data class Alarm(val hour: Int, val minute: Int,val isActive : Boolean, val isVibrate : Boolean/*, val alarmRingtone: Ringtone, val date: Date*/)