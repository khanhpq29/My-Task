package ht.pq.khanh.multitask.weather

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ht.pq.khanh.api.forecast.List

import ht.pq.khanh.multitask.R

class WeatherDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_detail)
        val weatherFragment = WeatherFragment()
        val bundle = Bundle()
        bundle.putParcelable("fragment_key", intent.getParcelableExtra("weather_detail"))
        weatherFragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.detail_container, weatherFragment).commit()
        supportPostponeEnterTransition()
    }
}
