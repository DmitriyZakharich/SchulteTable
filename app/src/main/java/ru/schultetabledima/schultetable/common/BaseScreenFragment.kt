package ru.schultetabledima.schultetable.common

import androidx.activity.addCallback
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import moxy.MvpAppCompatFragment
import ru.schultetabledima.schultetable.utils.popBackStackAllInstances


/**
 * Class for removing duplicates from the backstack.
 * Works in pairs with NavController.popBackStackAllInstances
 * */
open class BaseScreenFragment(id: Int) : MvpAppCompatFragment(id) {

    var isNavigated = false

    fun navigateWithAction(action: NavDirections) {
        isNavigated = true
        findNavController().navigate(action)
    }

    fun navigate(resId: Int) {
        isNavigated = true
        findNavController().navigate(resId)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        if (!isNavigated)
            requireActivity().onBackPressedDispatcher.addCallback(this) {
                val navController = findNavController()
                if (navController.currentBackStackEntry?.destination?.id != null) {
                    findNavController().popBackStackAllInstances(
                        navController.currentBackStackEntry?.destination?.id!!,
                        true
                    )
                } else
                    navController.popBackStack()
            }
    }
}