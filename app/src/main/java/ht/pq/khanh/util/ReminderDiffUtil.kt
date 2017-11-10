package ht.pq.khanh.util

import android.support.v7.util.DiffUtil
import ht.pq.khanh.model.Reminder

/**
 * Created by khanh on 30/09/2017.
 */
class ReminderDiffUtil(private val oldList : MutableList<Reminder>, private val newList : MutableList<Reminder>) : DiffUtil.Callback(){
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean = oldList[oldItemPosition].title == newList[newItemPosition].title

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        if (oldItem.title == newItem.title && oldItem.dateTime == newItem.dateTime){
            return true
        }
        return false
    }
}