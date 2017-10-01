package ht.pq.khanh.task.reminder

import android.graphics.Color
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
import android.graphics.Typeface

/**
 * Created by khanhpq on 9/29/17.
 */
class ReminderAdapter(val listRemind : MutableList<Reminder>) : RecyclerView.Adapter<ReminderAdapter.ReminderHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ReminderHolder {
        val view = parent!!.inflateLayout(R.layout.item_reminder)
        return ReminderHolder(view)
    }

    override fun onBindViewHolder(holder: ReminderHolder, position: Int) {
        val remind = listRemind[position]
        holder.tvMessage.text = remind.message
        holder.tvTitle.text = remind.title
        val myDrawable = TextDrawable.builder().beginConfig()
                .textColor(Color.WHITE)
                .useFont(Typeface.DEFAULT)
                .toUpperCase()
                .endConfig()
                .buildRound(remind.title.substring(0, 1), Color.BLUE)
        holder.imgText.setImageDrawable(myDrawable)
    }

    override fun getItemCount(): Int = listRemind.size

    class ReminderHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.imgText)
        lateinit var imgText : ImageView
        @BindView(R.id.tv_date)
        lateinit var tvTime : TextView
        @BindView(R.id.tvTitle)
        lateinit var tvTitle : TextView
        @BindView(R.id.tvMessage)
        lateinit var tvMessage : TextView
        init {
            ButterKnife.bind(this, itemView)
        }
    }
}