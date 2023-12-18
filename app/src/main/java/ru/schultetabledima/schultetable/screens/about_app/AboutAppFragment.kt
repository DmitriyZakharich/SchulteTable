package ru.schultetabledima.schultetable.screens.about_app

import android.view.View
import androidx.fragment.app.Fragment
import ru.schultetabledima.schultetable.R
import ru.schultetabledima.schultetable.screens.main.MainActivity

class AboutAppFragment : Fragment(R.layout.fragment_about_app) {
    companion object {
        @JvmStatic
        fun newInstance(): AboutAppFragment {
            return AboutAppFragment()
        }
    }

    override fun onResume() {
        (activity as MainActivity).visibilityBottomNavigationView(View.VISIBLE)
        super.onResume()
    }
}