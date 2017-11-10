package ht.pq.khanh.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by khanhpq on 11/10/17.
 */
@Entity
open class Note constructor(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "note_id") var id: Long,
        @ColumnInfo(name = "note_title") var title: String)