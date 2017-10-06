package ht.pq.khanh.multitask.forecast

import ht.pq.khanh.api.forecast.Forecast
import ht.pq.khanh.api.repository.ForecastRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by khanhpq on 9/28/17.
 */
class ForecastPresenter(val view: ForecastContract.View, val disposal: CompositeDisposable) : ForecastContract.Presenter {
    val repository: ForecastRepository by lazy { ForecastRepository() }
    override fun fetchData() {
        disposal.add(repository.getForecast().subscribe({ forecasts: Forecast ->
            view.addForecast(forecasts)
        }, {it
            view.showError(it)
        },{
            view.loadForecastList()
        })
        )
    }
}