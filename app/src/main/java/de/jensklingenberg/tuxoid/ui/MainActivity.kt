package de.jensklingenberg.tuxoid.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import de.jensklingenberg.tuxoid.R

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()

    }



    private fun setupNavigation() {
        navController = findNavController(R.id.mainNavigationFragment)
       // setupActionBarWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->


        }
    }
}
