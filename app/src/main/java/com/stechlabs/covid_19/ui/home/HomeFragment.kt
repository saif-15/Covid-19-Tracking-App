package com.stechlabs.covid_19.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.stechlabs.covid_19.R
import com.stechlabs.covid_19.ui.adapters.CountryAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {

        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView=root.country_recyclerview
        recyclerView.layoutManager=LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        homeViewModel.getGlobalResults().observe(this, Observer {
            val adapter=CountryAdapter()
            recyclerView.adapter=adapter
            adapter.setList(it)
        })
        return root
    }

    override fun onDestroy() {
        homeViewModel.cancelJobs()
        super.onDestroy()
    }

}