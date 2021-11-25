package ru.schultetabledima.schultetable.utils

import androidx.navigation.NavOptions
import androidx.navigation.navOptions
import ru.schultetabledima.schultetable.R

fun enterFromRightExitToLeft(): NavOptions = navOptions {
    anim {
        enter = R.anim.slide_in_right
        exit = R.anim.slide_out_left

        popEnter = R.anim.slide_in_left
        popExit = R.anim.slide_out_right
    }
}

fun enterFromLeftExitToRight(): NavOptions = navOptions {

    anim {
        enter = R.anim.slide_in_left
        exit = R.anim.slide_out_right

        popEnter = R.anim.slide_in_right
        popExit = R.anim.slide_out_left
    }
}