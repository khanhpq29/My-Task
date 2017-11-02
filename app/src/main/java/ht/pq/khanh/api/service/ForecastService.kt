package ht.pq.khanh.api.service

import ht.pq.khanh.api.forecast.Forecast
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by khanhpq on 9/28/17.
 */
interface ForecastService {
    @GET("data/2.5/forecast/daily")
    fun getForecastWeather(@Query("q") location: String,
                           @Query("appid")apiKey : String) : Observable<Forecast>
}