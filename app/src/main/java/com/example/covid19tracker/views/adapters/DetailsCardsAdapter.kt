package com.example.covid19tracker.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.covid19tracker.R
import com.example.covid19tracker.domain.entities.Attribute
import com.example.covid19tracker.views.viewholders.CardViewHolder
import com.example.covid19tracker.views.viewholders.IntroCardViewHolder

/** we can use higher order functions here to capture click events on each card */
/** Using the generic view holder parent class to accommodate other types of views in the same adapter*/
class DetailsCardsAdapter(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var attributes = mutableListOf<Attribute>()
    private var countryName = ""

    fun setAttributes(attributes: List<Attribute>, countryName: String) {
        this.attributes=attributes.toMutableList()
        this.countryName = countryName
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(viewType, parent, false)
        return when (viewType) {
            R.layout.intro_card ->
                IntroCardViewHolder(view)
            R.layout.country_card ->
                CardViewHolder(view, countryName)
            else -> IntroCardViewHolder(view)
        }
    }

    override fun getItemCount(): Int = attributes.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.let {
            when (it) {
                is IntroCardViewHolder -> Unit
                is CardViewHolder -> it.bindViewHolder(attributes[position])
            }
        }
    }

    /** ideally this method should return int id of layout file based on one the object's property */
    override fun getItemViewType(position: Int): Int {
        return when {
            attributes[position].objectId < 0 -> {
                R.layout.intro_card
            }
            else -> {
                R.layout.country_card
            }
        }
    }
}