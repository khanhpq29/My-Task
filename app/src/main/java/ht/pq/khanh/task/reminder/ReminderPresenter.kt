package ht.pq.khanh.task.reminder

import ht.pq.khanh.model.reminder.Reminder

/**
 * Created by khanh on 30/09/2017.
 */
class ReminderPresenter(val view: ReminderContract.View) : ReminderContract.Presenter {
    override fun initReminder() {
        view.loadAllReminder()
    }

    override fun addReminder(reminder: Reminder) {
        view.loadChange()
    }

    override fun updateReminder(reminder: Reminder) {
        view.loadChange()
    }

    override fun deleteReminder(reminder: Reminder) {
        view.loadChange()
    }
}