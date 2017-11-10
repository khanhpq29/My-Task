package ht.pq.khanh.task.reminder

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.*
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.pawegio.kandroid.IntentFor
import com.pawegio.kandroid.d
import ht.pq.khanh.TaskApplication
import ht.pq.khanh.bus.RxBus
import ht.pq.khanh.bus.event.TodoEvent
import ht.pq.khanh.extension.getAlarmManager
import ht.pq.khanh.extension.inflateLayout
import ht.pq.khanh.helper.SimpleItemTouchHelperCallBack
import ht.pq.khanh.model.reminder.Reminder
import ht.pq.khanh.multitask.R
import ht.pq.khanh.notification.ReminderNotificationService
import ht.pq.khanh.util.Common
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class ReminderFragment : Fragment(), ReminderAdapter.OnAlterItemRecyclerView, ReminderAdapter.OnDeleteItemListener {
    private val REQUEST_CODE_CREATE = 117
    private val REQUEST_UPDATE = 113
    @BindView(R.id.list_reminder)
    lateinit var recyclerRemind: RecyclerView

    private lateinit var remindAdapter: ReminderAdapter
    private var listReminder: MutableList<Reminder> = arrayListOf()
    private lateinit var reminder: Reminder
    private var selectedPosition = 0
    private lateinit var simpleTouch: ItemTouchHelper
    private val subscription: CompositeDisposable by lazy { CompositeDisposable() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = container!!.inflateLayout(R.layout.reminder_fragment)
        ButterKnife.bind(this, view)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        d("onviewcreated")
        setNotification()
        Single.fromCallable { TaskApplication.db.reminderDao().getAllReminder() }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { reminders -> listReminder = reminders }
        remindAdapter = ReminderAdapter(listReminder)
        remindAdapter.setHasStableIds(true)
        remindAdapter.setOnChangeItem(this)
        remindAdapter.setOnDeleteItemListener(this)
        initialRecyclerView()
        setUpTouch()
        listenReminderNotification()
    }

    private fun setUpTouch() {
        val callback = SimpleItemTouchHelperCallBack(remindAdapter)
        simpleTouch = ItemTouchHelper(callback)
        simpleTouch.attachToRecyclerView(recyclerRemind)
    }

    override fun onStart() {
        super.onStart()
        setNotification()
    }

    @OnClick(R.id.fab_remind)
    fun createReminder() {
        createIntent(Reminder(), REQUEST_CODE_CREATE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        d("on activity result")
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_CREATE) {
                addReminder(data)
            } else if (requestCode == REQUEST_UPDATE) {
                updateReminder(data)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) = inflater.inflate(R.menu.menu_reminder, menu)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mSort) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onChangeItem(position: Int) {
        selectedPosition = position
        val item = listReminder[position]
        createIntent(item, REQUEST_UPDATE)
    }

    override fun onDeleteItem(position: Int) {
        selectedPosition = position
        var itemSelected = listReminder[position]
        listReminder.removeAt(position)
        remindAdapter.notifyItemRemoved(position)
        val intent = IntentFor<ReminderNotificationService>(activity)
        deleteAlarm(intent, itemSelected.id.hashCode())
        Single.fromCallable { TaskApplication.db.reminderDao().deleteReminder(itemSelected) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
        Snackbar.make(recyclerRemind, "delete on item", Snackbar.LENGTH_LONG)
                .setAction("Undo", {
                    listReminder.add(selectedPosition, itemSelected)
                    Single.fromCallable { TaskApplication.db.reminderDao().insertReminder(itemSelected) }
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe()
                    deleteAlarm(intent, itemSelected.id.hashCode())
                    remindAdapter.notifyDataSetChanged()
                }).show()
    }

    override fun onPause() {
        super.onPause()
        d("on pause")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        d("ondestroyview")
        subscription.clear()
    }

    override fun onStop() {
        super.onStop()
        d("onstop")
    }

    override fun onResume() {
        super.onResume()
        d("onResume")
        d("size", "${listReminder.size}")
    }

    override fun onDestroy() {
        super.onDestroy()
        d("onDestroy")
    }

    private fun createIntent(reminder: Reminder, requestCode: Int) {
        val intent = IntentFor<ReminderEditActivity>(activity)
        intent.putExtra("reminder_data", reminder)
        startActivityForResult(intent, requestCode)
    }

    private fun initialRecyclerView() {
        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        recyclerRemind.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = remindAdapter
            setHasFixedSize(true)
            addItemDecoration(itemDecoration)
        }
    }

    private fun setNotification() {
        for (item in listReminder) {
            if (item.isNotify && item.dateTime != null) {
                if (item.dateTime!!.before(Date())) {
                    item.dateTime = null
                    continue
                }
                val intent = IntentFor<ReminderNotificationService>(activity)
                intent.putExtra(Common.TODOTEXT, item.title)
                intent.putExtra(Common.TODOUUID, item.id)
                setAlarmManager(intent, item.id.hashCode(), item.dateTime?.time!!)
            }
        }
    }

    private fun setAlarmManager(intent: Intent, requestCode: Int, timeAtMillis: Long) {
        val alarmManger = context.getAlarmManager()
        val pendingIntent = PendingIntent.getService(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManger.set(AlarmManager.RTC_WAKEUP, timeAtMillis, pendingIntent)
    }

    private fun doesPendingIntentExist(i: Intent, requestCode: Int): Boolean {
        val pi = PendingIntent.getService(context, requestCode, i, PendingIntent.FLAG_NO_CREATE)
        return pi != null
    }

    private fun deleteAlarm(i: Intent, requestCode: Int) {
        if (doesPendingIntentExist(i, requestCode)) {
            val pi = PendingIntent.getService(context, requestCode, i, PendingIntent.FLAG_NO_CREATE)
            pi.cancel()
            context.getAlarmManager().cancel(pi)
        }
    }

    private fun addNotification(reminder: Reminder) {
        if (reminder.dateTime != null) {
            val intent = IntentFor<ReminderNotificationService>(activity)
            intent.putExtra(Common.TODOUUID, reminder.id.hashCode())
            intent.putExtra(Common.TODOTEXT, reminder.title)
            setAlarmManager(intent, reminder.id.hashCode(), reminder.dateTime!!.time)
        }
    }

    private fun updateReminder(data: Intent?) {
        data?.let {
            reminder = data.getParcelableExtra("reminder_result")
            listReminder.removeAt(selectedPosition)
            listReminder.add(selectedPosition, reminder)
            Single.fromCallable { TaskApplication.db.reminderDao().updateReminder(reminder) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            remindAdapter.notifyDataSetChanged()
        }
    }

    private fun addReminder(data: Intent?) {
        data?.let {
            reminder = data.getParcelableExtra("reminder_result")
            addNotification(reminder)
            listReminder.add(reminder)
            Single.fromCallable { TaskApplication.db.reminderDao().insertReminder(reminder) }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe()
            remindAdapter.notifyDataSetChanged()
        }
    }

    private fun listenReminderNotification() {
        subscription.add(RxBus.instance.toObservable(TodoEvent::class.java)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Single.fromCallable { TaskApplication.db.reminderDao().getAllReminder() }
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe { reminders -> listReminder = reminders }
                    remindAdapter.notifyDataSetChanged()
                }, { error -> error.printStackTrace() }))
    }
}
