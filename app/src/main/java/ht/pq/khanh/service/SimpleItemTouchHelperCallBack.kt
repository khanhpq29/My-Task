package ht.pq.khanh.service

import android.graphics.Canvas
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper


/**
 * Created by khanhpq on 10/20/17.
 */
class SimpleItemTouchHelperCallBack(private var itemTouchHelperAdapter: ItemTouchHelperAdapter) : ItemTouchHelper.Callback() {
    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }

    override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.END
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(recyclerView: RecyclerView?, source: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
        if (source?.itemViewType != target?.itemViewType) {
            return false
        }
        itemTouchHelperAdapter.onItemMove(source?.adapterPosition!!, target?.adapterPosition!!)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
        itemTouchHelperAdapter.onItemDissmiss(viewHolder?.adapterPosition!!)
    }

    override fun onChildDraw(c: Canvas?, recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val alpha = 1.0f - Math.abs(dX) / viewHolder.itemView.width
            viewHolder.itemView.alpha = alpha
            viewHolder.itemView.translationX = dX
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        }
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (viewHolder is ItemTouchViewholder) {
                val itemViewHolder = viewHolder as ItemTouchViewholder
                itemViewHolder.onItemSelect()
            }
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?) {
        super.clearView(recyclerView, viewHolder)
        viewHolder?.itemView?.alpha = 1.0f
        if (viewHolder is ItemTouchViewholder) {
            val itemViewHolder = viewHolder as ItemTouchViewholder
            itemViewHolder.onItemClear()
        }
    }
}