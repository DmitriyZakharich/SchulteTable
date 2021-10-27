package ru.schultetabledima.schultetable.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.schultetabledima.schultetable.R
import ru.schultetabledima.schultetable.advice.AdviceFragment
import ru.schultetabledima.schultetable.settings.SettingsFragment
import ru.schultetabledima.schultetable.statistic.StatisticFragment
import ru.schultetabledima.schultetable.table.mvp.view.TableFragment
import ru.schultetabledima.schultetable.utils.enterFromLeftExitToRight
import ru.schultetabledima.schultetable.utils.enterFromRightExitToLeft
import ru.schultetabledima.schultetable.utils.getNavOptions

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        bottomNavigationView = findViewById(R.id.mainBottomNavigationView)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainContainerView) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }

    override fun onResume() {
        super.onResume()
        setBottomNavigationListener()
    }

    private fun setBottomNavigationListener() {
        bottomNavigationView.setOnItemSelectedListener { item ->

            var currentFragment = navHostFragment.childFragmentManager.fragments[0]
            var newFragment = 0
            var customNavOptions: NavOptions? = null
            var tagFragment: String? = null

            when (item.itemId) {
                R.id.adviceFragment ->
                    if (currentFragment::class != AdviceFragment::class) {
                        newFragment = R.id.adviceFragment
                        tagFragment = "adviceFragment"

                        customNavOptions = getNavOptions(R.id.adviceFragment)
                    }

                R.id.statisticFragment -> {
                    if (currentFragment::class != StatisticFragment::class) {
                        newFragment = R.id.statisticFragment
                        tagFragment = "statisticFragment"

                        customNavOptions = if (currentFragment::class == AdviceFragment::class) {
                            enterFromRightExitToLeft()

                        } else {
                            enterFromLeftExitToRight()
                        }
                    }
                }
                R.id.settingsFragment -> {
                    if (currentFragment::class != SettingsFragment::class) {
                        newFragment = R.id.settingsFragment
                        tagFragment = "settingsFragment"


                        customNavOptions =
                            if (currentFragment::class == AdviceFragment::class || currentFragment::class == StatisticFragment::class)
                                enterFromRightExitToLeft()
                            else
                                enterFromLeftExitToRight()
                    }
                }
                R.id.tableFragment -> {
                    if (currentFragment::class != TableFragment::class) {
                        newFragment = R.id.tableFragment
                        tagFragment = "tableFragment"
                        customNavOptions = enterFromRightExitToLeft()
                    }
                }
            }

            if (newFragment != 0) {

                navController.navigate(newFragment, null, customNavOptions)
                supportFragmentManager.executePendingTransactions()
            }
            true
        }

    }

    fun visibilityBottomNavigationView(visibility: Int) {
        findViewById<BottomNavigationView>(R.id.mainBottomNavigationView).visibility = visibility
    }
}


fun NavController.popBackStackAllInstances(destination: Int, inclusive: Boolean): Boolean {
    var popped: Boolean
    while (true) {
        popped = popBackStack(destination, inclusive)
        if (!popped) {
            break
        }
    }
    return popped
}