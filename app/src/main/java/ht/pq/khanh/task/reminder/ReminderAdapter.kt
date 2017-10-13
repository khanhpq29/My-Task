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
import ht.pq.khanh.model.Reminder
import ht.pq.khanh.multitask.R
import ht.pq.khanh.util.ReminderDiffUtil
import java.text.SimpleDateFormat

/**
 * Created by khanhpq on 9/29/17.
 */
class ReminderAdapter(private val listRemind: MutableList<Reminder>) : RecyclerView.Adapter<ReminderAdapter.ReminderHolder>() {
    private val DATE_FORMAT = "yyyy/mm/dd"
    private val TIME_FORMAT = "hh:mm a"
    private var listener: OnAlterItemRecyclerView? = null
    private var longListener : OnLongRclItemClick? = null
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
    fun setOnLongClickListener(callback: OnLongRclItemClick?){
        this.longListener = callback
    }
    inner class ReminderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.imgText)
        lateinit var imgText: ImageView
        @BindView(R.id.tv_date)
        lateinit var tvDateTime: TextView
        @BindView(R.id.tvTitle)
        lateinit var tvTitle: TextView
        @BindView(R.id.tvMessage)
        lateinit var tvMessage: TextView
        @BindView(R.id.tv_time)
        lateinit var tvTimeHour : TextView

        init {
            ButterKnife.bind(this, itemView)
            itemView.setOnClickListener {
                val position = adapterPosition
                listener?.onChangeItem(position)
            }
            itemView.setOnLongClickListener{
                val position = adapterPosition
                longListener?.onLongClick(position)
                true
            }
        }
    }

    interface OnAlterItemRecyclerView {
        fun onChangeItem(position: Int)
    }
    interface OnLongRclItemClick{
        fun onLongClick(position: Int)
    }
    fun fixwrong(){
        
    }
}