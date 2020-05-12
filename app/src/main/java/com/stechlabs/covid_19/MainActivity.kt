package com.stechlabs.covid_19

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {


    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        button_dashboard.setOnClickListener(this)
        button_preventions.setOnClickListener(this)
        button_symptoms.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.button_dashboard -> {
                navigation(
                    "DashboardFragment",
                    "PreventionsFragment",
                    "SymptonsFragment",
                    R.id.action_preventionsFragment_to_dashboardFragment,
                    R.id.action_symptonsFragment_to_dashboardFragment
                )
            }
            R.id.button_preventions -> {
                navigation(
                    "PreventionsFragment",
                    "DashboardFragment",
                    "SymptonsFragment",
                    R.id.action_dashboardFragment_to_preventionsFragment,
                    R.id.action_symptonsFragment_to_preventionsFragment
                )
            }
            R.id.button_symptoms -> {
                navigation(
                    "SymptonsFragment",
                    "DashboardFragment",
                    "PreventionsFragment",
                    R.id.action_dashboardFragment_to_symptonsFragment,
                    R.id.action_preventionsFragment_to_symptonsFragment
                )
            }
        }
    }

    private fun navigation(from: String, to: String, and: String, id: Int, id2: Int) {

        if (navController.currentDestination!!.label != from &&
            navController.currentDestination!!.label == to
        )
            navController.navigate(id)

        if (navController.currentDestination!!.label != from &&
            navController.currentDestination!!.label == and
        )
            navController.navigate(id2)
    }
}
