package ru.schultetabledima.schultetable.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import ru.schultetabledima.schultetable.R
import ru.schultetabledima.schultetable.databinding.FragmentMainMenuBinding
import ru.schultetabledima.schultetable.utils.enterFromRightExitToLeft


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

        when (v?.id) {

            R.id.tableButton ->
                findNavController().navigate(R.id.action_menuFragment_to_tableFragment, null, enterFromRightExitToLeft())


            R.id.adviceButton ->{
                findNavController().navigate(R.id.action_menuFragment_to_adviceFragment, null, enterFromRightExitToLeft())

                val navHostFragment =
                    activity?.supportFragmentManager?.findFragmentById(R.id.mainContainerView) as NavHostFragment


                Log.d("fragmentBackStackCount", "onClickMenuFragment  =  ${navHostFragment.childFragmentManager.backStackEntryCount}")

            }

            R.id.statisticsButton ->
                findNavController().navigate(R.id.action_menuFragment_to_statisticFragment, null, enterFromRightExitToLeft())

            R.id.settingsButton ->
                findNavController().navigate(R.id.action_menuFragment_to_settingsFragment,null,enterFromRightExitToLeft())

        }
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).visibilityBottomNavigationView(View.GONE)

    }
}

