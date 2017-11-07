//package ht.pq.khanh.model
//
//import android.arch.persistence.room.*
//import io.reactivex.Flowable
//
///**
// * Created by khanhpq on 11/7/17.
// */
//@Dao
//interface TaskDao {
//    @Query("select * from Alarm")
//    fun getAllAlarm(): Flowable<List<Alarm>>
//
//    @Insert
//    fun insertAlarm(alarm: Alarm)
//
//    @Delete
//    fun deleteAlarm(alarm: Alarm)
//
//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    fun updateAlarm(alarm: Alarm)
//
//    @Query("select * from reminder")
//    fun getAllReminder(): Flowable<List<Reminder>>
//
//    @Insert
//    fun insertReminder(reminder: Reminder)
//
//    @Delete
//    fun deleteReminder(reminder: Reminder)
//
//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    fun updateReminder(reminder: Reminder)
//}