package com.example.covid19tracker.views.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.covid19tracker.R
import com.example.covid19tracker.data.constants.AppConstants
import com.example.covid19tracker.views.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    /**this instantiates the view model lazily */
    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel()::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeEvents()
        val code=intent.getStringExtra(AppConstants.COUNTRY_CODE)
        viewModel.getData(code)
    }

    private fun observeEvents(){
        viewModel.attributesLiveData.observe(this, Observer {
            tv_text.text=it.totalConfirmedCases.toString()
        })
    }
}
