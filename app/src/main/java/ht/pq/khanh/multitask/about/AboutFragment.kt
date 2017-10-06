package ht.pq.khanh.multitask.about

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import ht.pq.khanh.extension.inflateLayout
import ht.pq.khanh.multitask.BuildConfig
import ht.pq.khanh.multitask.R

/**
 * Created by khanhpq on 10/5/17.
 */
class AboutFragment : Fragment(){
    @BindView(R.id.lbVersion)
    lateinit var tvVersion : TextView
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = container!!.inflateLayout(R.layout.fragment_about)
        ButterKnife.bind(this, view)
        return view
    }
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvVersion.text = "Version: ${BuildConfig.VERSION_NAME}"
    }
}