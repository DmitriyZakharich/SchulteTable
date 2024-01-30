package ru.schultetabledima.schultetable.screens.settings

import android.content.Context
import android.content.SharedPreferences
import ru.schultetabledima.schultetable.App

class PreferencesWriter {

    fun putBoolean(key: String?, isChecked: Boolean) {
        val ed = settings!!.edit()
        ed.putBoolean(key, isChecked)
        ed.apply()
    }

    fun putInt(key: String?, position: Int) {
        val ed = settings!!.edit()
        ed.putInt(key, position)
        ed.apply()
    }

    companion object {
        const val APP_PREFERENCES = "my_settingsd"
        private var settings: SharedPreferences? = App.getAppContext().getSharedPreferences(
            APP_PREFERENCES, Context.MODE_PRIVATE)

        const val keyAnimation = "switchAnimation"
        const val keyTouchCells = "switchTouchCells"
        const val keyIsLetters = "switchNumbersLetters"
        const val keyRussianOrEnglish = "switchRussianOrEnglish"
        const val keyTwoTables = "switchTwoTables"
        const val keyMoveHint = "switchMoveHint"
        const val keyRowsNumbers = "saveSpinnerRowsNumbers"
        const val keyColumnsNumbers = "saveSpinnerColumnsNumbers"
        const val keyRowsLetters = "saveSpinnerRowsLetters"
        const val keyColumnsLetters = "saveSpinnerColumnsLetters"
    }
}