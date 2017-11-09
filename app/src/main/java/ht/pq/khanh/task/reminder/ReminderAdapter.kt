package ht.pq.khanh.task.reminder

import android.graphics.Color
import android.graphics.Typeface
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.amulyakhare.textdrawable.TextDrawable
import ht.pq.khanh.extension.inflateLayout
import ht.pq.khanh.extension.isHide
import ht.pq.khanh.model.Reminder
import ht.pq.khanh.multitask.R
import ht.pq.khanh.helper.ItemTouchHelperAdapter
import ht.pq.khanh.helper.ItemTouchViewholder
import ht.pq.khanh.util.ReminderDiffUtil
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by khanhpq on 9/29/17.
 */
class ReminderAdapter(private val listRemind: MutableList<Reminder>) : RecyclerView.Adapter<ReminderAdapter.ReminderHolder>(), ItemTouchHelperAdapter {
    private val simpleDateFormat by lazy { SimpleDateFormat(DATE_FORMAT) }
    private val DATE_FORMAT = "MMM, dd yyyy, hh:mm a"
    private var listener: OnAlterItemRecyclerView? = null
    private var delListener: OnDeleteItemListener? = null
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ReminderHolder {
        val view = parent!!.inflateLayout(R.layout.item_reminder)
        return ReminderHolder(view)
    }

    override fun onBindViewHolder(holder: ReminderHolder, position: Int) {
        val remind = listRemind[position]
        holder.tvTitle.text = remind.title
        if (remind.dateTime != null) {
            val textTime = simpleDateFormat.format(remind.dateTime)
            holder.tvTimeHour.text = textTime
        } else {
            holder.tvTimeHour.isHide()
        }
        val myDrawable = TextDrawable.builder().beginConfig()
                .textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .toUpperCase()
                .endConfig()
                .buildRound(remind.title.substring(0, 1), remind.color)
        holder.imgText.setImageDrawable(myDrawable)
    }

    override fun getItemCount(): Int = listRemind.size

    override fun onItemMove(fromPosition: Int, toPosition: Int): Boolean {
        Collections.swap(listRemind, fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onItemDissmiss(position: Int) {
        delListener?.onDeleteItem(position)
    }
    fun loadChangeList(reminders: MutableList<Reminder>) {
        val remindDiff = ReminderDiffUtil(listRemind, reminders)
        val diffResult = DiffUtil.calculateDiff(remindDiff)
        listRemind.clear()
        listRemind.addAll(reminders)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnChangeItem(callback: OnAlterItemRecyclerView?) {
        listener = callback
    }

    fun setOnDeleteItemListener(callback: OnDeleteItemListener) {
        this.delListener = callback
    }

    inner class ReminderHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ItemTouchViewholder {
        override fun onItemSelect() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }

        @BindView(R.id.imgText)
        lateinit var imgText: ImageView
        @BindView(R.id.tvTitle)
        lateinit var tvTitle: TextView
        @BindView(R.id.tv_time)
        lateinit var tvTimeHour: TextView

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener {
                val position = adapterPosition
                listener?.onChangeItem(position)
            }
        }
    }

    interface OnAlterItemRecyclerView {
        fun onChangeItem(position: Int)
    }

    interface OnDeleteItemListener {
        fun onDeleteItem(position: Int)
    }
}