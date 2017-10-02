package ht.pq.khanh.preference

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Created by khanhpq on 9/29/17.
 */
object WeatherPreference {
    private val DESCRIPTION_KEY = "description"
    fun getSharepreferenceManager(context: Context) : SharedPreferences{
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
    fun getDescription(context: Context) : String{
        val sharepreference = getSharepreferenceManager(context)
        return sharepreference.getString(DESCRIPTION_KEY, "")
    }
}