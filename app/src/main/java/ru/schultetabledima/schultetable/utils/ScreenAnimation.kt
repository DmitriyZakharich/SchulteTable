package ru.schultetabledima.schultetable.utils

import android.util.Log
import androidx.navigation.NavOptions
import androidx.navigation.PopUpToBuilder
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
//    popUpTo (
////        R.id.tableFragment, PopUpToBuilder()
//    )

    anim {
        enter = R.anim.slide_in_left
        exit = R.anim.slide_out_right

        popEnter = R.anim.slide_in_right
        popExit = R.anim.slide_out_left
    }
}

fun getNavOptions(id: Int): NavOptions = navOptions {
        NavOptions.Builder().setPopUpTo(id, true)
            .setEnterAnim(R.anim.slide_in_left)
            .setExitAnim(R.anim.slide_out_right)
            .setPopEnterAnim(R.anim.slide_in_right)
            .setPopExitAnim(R.anim.slide_out_left)
            .build()
}