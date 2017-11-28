package ht.pq.khanh.multitask.forecast

import ht.pq.khanh.api.forecast.Forecast
import ht.pq.khanh.api.repository.ForecastRepository
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by khanhpq on 9/28/17.
 */
class ForecastPresenter(private val view: ForecastContract.View, private val disposal: CompositeDisposable) : ForecastContract.Presenter {
    private val repository: ForecastRepository by lazy { ForecastRepository() }

    override fun networkChange() {
        view.changeNetworkState()
    }

    override fun fetchData(location: String) {
        disposal.add(repository.getForecast(location)
                .doOnNext { view.showProgressDialog() }
                .doAfterNext { view.hideProgressDialog() }
                .single(Forecast())
                .subscribe({ forecast: Forecast ->
                    view.addForecast(forecast)
                }, { throwable: Throwable ->
                    view.showError(throwable)
                }))
    }
}