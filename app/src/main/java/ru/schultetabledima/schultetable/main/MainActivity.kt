package ru.schultetabledima.schultetable.main

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yandex.mobile.ads.common.MobileAds
import ru.schultetabledima.schultetable.R
import ru.schultetabledima.schultetable.advice.AdviceFragment
import ru.schultetabledima.schultetable.settings.SettingsFragment
import ru.schultetabledima.schultetable.statistic.StatisticFragment
import ru.schultetabledima.schultetable.table.view.TableFragment
import ru.schultetabledima.schultetable.utils.enterFromLeftExitToRight
import ru.schultetabledima.schultetable.utils.enterFromRightExitToLeft

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController
    private companion object {
        const val YANDEX_MOBILE_ADS_TAG = "YandexMobileAds"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.mainBottomNavigationView)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.mainContainerView) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(bottomNavigationView, navController)

        screenSettings()

        MobileAds.initialize(this){
            Log.d(YANDEX_MOBILE_ADS_TAG, "SDK initialized")
        }
    }

    private fun screenSettings() {
        @Suppress("DEPRECATION")
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                window.insetsController?.hide(WindowInsets.Type.statusBars())
            } else {
                window.setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN
                )
            }
        }
    }


    override fun onResume() {
        super.onResume()
        setBottomNavigationListener()
    }

    private fun setBottomNavigationListener() {
        bottomNavigationView.setOnItemSelectedListener { item ->

            val currentFragment = navHostFragment.childFragmentManager.fragments[0]
            var newFragment = 0
            var customNavOptions: NavOptions? = null

            when (item.itemId) {
                R.id.adviceFragment ->
                    if (currentFragment::class != AdviceFragment::class) {
                        newFragment = R.id.adviceFragment
                        customNavOptions = enterFromLeftExitToRight()
                    }

                R.id.statisticFragment -> {
                    if (currentFragment::class != StatisticFragment::class) {
                        newFragment = R.id.statisticFragment
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

    fun setWindowFlags(commandString: String) {
        if (commandString == "Add_Flag_KEEP_SCREEN_ON")
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        else
            window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    }
}
