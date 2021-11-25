package ru.schultetabledima.schultetable.utils

import androidx.navigation.NavController

/**Fun for removing duplicates from the backstack.
 * Works in pairs with BaseScreenFragment.
 */
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