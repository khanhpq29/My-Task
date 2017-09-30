package ht.pq.khanh.task.alarm

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AlarmHolder {
        val view = parent!!.inflateLayout(R.layout.item_alarm)
        return AlarmHolder(view)
    }

    override fun getItemCount(): Int {
        return alarmList.size
    }

    private fun setDay(holder: AlarmHolder){
        val tm = TextDrawable.builder().buildRoundRect("M", Color.BLUE, 10)
        holder.imgMon.setImageDrawable(tm)
        val tt = TextDrawable.builder().buildRoundRect("M", Color.BLUE, 10)
        holder.imgTues.setImageDrawable(tt)
        val tw = TextDrawable.builder().buildRoundRect("M", Color.BLUE, 10)
        holder.imgWed.setImageDrawable(tw)
        val tth = TextDrawable.builder().buildRoundRect("M", Color.BLUE, 10)
        holder.imgThu.setImageDrawable(tth)
        val tf = TextDrawable.builder().buildRoundRect("M", Color.BLUE, 10)
        holder.imgFri.setImageDrawable(tf)
        val ts = TextDrawable.builder().buildRoundRect("M", Color.BLUE, 10)
        holder.imgSat.setImageDrawable(ts)
        val tsu = TextDrawable.builder().buildRoundRect("M", Color.BLUE, 10)
        holder.imgSun.setImageDrawable(tsu)
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
        init {
            ButterKnife.bind(this, itemView)
        }
    }

}