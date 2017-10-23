package ht.pq.khanh.multitask.forecast

import ht.pq.khanh.api.forecast.Forecast

/**
 * Created by khanhpq on 9/28/17.
 */
interface ForecastContract {
    interface View{
        fun addForecast(forecast : Forecast)
        fun showError(throwable: Throwable)
        fun showProgressDialog()
        fun hideProgressDialog()
    }
    interface Presenter{
        fun fetchData(location: String)
    }
}