package ru.schultetabledima.schultetable.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.schultetabledima.schultetable.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.mainBottomNavigationView)
        val navController = (supportFragmentManager.findFragmentById(R.id.mainContainerView) as NavHostFragment).navController
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

    }

    fun visibilityBottomNavigationView(visibility: Int){
        findViewById<BottomNavigationView>(R.id.mainBottomNavigationView).visibility = visibility
    }
}