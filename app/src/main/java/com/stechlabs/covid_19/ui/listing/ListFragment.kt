package com.stechlabs.covid_19.ui.listing

import android.animation.LayoutTransition
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.stechlabs.covid_19.R
import com.stechlabs.covid_19.ui.adapters.CountryAdapter
import kotlinx.android.synthetic.main.fragment_list.view.*

class ListFragment : Fragment(R.layout.fragment_list) {

    private lateinit var listViewModel: ListViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listViewModel =
            ViewModelProviders.of(this).get(ListViewModel::class.java)

        view.apply {

            /**       Refresh layout properties         **/
            refresh_layout.isRefreshing = true
            refresh_layout.setColorSchemeColors(
                ContextCompat.getColor(
                    activity!!.applicationContext,
                    R.color.colorPrimary
                )
            )
            refresh_layout.layoutTransition = LayoutTransition()

            /**        List of All Countries Recyclerview          **/
            val adapter = CountryAdapter()
            with(main_recyclerview) {
                layoutManager = LinearLayoutManager(this.context)
                setHasFixedSize(true)
                listViewModel.getCountries().observe(this@ListFragment, Observer {
                    if (it.isNotEmpty()) {
                        adapter.submitList(it)
                        adapter.notifyDataSetChanged()
                        this@apply.refresh_layout.isRefreshing = false
                    }
                })
                main_recyclerview.adapter = adapter
            }


            /**            SearchView TextChanged  Listener           **/
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
            searchView.setOnQueryTextListener(listener)
            searchView.setOnSearchClickListener {
                openSearch()
            }


            /**                Refresh Layout                **/
            refresh_layout.setOnRefreshListener {
                listViewModel.getCountries().observe(this@ListFragment, Observer {
                    adapter.submitList(it)
                    adapter.notifyDataSetChanged()
                    refresh_layout.isRefreshing = false
                })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        listViewModel.cancelJobs()
    }


    /**     SearchView Reveal Animation **/
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