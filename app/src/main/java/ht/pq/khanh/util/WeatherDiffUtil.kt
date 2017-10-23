package ht.pq.khanh.util

import android.support.v7.util.DiffUtil
import com.pawegio.kandroid.d
import ht.pq.khanh.api.forecast.List

/**
 * Created by khanhpq on 10/6/17.
 */
class WeatherDiffUtil(val oldList: MutableList<List>, val newList : MutableList<List>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition].dt == newList[newItemPosition].dt

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
//        if (oldItem.dt == newItem.dt && oldItem.dtTxt == newItem.dtTxt){
//            return true
//        }
        return false
    }
}