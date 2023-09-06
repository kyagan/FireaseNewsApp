package com.example.firebasenewsexample.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.firebasenewsexample.R
import com.example.firebasenewsexample.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHost=supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController=navHost.navController
        val appBarConfiguration= AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController,appBarConfiguration)
    }
}