package com.stechlabs.covid_19.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stechlabs.covid_19.R
import com.stechlabs.covid_19.models.persistence.Country
import kotlinx.android.synthetic.main.item_adpater.view.*


class CountryAdapter(private val list: List<Country>) :
    RecyclerView.Adapter<CountryAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val view=LayoutInflater.from(parent.context).inflate(R.layout.item_adpater,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount():Int{
        return list.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
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