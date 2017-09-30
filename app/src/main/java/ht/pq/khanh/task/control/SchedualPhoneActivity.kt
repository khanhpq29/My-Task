package ht.pq.khanh.task.control

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import ht.pq.khanh.multitask.R

/**
 * Created by khanhpq on 9/25/17.
 */

class SchedualPhoneActivity : AppCompatActivity() {

    @BindView(R.id.sliding_tabs)
    lateinit var tabs: TabLayout
    @BindView(R.id.viewpager)
    lateinit var pagers: ViewPager

    private lateinit var scheduleAdapter: ScheduleAdapter
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.schedule_layout)
        ButterKnife.bind(this)
        scheduleAdapter = ScheduleAdapter(supportFragmentManager)
        pagers.adapter = scheduleAdapter
        tabs.setupWithViewPager(pagers)
    }
}
