package ht.pq.khanh.multitask.paint

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.DisplayMetrics
import android.view.*
import butterknife.BindView
import butterknife.ButterKnife
import ht.pq.khanh.extension.inflateLayout
import ht.pq.khanh.multitask.R

class PaintFragment : Fragment() {
    @BindView(R.id.paint)
    lateinit var paint : PaintView
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = container!!.inflateLayout(R.layout.activity_paint)
        ButterKnife.bind(this, view)
        setHasOptionsMenu(true)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        val metrics = DisplayMetrics()
       activity.windowManager.defaultDisplay.getMetrics(metrics)
        paint.init(metrics)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_paint, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.normal ->
                paint.normal()
            R.id.emboss ->
                paint.emboss()
            R.id.blur ->
                paint.blur()
            R.id.clear ->
                paint.clear()
        }
        return true
    }
}