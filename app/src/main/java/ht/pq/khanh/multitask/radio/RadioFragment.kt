package ht.pq.khanh.multitask.radio

import android.content.Context
import android.os.Bundle
import android.os.PowerManager
import android.support.design.widget.TextInputLayout
import android.support.v4.app.Fragment
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.OnClick
import ht.pq.khanh.extension.inflateLayout
import ht.pq.khanh.multitask.R
import android.view.WindowManager
import android.widget.EditText
import butterknife.BindView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

class RadioFragment : Fragment() {
    @BindView(R.id.editTextEmail)
    lateinit var editTextEmail : EditText
    @BindView(R.id.editTextPassword)
    lateinit var editTextPassword : EditText
    @BindView(R.id.emailWrapper)
    lateinit var emailWrapper : TextInputLayout
    @BindView(R.id.passwordWrapper)
    lateinit var passwordWrapper : TextInputLayout
    private inline fun retryWhenError(crossinline onError: (ex: Throwable) -> Unit): ObservableTransformer<String, String> = ObservableTransformer { observable ->
        observable.retryWhen { errors ->
            errors.flatMap {
                onError(it)
                Observable.just("")
            }
        }
    }
    private val lengthGreaterThanSix = ObservableTransformer<String, String> { observable ->
        observable.map { it.trim() }
                .filter { it.length > 6 }
                .singleOrError()
                .onErrorResumeNext {
                    if (it is NoSuchElementException){
                        Single.error(Exception("length should be greater than 6"))
                    } else{
                        Single.error(it)
                    }
                }
                .toObservable()
    }
    private val verifyEmailPattern = ObservableTransformer<String, String> { observable ->
        observable.map { it.trim() }
                .filter { Patterns.EMAIL_ADDRESS.matcher(it).matches() }
                .singleOrError()
                .onErrorResumeNext {
                    if (it is NoSuchElementException){
                        Single.error(Exception("Email not valid"))
                    } else {
                        Single.error(it)
                    }
                }
                .toObservable()
    }


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
        RxTextView.afterTextChangeEvents(editTextEmail)
                .skipInitialValue()
                .map {
                    emailWrapper.error = null
                    it.view().text.toString()
                }
                .debounce(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
                .compose(lengthGreaterThanSix)
                .compose(verifyEmailPattern)
                .compose(retryWhenError {
                    emailWrapper.error = it.message
                })
                .subscribe()

        RxTextView.afterTextChangeEvents(editTextPassword)
                .skipInitialValue()
                .map {
                    passwordWrapper.error = null
                    it.view().text.toString()
                }
                .debounce(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
                .compose(lengthGreaterThanSix)
                .compose(retryWhenError {
                    passwordWrapper.error = it.message
                })
                .subscribe()
    }

//    @OnClick(R.id.btnRadio)
//    fun lock(){
//        val manager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
//        val params = activity.window.attributes
//        params.flags = params.flags or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
//        params.screenBrightness = 0f
//        activity.window.attributes = params
////        activity.startService(IntentFor<LogScreenService>(activity))
//    }
}
