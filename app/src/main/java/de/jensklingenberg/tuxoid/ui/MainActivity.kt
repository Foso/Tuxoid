package de.jensklingenberg.tuxoid.ui

import android.app.Activity
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import de.jensklingenberg.tuxoid.R

class MainActivity : AppCompatActivity() {

    companion object{

        private var activity : Activity?=null


        @JvmField
        val  ARG_LEVEL = "ARG_LEVEL"

        @JvmStatic
        fun getActivity()= activity

    }

    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this
        setContentView(R.layout.activity_main)



        setupNavigation()

    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    private fun setupNavigation() {
        navController = findNavController(R.id.mainNavigationFragment)
       // setupActionBarWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->


        }
    }
}
