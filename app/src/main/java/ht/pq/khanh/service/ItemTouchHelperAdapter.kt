package ht.pq.khanh.service

/**
 * Created by khanhpq on 10/20/17.
 */
interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int) : Boolean
    fun onItemDissmiss(position: Int)
}