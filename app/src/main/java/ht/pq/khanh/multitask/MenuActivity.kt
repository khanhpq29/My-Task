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
import ht.pq.khanh.TaskApplication
import ht.pq.khanh.dialog.TimePickerDialogFragment
import ht.pq.khanh.multitask.forecast.ForecastFragment
import ht.pq.khanh.multitask.weather.WeatherFragment
import ht.pq.khanh.task.alarm.AlarmCallback
import ht.pq.khanh.task.alarm.AlarmFragment
import ht.pq.khanh.task.reminder.ReminderFragment
import ht.pq.khanh.task.sleepawake.SleepAwakeFragment


class MenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, AlarmCallback, TimePickerDialog.OnTimeSetListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
        val item = navigationView.menu.getItem(0)
        onNavigationItemSelected(item)
    }

    override fun onBackPressed() {
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
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
            R.id.nav_camera ->
                navigateToFragment(ReminderFragment())
            R.id.nav_gallery ->
                navigateToFragment(WeatherFragment())
            R.id.nav_slideshow ->
                navigateToFragment(ForecastFragment())
            R.id.nav_manage ->
                navigateToFragment(AlarmFragment())
            else ->
                navigateToFragment(SleepAwakeFragment())
        }
        item.isChecked = true
        val drawer = findViewById(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {

    }

    override fun onChangeTime() {
        val timeDialog = TimePickerDialogFragment()
        timeDialog.show(supportFragmentManager, "time picker")
    }

    override fun onDestroy() {
        super.onDestroy()
        val ref = TaskApplication().getRefWatcher(this)
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
