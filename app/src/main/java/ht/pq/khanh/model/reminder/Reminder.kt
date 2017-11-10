package ht.pq.khanh.model.reminder

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * Created by khanhpq on 9/29/17.
 */
@Entity
open class Reminder constructor(@PrimaryKey(autoGenerate = true) var id: Long = 0,
                                var title: String = "",
                                var dateTime: Date? = null,
                                var color: Int = 0,
                                var isNotify: Boolean = false) : Parcelable {
    constructor(source: Parcel) : this(
            source.readLong(),
            source.readString(),
            source.readSerializable() as Date?,
            source.readInt(),
            1 == source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(title)
        writeSerializable(dateTime)
        writeInt(color)
        writeInt((if (isNotify) 1 else 0))
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Reminder> = object : Parcelable.Creator<Reminder> {
            override fun createFromParcel(source: Parcel): Reminder = Reminder(source)
            override fun newArray(size: Int): Array<Reminder?> = arrayOfNulls(size)
        }
    }
}