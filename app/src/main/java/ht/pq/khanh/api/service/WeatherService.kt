package ht.pq.khanh.api.service

import ht.pq.khanh.api.current.CurrentWeather
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by khanhpq on 9/28/17.
 */
interface WeatherService {
    @GET("data/2.5/weather")
    fun getDetailWeather(@Query("q") address: String,
                         @Query("appid") apiKey : String) : Observable<CurrentWeather>
}