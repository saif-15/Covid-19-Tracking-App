package com.stechlabs.covid_19.ui.symptoms

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.stechlabs.covid_19.R
import com.stechlabs.covid_19.models.ui.Preventions
import com.stechlabs.covid_19.models.ui.Symptoms
import com.stechlabs.covid_19.ui.adapters.PreventionAdpater
import com.stechlabs.covid_19.ui.adapters.SymptomsAdapter
import kotlinx.android.synthetic.main.fragment_symptoms.view.*


class SymptonsFragment : Fragment() {

    private lateinit var symptomsViewModel: SymptomsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_symptoms, container, false)

        // Symptoms RecyclerView Adapter
        val recyclerView = root.symptoms_recyclerview
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)
        val adapter = SymptomsAdapter()
        val item = populateSymptomsList()
        adapter.setList(item)
        recyclerView.adapter = adapter

        //Symptoms RecyclerView click listener
        adapter.setOnItemClickListener {
            view?.symptom_animation?.setAnimation(it.animationDrawable)
            view!!.symptom_animation.playAnimation()
        }


        // Prevention RecyclerView
        val recyclerviewPreventions = root.preventions_recyclerview
        recyclerviewPreventions.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerviewPreventions.setHasFixedSize(true)
        val adapterPrevention = PreventionAdpater()
        val item1 = populatePreventionsList()
        adapterPrevention.setList(item1)
        recyclerviewPreventions.adapter = adapterPrevention

        // Prevention recyclerview listener
        adapterPrevention.setOnItemClickListener {
            view!!.prevention_animation.setAnimation(it.animationDrawable)
            view!!.prevention_animation.playAnimation()
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
        if (::symptomsViewModel.isInitialized)
            symptomsViewModel.cancelJobs()
        super.onDestroy()
    }

    private fun populateSymptomsList(): List<Symptoms> {
        val list = listOf(
            Symptoms("Fever", "jshdjhjdhsjh", R.raw.fever),
            Symptoms("Temp", "ffdfd", R.raw.temperature),
            Symptoms("Breathe", "husugdgs", R.raw.lungs),
            Symptoms("Sneeze", "khhds", R.raw.sneezing)
        )
        return list
    }

    private fun populatePreventionsList(): List<Preventions> {
        val list = listOf(
            Preventions("Wear Mask", "jshdjhjdhsjh", R.raw.mask),
            Preventions("Wash Hand", "ffdfd", R.raw.handwashing),
            Preventions("Social Distance", "husugdgs", R.raw.distancing),
            Preventions("Stay Home", "khhds", R.raw.stayhome)
        )
        return list
    }

}

