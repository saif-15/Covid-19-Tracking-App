package com.stechlabs.covid_19.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.recyclerview.widget.RecyclerView
import com.stechlabs.covid_19.R
import com.stechlabs.covid_19.models.persistence.Country
import kotlinx.android.synthetic.main.item_adpater.view.*
import java.text.DecimalFormat


class CountryAdapter :
    RecyclerView.Adapter<CountryAdapter.MyViewHolder>() {

    private var list: List<Country> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        return MyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_adpater, parent, false)
        )
    }

    override fun getItemCount() = list.size

    fun setList(list: List<Country>) {
        this.list = list
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position])
    }


    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val country_name=itemView.country_name
        val cases=itemView.cases
        val today_cases = itemView.today_cases
        fun bind(item: Country){
            country_name.text=item.country
            cases.setAnimationDuration(2000)
            cases.setDecimalFormat(DecimalFormat("###,###,###"))
            cases.countAnimation(0, item.cases)
            cases.setInterpolator(AccelerateInterpolator())
            today_cases.setAnimationDuration(2000)
            today_cases.setDecimalFormat(DecimalFormat("###,###,###"))
            today_cases.countAnimation(0, item.todayCases)
            today_cases.setInterpolator(AccelerateInterpolator())

        }
    }
}