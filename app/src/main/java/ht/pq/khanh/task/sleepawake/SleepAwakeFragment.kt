package ht.pq.khanh.task.sleepawake

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import ht.pq.khanh.extension.inflateLayout

import ht.pq.khanh.multitask.R

class SleepAwakeFragment : Fragment() {

    private var mParam1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = container!!.inflateLayout(R.layout.fragment_sleep_awake)
        ButterKnife.bind(this, view)
        return view
    }

    companion object {
        private val ARG_PARAM1 = "param1"

        fun newInstance(param1: String): SleepAwakeFragment {
            val fragment = SleepAwakeFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            fragment.arguments = args
            return fragment
        }
    }

}
