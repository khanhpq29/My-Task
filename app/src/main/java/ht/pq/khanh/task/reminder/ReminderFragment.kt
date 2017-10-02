package ht.pq.khanh.task.reminder

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
import ht.pq.khanh.model.DummyData
import ht.pq.khanh.model.Reminder
import ht.pq.khanh.multitask.DetailActivity
import ht.pq.khanh.multitask.R
import io.realm.Realm

class ReminderFragment : Fragment(), ReminderContract.View {
    private val REQUEST_CODE = 117
    override fun loadAllReminder() {

    }

    override fun loadChange() {
    }

    @BindView(R.id.list_reminder)
    lateinit var recyclerRemind: RecyclerView
    private var remindAdapter : ReminderAdapter? = null
    private var listReminder : MutableList<Reminder> = arrayListOf()
    private val realm : Realm by lazy { Realm.getDefaultInstance() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = container!!.inflateLayout(R.layout.reminder_fragment)
        ButterKnife.bind(this, view)
        Realm.init(context)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listReminder = realm.copyFromRealm(realm.findAllRemind())
        remindAdapter = ReminderAdapter(listReminder)

        recyclerRemind.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = remindAdapter
        }
    }
    @OnClick(R.id.fab_remind)
    fun createReminder(){
        val intent = IntentFor<DetailActivity>(activity)
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        realm.close()
    }
}
