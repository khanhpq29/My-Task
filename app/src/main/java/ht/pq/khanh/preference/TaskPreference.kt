package ht.pq.khanh.preference

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Created by khanhpq on 9/29/17.
 */
object TaskPreference {
    private val DESCRIPTION_KEY = "description"
    val PROPERTY_COLOR_PICKER = "color_selected"
    val PROPERTY_IS24H = "color_selected"

    fun getSharepreferenceManager(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun getDescription(context: Context): String {
        val sharepreference = getSharepreferenceManager(context)
        return sharepreference.getString(DESCRIPTION_KEY, "")
    }

    fun setSelectedColor(context: Context, color: Int) {
        val sharepreference = getSharepreferenceManager(context)
        sharepreference.edit()
                .putInt(PROPERTY_COLOR_PICKER, color)
                .apply()
    }

    fun getSelectedColor(context: Context): Int {
        val sharepreference = getSharepreferenceManager(context)
        return sharepreference.getInt(PROPERTY_COLOR_PICKER, 0)
    }
}