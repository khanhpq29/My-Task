package ht.pq.khanh.multitask.forecast

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.pawegio.kandroid.IntentFor
import ht.pq.khanh.api.forecast.Forecast
import ht.pq.khanh.api.forecast.List
import ht.pq.khanh.extension.inflateLayout
import ht.pq.khanh.multitask.R
import ht.pq.khanh.multitask.weather.WeatherDetailActivity
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by khanhpq on 9/25/17.
 */

class ForecastFragment : Fragment(), ForecastContract.View, ForecastAdapter.OnWeatherItemClickListener{
    @BindView(R.id.listForecast)
    lateinit var recyclerForecast : RecyclerView
    @BindView(R.id.no_layout)
    lateinit var noLayout : RelativeLayout
    @BindView(R.id.swipe_view)
    lateinit var swipeLayout : SwipeRefreshLayout
    private var forecastAdapter : ForecastAdapter? = null
    private lateinit var presenter : ForecastPresenter
    private var listForecast : MutableList<List> = arrayListOf()
    private val disposal : CompositeDisposable by lazy { CompositeDisposable() }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = container!!.inflateLayout(R.layout.fragment_forecast)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipeLayout.isRefreshing = true
        forecastAdapter = ForecastAdapter(listForecast)
        presenter = ForecastPresenter(this, disposal)
        val itemRclDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        val linearManager = LinearLayoutManager(activity)
        presenter.fetchData()
        with(recyclerForecast){
            layoutManager = linearManager
            adapter = forecastAdapter
            addItemDecoration(itemRclDecorator)
            setHasFixedSize(true)
        }
        forecastAdapter?.setOnWeatherItemClickListener(this)
    }

    override fun addForecast(forecast: Forecast) {
        listForecast.addAll(forecast.list)
    }

    override fun showError(t :Throwable) {
        Log.e("error", t.toString())
        swipeLayout.isRefreshing = false
    }
    override fun loadForecastList() {
        forecastAdapter?.notifyDataSetChanged()
        swipeLayout.isRefreshing = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        swipeLayout.isRefreshing = false
        disposal.clear()
        disposal.dispose()
    }
    override fun onWeatherItemClick(position: Int) {
        val item = listForecast[position]
        val intent = IntentFor<WeatherDetailActivity>(activity)
        intent.putExtra("weather_detail", item)
        startActivity(intent)
    }
}
