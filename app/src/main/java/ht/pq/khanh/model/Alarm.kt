package ht.pq.khanh.model

import io.realm.RealmObject

/**
 * Created by khanhpq on 10/2/17.
 */

open class Alarm : RealmObject {
    var time : Long = 0
    var label : String = ""
    var isActive: Boolean = false
    var isVibrate: Boolean = false

    constructor() {}

    constructor(time: Long, label : String, isActive: Boolean, isVibrate: Boolean) {
        this.time = time
        this.label = label
        this.isActive = isActive
        this.isVibrate = isVibrate
    }
}
