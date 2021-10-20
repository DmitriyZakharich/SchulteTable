package ru.schultetabledima.schultetable.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.schultetabledima.schultetable.R


class MainFragment : Fragment(R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val bottomNavigationView = view.findViewById<BottomNavigationView>(R.id.mainBottomNavigationView)
//        val navController = (childFragmentManager.findFragmentById(R.id.mainContainerView) as NavHostFragment).navController
//        NavigationUI.setupWithNavController(bottomNavigationView, navController)
    }

    fun visibilityBottomNavigationView(visibility: Int){
        requireView().findViewById<BottomNavigationView>(R.id.mainBottomNavigationView).visibility = visibility
    }
}




