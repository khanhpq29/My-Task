package ht.pq.khanh.task.alarm

import android.graphics.Color
import android.graphics.Typeface
import android.support.constraint.ConstraintLayout
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SwitchCompat
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
import ht.pq.khanh.util.AlarmDiffUtil
import java.text.SimpleDateFormat

/**
 * Created by khanhpq on 9/25/17.
 */
class AlarmAdapter(val alarmList: MutableList<Alarm>) : RecyclerView.Adapter<AlarmAdapter.AlarmHolder>() {
    private var isMonOn = true
    private var isTueOn = true
    private var isWedOn = true
    private var isThuOn = true
    private var isFriON = true
    private var isSatOn = true
    private var isSunOn = true
    private var callBack: AlarmCallback? = null
    private val timeFormatString = "hh:mm a"
    override fun onBindViewHolder(holder: AlarmHolder, position: Int) {
        setDay(holder)
        val alarm = alarmList[position]
        val timeFormat = SimpleDateFormat(timeFormatString)
        holder.tvTimeAlarm.text = "${timeFormat.format(alarm.time)}"
        holder.switchAlarm.isChecked = !alarm.isActive
        holder.cbVibrate.isChecked = !alarm.isVibrate
        handleListener(holder)
        changeDayAlarm(holder)
        holder.tvTimeAlarm.setOnClickListener {
            callBack?.onChangeTime(alarm)
        }
    }

    private fun handleListener(holder: AlarmHolder) {

    }
    fun setOnChangeDate(listener: AlarmCallback?){
        callBack = listener
    }
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AlarmHolder {
        val view = parent!!.inflateLayout(R.layout.item_alarm)
        return AlarmHolder(view)
    }

    override fun getItemCount(): Int {
        return alarmList.size
    }

    private fun setDay(holder: AlarmHolder) {
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

    private fun setTextDrawable(day: String, colorText: Int = Color.BLUE, colorRound: Int = Color.WHITE): TextDrawable {
        return TextDrawable.builder().beginConfig()
                .textColor(colorText)
                .useFont(Typeface.DEFAULT)
                .toUpperCase()
                .endConfig()
                .buildRound(day, colorRound)
    }

    private fun changeDayAlarm(holder: AlarmHolder) {
        holder.imgMon.setOnClickListener {
            if (isMonOn) {
                val textDrawable = setTextDrawable("M", Color.WHITE, Color.BLUE)
                holder.imgMon.setImageDrawable(textDrawable)
                isMonOn = false
            } else {
                setDay(holder)
                isMonOn = true
            }
        }
        holder.imgTues.setOnClickListener {
            if (isTueOn) {
                val textDrawable = setTextDrawable("T", Color.WHITE, Color.BLUE)
                holder.imgTues.setImageDrawable(textDrawable)
                isTueOn = false
            } else {
                setDay(holder)
                isTueOn = true
            }
        }
        holder.imgWed.setOnClickListener {
            isWedOn = if (isWedOn) {
                val textDrawable = setTextDrawable("W", Color.WHITE, Color.BLUE)
                holder.imgWed.setImageDrawable(textDrawable)
                false
            } else {
                setDay(holder)
                true
            }
        }
        holder.imgThu.setOnClickListener {
            if (isThuOn) {
                val textDrawable = setTextDrawable("T", Color.WHITE, Color.BLUE)
                holder.imgThu.setImageDrawable(textDrawable)
                isThuOn = false
            } else {
                setDay(holder)
                isThuOn = true
            }
        }
        holder.imgFri.setOnClickListener {
            isFriON = if (isFriON) {
                val textDrawable = setTextDrawable("F", Color.WHITE, Color.BLUE)
                holder.imgFri.setImageDrawable(textDrawable)
                false
            } else {
                setDay(holder)
                true
            }
        }
        holder.imgSat.setOnClickListener {
            if (isSatOn) {
                val textDrawable = setTextDrawable("S", Color.WHITE, Color.BLUE)
                holder.imgSat.setImageDrawable(textDrawable)
                isSatOn = false
            } else {
                setDay(holder)
                isSatOn = true
            }
        }
        holder.imgSun.setOnClickListener {
            if (isSunOn) {
                val textDrawable = setTextDrawable("S", Color.WHITE, Color.BLUE)
                holder.imgSun.setImageDrawable(textDrawable)
                isSunOn = false
            } else {
                setDay(holder)
                isSunOn = true
            }
        }
    }
    fun addChange(alarms : MutableList<Alarm>){
        val diffCallback = AlarmDiffUtil(alarmList, alarms)
        val diffAlarm = DiffUtil.calculateDiff(diffCallback)
        alarmList.clear()
        alarmList.addAll(alarms)
        diffAlarm.dispatchUpdatesTo(this)
    }

    class AlarmHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @BindView(R.id.imageView2)
        lateinit var imgMon: ImageView
        @BindView(R.id.imageView3)
        lateinit var imgTues: ImageView
        @BindView(R.id.imageView4)
        lateinit var imgWed: ImageView
        @BindView(R.id.imageView5)
        lateinit var imgThu: ImageView
        @BindView(R.id.imageView6)
        lateinit var imgFri: ImageView
        @BindView(R.id.imageView7)
        lateinit var imgSat: ImageView
        @BindView(R.id.imageView8)
        lateinit var imgSun: ImageView
        @BindView(R.id.tvRingAlarm)
        lateinit var tvRingAlarm: TextView
        @BindView(R.id.cbVibrate)
        lateinit var cbVibrate: CheckBox
        @BindView(R.id.tvTimeAlarm)
        lateinit var tvTimeAlarm: TextView
        @BindView(R.id.constraintLayout)
        lateinit var changeRington: ConstraintLayout
        @BindView(R.id.switch_alarm)
        lateinit var switchAlarm: SwitchCompat

        init {
            ButterKnife.bind(this, itemView)
        }
    }

}