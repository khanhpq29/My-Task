package ht.pq.khanh.extension

import ht.pq.khanh.model.Alarm
import ht.pq.khanh.model.Reminder
import io.realm.Realm
import io.realm.RealmObject
import io.realm.RealmResults

/**
 * Created by khanhpq on 11/9/17.
 */
object DatabaseHelper {
    fun deleteAlarm(realm: Realm, alarm: Alarm) {
        realm.executeTransaction {
            val realQuery = realm.where(Alarm::class.java)
            val alarmQuery = realQuery.equalTo("id", alarm.id)
            val alarmResult = alarmQuery.findAll()
            if (alarmResult.size > 0) {
                alarmResult.deleteFromRealm(0)
            }
        }
    }

//    fun Realm.findAllAlarm(): RealmResults<Alarm> {
//        var realmQuery: RealmResults<Alarm>? = null
//        this.executeTransaction { realm: Realm ->
//            realmQuery = realm.where(Alarm::class.java).findAll()
//        }
//        return realmQuery!!
//    }
//
//    fun Realm.deleteAlarmRx(alarm: Alarm): Completable {
//        return Completable.create({ emitter: CompletableEmitter ->
//            val realQuery = this.where(Alarm::class.java)
//            val alarmQuery = realQuery.equalTo("id", alarm.id)
//            val alarmResult = alarmQuery.findAll()
//            if (alarmResult.size > 0) {
//                alarmResult.deleteFromRealm(0)
//                emitter.onComplete()
//            } else {
//                emitter.onError(Exception())
//            }
//        })
//    }

    /**
     * reminder data extension
     */
    fun <T : RealmObject> insert(realm: Realm, remind: T) {
        realm.executeTransaction { realmObj ->
            realmObj.copyToRealmOrUpdate(remind)
        }
    }

    fun deleteRemind(realm: Realm, reminder: Reminder) {
        realm.executeTransaction {
            val realmQuery = realm.where(Reminder::class.java)
            realmQuery.equalTo("id", reminder.id)
            val realmAlarm = realmQuery.findAll()
            if (realmAlarm.size > 0) {
                realmAlarm.deleteFromRealm(0)
            }
        }
    }

    fun <E : RealmObject> update(realm: Realm, remind: E) {
        realm.executeTransaction { realmObj ->
            realmObj.copyToRealmOrUpdate(remind)
        }
    }

    inline fun <reified T : RealmObject> findAll(realm: Realm): RealmResults<T> {
        var realmResults: RealmResults<T>? = null
        realm.executeTransaction { realmObj: Realm ->
            realmResults = realmObj.where(T::class.java).findAll()
        }
        return realmResults!!
    }

}
