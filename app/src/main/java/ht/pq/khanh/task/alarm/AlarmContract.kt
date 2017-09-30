package ht.pq.khanh.task.alarm

/**
 * Created by khanhpq on 9/25/17.
 */
interface AlarmContract {
    interface View{
        fun display()
    }
    interface Presenter{
        fun setAlarm(hour : Int, minute : Int)
        fun editAlarm(hour : Int, minute : Int)
        fun deleteAlarm()
    }
}