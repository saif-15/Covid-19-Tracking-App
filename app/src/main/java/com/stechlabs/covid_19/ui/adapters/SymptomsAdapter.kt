package com.stechlabs.covid_19.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.stechlabs.covid_19.R
import com.stechlabs.covid_19.models.ui.Symptoms
import kotlinx.android.synthetic.main.item_symptom.view.*

class SymptomsAdapter : RecyclerView.Adapter<SymptomsAdapter.MyViewHolder>() {


    private lateinit var listener: (Symptoms) -> Unit
    private var list = listOf<Symptoms>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_symptom, parent, false)
        return MyViewHolder(view)
    }

    fun setList(list: List<Symptoms>) {
        this.list = list
    }

    override fun getItemCount(): Int {
        return list.size

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val symptom = list.get(position)
        holder.bind(symptom, listener)
    }

    fun setOnItemClickListener(listener: (Symptoms) -> Unit) {
        this.listener = listener
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title: TextView = itemView.symptom_title
        val animation: LottieAnimationView = itemView.symptom_animation

        fun bind(s: Symptoms, listener: (Symptoms) -> Unit) {
            title.text = s.title
            animation.setAnimation(s.animationDrawable)
            itemView.setOnClickListener {
                listener(s)
            }
        }
    }

}