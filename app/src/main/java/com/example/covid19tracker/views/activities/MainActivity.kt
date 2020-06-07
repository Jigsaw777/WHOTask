package com.example.covid19tracker.views.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.covid19tracker.R
import com.example.covid19tracker.data.constants.AppConstants
import com.example.covid19tracker.domain.entities.Attribute
import com.example.covid19tracker.views.adapters.DetailsCardsAdapter
import com.example.covid19tracker.views.customviews.CirclePagerIndicatorDecoration
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
            setDataInRecyclerView(it)
            hideLoading()
        })
    }

    private fun setDataInRecyclerView(attributes : List<Attribute>){
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false)
        val snapHelper=LinearSnapHelper()
        snapHelper.attachToRecyclerView(rv_cases)
        val adapter = DetailsCardsAdapter(this)
        adapter.setAttributes(attributes,countryName?:"")
        rv_cases.layoutManager=layoutManager
        rv_cases.adapter=adapter
        rv_cases.addItemDecoration(CirclePagerIndicatorDecoration())
    }

    private fun hideLoading(){
        results_loading.visibility= View.GONE
        rv_cases.visibility=View.VISIBLE
    }
}
