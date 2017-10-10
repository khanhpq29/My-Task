package ht.pq.khanh.multitask.paint

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import ht.pq.khanh.extension.inflateLayout
import ht.pq.khanh.multitask.R

import kotlinx.android.synthetic.main.activity_paint.*

class PaintFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = container!!.inflateLayout(R.layout.activity_paint)
        return view
    }
}
