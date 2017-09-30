package ht.pq.khanh.multitask.weather

import ht.pq.khanh.api.current.CurrentWeather

/**
 * Created by khanhpq on 9/28/17.
 */
interface WeatherContact {
    interface View{
        fun showWeather(current : CurrentWeather)
        fun showError()
    }
    interface Presenter{
        fun getTodayWeather()
    }
}