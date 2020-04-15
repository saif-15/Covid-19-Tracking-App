package com.stechlabs.covid_19.ui.home

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val recyclerView=root.country_recyclerview
        recyclerView.layoutManager=LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        homeViewModel.getCountriesResultFromDB().observe(this, Observer {
            val adapter = CountryAdapter(it)
            recyclerView.adapter=adapter
        })
        if (isNetworkAvailablity()) {
            Toast.makeText(activity!!.applicationContext, "internet connected", Toast.LENGTH_LONG)
                .show()
        } else {
            Toast.makeText(
                activity!!.applicationContext,
                "Must be connect to internet first time",
                Toast.LENGTH_LONG
            )
                .show()
        }


        return root
    }


    private fun isNetworkAvailablity(): Boolean {
        val manager = activity!!.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo: NetworkInfo? = manager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }


    override fun onDestroy() {
        homeViewModel.cancelJobs()
        super.onDestroy()
    }
}