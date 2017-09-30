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
    fun getForecast() : Observable<Forecast>{
        return forecastService.getForecastWeather().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }
}