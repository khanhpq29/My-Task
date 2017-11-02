package ht.pq.khanh

import android.app.Application
import android.content.Context
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher

/**
 * Created by khanhpq on 10/3/17.
 */
class TaskApplication : Application() {
    private lateinit var refWatcher: RefWatcher

    fun getRefWatcher(context: Context): RefWatcher {
        val application = context.applicationContext as TaskApplication
        return application.refWatcher
    }

    override fun onCreate() {
        super.onCreate()
        refWatcher = LeakCanary.install(this)
    }
}