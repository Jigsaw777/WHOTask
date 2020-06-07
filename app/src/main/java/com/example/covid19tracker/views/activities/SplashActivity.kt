package com.example.covid19tracker.views.activities

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.covid19tracker.R
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        initViews()
    }

    private fun initViews() {

        /** This handler posts the runnable in the UI thread's event queue as we are executing this in the UI thread and the
         * handler references the UI thread. */
        Handler().postDelayed({
            tv_fetching_location.visibility = View.VISIBLE
            pb_loading.visibility = View.VISIBLE
        }, 1500)

        
    }
}
