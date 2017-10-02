package ht.pq.khanh.model

import io.realm.RealmObject

/**
 * Created by khanhpq on 10/2/17.
 */

open class Alarm : RealmObject {
    var hour: Int = 0
    var minute: Int = 0
    var isActive: Boolean = false
    var isVibrate: Boolean = false

    constructor() {}

    constructor(hour: Int, minute: Int, isActive: Boolean, isVibrate: Boolean) {
        this.hour = hour
        this.minute = minute
        this.isActive = isActive
        this.isVibrate = isVibrate
    }
}
