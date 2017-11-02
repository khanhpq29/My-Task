package ht.pq.khanh.multitask

import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.SystemClock
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
import ht.pq.khanh.multitask.forecast.ForecastFragment
import ht.pq.khanh.multitask.paint.PaintFragment
import ht.pq.khanh.multitask.radio.RadioFragment
import ht.pq.khanh.setting.SettingFragment
import ht.pq.khanh.task.alarm.AlarmFragment
import ht.pq.khanh.task.reminder.ReminderActivity
import ht.pq.khanh.task.reminder.ReminderFragment
import ht.pq.khanh.task.sleepawake.AwakeFragment
import ht.pq.khanh.util.Common

class MenuActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.drawer_layout)
    lateinit var drawer: DrawerLayout
    @BindView(R.id.nav_view)
    lateinit var navigationView: NavigationView
    private lateinit var title: String
    private var theme = "name_of_the_theme"
    private val RECREATE_ACTIVITY = "recreate_theme"
    private var themeStyle = -1
    private var currentId = R.id.nav_reminder
    private lateinit var connectingReceiver : ConnectivityReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        setUpTheme()
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
        if (getSharedPreferences(Common.THEME_PREFERENCES, Context.MODE_PRIVATE).getBoolean(RECREATE_ACTIVITY, false)) {
            val editor = getSharedPreferences(Common.THEME_PREFERENCES, Context.MODE_PRIVATE).edit()
            editor.putBoolean(RECREATE_ACTIVITY, false)
            editor.apply()
            recreate()
        }
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(connectingReceiver)
    }
    override fun onDestroy() {
        super.onDestroy()
        val ref = TaskApplication().getRefWatcher(this)
        ref.watch(ref)
    }

    private fun setUpTheme() {
        val preference = getSharedPreferences(Common.THEME_PREFERENCES, Context.MODE_PRIVATE)
        theme = preference.getString(Common.THEME_SAVED, Common.LIGHTTHEME)
        themeStyle = if (theme == Common.DARKTHEME) {
            R.style.AppTheme_DarkTheme
        } else {
            R.style.AppTheme_LightTheme
        }
        setTheme(themeStyle)
    }

    private fun navigateToFragment(fragmentManager: FragmentManager, fragment: Fragment) {
        val transaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    private fun scheduleNotification(notification: Notification, delay: Int) {

        val notificationIntent = Intent(this, ReminderActivity::class.java)
        notificationIntent.putExtra("notification-id", 1)
        notificationIntent.putExtra("notification", notification)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val futureInMillis = SystemClock.elapsedRealtime() + delay
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent)
    }

    private fun getNotification(content: String): Notification {
        val builder = Notification.Builder(this)
        builder.setContentTitle("Scheduled Notification")
        builder.setContentText(content)
        builder.setSmallIcon(R.mipmap.ic_launcher)
        return builder.build()
    }
}
