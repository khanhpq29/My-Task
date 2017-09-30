package ht.pq.khanh.api.service

import ht.pq.khanh.api.forecast.Forecast
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * Created by khanhpq on 9/28/17.
 */
interface ForecastService {
    @GET("data/2.5/forecast?lat=35&lon=139&appid=b1b15e88fa797225412429c1c50c122a1")
    fun getForecastWeather() : Observable<Forecast>
}