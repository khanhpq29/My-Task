package ht.pq.khanh.multitask

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.pawegio.kandroid.IntentFor

class SplashActivity : AppCompatActivity() {
    private val runnable: Runnable = Runnable {
        val intent = IntentFor<MenuActivity>(this@SplashActivity)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        Handler().postDelayed(runnable, 2000)
    }

}

