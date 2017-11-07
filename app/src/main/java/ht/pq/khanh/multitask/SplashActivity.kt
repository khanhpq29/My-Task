package ht.pq.khanh.multitask

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.pawegio.kandroid.IntentFor
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.launch
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {
    private val runnable: Runnable = Runnable {
        val intent = IntentFor<MenuActivity>(this)
        startActivity(intent)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        Handler().postDelayed(runnable, 2000)
        launch(CommonPool) {
            delay(2, TimeUnit.SECONDS)
            val intent = IntentFor<MenuActivity>(this@SplashActivity)
            startActivity(intent)
            finish()
        }
    }

}

