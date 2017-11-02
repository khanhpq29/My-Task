package ht.pq.khanh.model

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

/**
 * Created by khanhpq on 9/29/17.
 */
@RealmClass
open class Reminder(@PrimaryKey open var id: Long = 0,
                    var title: String = "",
                    var message: String? = null,
                    var timeDay: Long? = null,
                    var timeHour: Long? = null,
                    var color: Int = 0,
                    var isNotify: Boolean = false) : RealmObject(), Parcelable {
    constructor(source: Parcel) : this(
            source.readLong(),
            source.readString(),
            source.readString(),
            source.readValue(Long::class.java.classLoader) as Long?,
            source.readValue(Long::class.java.classLoader) as Long?,
            source.readInt(),
            1 == source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(title)
        writeString(message)
        writeValue(timeDay)
        writeValue(timeHour)
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