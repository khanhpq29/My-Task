package ht.pq.khanh.task.reminder

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.pawegio.kandroid.IntentFor
import com.pawegio.kandroid.d
import ht.pq.khanh.TaskApplication
import ht.pq.khanh.extension.deleteRemind
import ht.pq.khanh.extension.findAllRemind
import ht.pq.khanh.extension.inflateLayout
import ht.pq.khanh.extension.insertRemind
import ht.pq.khanh.model.Reminder
import ht.pq.khanh.multitask.R
import io.realm.Realm

class ReminderFragment : Fragment(), ReminderAdapter.OnAlterItemRecyclerView,
        ReminderAdapter.OnLongRclItemClick {
    private val REQUEST_CODE_CREATE = 117

    @BindView(R.id.list_reminder)
    lateinit var recyclerRemind: RecyclerView

    private var remindAdapter: ReminderAdapter? = null
    private var listReminder: MutableList<Reminder> = arrayListOf()
    private lateinit var realm: Realm
    private lateinit var reminder: Reminder
    private var selectedPosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
        }
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
        initRecyclerview()
        registerForContextMenu(recyclerRemind)
        remindAdapter?.setOnChangeItem(this)
        remindAdapter?.setOnLongClickListener(this)

    }

    private fun initRecyclerview() {
        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        recyclerRemind.apply {
            layoutManager = LinearLayoutManager(this@ReminderFragment.activity)
            adapter = remindAdapter
            setHasFixedSize(true)
            addItemDecoration(itemDecoration)
        }
    }

    @OnClick(R.id.fab_remind)
    fun createReminder() {
        createIntent(Reminder())
    }

    private fun createIntent(reminder: Reminder) {
        val intent = IntentFor<ReminderEditActivity>(activity)
        intent.putExtra("reminder_data", reminder)
        startActivityForResult(intent, REQUEST_CODE_CREATE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        d("on activity result")
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_CREATE) {
            if (data != null) {
                reminder = data.getParcelableExtra("reminder_result")
                listReminder.add(reminder)
                remindAdapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_reminder, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.mSort) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onChangeItem(position: Int) {
        selectedPosition = position
        val item = listReminder[position]
        createIntent(item)
    }

    override fun onLongClick(position: Int) {
        selectedPosition = position
        activity.openContextMenu(recyclerRemind)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        activity.menuInflater.inflate(R.menu.context_menu, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        when {
            item.itemId == R.id.cDelete -> {
                var itemSelected = listReminder[selectedPosition]
                listReminder.removeAt(selectedPosition)
                remindAdapter?.notifyDataSetChanged()
                realm.deleteRemind(selectedPosition)
                Snackbar.make(recyclerRemind, "delete on item", Snackbar.LENGTH_LONG)
                        .setAction("Undo", {
                            listReminder.add(selectedPosition, itemSelected)
                            remindAdapter?.notifyDataSetChanged()
                            realm.insertRemind(itemSelected)
                        }).show()
            }
        }
        return super.onContextItemSelected(item)
    }

    override fun onPause() {
        super.onPause()
        d("on pause")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        d("ondestroyview")
        if (!realm.isClosed) {
            realm.close()
        }
        val ref = TaskApplication().getRefWatcher(context)
        ref.watch(ref)
    }

    override fun onStop() {
        super.onStop()
        if (!realm.isClosed) {
            realm.close()
        }
        d("onstop")
    }

    override fun onResume() {
        super.onResume()
        d("onResume")
    }

    override fun onDestroy() {
        super.onDestroy()
        d("onDestroy")
    }
}
