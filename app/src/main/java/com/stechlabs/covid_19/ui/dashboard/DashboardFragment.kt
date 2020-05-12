package com.stechlabs.covid_19.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.stechlabs.covid_19.R
import com.stechlabs.covid_19.ui.adapters.CountryAdapter
import kotlinx.android.synthetic.main.fragment_dashboard.view.*

class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var _popupMenu: PopupMenu

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

        root.refresh_layout.isRefreshing = true

        //init recyclerview
        root.main_recyclerview.layoutManager = LinearLayoutManager(this.context)
        root.main_recyclerview.setHasFixedSize(true)
        val adapter = CountryAdapter()

        //Observing data from db
        dashboardViewModel.getCountriesResultFromDB().observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
            root.refresh_layout.isRefreshing = false
        })
        root.main_recyclerview.adapter = adapter

        // Text change listener to a Searchview
        val listener = object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                dashboardViewModel.getSearchedCountries(query!!).observe(this@DashboardFragment,
                    Observer {
                        adapter.setList(it)
                        adapter.notifyDataSetChanged()

                    })
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                dashboardViewModel.getSearchedCountries(newText!!).observe(this@DashboardFragment,
                    Observer {
                        adapter.setList(it)
                        adapter.notifyDataSetChanged()

                    })
                return true
            }
        }
        root.searchView.setOnQueryTextListener(listener)


        //Refresh Layout
        root.refresh_layout.setOnRefreshListener {
            dashboardViewModel.getCountriesResultFromDB().observe(this, Observer {
                adapter.setList(it)
                adapter.notifyDataSetChanged()
                root.refresh_layout.isRefreshing = false
            })
        }
        return root
    }


    override fun onDestroy() {
        dashboardViewModel.cancelJobs()
        super.onDestroy()
    }
}