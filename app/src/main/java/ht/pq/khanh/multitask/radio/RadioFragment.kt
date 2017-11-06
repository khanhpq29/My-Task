package ht.pq.khanh.multitask.radio

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.OnClick
import ht.pq.khanh.extension.inflateLayout
import ht.pq.khanh.multitask.R

class RadioFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  container!!.inflateLayout(R.layout.fragment_radio)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
    @OnClick(R.id.btnRadio)
    fun lock(){
//        activity.startService(IntentFor<LogScreenService>(activity))
    }
}
