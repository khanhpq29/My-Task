package ht.pq.khanh.task.reminder

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.pawegio.kandroid.IntentFor
import ht.pq.khanh.extension.findAllRemind
import ht.pq.khanh.extension.inflateLayout
import ht.pq.khanh.model.Reminder
import ht.pq.khanh.multitask.DetailActivity
import ht.pq.khanh.multitask.R
import io.realm.Realm
import android.support.v7.widget.DividerItemDecoration
import android.util.Log

class ReminderFragment : Fragment(), ReminderContract.View, ReminderAdapter.OnAlterItemRecyclerView {

    private val REQUEST_CODE_CREATE = 117
    private val REQUEST_CODE_EDIT = 116
    @BindView(R.id.list_reminder)
    lateinit var recyclerRemind: RecyclerView
    private var remindAdapter: ReminderAdapter? = null
    private var listReminder: MutableList<Reminder> = arrayListOf()
    private val realm: Realm by lazy { Realm.getDefaultInstance() }
    private lateinit var reminderPresenter : ReminderPresenter
    private lateinit var reminder: Reminder
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
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        reminderPresenter = ReminderPresenter(this)
        reminderPresenter.initReminder()
        remindAdapter = ReminderAdapter(listReminder)
        val itemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        recyclerRemind.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = remindAdapter
            setHasFixedSize(true)
            addItemDecoration(itemDecoration)
        }
        remindAdapter?.setOnChangeItem(this)
    }

    @OnClick(R.id.fab_remind)
    fun createReminder() {
        val intent = IntentFor<DetailActivity>(activity)
        startActivityForResult(intent, REQUEST_CODE_CREATE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE_EDIT) {
            if (data != null)
                reminder = data.getParcelableExtra<Reminder>("reminder")

            listReminder.add(reminder)
            reminderPresenter.addReminder(reminder)
        }
    }

    override fun loadAllReminder() {
        listReminder = realm.copyFromRealm(realm.findAllRemind())
    }

    override fun loadChange() {
        remindAdapter?.swipeReminder(listReminder)
    }

    override fun onChangeItem(position: Int) {
        val item = listReminder[position]
        val intent = IntentFor<DetailActivity>(activity)
        intent.putExtra("reminder_data", item)
        startActivityForResult(intent, REQUEST_CODE_EDIT)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("destroy remind", "destroy")
        realm.close()
    }
}
