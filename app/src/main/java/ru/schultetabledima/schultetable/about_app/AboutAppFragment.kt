package ru.schultetabledima.schultetable.about_app

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import ru.schultetabledima.schultetable.R
import ru.schultetabledima.schultetable.main.MainActivity

class AboutAppFragment : Fragment(R.layout.fragment_about_app) {

    companion object {
        @JvmStatic
        fun newInstance(): AboutAppFragment {
            return AboutAppFragment()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            view.findViewById<ImageView>(R.id.imageViewAbout).setImageDrawable(
                ContextCompat.getDrawable(
                    requireContext(),
                    R.drawable.main_menu_background
                )
            )
    }

    override fun onResume() {
        (activity as MainActivity).visibilityBottomNavigationView(View.VISIBLE)
        super.onResume()
    }
}