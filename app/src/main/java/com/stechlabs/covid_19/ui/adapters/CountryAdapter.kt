package com.stechlabs.covid_19.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stechlabs.covid_19.R
import com.stechlabs.covid_19.models.apiResponse.Country
import kotlinx.android.synthetic.main.item_adpater.view.*

class CountryAdapter: RecyclerView.Adapter<CountryAdapter.MyViewHolder>() {

    private var list:List<Country>?= listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
    val view=LayoutInflater.from(parent.context).inflate(R.layout.item_adpater,parent,false)
        return MyViewHolder(view)
    }

    override fun getItemCount():Int{
        return list!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item=list?.get(position)
        holder.bind(item!!)
    }

    fun setList(list:List<Country>?){
        this.list=list
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