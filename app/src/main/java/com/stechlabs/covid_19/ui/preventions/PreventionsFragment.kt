package com.stechlabs.covid_19.ui.preventions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.stechlabs.covid_19.R

class PreventionsFragment : Fragment() {

    private lateinit var preventionsViewModel: PreventionsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        preventionsViewModel =
            ViewModelProviders.of(this).get(PreventionsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_preventions, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        preventionsViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}