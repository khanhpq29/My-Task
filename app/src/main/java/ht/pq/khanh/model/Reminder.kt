package ht.pq.khanh.model

import android.os.Parcel
import android.os.Parcelable
import io.realm.RealmObject
import java.util.*

/**
 * Created by khanhpq on 9/29/17.
 */
open class Reminder : RealmObject, Parcelable {
    var title: String = ""

    var message: String? = null

    var time: Date? = null

    var isNotify: Boolean = false

    constructor() {}

    constructor(title: String, message: String?, time: Date?, isNotify: Boolean) {
        this.title = title
        this.message = message
        this.time = time
        this.isNotify = isNotify
    }

    constructor(source: Parcel) : this(
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {}

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Reminder> = object : Parcelable.Creator<Reminder> {
            override fun createFromParcel(source: Parcel): Reminder = Reminder(source)
            override fun newArray(size: Int): Array<Reminder?> = arrayOfNulls(size)
        }
    }
}