package ht.pq.khanh.util

import android.content.Context
import android.graphics.Color
import com.amulyakhare.textdrawable.util.ColorGenerator
import java.util.*
import android.content.res.TypedArray
import ht.pq.khanh.multitask.R


/**
 * Created by khanhpq on 9/9/17.
 */

object Common {
    val URL = "http://openweathermap.org/data/2.5/weather?q=London,uk&appid=b1b15e88fa797225412429c1c50c122a1"
    val URL_FORECAST = "http://openweathermap.org/data/2.5/forecast?lat=35&lon=139&appid=b1b15e88fa797225412429c1c50c122a1"
    val URL_WEATHER_BASE = "http://openweathermap.org/"
    val URl_ICON = "http://openweathermap.org/img/w/"
    fun randomColor() : Int{
        val colorGenerate = ColorGenerator.MATERIAL.randomColor
        return colorGenerate
    }

    fun getToolbarHeight(context: Context): Int {
        val styledAttributes = context.theme.obtainStyledAttributes(
                intArrayOf(R.attr.actionBarSize))
        val toolbarHeight = styledAttributes.getDimension(0, 0f).toInt()
        styledAttributes.recycle()

        return toolbarHeight
    }
}
