package ht.pq.khanh.api.service

import ht.pq.khanh.model.Reminder
import io.reactivex.Observable

/**
 * Created by khanhpq on 10/3/17.
 */
interface DataService {
    fun getData() : Observable<MutableList<Reminder>>
}