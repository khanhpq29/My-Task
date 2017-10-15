package ht.pq.khanh.model

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmObject

/**
 * Created by khanh on 14/10/2017.
 */
open class Remind(var title: String = "") : Parcelable, RealmObject() {
    constructor(source: Parcel) : this(
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(title)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Remind> = object : Parcelable.Creator<Remind> {
            override fun createFromParcel(source: Parcel): Remind = Remind(source)
            override fun newArray(size: Int): Array<Remind?> = arrayOfNulls(size)
        }
    }
}