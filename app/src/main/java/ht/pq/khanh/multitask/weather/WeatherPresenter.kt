package ht.pq.khanh.multitask.weather

import ht.pq.khanh.api.repository.WeatherRepository
import ht.pq.khanh.multitask.weather.WeatherContact
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by khanhpq on 9/28/17.
 */
class WeatherPresenter(var compositeDisposable: CompositeDisposable, val view: WeatherContact.View) : WeatherContact.Presenter {
    val repository: WeatherRepository by lazy { WeatherRepository() }
    override fun getTodayWeather() {
        compositeDisposable.add(repository.getCurrentWeather().subscribe({ current ->
            view.showWeather(current)
        }, {
            view.showError()
        })
        )
    }

}