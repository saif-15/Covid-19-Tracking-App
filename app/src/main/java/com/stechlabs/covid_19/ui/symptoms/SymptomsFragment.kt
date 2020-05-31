package com.stechlabs.covid_19.ui.symptoms

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.stechlabs.covid_19.R
import com.stechlabs.covid_19.ui.adapters.ItemAdapter
import kotlinx.android.synthetic.main.fragment_symptoms.view.*


class SymptomsFragment : Fragment(R.layout.fragment_symptoms) {

    private lateinit var symptomsViewModel: SymptomsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        symptomsViewModel = ViewModelProviders.of(this).get(SymptomsViewModel::class.java)

        view.apply {

            /**    Symptoms Recyclerview     **/
            symptoms_recyclerview.apply {
                layoutManager =
                    LinearLayoutManager(
                        this@SymptomsFragment.context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                setHasFixedSize(true)
                val symptomsAdapter = ItemAdapter()
                symptomsViewModel.observeSymptomsList()
                    .observe(this@SymptomsFragment, Observer {
                        symptomsAdapter.setList(it)
                    })
                adapter = symptomsAdapter
                symptomsAdapter.setOnItemClickListener {
                    Toast.makeText(this@SymptomsFragment.context, it.title, Toast.LENGTH_SHORT)
                        .show()
                }
            }

            /**       Preventions Recyclerview   **/
            preventions_recyclerview.apply {
                layoutManager =
                    LinearLayoutManager(
                        this@SymptomsFragment.context,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                setHasFixedSize(true)
                val adapterPrevention = ItemAdapter()
                symptomsViewModel.observePreventionsList().observe(this@SymptomsFragment,
                    Observer {
                        adapterPrevention.setList(it)
                    })
                adapter = adapterPrevention
                adapterPrevention.setOnItemClickListener {
                    Toast.makeText(this@SymptomsFragment.context, it.title, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun isNetworkAvailablity(): Boolean {
        val manager = activity!!.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo: NetworkInfo? = manager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    override fun onDestroy() {
        super.onDestroy()

    }

}

