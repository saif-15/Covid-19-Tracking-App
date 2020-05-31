package com.stechlabs.covid_19.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.stechlabs.covid_19.R
import com.stechlabs.covid_19.models.persistence.Country
import kotlinx.android.synthetic.main.recyclerview_layout.view.*
import java.text.DecimalFormat

class DashboardItemAdapter(val context: Context, val params: String) :
    RecyclerView.Adapter<DashboardItemAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_layout, parent, false)
        )
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(list[position], params)
    }

    private var list = listOf<Country>()
    override fun getItemCount() = list.size


    fun setList(list: List<Country>) {
        this.list = list
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title = itemView.title_recyclerview
        val cases = itemView.cases_recyclerview
        val parent = itemView.relative_parent

        fun bind(item: Country, params: String) {

            title.text = item.country
            when (params) {

                "cases" -> setCases(item.cases)
                "casesToday" -> setCasesToday(item.todayCases)
                "deaths" -> setDeaths(item.deaths)
                "deathsToday" -> setDeathsToday(item.todayDeaths)
                "tests" -> setTests(item.totalTests)
                "recovered" -> setRecovered(item.recovered)

            }

        }

        private fun setRecovered(item: Int) {
            parent.background =
                ContextCompat.getDrawable(context, R.drawable.recovered_bg)
            cases.apply {
                setAnimationDuration(500)
                setDecimalFormat(DecimalFormat("###,###,###"))
                countAnimation(0, item)
                setInterpolator(AccelerateInterpolator())
            }
        }

        private fun setTests(item: Int) {

            cases.apply {
                setAnimationDuration(500)
                setDecimalFormat(DecimalFormat("###,###,###"))
                countAnimation(0, item)
                setInterpolator(AccelerateInterpolator())
            }

        }

        private fun setCasesToday(item: Int) {
            parent.background = ContextCompat.getDrawable(context, R.drawable.cases_bg)
            cases.apply {
                setAnimationDuration(500)
                setDecimalFormat(DecimalFormat("###,###,###"))
                countAnimation(0, item)
                setInterpolator(AccelerateInterpolator())
            }

        }

        private fun setDeathsToday(item: Int) {
            parent.background =
                ContextCompat.getDrawable(context, R.drawable.deaths_bg)
            cases.apply {
                setAnimationDuration(500)
                setDecimalFormat(DecimalFormat("###,###,###"))
                countAnimation(0, item)
                setInterpolator(AccelerateInterpolator())
            }

        }

        private fun setDeaths(item: Int) {
            parent.background =
                ContextCompat.getDrawable(context, R.drawable.deaths_bg)
            cases.apply {
                setAnimationDuration(500)
                setDecimalFormat(DecimalFormat("###,###,###"))
                countAnimation(0, item)
                setInterpolator(AccelerateInterpolator())
            }
        }


        private fun setCases(item: Int) {
            parent.background = ContextCompat.getDrawable(context, R.drawable.cases_bg)
            cases.apply {
                setAnimationDuration(500)
                setDecimalFormat(DecimalFormat("###,###,###"))
                countAnimation(0, item)
                setInterpolator(AccelerateInterpolator())
            }

        }

    }


}
