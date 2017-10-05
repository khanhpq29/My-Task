package ht.pq.khanh.extension

import ht.pq.khanh.model.Alarm
import ht.pq.khanh.model.Reminder
import io.realm.Realm
import io.realm.RealmResults

/**
 * Created by khanh on 01/10/2017.
 */
fun Realm.insertAlarm(alarm: Alarm) {
    this.beginTransaction()
    this.insert(alarm)
    this.commitTransaction()
}

fun Realm.deleteAlarm(position: Int) {
    val data = this.findAllAlarm()
    this.executeTransaction {
        val movie = data[position]
        movie.deleteFromRealm()
    }
}

fun Realm.findAllAlarm(): RealmResults<Alarm> {
    this.beginTransaction()
    val realmQuery = this.where(Alarm::class.java).findAll()
    this.commitTransaction()
    return realmQuery
}

fun Realm.insertRemind(remind: Reminder) {
    this.beginTransaction()
    this.insert(remind)
    this.commitTransaction()
}

fun Realm.deleteRemind(position: Int) {
    val data = this.findAllRemind()
    this.executeTransaction {
        val movie = data[position]
        movie.deleteFromRealm()
    }
}

fun Realm.findAllRemind(): RealmResults<Reminder> {
    this.beginTransaction()
    val realmQuery = this.where(Reminder::class.java).findAll()
    this.commitTransaction()
    return realmQuery
}