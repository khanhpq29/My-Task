package ht.pq.khanh.extension

import ht.pq.khanh.model.Alarm
import ht.pq.khanh.model.Remind
import ht.pq.khanh.model.Reminder
import io.realm.Realm
import io.realm.RealmResults

/**
 * Created by khanh on 01/10/2017.
 */
fun Realm.insertAlarm(alarm: Alarm) {
    this.executeTransaction { realm ->
        realm.insertOrUpdate(alarm)
    }
}

fun Realm.deleteAlarm(position: Int) {
    val data = this.findAllAlarm()
    this.executeTransaction {
        val movie = data[position]
        movie.deleteFromRealm()
    }
}

fun Realm.findAllAlarm(): RealmResults<Alarm> {
    var realmQuery : RealmResults<Alarm>? = null
    this.executeTransaction {
        realm: Realm ->
        realmQuery = realm.where(Alarm::class.java).findAll()
    }
    return realmQuery!!
}

fun Realm.insertRemind(remind: Reminder) {
    this.executeTransaction { realm ->
        realm.insertOrUpdate(remind)
    }
}

fun Realm.deleteRemind(position: Int) {
    val data = this.findAllRemind()
    this.executeTransaction {
        val movie = data[position]
        movie.deleteFromRealm()
    }
}

fun Realm.updateReminder(remind: Reminder){
    this.executeTransaction {
        realm -> realm.copyToRealmOrUpdate(remind)
    }
}

fun Realm.findAllRemind(): RealmResults<Reminder> {
    var realmResults : RealmResults<Reminder>? = null
    this.executeTransaction {
        realm: Realm ->
        realmResults = realm.where(Reminder::class.java).findAll()
    }
    return realmResults!!
}
