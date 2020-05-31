package com.stechlabs.covid_19.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.stechlabs.covid_19.R
import com.stechlabs.covid_19.models.persistence.Country
import kotlinx.android.synthetic.main.item_adpater.view.*
import java.text.DecimalFormat


class CountryAdapter :
    ListAdapter<Country, CountryAdapter.MyViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Country>() {
            override fun areItemsTheSame(oldItem: Country, newItem: Country): Boolean {
                return oldItem.country == newItem.country
            }

            override fun areContentsTheSame(oldItem: Country, newItem: Country): Boolean {

                return (oldItem.cases == newItem.cases)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_adpater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val country_name = itemView.country_name
        val cases = itemView.cases
        val recovered = itemView.recovered
        val deaths = itemView.deaths
        fun bind(item: Country) {
            country_name.text = item.country
            cases.apply {
                setAnimationDuration(500)
                setDecimalFormat(DecimalFormat("###,###,###"))
                countAnimation(0, item.cases)
                setInterpolator(AccelerateInterpolator())
            }
            recovered.apply {
                setAnimationDuration(500)
                setDecimalFormat(DecimalFormat("###,###,###"))
                countAnimation(0, item.recovered)
                setInterpolator(AccelerateInterpolator())
            }
            deaths.apply {
                setAnimationDuration(500)
                setDecimalFormat(DecimalFormat("###,###,###"))
                countAnimation(0, item.deaths)
                setInterpolator(AccelerateInterpolator())
            }

        }
    }
}