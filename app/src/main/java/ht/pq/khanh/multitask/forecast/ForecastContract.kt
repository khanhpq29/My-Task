package ht.pq.khanh.multitask.forecast

import ht.pq.khanh.api.forecast.Forecast

/**
 * Created by khanhpq on 9/28/17.
 */
interface ForecastContract {
    interface View{
        fun showForecast(forecast : Forecast)
        fun loadForecast()
        fun showError(throwable: Throwable)
    }
    interface Presenter{
        fun fetchData()
    }
}