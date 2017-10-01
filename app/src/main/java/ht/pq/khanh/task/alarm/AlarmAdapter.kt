package ht.pq.khanh.task.alarm

import android.graphics.Color
import android.graphics.Typeface
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.amulyakhare.textdrawable.TextDrawable
import ht.pq.khanh.extension.inflateLayout
import ht.pq.khanh.model.Alarm
import ht.pq.khanh.multitask.R

/**
 * Created by khanhpq on 9/25/17.
 */
class AlarmAdapter(val alarmList: List<Alarm> = arrayListOf()) : RecyclerView.Adapter<AlarmAdapter.AlarmHolder>(){
    override fun onBindViewHolder(holder: AlarmHolder?, position: Int) {
        setDay(holder!!)
        val alarm = alarmList[position]
        holder.tvTimeAlarm.text = "${alarm.hour}:${alarm.minute}"
        holder.imgMon.setOnClickListener({
            val tm = setTextDrawable("M", Color.WHITE, Color.BLUE)
            holder.imgMon.setImageDrawable(tm)
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AlarmHolder {
        val view = parent!!.inflateLayout(R.layout.item_alarm)
        return AlarmHolder(view)
    }

    override fun getItemCount(): Int {
        return alarmList.size
    }

    private fun setDay(holder: AlarmHolder){
        val tm = setTextDrawable("M")
        holder.imgMon.setImageDrawable(tm)
        val tt = setTextDrawable("T")
        holder.imgTues.setImageDrawable(tt)
        val tw = setTextDrawable("W")
        holder.imgWed.setImageDrawable(tw)
        val tth = setTextDrawable("T")
        holder.imgThu.setImageDrawable(tth)
        val tf = setTextDrawable("F")
        holder.imgFri.setImageDrawable(tf)
        val ts = setTextDrawable("S")
        holder.imgSat.setImageDrawable(ts)
        val tsu = setTextDrawable("S")
        holder.imgSun.setImageDrawable(tsu)
    }
    private fun setTextDrawable(day : String, colorText : Int = Color.BLUE, colorRound : Int = Color.WHITE) : TextDrawable{
        return TextDrawable.builder().beginConfig()
                .textColor(colorText)
                .useFont(Typeface.DEFAULT)
                .toUpperCase()
                .endConfig()
                .buildRound(day, colorRound)
    }
    class AlarmHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        @BindView(R.id.imageView2)
        lateinit var imgMon : ImageView
        @BindView(R.id.imageView3)
        lateinit var imgTues : ImageView
        @BindView(R.id.imageView4)
        lateinit var imgWed : ImageView
        @BindView(R.id.imageView5)
        lateinit var imgThu : ImageView
        @BindView(R.id.imageView6)
        lateinit var imgFri : ImageView
        @BindView(R.id.imageView7)
        lateinit var imgSat : ImageView
        @BindView(R.id.imageView8)
        lateinit var imgSun : ImageView
        @BindView(R.id.tvRingAlarm)
        lateinit var tvRingAlarm : TextView
        @BindView(R.id.cbVibrate)
        lateinit var cbVibrate : CheckBox
        @BindView(R.id.tvTimeAlarm)
        lateinit var tvTimeAlarm : TextView
        @BindView(R.id.constraintLayout)
        lateinit var changeRington : ConstraintLayout
        init {
            ButterKnife.bind(this, itemView)
        }
    }

}