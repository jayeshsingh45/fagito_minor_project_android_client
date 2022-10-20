package com.nexlatech.fagito

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.nexlatech.fagito.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        /*There is a weird background to solve it we are setting background to null*/
        binding.bottomNavigationView.background = null
        /*there is a invisible icon and we are setting it false so that no one can click on
        it. It is for layout of bottom navigation to look good.*/
        binding.bottomNavigationView.menu.getItem(1).isEnabled = false

        /*Telling our bottomNavigationView where is our navHostFragment.*/
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView2) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)



    }
}