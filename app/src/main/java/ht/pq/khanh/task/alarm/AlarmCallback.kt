package ht.pq.khanh.task.alarm

/**
 * Created by khanh on 01/10/2017.
 */
interface AlarmCallback {
    fun onChangeRington()
    fun onChangeTime()
    fun onChangeDay()
    fun onChangeOnOff(isOff : Boolean)
    fun onIsVibrate(isVibrate : Boolean)
}