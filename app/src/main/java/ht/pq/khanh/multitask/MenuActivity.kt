package ht.pq.khanh.multitask

import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import butterknife.BindView
import butterknife.ButterKnife
import com.pawegio.kandroid.d
import ht.pq.khanh.TaskApplication
import ht.pq.khanh.broadcast.ConnectivityReceiver
import ht.pq.khanh.extension.setUpTheme
import ht.pq.khanh.multitask.forecast.ForecastFragment
import ht.pq.khanh.multitask.paint.PaintFragment
import ht.pq.khanh.multitask.radio.RadioFragment
import ht.pq.khanh.setting.SettingFragment
import ht.pq.khanh.task.alarm.AlarmFragment
import ht.pq.khanh.task.reminder.ReminderFragment
import ht.pq.khanh.task.sleepawake.AwakeFragment

class MenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.drawer_layout)
    lateinit var drawer: DrawerLayout
    @BindView(R.id.nav_view)
    lateinit var navigationView: NavigationView
    private lateinit var title: String
    private var currentId = R.id.nav_reminder
    private lateinit var connectingReceiver: ConnectivityReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        this.setUpTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        ButterKnife.bind(this)
        setSupportActionBar(toolbar)
        connectingReceiver = ConnectivityReceiver()

        supportFragmentManager.beginTransaction().replace(R.id.container, ReminderFragment()).commit()
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)
//        val item = navigationView.menu.getItem(0)
//        onNavigationItemSelected(item)
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
        if (currentId == id) {
            drawer.closeDrawer(GravityCompat.START)
            return false
        }
        val fragmentManager = supportFragmentManager
        when (id) {
            R.id.nav_reminder -> {
                navigateToFragment(fragmentManager, ReminderFragment())
                title = "Reminder"
            }
            R.id.nav_schedule -> {
                navigateToFragment(fragmentManager, AwakeFragment())
                title = "Schedule"
            }
            R.id.nav_forecast -> {
                navigateToFragment(fragmentManager, ForecastFragment())
                title = "Weather Forecast"
            }

            R.id.nav_alarm -> {
                navigateToFragment(fragmentManager, AlarmFragment())
                title = "AlarmJ"
            }
            R.id.nav_about -> {
                navigateToFragment(fragmentManager, RadioFragment())
                title = "Radio"
//                scheduleNotification(getNotification("30 second delay"), 30000)
            }
            R.id.nav_paint -> {
                navigateToFragment(fragmentManager, PaintFragment())
                title = "Painting"
            }
            else -> {
                navigateToFragment(fragmentManager, SettingFragment())
                title = "Setting"
            }
        }
        supportActionBar?.title = title
        currentId = id
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onResume() {
        super.onResume()
        d("onresume")
        registerReceiver(connectingReceiver, IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(connectingReceiver)
        d("onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        d("onDestroy")
        val ref = TaskApplication().getRefWatcher(this)
        ref.watch(ref)
    }

    private fun navigateToFragment(fragmentManager: FragmentManager, fragment: Fragment) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

}
