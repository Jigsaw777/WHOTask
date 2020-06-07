package com.example.covid19tracker.views.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.covid19tracker.domain.entities.Attribute
import kotlinx.android.synthetic.main.country_card.view.*
import java.text.NumberFormat
import java.util.*

class CardViewHolder (private val view:View, private val countryName:String) : RecyclerView.ViewHolder(view){

    fun bindViewHolder(attribute: Attribute){
        view.tv_country.text = countryName

        /** setting default locale in number format instance inserts commas according to the phone's default set country */
        val confirmedCases = NumberFormat.getNumberInstance(Locale.getDefault()).format(attribute.totalConfirmedCases)
        val deaths =  NumberFormat.getNumberInstance(Locale.getDefault()).format(attribute.totalDeathCases)

        view.tv_confirmed.text = confirmedCases
        view.tv_deaths.text = deaths
    }
}