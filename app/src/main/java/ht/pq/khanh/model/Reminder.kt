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
                    var time: Long? = null,
                    var color: Int = 0,
                    var isNotify: Boolean = false) : RealmObject(), Parcelable {

    constructor(source: Parcel) : this(
            source.readLong(),
            source.readString(),
            source.readValue(Long::class.java.classLoader) as Long?,
            source.readInt(),
            1 == source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(id)
        writeString(title)
        writeValue(time)
        writeInt(color)
        writeInt((if (isNotify) 1 else 0))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Reminder

        if (id != other.id) return false
        if (title != other.title) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        return result
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Reminder> = object : Parcelable.Creator<Reminder> {
            override fun createFromParcel(source: Parcel): Reminder = Reminder(source)
            override fun newArray(size: Int): Array<Reminder?> = arrayOfNulls(size)
        }
    }
}