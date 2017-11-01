package ht.pq.khanh.multitask

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.View
import ht.pq.khanh.extension.showToast
import ht.pq.khanh.multitask.forecast.ForecastFragment
import ht.pq.khanh.multitask.weather.WeatherFragment
import ht.pq.khanh.task.reminder.ReminderFragment

class MainActivity : AppCompatActivity() {
    private var isBackPress = false
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                navigateToFragment(ReminderFragment())
            }
            R.id.navigation_dashboard -> {
                navigateToFragment(WeatherFragment())
            }
            R.id.navigation_notifications -> {
                navigateToFragment(ForecastFragment())
            }
        }
        false
    }

    private val exitHandler = { isBackPress = false }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navigation = findViewById<View>(R.id.navigation) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun navigateToFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        if (fragment.isAdded) {
            transaction.show(fragment)
        } else {
            transaction.replace(R.id.container, fragment)
        }
        transaction.commit()
    }

    override fun onBackPressed() {
        if (isTaskRoot || isBackPress) {
            super.onBackPressed()
            return
        }
        isBackPress = true
        this.showToast("press back to exit")
        Handler().postDelayed(exitHandler, 1500)
    }
}
