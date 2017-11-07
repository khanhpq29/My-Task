package ht.pq.khanh.bus

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

/**
 * Created by khanhpq on 9/25/17.
 */

class RxBus private constructor(){

    private val bus = PublishSubject.create<Any>()

    fun send(event: Any) {
        bus.onNext(event)
    }

    fun toObservable(): Observable<Any> {
        return bus
    }

    fun <T> toObservable(eventType: Class<T>): Observable<T> {
        return bus.ofType(eventType)
    }

    fun hasObservers(): Boolean {
        return bus.hasObservers()
    }

    companion object {
        val instance = RxBus()
    }
}
