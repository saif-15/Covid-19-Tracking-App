package com.stechlabs.covid_19.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.stechlabs.covid_19.R
import com.stechlabs.covid_19.ui.adapters.DashboardItemAdapter
import kotlinx.android.synthetic.main.fragment_dashboard.view.*

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.apply {

            dashboardViewModel.getGlobalResult().observe(this@DashboardFragment, Observer {
                global_today_cases.text = "+${it.todayCases}"
                global_today_deaths.text = "+${it.todayDeaths}"
                global_total_cases.text = it.cases.toString()
                global_total_recovered.text = it.recovered.toString()
                global_total_deaths.text = it.deaths.toString()
            })

            // Most Affected Countries Recyclerview
            most_affected_countries_recyclerview.layoutManager =
                LinearLayoutManager(
                    this@DashboardFragment.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            most_affected_countries_recyclerview.setHasFixedSize(true)
            most_affected_countries_recyclerview.isNestedScrollingEnabled = false
            val mostAdapter = DashboardItemAdapter()
            dashboardViewModel.getTop10Countries().observe(this@DashboardFragment,
                Observer {
                    if (it.isNotEmpty()) {
                        println("list 1..$it")
                        mostAdapter.setList(it!!)
                        mostAdapter.notifyDataSetChanged()
                    }

                })

            // Least Affested Countries Recyclerview
            least_affected_countries_recyclerview.layoutManager =
                LinearLayoutManager(
                    this@DashboardFragment.context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            least_affected_countries_recyclerview.setHasFixedSize(true)
            least_affected_countries_recyclerview.isNestedScrollingEnabled = false
            val leastAdapter = DashboardItemAdapter()
            dashboardViewModel.getBottom10Countries().observe(this@DashboardFragment,
                Observer {
                    if (it.isNotEmpty()) {
                        println("list 2..$it")
                        leastAdapter.setList(it!!)
                        leastAdapter.notifyDataSetChanged()
                    }
                })
            least_affected_countries_recyclerview.adapter = leastAdapter
            most_affected_countries_recyclerview.adapter = mostAdapter
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        dashboardViewModel.cancelJobs()
    }
}