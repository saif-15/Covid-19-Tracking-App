package com.stechlabs.covid_19.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.stechlabs.covid_19.R
import com.stechlabs.covid_19.models.persistence.Country
import kotlinx.android.synthetic.main.recyclerview_layout.view.*

class DashboardItemAdapter : RecyclerView.Adapter<DashboardItemAdapter.MyViewHolder>() {

    companion object {
        private val lineSet = linkedMapOf(
            "label1" to 2f,
            "label2" to 0.5f,
            "label3" to 0.3f,
            "label4" to 0.1f,
            "label5" to 1f
        )
        private val animationDuration = 1000L
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_layout, parent, false)
        )
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position])
    }

    private var list = listOf<Country>()
    override fun getItemCount() = list.size


    fun setList(list: List<Country>) {
        this.list = list
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title = itemView.title_recyclerview
        val cases = itemView.cases_recyclerview
        val chartView = itemView.linechart
        val parent = itemView.cardview_parent
        fun bind(item: Country) {
            title.text = item.country
            cases.text = item.cases.toString()
            chartView.gradientFillColors =
                intArrayOf(
                    Color.parseColor("#81FFFFFF"),
                    Color.TRANSPARENT
                )
            chartView.animation.duration = animationDuration
            chartView.animate(lineSet)
        }
    }
}