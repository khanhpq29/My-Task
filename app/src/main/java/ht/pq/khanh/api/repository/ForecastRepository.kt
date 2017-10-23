package ht.pq.khanh.api.repository

import ht.pq.khanh.api.ApiManager
import ht.pq.khanh.api.forecast.Forecast
import ht.pq.khanh.api.service.ForecastService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by khanhpq on 9/28/17.
 */
class ForecastRepository {
    val forecastService : ForecastService = ApiManager.createApiService(ForecastService::class.java)
    fun getForecast(location : String) : Observable<Forecast>{
        return forecastService.getForecastWeather(location, "b1b15e88fa797225412429c1c50c122a1").observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }
}