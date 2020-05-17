package com.stechlabs.covid_19.ui.listing

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.stechlabs.covid_19.R
import com.stechlabs.covid_19.ui.adapters.CountryAdapter
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment() {

    private lateinit var listViewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        listViewModel =
            ViewModelProviders.of(this).get(ListViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_list, container, false)

        root.refresh_layout.isRefreshing = true
        root.refresh_layout.setColorSchemeColors(
            ContextCompat.getColor(
                activity!!.applicationContext,
                R.color.colorPrimary
            )
        )
        root.refresh_layout.layoutTransition = LayoutTransition()

        //init recyclerview
        root.main_recyclerview.layoutManager = LinearLayoutManager(this.context)
        root.main_recyclerview.setHasFixedSize(true)
        val adapter = CountryAdapter()

        //Observing data from db
        listViewModel.getCountries().observe(this, Observer {
            if (it.isNotEmpty()) {
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
                root.refresh_layout.isRefreshing = false
            }
        })
        root.main_recyclerview.adapter = adapter

        // Text change listener to a Searchview
        val listener = object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                listViewModel.getCountriesByQuery(query!!).observe(this@ListFragment,
                    Observer {
                        adapter.submitList(it)
                        adapter.notifyDataSetChanged()

                    })
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                listViewModel.getCountriesByQuery(newText!!).observe(this@ListFragment,
                    Observer {
                        adapter.submitList(it)
                        adapter.notifyDataSetChanged()

                    })
                return true
            }
        }
        root.searchView.setOnQueryTextListener(listener)
        root.searchView.setOnSearchClickListener {
            openSearch()
        }


        //Refresh Layout
        root.refresh_layout.setOnRefreshListener {
            listViewModel.getCountries().observe(this, Observer {
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
                root.refresh_layout.isRefreshing = false
            })
        }

        return root
    }

    override fun onDestroy() {
        super.onDestroy()
        listViewModel.cancelJobs()
    }

    private fun openSearch() {
        view!!.searchView.visibility = View.VISIBLE
        val circularReveal = ViewAnimationUtils.createCircularReveal(
            view!!.searchView,
            view!!.searchView.left,
            (view!!.searchView.top + view!!.searchView.bottom) / 2,
            0f, view!!.searchView.width.toFloat()
        )
        circularReveal.duration = 700
        circularReveal.start()
    }
}