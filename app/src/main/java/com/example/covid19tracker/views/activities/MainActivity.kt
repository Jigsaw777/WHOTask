package com.example.covid19tracker.views.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.covid19tracker.R
import com.example.covid19tracker.data.constants.AppConstants
import com.example.covid19tracker.views.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var code: String? = ""
    private var countryName: String? = ""

    /**this instantiates the view model lazily */
    private val viewModel: MainViewModel by lazy {
        ViewModelProviders.of(this).get(MainViewModel()::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        observeEvents()
        getValuesFromBundle()
        viewModel.getData(code ?: "US")
    }

    private fun getValuesFromBundle() {
        code = intent.getStringExtra(AppConstants.COUNTRY_CODE)
        countryName = intent.getStringExtra(AppConstants.COUNTRY_NAME)
    }

    private fun observeEvents(){
        viewModel.attributesLiveData.observe(this, Observer {
//            tv_header.text=it.totalConfirmedCases.toString()
            setDataInRecyclerView()
            hideLoading()
        })
    }

    private fun setDataInRecyclerView(){

    }

    private fun hideLoading(){
        results_loading.visibility= View.GONE
        rv_cases.visibility=View.VISIBLE
    }
}
