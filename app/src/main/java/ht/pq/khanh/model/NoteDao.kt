package ht.pq.khanh.model

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

/**
 * Created by khanhpq on 11/10/17.
 */
@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun getAllNote() : List<Note>

    @Insert
    fun insertNote(note: Note)
}
