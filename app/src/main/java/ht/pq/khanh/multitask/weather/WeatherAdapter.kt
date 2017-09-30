package ht.pq.khanh.multitask.weather

import android.content.Context
import android.database.Cursor
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import ht.pq.khanh.extension.inflateLayout
import ht.pq.khanh.multitask.R

/**
 * Created by khanhpq on 9/28/17.
 */
class WeatherAdapter(val context: Context, cursor: Cursor, val flag: Int) : CursorAdapter(context, cursor, flag) {

    private val VIEW_TYPE_COUNT = 2
    private val VIEW_TYPE_TODAY = 0
    private val VIEW_TYPE_FUTURE_DAY = 1
    private val mdataToday = true

    override fun newView(context: Context?, cursor: Cursor?, parent: ViewGroup?): View {
        val viewType = getItemViewType(cursor!!.position)
        var layoutId = -1
        when(viewType){
            VIEW_TYPE_TODAY ->
                    layoutId = R.layout.fragment_current_weather
            VIEW_TYPE_FUTURE_DAY ->
                    layoutId = R.layout.item_weather
        }
        val view = parent!!.inflateLayout(layoutId)
        val holder = ForecastHolder(view)
        view.tag = holder
        return view
    }

    override fun bindView(view: View?, context: Context?, cursor: Cursor?) {
        val holder = view?.tag
        val viewType = getItemViewType(cursor!!.position)
        when (viewType){

        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0 && mdataToday){
            return VIEW_TYPE_TODAY
        }else {
            return VIEW_TYPE_FUTURE_DAY
        }
    }

    class ForecastHolder(view: View){
        @BindView(R.id.list_item_icon)
        lateinit var iconView : ImageView
        @BindView(R.id.list_item_date_textview)
        lateinit var dateView : TextView
        @BindView(R.id.tv_item_forecast)
        lateinit var descriptionView : TextView
        @BindView(R.id.tv_hight_temp)
        lateinit var highTempView : TextView
        @BindView(R.id.tv_low_temp)
        lateinit var lowTempView : TextView
        init {
            ButterKnife.bind(this, view)
        }
    }
}