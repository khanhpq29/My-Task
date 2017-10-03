package ht.pq.khanh.task.alarm

import ht.pq.khanh.model.Alarm

/**
 * Created by khanh on 01/10/2017.
 */
interface AlarmCallback {
//    fun onChangeRington()
    fun onChangeTime(time : Alarm)
//    fun onChangeDay()
//    fun onChangeOnOff(isOff : Boolean)
//    fun onIsVibrate(isVibrate : Boolean)
}