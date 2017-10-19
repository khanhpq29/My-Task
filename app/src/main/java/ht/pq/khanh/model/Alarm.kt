package ht.pq.khanh.model

import android.media.Ringtone
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import android.util.SparseBooleanArray
import io.realm.RealmObject

/**
 * Created by khanhpq on 10/2/17.
 */

open class Alarm(var time: Long = 0,
                 var label: String = "",
                 var isActive: Boolean = false,
                 var isVibrate: Boolean = false,
                 var ringtoneUri: String? = null) : RealmObject(), Parcelable {
    constructor(source: Parcel) : this(
            source.readLong(),
            source.readString(),
            1 == source.readInt(),
            1 == source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeLong(time)
        writeString(label)
        writeInt((if (isActive) 1 else 0))
        writeInt((if (isVibrate) 1 else 0))
        writeString(ringtoneUri)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Alarm> = object : Parcelable.Creator<Alarm> {
            override fun createFromParcel(source: Parcel): Alarm = Alarm(source)
            override fun newArray(size: Int): Array<Alarm?> = arrayOfNulls(size)
        }
    }
}