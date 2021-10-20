package ru.schultetabledima.schultetable.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import ru.schultetabledima.schultetable.R
import ru.schultetabledima.schultetable.databinding.FragmentMainMenuBinding


class MenuFragment : Fragment(R.layout.fragment_main_menu), View.OnClickListener {

    private lateinit var binding: FragmentMainMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View? = inflater.inflate(R.layout.fragment_main_menu, container, false)

        view?.findViewById<Button>(R.id.tableButton)?.setOnClickListener(this)
        view?.findViewById<Button>(R.id.adviceButton)?.setOnClickListener(this)
        view?.findViewById<Button>(R.id.statisticsButton)?.setOnClickListener(this)
        view?.findViewById<Button>(R.id.settingsButton)?.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {

        val navOptions: NavOptions = navOptions {
            anim {
                enter = R.anim.slide_in_right
                exit = R.anim.slide_out_left

                popEnter = R.anim.slide_in_left
                popExit = R.anim.slide_out_right
            }
        }


        when (v?.id) {

            R.id.tableButton -> {
                findNavController().navigate(
                    R.id.action_menuFragment_to_tableFragment,
                    null,
                    navOptions
                )
            }

            R.id.adviceButton -> {
                findNavController().navigate(
                    R.id.action_menuFragment_to_adviceFragment,
                    null,
                    navOptions
                )

            }
            R.id.statisticsButton -> {
                findNavController().navigate(
                    R.id.action_menuFragment_to_statisticFragment,
                    null,
                    navOptions
                )
            }
            R.id.settingsButton -> {
                findNavController().navigate(
                    R.id.action_menuFragment_to_settingsFragment,
                    null,
                    navOptions
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()

        (activity as MainActivity).visibilityBottomNavigationView(View.GONE)

    }
}

