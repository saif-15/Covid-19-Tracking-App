package com.stechlabs.covid_19.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stechlabs.covid_19.R
import com.stechlabs.covid_19.models.persistence.Country
import kotlinx.android.synthetic.main.item_adpater.view.*


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

        fun bind(item: Country){
            country_name.text=item.country
            cases.text=item.cases.toString()
        }
    }
}