package ht.pq.khanh.task.reminder

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.support.constraint.ConstraintLayout
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.amulyakhare.textdrawable.TextDrawable
import ht.pq.khanh.extension.inflateLayout
import ht.pq.khanh.helper.ItemTouchHelperAdapter
import ht.pq.khanh.helper.ItemTouchHelperViewHolder
import ht.pq.khanh.model.Reminder
import ht.pq.khanh.multitask.R
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by khanhpq on 9/29/17.
 */
class ReminderAdapter(private val context: Context, private val listRemind: MutableList<Reminder>) : RecyclerView.Adapter<ReminderAdapter.ReminderHolder>(), ItemTouchHelperAdapter {
    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(listRemind, i, i + 1)
            }
        }else{
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(listRemind, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)

    }

    override fun onItemDismiss(position: Int) {
        longListener?.onLongClick(position)
    }

    private val DATE_FORMAT = "MMM, dd yyyy"
    private val TIME_FORMAT = "hh:mm a"
    private var listener: OnAlterItemRecyclerView? = null
    private var longListener: OnLongRclItemClick? = null
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ReminderHolder {
        val view = parent!!.inflateLayout(R.layout.item_reminder)
        return ReminderHolder(view)
    }

    override fun onBindViewHolder(holder: ReminderHolder, position: Int) {
        val remind = listRemind[position]
        holder.tvMessage.text = remind.message
        holder.tvTitle.text = remind.title
        val simpleDateFormat = SimpleDateFormat(DATE_FORMAT)
        val textTime = simpleDateFormat.format(remind.timeHour)
        val simpleTimeFormat = SimpleDateFormat(TIME_FORMAT)
        val textDate = simpleTimeFormat.format(remind.timeDay)
        holder.tvDateTime.text = textDate
        holder.tvTimeHour.text = textTime
        val myDrawable = TextDrawable.builder().beginConfig()
                .textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .toUpperCase()
                .endConfig()
                .buildRound(remind.title.substring(0, 1), remind.color)
        holder.imgText.setImageDrawable(myDrawable)
    }

    override fun getItemCount(): Int = listRemind.size

//    fun loadChangeList(reminders: MutableList<Reminder>) {
//        val remindDiff = ReminderDiffUtil(listRemind, reminders)
//        val diffResult = DiffUtil.calculateDiff(remindDiff)
//        listRemind.clear()
//        listRemind.addAll(reminders)
//        diffResult.dispatchUpdatesTo(this)
//    }

    fun setOnChangeItem(callback: OnAlterItemRecyclerView?) {
        listener = callback
    }

    fun setOnLongClickListener(callback: OnLongRclItemClick?) {
        this.longListener = callback
    }

    inner class ReminderHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ItemTouchHelperViewHolder {
        override fun onItemSelected(context: Context?) {
            itemContain.setBackgroundColor(ContextCompat.getColor(context, R.color.blue_grey))
        }

        override fun onItemClear(context: Context?) {
            itemContain.setBackgroundColor(0)
        }

        @BindView(R.id.imgText)
        lateinit var imgText: ImageView
        @BindView(R.id.tv_date)
        lateinit var tvDateTime: TextView
        @BindView(R.id.tvTitle)
        lateinit var tvTitle: TextView
        @BindView(R.id.tvMessage)
        lateinit var tvMessage: TextView
        @BindView(R.id.tv_time)
        lateinit var tvTimeHour: TextView
        @BindView(R.id.item_container)
        lateinit var itemContain: ConstraintLayout

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

    interface OnLongRclItemClick {
        fun onLongClick(position: Int)
    }
}