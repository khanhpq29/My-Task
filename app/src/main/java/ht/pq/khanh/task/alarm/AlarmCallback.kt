package ht.pq.khanh.task.alarm

/**
 * Created by khanh on 01/10/2017.
 */
interface AlarmCallback {
    fun onChangeRingtone(position: Int)
    fun onChangeTime(position: Int)
//    fun onChangeDay()
    fun onIsVibrate(isVibrate : Boolean, position: Int)
    fun onDeleteAlarm(position: Int)
    fun onActivate(isActivation: Boolean, position: Int)
}