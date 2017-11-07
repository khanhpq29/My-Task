package ht.pq.khanh

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import ht.pq.khanh.model.TaskDatabase

/**
 * Created by khanhpq on 10/3/17.
 */
class TaskApplication : Application() {
    private lateinit var refWatcher: RefWatcher
    companion object {
        var taskDb : TaskDatabase? = null
    }

    fun getRefWatcher(context: Context): RefWatcher {
        val application = context.applicationContext as TaskApplication
        return application.refWatcher
    }

    override fun onCreate() {
        super.onCreate()
        refWatcher = LeakCanary.install(this)
        taskDb = Room.databaseBuilder(this, TaskDatabase::class.java, "task").build()
    }
}