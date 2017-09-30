package ht.pq.khanh.api.service

import ht.pq.khanh.api.current.CurrentWeather
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by khanhpq on 9/28/17.
 */
interface WeatherService {
    @GET("data/2.5/weather?q=London,uk&appid=b1b15e88fa797225412429c1c50c122a1")
    fun getDetailWeather(/*@Query("q") address: String*/) : Observable<CurrentWeather>
}