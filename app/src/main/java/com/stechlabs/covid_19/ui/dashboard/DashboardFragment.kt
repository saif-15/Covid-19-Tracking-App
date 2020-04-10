package com.stechlabs.covid_19.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.stechlabs.covid_19.R

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val cases:TextView= root.findViewById(R.id.total_cases)
        val recovered: TextView = root.findViewById(R.id.total_recovered)
        val deaths: TextView = root.findViewById(R.id.total_deaths)
        dashboardViewModel.getGlobalResults().observe(this, Observer {
            cases.text=it.body()?.cases.toString()
            recovered.text=it.body()?.recovered.toString()
            deaths.text=it.body()?.deaths.toString()
        })
        return root
    }

    override fun onDestroy() {
        dashboardViewModel.cancelJob()
        super.onDestroy()
    }
}