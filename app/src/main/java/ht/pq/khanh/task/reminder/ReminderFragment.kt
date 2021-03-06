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
import android.util.Log
import android.view.*
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.pawegio.kandroid.IntentFor
import com.pawegio.kandroid.d
import ht.pq.khanh.extension.*
import ht.pq.khanh.helper.SimpleItemTouchHelperCallBack
import ht.pq.khanh.model.Reminder
import ht.pq.khanh.multitask.R
import ht.pq.khanh.notification.ReminderNotificationService
import ht.pq.khanh.util.Common
import io.realm.Realm

class ReminderFragment : Fragment(), ReminderAdapter.OnAlterItemRecyclerView, ReminderAdapter.OnDeleteItemListener {
    private val REQUEST_CODE_CREATE = 117
    private val REQUEST_UPDATE = 113
    @BindView(R.id.list_reminder)
    lateinit var recyclerRemind: RecyclerView

    private var remindAdapter: ReminderAdapter? = null
    private var listReminder: MutableList<Reminder> = arrayListOf()
    private lateinit var realm: Realm
    private lateinit var reminder: Reminder
    private var selectedPosition = 0
    private lateinit var simpleTouch: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
        }
        setNotification()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = container!!.inflateLayout(R.layout.reminder_fragment)
        ButterKnife.bind(this, view)
        Realm.init(context)
        realm = Realm.getDefaultInstance()
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listReminder = realm.copyFromRealm(realm.findAllRemind())
        remindAdapter = ReminderAdapter(listReminder)
        remindAdapter?.setHasStableIds(true)
        initialRecyclerView()
        registerForContextMenu(recyclerRemind)
        val callback = SimpleItemTouchHelperCallBack(remindAdapter!!)
        simpleTouch = ItemTouchHelper(callback)
        simpleTouch.attachToRecyclerView(recyclerRemind)
        remindAdapter?.setOnChangeItem(this)
        remindAdapter?.setOnDeleteItemListener(this)
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
                data?.let {
                    reminder = data.getParcelableExtra("reminder_result")
                    listReminder.add(reminder)
                    realm.insertRemind(reminder)
                    remindAdapter?.notifyDataSetChanged()
                }
            } else if (requestCode == REQUEST_UPDATE) {
                data?.let {
                    reminder = data.getParcelableExtra("reminder_result")
                    listReminder.removeAt(selectedPosition)
                    listReminder.add(selectedPosition, reminder)
                    realm.updateReminder(reminder)
                    remindAdapter?.notifyDataSetChanged()
                }
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
        remindAdapter?.notifyItemRemoved(position)
        val intent = IntentFor<ReminderNotificationService>(activity)
        deleteAlarm(intent, itemSelected.id.hashCode())
        realm.deleteRemind(itemSelected)
        Snackbar.make(recyclerRemind, "delete on item", Snackbar.LENGTH_LONG)
                .setAction("Undo", {
                    listReminder.add(selectedPosition, itemSelected)
                    realm.insertRemind(itemSelected)
                    remindAdapter?.notifyDataSetChanged()
                }).show()
    }

    override fun onPause() {
        super.onPause()
        realm.close()
        d("on pause")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        d("ondestroyview")
        realm.close()
    }

    override fun onStop() {
        super.onStop()
        d("onstop")
    }

    override fun onResume() {
        super.onResume()
        realm = Realm.getDefaultInstance()
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
            if (item.isNotify && item.timeDay != null && item.timeHour != null) {
                val intent = IntentFor<ReminderNotificationService>(activity)
                intent.putExtra(Common.TODOTEXT, item.title)
                intent.putExtra(Common.TODOUUID, item.id)
                setAlarmManager(intent, item.id.hashCode(), item.timeHour!!)
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
            Log.d("OskarSchindler", "PI Cancelled " + doesPendingIntentExist(i, requestCode))
        }
    }
}
