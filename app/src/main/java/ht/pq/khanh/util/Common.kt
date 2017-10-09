package ht.pq.khanh.util

import android.content.Context
import com.amulyakhare.textdrawable.util.ColorGenerator
import android.text.format.Time
import ht.pq.khanh.multitask.R
import java.text.SimpleDateFormat


/**
 * Created by khanhpq on 9/9/17.
 */

object Common {
    val URL = "http://openweathermap.org/data/2.5/weather?q=London,uk&appid=b1b15e88fa797225412429c1c50c122a1"
    val URL_FORECAST = "http://openweathermap.org/data/2.5/forecast?lat=35&lon=139&appid=b1b15e88fa797225412429c1c50c122a1"
    val URL_WEATHER_BASE = "http://openweathermap.org/"
    val URl_ICON = "http://openweathermap.org/img/w/"
    val DATE_FORMAT = "yyyyMMdd"
    fun randomColor() : Int{
        return ColorGenerator.MATERIAL.randomColor
    }

    fun getToolbarHeight(context: Context): Int {
        val styledAttributes = context.theme.obtainStyledAttributes(
                intArrayOf(R.attr.actionBarSize))
        val toolbarHeight = styledAttributes.getDimension(0, 0f).toInt()
        styledAttributes.recycle()

        return toolbarHeight
    }

    fun getFriendlyDayString(context: Context, dateInMillis: Long, displayLongToday: Boolean): String {
        // The day string for forecast uses the following logic:
        // For today: "Today, June 8"
        // For tomorrow:  "Tomorrow"
        // For the next 5 days: "Wednesday" (just the day name)
        // For all days after that: "Mon Jun 8"

        val time = Time()
        time.setToNow()
        val currentTime = System.currentTimeMillis()
        val julianDay = Time.getJulianDay(dateInMillis, time.gmtoff)
        val currentJulianDay = Time.getJulianDay(currentTime, time.gmtoff)

        // If the date we're building the String for is today's date, the format
        // is "Today, June 24"
        if (displayLongToday && julianDay == currentJulianDay) {
            val today = "Today"
            val formatId = R.string.format_full_friendly_date
            return String.format(context.getString(
                    formatId,
                    today,
                    getFormattedMonthDay(dateInMillis)))
        } else if (julianDay < currentJulianDay + 7) {
            // If the input date is less than a week in the future, just return the day name.
            return getDayName(dateInMillis)
        } else {
            // Otherwise, use the form "Mon Jun 3"
            val shortenedDateFormat = SimpleDateFormat("EEE MMM dd")
            return shortenedDateFormat.format(dateInMillis)
        }
    }

    fun getDayName(dateInMillis: Long): String {
        // If the date is today, return the localized version of "Today" instead of the actual
        // day name.

        val t = Time()
        t.setToNow()
        val julianDay = Time.getJulianDay(dateInMillis, t.gmtoff)
        val currentJulianDay = Time.getJulianDay(System.currentTimeMillis(), t.gmtoff)
        if (julianDay == currentJulianDay) {
            return "Today"
        } else if (julianDay == currentJulianDay + 1) {
            return "Tomorrow"
        } else {
            val time = Time()
            time.setToNow()
            // Otherwise, the format is just the day of the week (e.g "Wednesday".
            val dayFormat = SimpleDateFormat("EEEE")
            return dayFormat.format(dateInMillis)
        }
    }

    fun getFormattedMonthDay(dateInMillis: Long): String {
        val time = Time()
        time.setToNow()
        val dbDateFormat = SimpleDateFormat(DATE_FORMAT)
        val monthDayFormat = SimpleDateFormat("MMMM dd")
        return monthDayFormat.format(dateInMillis)
    }
}
