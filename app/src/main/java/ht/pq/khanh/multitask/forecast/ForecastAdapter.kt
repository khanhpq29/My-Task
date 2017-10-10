package ht.pq.khanh.multitask.forecast

import android.content.Context
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import ht.pq.khanh.api.forecast.List
import ht.pq.khanh.extension.inflateLayout
import ht.pq.khanh.extension.loadImage
import ht.pq.khanh.multitask.R
import ht.pq.khanh.util.Common
import ht.pq.khanh.util.WeatherDiffUtil

/**
 * Created by khanhpq on 9/29/17.
 */
class ForecastAdapter(private val context : Context, private val forecast: MutableList<List>) : RecyclerView.Adapter<ForecastAdapter.ForecastHolder>() {
    private var listener: OnWeatherItemClickListener? = null
    override fun onBindViewHolder(holder: ForecastHolder, position: Int) {
        val main = forecast[position].main
        val weather = forecast[position].weather

        holder.tvDescript.text = weather[0].description
        holder.tvDate.text = Common.getFriendlyDayString(context, forecast[position].dt, false)
        holder.tvHighTemp.text = "${main.tempMax}°"
        holder.tvLowTemp.text = "${main.tempMin}°"
        holder.imgIcon.loadImage("${Common.URl_ICON}${weather[0].icon}.png")

    }

    override fun getItemCount(): Int = forecast.size

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ForecastHolder {
        val view = parent!!.inflateLayout(R.layout.item_weather)
        return ForecastHolder(view)
    }

    fun setOnWeatherItemClickListener(callBack: OnWeatherItemClickListener?) {
        this.listener = callBack
    }

    fun loadChange(forecastList: MutableList<List>) {
        val diffCallback = WeatherDiffUtil(forecast, forecastList)
        val diffCal = DiffUtil.calculateDiff(diffCallback)
        forecast.clear()
        forecast.addAll(forecastList)
        diffCal.dispatchUpdatesTo(this)
    }

    inner class ForecastHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.tv_item_forecast)
        lateinit var tvDescript: TextView
        @BindView(R.id.list_item_icon)
        lateinit var imgIcon: ImageView
        @BindView(R.id.list_item_date_textview)
        lateinit var tvDate: TextView
        @BindView(R.id.tv_hight_temp)
        lateinit var tvHighTemp: TextView
        @BindView(R.id.tv_low_temp)
        lateinit var tvLowTemp: TextView

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener {
                listener?.onWeatherItemClick(adapterPosition)
            }
        }
    }

    interface OnWeatherItemClickListener {
        fun onWeatherItemClick(position: Int)
    }
}