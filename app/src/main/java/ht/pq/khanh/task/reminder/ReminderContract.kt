package ht.pq.khanh.task.reminder

import ht.pq.khanh.model.Reminder

/**
 * Created by khanh on 30/09/2017.
 */
interface ReminderContract {
    interface View{
        fun loadAllReminder()
        fun loadChange()
    }
    interface Presenter{
        fun initReminder()
        fun addReminder(reminder :Reminder)
        fun updateReminder(reminder: Reminder)
        fun deleteReminder(reminder: Reminder)
    }
}