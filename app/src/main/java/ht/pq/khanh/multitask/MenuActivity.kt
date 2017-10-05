package ht.pq.khanh.multitask

import android.app.TimePickerDialog
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.TimePicker
import butterknife.BindView
import butterknife.ButterKnife
import ht.pq.khanh.TaskApplication
import ht.pq.khanh.multitask.about.AboutFragment
import ht.pq.khanh.multitask.forecast.ForecastFragment
import ht.pq.khanh.multitask.setting.SettingFragment
import ht.pq.khanh.task.alarm.AlarmFragment
import ht.pq.khanh.task.reminder.ReminderFragment
import ht.pq.khanh.task.sleepawake.SleepAwakeFragment


class MenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, TimePickerDialog.OnTimeSetListener {
    @BindView(R.id.toolbar)
    lateinit var toolbar : Toolbar
    @BindView(R.id.drawer_layout)
    lateinit var drawer : DrawerLayout
    @BindView(R.id.nav_view)
    lateinit var navigationView : NavigationView
    private lateinit var title : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        ButterKnife.bind(this)
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)
        val item = navigationView.menu.getItem(0)
        onNavigationItemSelected(item)
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId
        when (id) {
            R.id.nav_reminder ->{
                navigateToFragment(ReminderFragment())
                title = "Reminder"
            }
            R.id.nav_schedule ->{
                navigateToFragment(SleepAwakeFragment())
                title = "Schedule"
            }
            R.id.nav_forecast ->{
                navigateToFragment(ForecastFragment())
                title = "Weather Forecast"
            }
            R.id.nav_alarm ->{
                navigateToFragment(AlarmFragment())
                title = "Alarm"
            }
            R.id.nav_about ->{
                navigateToFragment(AboutFragment())
                title = "About"
            }
            R.id.nav_setting ->{
                navigateToFragment(SettingFragment())
                title = "Setting"
            }
            else -> {
                navigateToFragment(SleepAwakeFragment())
                title = "Other"
            }
        }
        supportActionBar?.title = title
        item.isChecked = true
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

    }

    override fun onDestroy() {
        super.onDestroy()
        val ref = TaskApplication().getRefWatcher(applicationContext)
        ref.watch(ref)
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

}
