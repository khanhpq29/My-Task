package ht.pq.khanh.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import ht.pq.khanh.model.alarm.Alarm
import ht.pq.khanh.model.alarm.AlarmDao
import ht.pq.khanh.model.reminder.Reminder
import ht.pq.khanh.model.reminder.ReminderDao

/**
 * Created by khanhpq on 11/10/17.
 */
@Database(entities = arrayOf(Alarm::class, Reminder::class, Note::class), version = 1)
abstract class TaskDatabase : RoomDatabase(){
    abstract fun alarmDao() : AlarmDao
    abstract fun reminderDao() : ReminderDao
    abstract fun noteDao() : NoteDao
}