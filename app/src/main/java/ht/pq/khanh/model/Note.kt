package ht.pq.khanh.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable

/**
 * Created by khanhpq on 11/10/17.
 */
@Entity
open class Note constructor(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "note_id") var id: Long,
                            @ColumnInfo(name = "note_title") var title: String) : Parcelable {
    constructor(source: Parcel) : this(
            source.readLong(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(title)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Note> = object : Parcelable.Creator<Note> {
            override fun createFromParcel(source: Parcel): Note = Note(source)
            override fun newArray(size: Int): Array<Note?> = arrayOfNulls(size)
        }
    }
}