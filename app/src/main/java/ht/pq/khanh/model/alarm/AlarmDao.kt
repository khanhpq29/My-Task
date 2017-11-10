package ht.pq.khanh.model.alarm

import android.arch.persistence.room.*

/**
 * Created by khanhpq on 11/10/17.
 */
@Dao
interface AlarmDao {
    @Query("SELECT * FROM alarm")
    fun getAllAlarm() : MutableList<Alarm>

    @Insert
    fun insertAlarm(alarm: Alarm)

    @Update
    fun updateAlarm(alarm: Alarm)

    @Delete
    fun deleteAlarm(alarm: Alarm)
}