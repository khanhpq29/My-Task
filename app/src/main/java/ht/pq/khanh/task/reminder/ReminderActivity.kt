package ht.pq.khanh.task.reminder

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.pawegio.kandroid.IntentFor
import com.pawegio.kandroid.d
import ht.pq.khanh.TaskApplication
import ht.pq.khanh.bus.RxBus
import ht.pq.khanh.bus.event.TodoEvent
import ht.pq.khanh.extension.setUpTheme
import ht.pq.khanh.model.reminder.Reminder
import ht.pq.khanh.multitask.MenuActivity
import ht.pq.khanh.multitask.R
import ht.pq.khanh.util.Common
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

class ReminderActivity : AppCompatActivity() {
    @BindView(R.id.txtContent)
    lateinit var tvContent: TextView

    private lateinit var todoItem: Reminder
    private lateinit var items : MutableList<Reminder>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setUpTheme()
        setContentView(R.layout.activity_reminder)
        ButterKnife.bind(this)
        val title = intent.getStringExtra(Common.TODOTEXT)
        val todoId = intent.getLongExtra(Common.TODOUUID, 0)
        tvContent.text = title
        Single.fromCallable { TaskApplication.db.reminderDao().getAllReminder() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { itemList -> items = itemList }
        for (item in items) {
            if (item.id == todoId) {
                todoItem = item
                break
            }
        }
        d("todoitem ${todoItem.dateTime}")
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    @OnClick(R.id.btnRemove)
    fun removeReminder() {
        moveToReminderList()
    }

    @OnClick(R.id.btnSnooze)
    fun snoozeMore() {
        saveItem()
        finish()
    }

    private fun saveItem() {
        val snoozeTime = 3
        val timeSnooze = addDate(snoozeTime)
        todoItem.dateTime = timeSnooze
        todoItem.isNotify = true
        Single.fromCallable { TaskApplication.db.reminderDao().updateReminder(todoItem) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        d("todoitem ${todoItem.dateTime}")
        RxBus.instance.send(TodoEvent::class)
        val intent = IntentFor<MenuActivity>(this)
        startActivity(intent)
    }

    private fun addDate(snoozeTime: Int): Date {
        val date = Date()
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.add(Calendar.MINUTE, snoozeTime)
        return calendar.time
    }

    private fun moveToReminderList() {
        Single.fromCallable { TaskApplication.db.reminderDao().deleteReminder(todoItem) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        val intent = IntentFor<MenuActivity>(this)
        startActivity(intent)
        RxBus.instance.send(TodoEvent::class)
        finish()
    }
}
