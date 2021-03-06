package com.stechlabs.covid_19.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.stechlabs.covid_19.R
import com.stechlabs.covid_19.models.ui.Items
import kotlinx.android.synthetic.main.item_symptom.view.*

class ItemAdapter : RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {

    private var list = listOf<Items>()
    private lateinit var listener: (Items) -> Unit


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_symptom, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setList(list: List<Items>) {
        this.list = list
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setOnItemClickListener(listener: (Items) -> Unit) {
        this.listener = listener
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title = itemView.symptom_title
        val animation: LottieAnimationView = itemView.symptom_animation
        fun bind(item: Items) {
            title.text = item.title
            animation.setAnimation(item.animationDrawable)
            itemView.setOnClickListener {
                listener(item)
            }
        }
    }
}