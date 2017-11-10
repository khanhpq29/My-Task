package ht.pq.khanh.multitask

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import ht.pq.khanh.extension.inflateLayout
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by khanhpq on 11/8/17.
 */

class Example : Fragment() {
    @BindView(R.id.editTitle)
    lateinit var hours: EditText
    @BindView(R.id.btnInsert)
    lateinit var btnInsert: Button
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = container!!.inflateLayout(R.layout.layout_example)
        ButterKnife.bind(this, view)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    @OnClick(R.id.btnGetAll)
    fun getAll() {

    }

    @OnClick(R.id.btnInsert)
    fun insert() {

    }

    override fun onDestroy() {
        super.onDestroy()
    }
}

