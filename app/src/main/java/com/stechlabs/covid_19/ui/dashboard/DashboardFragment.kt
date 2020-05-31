package com.stechlabs.covid_19.ui.dashboard

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.stechlabs.covid_19.R
import com.stechlabs.covid_19.models.persistence.Country
import com.stechlabs.covid_19.ui.adapters.DashboardItemAdapter
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import java.text.DecimalFormat

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dashboardViewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)


        view.apply {


            /** Swipe Refresh Layout **/
            swipe_refresh_dashboard.setColorSchemeColors(
                ContextCompat.getColor(
                    activity!!.applicationContext,
                    R.color.colorPrimary
                )
            )
            swipe_refresh_dashboard.setOnRefreshListener {
                dashboardViewModel.getGlobalResult()
                    .observe(this@DashboardFragment, Observer {
                        setGlobalDataOnViews(this, it)
                        swipe_refresh_dashboard.isRefreshing = false
                    })
            }


            dashboardViewModel.getGlobalResult().observe(this@DashboardFragment, Observer {
                setGlobalDataOnViews(this, it)
                last_update.text = it.date.toString()
            })

            /** Most Affected Countries Recyclerview **/
            most_affected_countries_recyclerview.apply {
                layoutManager =
                    LinearLayoutManager(
                        this@DashboardFragment.context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                setHasFixedSize(true)
                val mostAdapter = DashboardItemAdapter(activity!!.applicationContext, "cases")
                isNestedScrollingEnabled = false

                dashboardViewModel.getTop10Countries().observe(this@DashboardFragment,
                    Observer {
                        if (it.isNotEmpty()) {
                            println("list 1..$it")
                            mostAdapter.setList(it)
                            mostAdapter.notifyDataSetChanged()

                        }
                    })
                adapter = mostAdapter
            }

            /** Least Affected Countries Recyclerview    **/
            least_affected_countries_recyclerview.apply {
                layoutManager =
                    LinearLayoutManager(
                        this@DashboardFragment.context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                least_affected_countries_recyclerview.setHasFixedSize(true)
                least_affected_countries_recyclerview.isNestedScrollingEnabled = false
                val leastAdapter = DashboardItemAdapter(activity!!.applicationContext, "cases")
                dashboardViewModel.getBottom10Countries().observe(this@DashboardFragment,
                    Observer {
                        if (it.isNotEmpty()) {
                            println("list 2..$it")
                            leastAdapter.setList(it!!)
                            leastAdapter.notifyDataSetChanged()
                        }
                    })
                adapter = leastAdapter
            }


            /**    Most Deaths By Country Recyclerview  **/
            most_deaths_countries_recyclerview.apply {
                layoutManager =
                    LinearLayoutManager(
                        this@DashboardFragment.context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                most_deaths_countries_recyclerview.setHasFixedSize(true)
                most_deaths_countries_recyclerview.isNestedScrollingEnabled = false
                val mostDeathsAdapter =
                    DashboardItemAdapter(activity!!.applicationContext, "deaths")
                dashboardViewModel.getCountriesByDeaths().observe(this@DashboardFragment,
                    Observer {
                        if (it.isNotEmpty()) {
                            println("list 3..$it")
                            mostDeathsAdapter.setList(it)
                            mostDeathsAdapter.notifyDataSetChanged()
                        }
                    })
                adapter = mostDeathsAdapter
            }

            /**      Most Deaths Today Recyclerview           **/
            most_deaths_today_countries_recyclerview.apply {
                layoutManager =
                    LinearLayoutManager(
                        this@DashboardFragment.context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                setHasFixedSize(true)
                isNestedScrollingEnabled = false
                val mostDeathsToday =
                    DashboardItemAdapter(activity!!.applicationContext, "deathsToday")
                dashboardViewModel.getCountriesByDeathsToday().observe(this@DashboardFragment,
                    Observer {
                        if (it.isNotEmpty()) {
                            println("list 4..$it")
                            mostDeathsToday.setList(it!!)
                            mostDeathsToday.notifyDataSetChanged()
                        }
                    })
                adapter = mostDeathsToday
            }

            /**  Most Cases Today Recyclerview   **/
            most_cases_today_countries_recyclerview.apply {
                layoutManager =
                    LinearLayoutManager(
                        this@DashboardFragment.context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                setHasFixedSize(true)
                isNestedScrollingEnabled = false
                val mostCasesToday =
                    DashboardItemAdapter(activity!!.applicationContext, "casesToday")
                dashboardViewModel.getCountriesByTodayCases().observe(this@DashboardFragment,
                    Observer {
                        if (it.isNotEmpty()) {
                            println("list 5..$it")
                            mostCasesToday.setList(it!!)
                            mostCasesToday.notifyDataSetChanged()
                        }
                    })
                adapter = mostCasesToday
            }


            /**    Most Test By Countries Recyclerview   **/
            most_tests_countries_recyclerview.apply {
                layoutManager =
                    LinearLayoutManager(
                        this@DashboardFragment.context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                setHasFixedSize(true)
                isNestedScrollingEnabled = false
                val mostTestToday = DashboardItemAdapter(activity!!.applicationContext, "tests")
                dashboardViewModel.getCountriesByTests().observe(this@DashboardFragment,
                    Observer {
                        if (it.isNotEmpty()) {
                            println("list 6..$it")
                            mostTestToday.setList(it!!)
                            mostTestToday.notifyDataSetChanged()
                        }
                    })

                adapter = mostTestToday
            }


            /**    Most Recovered By Countries Recyclerview   **/
            most_recovered_countries_recyclerview.apply {
                layoutManager =
                    LinearLayoutManager(
                        this@DashboardFragment.context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                setHasFixedSize(true)
                isNestedScrollingEnabled = false
                val mostRecovered = DashboardItemAdapter(activity!!.applicationContext, "recovered")
                dashboardViewModel.getCountriesByRecovered().observe(this@DashboardFragment,
                    Observer {
                        if (it.isNotEmpty()) {
                            println("list 7..$it")
                            mostRecovered.setList(it!!)
                            mostRecovered.notifyDataSetChanged()
                        }
                    })

                adapter = mostRecovered
            }


        }
    }

    private fun setGlobalDataOnViews(view: View, it: Country?) {
        it?.let {
            view.global_today_cases.apply {
                val text1 = "+${it.todayCases}"
                text = text1

            }
            view.global_today_deaths.apply {
                val text2 = "+${it.todayDeaths}"
                text = text2

            }
            view.global_total_cases.apply {
                setDecimalFormat(DecimalFormat("###,###,###"))
                setAnimationDuration(1500)
                text = it.cases.toString()
                countAnimation(0, it.cases)

            }
            view.global_total_recovered.apply {
                setAnimationDuration(1500)
                setDecimalFormat(DecimalFormat("###,###,###"))
                text = it.recovered.toString()
                countAnimation(0, it.recovered)

            }
            view.global_total_deaths.apply {
                setAnimationDuration(1500)
                setDecimalFormat(DecimalFormat("###,###,###"))
                text = it.deaths.toString()
                countAnimation(0, it.deaths)

            }
            view.global_total_active.apply {
                setAnimationDuration(1500)
                setDecimalFormat(DecimalFormat("###,###,###"))
                text = it.active.toString()
                countAnimation(0, it.active)

            }
            view.global_total_cases_mm.apply {
                setAnimationDuration(1500)
                text = it.casesPerOneMillion.toString()
                setDecimalFormat(DecimalFormat("###,###,###"))
                countAnimation(0, it.casesPerOneMillion)
            }
            view.global_total_critical.apply {
                setAnimationDuration(1500)
                setDecimalFormat(DecimalFormat("###,###,###"))
                text = it.critical.toString()
                countAnimation(0, it.critical)

            }

        }


    }

    override fun onDestroy() {
        super.onDestroy()
        if (::dashboardViewModel.isInitialized)
            dashboardViewModel.cancelJobs()
    }
}