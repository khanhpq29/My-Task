package ht.pq.khanh.multitask.setting

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import android.view.View
import ht.pq.khanh.multitask.R

/**
 * Created by khanhpq on 10/5/17.
 */
class SettingFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.settings)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}