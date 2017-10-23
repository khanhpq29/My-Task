package ht.pq.khanh.setting

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.preference.CheckBoxPreference
import android.support.v7.preference.EditTextPreference
import android.support.v7.preference.PreferenceFragmentCompat
import android.support.v7.preference.SwitchPreferenceCompat
import android.view.View
import ht.pq.khanh.multitask.R
import ht.pq.khanh.util.Common

/**
 * Created by khanhpq on 10/5/17.
 */
class SettingFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.pref_general, rootKey)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val locationPref = activity.getSharedPreferences(Common.LOCATION_PREFERENCE, Context.MODE_PRIVATE)
        val locationValue = locationPref.getString("Location_name", "")
        val location = findPreference(resources.getString(R.string.location_preference)) as EditTextPreference
        location.summary = locationValue
    }
    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            Common.NIGHT_PREFERENCE_KEY -> {
                val sharedPreference = activity.getSharedPreferences(Common.THEME_PREFERENCES, Context.MODE_PRIVATE)
                val themeEditor = sharedPreference.edit()
                themeEditor.putBoolean(Common.RECREATE_ACTIVITY, true)
                val switchMode = findPreference(Common.NIGHT_PREFERENCE_KEY) as SwitchPreferenceCompat
                if (switchMode.isEnabled) {
                    themeEditor.putString(Common.THEME_SAVED, Common.DARKTHEME)
                } else {
                    themeEditor.putString(Common.THEME_SAVED, Common.LIGHTTHEME)
                }
                themeEditor.commit()
                activity.recreate()
            }
            Common.TEMP_PREFERENCE_KEY -> {
                val sharePref = activity.getSharedPreferences(Common.TEMP_PREFERENCE, Context.MODE_PRIVATE)
                val tempEditor = sharePref.edit()
                val checkTemp = findPreference(Common.TEMP_PREFERENCE_KEY) as CheckBoxPreference
                if (checkTemp.isChecked) {
                    tempEditor.putString(Common.TEMP_TYPE, Common.CELSIUS_TYPE)
                } else {
                    tempEditor.putString(Common.TEMP_TYPE, Common.FAHRENHEIT_TYPE)
                }
                tempEditor.commit()
            }
            resources.getString(R.string.location_preference) -> {
                val locationPref = activity.getSharedPreferences(Common.LOCATION_PREFERENCE, Context.MODE_PRIVATE)
                val locationEditor = locationPref.edit()
                val location = findPreference(resources.getString(R.string.location_preference)) as EditTextPreference
                val locationText = location.text.toString()
                location.summary = locationText
                locationEditor.putString("Location_name", locationText)
                locationEditor.commit()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this)
    }

}