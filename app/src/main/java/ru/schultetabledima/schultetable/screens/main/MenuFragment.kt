package ru.schultetabledima.schultetable.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdView
import ru.schultetabledima.schultetable.R
import ru.schultetabledima.schultetable.databinding.FragmentMainMenuBinding
import ru.schultetabledima.schultetable.utils.enterFromRightExitToLeft

class MenuFragment : Fragment(R.layout.fragment_main_menu), View.OnClickListener {

    private lateinit var binding: FragmentMainMenuBinding
    private var mAdView: AdView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View {
        binding = FragmentMainMenuBinding.inflate(inflater, container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            tableButton.setOnClickListener(this@MenuFragment)
            adviceButton.setOnClickListener(this@MenuFragment)
            statisticsButton.setOnClickListener(this@MenuFragment)
            settingsButton.setOnClickListener(this@MenuFragment)
            aboutAppButton.setOnClickListener(this@MenuFragment)
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            R.id.tableButton -> findNavController().navigate(
                R.id.action_menuFragment_to_tableFragment, null, enterFromRightExitToLeft())

            R.id.adviceButton -> findNavController().navigate(
                R.id.action_menuFragment_to_adviceFragment, null, enterFromRightExitToLeft())

            R.id.statisticsButton -> findNavController().navigate(
                R.id.action_menuFragment_to_statisticFragment, null, enterFromRightExitToLeft())

            R.id.settingsButton -> findNavController().navigate(
                R.id.action_menuFragment_to_settingsFragment, null, enterFromRightExitToLeft())

            R.id.aboutAppButton -> findNavController().navigate(
                R.id.action_menuFragment_to_aboutAppFragment, null, enterFromRightExitToLeft())
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).visibilityBottomNavigationView(View.GONE)
    }
}

