package ht.pq.khanh.extension

import android.app.AlarmManager
import android.app.Fragment
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import ht.pq.khanh.multitask.R
import ht.pq.khanh.util.Common

/**
 * Created by khanhpq on 9/25/17.
 */
fun ViewGroup.inflateLayout(layoutId: Int, attachToRoot: Boolean = false): View
        = LayoutInflater.from(context).inflate(layoutId, this, attachToRoot)

fun ImageView.loadImage(url: String) {
    Glide.with(context)
            .load(url)
            .crossFade()
            .override(42, 42)
            .error(R.mipmap.ic_launcher_round)
            .into(this)
}

fun Context.showToast(message: String) = Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

fun Fragment.d(message: String) {
    Log.d(this.tag, message)
}

fun Context.hideKeyBoard(et: EditText) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(et.windowToken, 0)
}

fun Context.getAlarmManager(): AlarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager

fun View.isHide() {
    this.visibility = View.GONE
}

fun View.isShow() {
    this.visibility = View.VISIBLE
}

fun Context.setUpTheme() {
    val preference = getSharedPreferences(Common.THEME_PREFERENCES, Context.MODE_PRIVATE)
    val theme = preference.getString(Common.THEME_SAVED, Common.LIGHTTHEME)
    val themeStyle = if (theme == Common.DARKTHEME) {
        R.style.AppTheme_DarkTheme
    } else {
        R.style.AppTheme_LightTheme
    }
    setTheme(themeStyle)
}
fun getId(): Long = Math.random().toLong()