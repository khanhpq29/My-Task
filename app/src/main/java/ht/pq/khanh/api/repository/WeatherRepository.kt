package ht.pq.khanh.api.repository

import ht.pq.khanh.api.ApiManager
import ht.pq.khanh.api.current.CurrentWeather
import ht.pq.khanh.api.service.WeatherService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by khanhpq on 9/28/17.
 */
class WeatherRepository {
    var weatherService : WeatherService = ApiManager.createApiService(WeatherService::class.java)
    fun getCurrentWeather() : Observable<CurrentWeather>{
        return weatherService.getDetailWeather("London", "b1b15e88fa797225412429c1c50c122a1")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}