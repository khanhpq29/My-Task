package ht.pq.khanh.util

import android.support.v7.util.DiffUtil
import ht.pq.khanh.model.alarm.Alarm

/**
 * Created by khanhpq on 10/3/17.
 */
class AlarmDiffUtil(val oldList : MutableList<Alarm>, val newList : MutableList<Alarm>) : DiffUtil.Callback() {
    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        if (oldItem.time == newItem.time && oldItem.isActive == newItem.isActive){
            return true
        }
        return false
    }

    override fun getOldListSize(): Int = oldList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition].time == newList[newItemPosition].time
}