package ru.schultetabledima.schultetable.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.schultetabledima.schultetable.R
import ru.schultetabledima.schultetable.databinding.ActivityMainBinding


class MenuFragment : Fragment(R.layout.activity_main), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View? = inflater.inflate(R.layout.activity_main, container, false)

        view?.findViewById<Button>(R.id.tableButton)?.setOnClickListener(this)
        view?.findViewById<Button>(R.id.adviceButton)?.setOnClickListener(this)
        view?.findViewById<Button>(R.id.statisticsButton)?.setOnClickListener(this)
        view?.findViewById<Button>(R.id.settingsButton)?.setOnClickListener(this)

        return view
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tableButton -> {
                findNavController().navigate(R.id.action_menuFragment_to_tableFragment2)
            }

            R.id.adviceButton -> {
                findNavController().navigate(R.id.action_menuFragment_to_adviceFragment)
            }
            R.id.statisticsButton -> {
                findNavController().navigate(R.id.action_menuFragment_to_statisticFragment)
            }
            R.id.settingsButton -> {
                findNavController().navigate(R.id.action_menuFragment_to_settingsFragment)
            }
        }
    }

    override fun onResume() {
        super.onResume()

//        val navHostFragment: NavHostFragment = parentFragment as NavHostFragment
//        val parent = navHostFragment.parentFragment
        (activity as MainActivity).visibilityBottomNavigationView(View.GONE)

    }
}