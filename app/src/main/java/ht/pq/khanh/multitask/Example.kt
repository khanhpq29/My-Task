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
import ht.pq.khanh.TaskApplication
import ht.pq.khanh.extension.inflateLayout
import ht.pq.khanh.extension.showToast
import ht.pq.khanh.model.Note
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiConsumer
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * Created by khanhpq on 11/8/17.
 */

class Example : Fragment() {
    @BindView(R.id.editTitle)
    lateinit var hours: EditText
    @BindView(R.id.btnInsert)
    lateinit var btnInsert: Button
    private val disposal: CompositeDisposable by lazy { CompositeDisposable() }
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
        disposal.add(
                Single.fromCallable {TaskApplication.db.noteDao().getAllNote()}.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({notes -> activity.showToast("${notes.size}") },
                                { error -> error.printStackTrace() })
        )
    }

    @OnClick(R.id.btnInsert)
    fun insert() {
        val content = hours.text.toString()
        val note = Note(ht.pq.khanh.extension.getId(), content)
        disposal.add(Single.fromCallable {
            TaskApplication.db.noteDao().insertNote(note)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe())
    }

    override fun onDestroy() {
        super.onDestroy()
        disposal.clear()
    }
}
