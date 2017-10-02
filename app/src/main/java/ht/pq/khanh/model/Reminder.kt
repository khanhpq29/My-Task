package ht.pq.khanh.model

import io.realm.RealmObject
import java.util.*

/**
 * Created by khanhpq on 9/29/17.
 */
open class Reminder : RealmObject {
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
}