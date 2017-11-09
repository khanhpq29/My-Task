package ht.pq.khanh.task.reminder

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import ht.pq.khanh.bus.RxBus
import ht.pq.khanh.bus.event.TodoEvent
import ht.pq.khanh.extension.DatabaseHelper
import ht.pq.khanh.extension.setUpTheme
import ht.pq.khanh.model.Reminder
import ht.pq.khanh.multitask.R
import ht.pq.khanh.util.Common
import io.realm.Realm
import java.util.*

class ReminderActivity : AppCompatActivity() {
    @BindView(R.id.txtContent)
    lateinit var tvContent : TextView

    private lateinit var todoItem : Reminder
    private lateinit var realm : Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setUpTheme()
        setContentView(R.layout.activity_reminder)
        ButterKnife.bind(this)
        Realm.init(this)
        realm = Realm.getDefaultInstance()
        val title = intent.getStringExtra(Common.TODOTEXT)
        val todoId = intent.getLongExtra(Common.TODOUUID, 0)
        tvContent.text = title
        val items = DatabaseHelper.findAll<Reminder>(realm)
        for(item in items){
            if (item.id == todoId){
                todoItem = item
                break
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    @OnClick(R.id.btnRemove)
    fun removeReminder(){
        moveToReminderList()
    }

    @OnClick(R.id.btnSnooze)
    fun snoozeMore(){
        saveItem()
        finish()
    }

    private fun saveItem() {
        val snoozeTime = 3
        val timeSnooze = addDate(snoozeTime)
        realm.beginTransaction()
        todoItem.dateTime = timeSnooze
        todoItem.isNotify = true
        realm.copyToRealmOrUpdate(todoItem)
        RxBus.instance.send(TodoEvent::class)
        realm.commitTransaction()
    }

    private fun addDate(snoozeTime : Int) : Date{
        val date = Date()
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.MINUTE, snoozeTime)
        return calendar.time
    }
    private fun moveToReminderList(){
        finish()
    }
}
