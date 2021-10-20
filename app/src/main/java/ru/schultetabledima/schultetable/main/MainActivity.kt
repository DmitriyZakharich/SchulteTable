package ru.schultetabledima.schultetable.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.navOptions
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.schultetabledima.schultetable.R
import ru.schultetabledima.schultetable.advice.AdviceFragment
import ru.schultetabledima.schultetable.settings.SettingsFragment
import ru.schultetabledima.schultetable.statistic.StatisticFragment
import ru.schultetabledima.schultetable.table.mvp.view.TableFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.mainBottomNavigationView)
        var navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        bottomNavigationView.setOnItemSelectedListener { item ->

            var currentFragment = navHostFragment.childFragmentManager.fragments[0]

            val navOptions1: NavOptions = navOptions {
                anim {
                    enter = R.anim.slide_in_right
                    exit = R.anim.slide_out_left

                    popEnter = R.anim.slide_in_left
                    popExit = R.anim.slide_out_right
                }
            }
            val navOptions2: NavOptions = navOptions {
                anim {
                    enter = R.anim.slide_in_left
                    exit = R.anim.slide_out_right

                    popEnter = R.anim.slide_in_right
                    popExit = R.anim.slide_out_left
                }
            }
            when (item.itemId) {
                R.id.adviceFragment -> {
                    if (currentFragment::class != AdviceFragment::class)
                        navController.navigate(R.id.adviceFragment, null, navOptions2)
                }
                R.id.statisticFragment -> {

                    if (currentFragment::class != StatisticFragment::class)
                        if (currentFragment::class == AdviceFragment::class)
                            navController.navigate(R.id.statisticFragment, null, navOptions1)
                        else {
                            navController.navigate(R.id.statisticFragment, null, navOptions2)
                        }
                }
                R.id.settingsFragment -> {
                    if (currentFragment::class != SettingsFragment::class)
                        if (currentFragment::class == AdviceFragment::class || currentFragment::class == StatisticFragment::class)
                            navController.navigate(R.id.settingsFragment, null, navOptions1)
                        else
                            navController.navigate(R.id.settingsFragment, null, navOptions2)
                }
                R.id.tableFragment -> {
                    if (currentFragment::class != TableFragment::class)
                        navController.navigate(R.id.tableFragment, null, navOptions1)
                }
            }
            true
        }
    }

    fun visibilityBottomNavigationView(visibility: Int) {
        findViewById<BottomNavigationView>(R.id.mainBottomNavigationView).visibility = visibility
    }
}