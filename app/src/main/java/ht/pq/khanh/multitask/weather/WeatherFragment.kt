package ht.pq.khanh.multitask.weather

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import ht.pq.khanh.api.current.CurrentWeather
import ht.pq.khanh.extension.inflateLayout
import ht.pq.khanh.extension.loadImage

import ht.pq.khanh.multitask.R
import ht.pq.khanh.util.Common
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by khanhpq on 9/25/17.
 */

class WeatherFragment : Fragment(), WeatherContact.View {

    @BindView(R.id.tvAdd)
    lateinit var tvAdd: TextView
    @BindView(R.id.tvDay)
    lateinit var tvDay: TextView
    @BindView(R.id.tvDescription)
    lateinit var tvDescription: TextView
    @BindView(R.id.imageIcon)
    lateinit var imgIcon: ImageView
    private lateinit var presenter: WeatherPresenter
    private val disposal: CompositeDisposable by lazy { CompositeDisposable() }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = container!!.inflateLayout(R.layout.fragment_weather)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = WeatherPresenter(disposal, this)
        presenter.getTodayWeather()
    }
    override fun showWeather(current: CurrentWeather) {
        tvAdd.text = "London"
        tvDay.text = "21/9/2017"
        imgIcon.loadImage("${Common.URl_ICON}${current.weather[0].icon}.png")
        tvDescription.text = current.weather[0].description
    }

    override fun showError() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposal.clear()
    }
}
