package ht.pq.khanh.multitask.weather

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import ht.pq.khanh.api.current.CurrentWeather
import ht.pq.khanh.api.forecast.List
import ht.pq.khanh.extension.inflateLayout
import ht.pq.khanh.extension.loadImage

import ht.pq.khanh.multitask.R
import ht.pq.khanh.util.Common
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by khanhpq on 9/25/17.
 */

class WeatherFragment : Fragment() {

    @BindView(R.id.tvAdd)
    lateinit var tvAdd: TextView
    @BindView(R.id.tvDay)
    lateinit var tvDay: TextView
    @BindView(R.id.tvDescription)
    lateinit var tvDescription: TextView
    @BindView(R.id.imageIcon)
    lateinit var imgIcon: ImageView
    private lateinit var presenter: WeatherPresenter
    private var itemList : List? = null
    private val disposal: CompositeDisposable by lazy { CompositeDisposable() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            itemList = arguments.getParcelable<List>("fragment_key")
        }
    }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = container!!.inflateLayout(R.layout.fragment_weather)
        ButterKnife.bind(this, view)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initLayout()
    }

    private fun initLayout() {
        tvAdd.text = "London"
        tvDay.text = "21/9/2017"
        imgIcon.loadImage("${Common.URl_ICON}${itemList!!.weather[0].icon}.png")
        tvDescription.text = itemList!!.weather[0].description
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.weather_menu, menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.wSearch){

        }
        return super.onOptionsItemSelected(item)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        disposal.clear()
    }
}
