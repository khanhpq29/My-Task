package ht.pq.khanh.extension

import ht.pq.khanh.model.Alarm
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

fun Realm.updateAlarm(alarm: Alarm) {
    this.executeTransaction { realm ->
        realm.copyToRealmOrUpdate(alarm)
    }
}

fun Realm.deleteAlarm(alarm: Alarm) {
    this.executeTransaction {
        val realQuery = this.where(Alarm::class.java)
        val alarmQuery = realQuery.equalTo("id", alarm.id)
        val alarmResult = alarmQuery.findAll()
        if (alarmResult.size > 0) {
            alarmResult.deleteFromRealm(0)
        }
    }
}

fun Realm.findAllAlarm(): RealmResults<Alarm> {
    var realmQuery: RealmResults<Alarm>? = null
    this.executeTransaction { realm: Realm ->
        realmQuery = realm.where(Alarm::class.java).findAll()
    }
    return realmQuery!!
}

/**
 * reminder data extension
 */
fun Realm.insertRemind(remind: Reminder) {
    this.executeTransaction { realm ->
        realm.copyToRealmOrUpdate(remind)
    }
}

fun Realm.deleteRemind(reminder: Reminder) {
    this.executeTransaction {
        val realmQuery = this.where(Reminder::class.java)
        realmQuery.equalTo("id", reminder.id)
        val realmAlarm = realmQuery.findAll()
        if (realmAlarm.size > 0) {
            realmAlarm.deleteFromRealm(0)
        }
    }
}

fun Realm.updateReminder(remind: Reminder) {
    this.executeTransaction { realm ->
        realm.copyToRealmOrUpdate(remind)
    }
}

fun Realm.findAllRemind(): RealmResults<Reminder> {
    var realmResults: RealmResults<Reminder>? = null
    this.executeTransaction { realm: Realm ->
        realmResults = realm.where(Reminder::class.java).findAll()
    }
    return realmResults!!
}
