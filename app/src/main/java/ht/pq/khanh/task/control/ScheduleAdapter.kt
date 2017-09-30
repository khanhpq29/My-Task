package ht.pq.khanh.task.control

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

import ht.pq.khanh.task.alarm.AlarmFragment
import ht.pq.khanh.task.sleepawake.SleepAwakeFragment

/**
 * Created by khanhpq on 9/25/17.
 */

class ScheduleAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
    private val tabTitles = arrayOf("alarm", "schedules")

    override fun getCount(): Int {
        return tabTitles.size
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return AlarmFragment()
            else -> return SleepAwakeFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence {
        return tabTitles[position]
    }
}
