package ht.pq.khanh.model.reminder

import android.arch.persistence.room.*

/**
 * Created by khanhpq on 11/10/17.
 */
@Dao
interface ReminderDao {
    @Query("SELECT * FROM reminder")
    fun getAllReminder() : MutableList<Reminder>

    @Insert
    fun insertReminder(Reminder: Reminder)

    @Update
    fun updateReminder(Reminder: Reminder)

    @Delete
    fun deleteReminder(Reminder: Reminder)
}