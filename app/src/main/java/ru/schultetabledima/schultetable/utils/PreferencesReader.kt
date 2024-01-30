package ru.schultetabledima.schultetable.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.crashlytics.FirebaseCrashlytics
import ru.schultetabledima.schultetable.App
import ru.schultetabledima.schultetable.screens.settings.PreferencesWriter

object PreferencesReader {

    private var spCustomization: SharedPreferences? = null
    private var keyColumns: String? = null
    private var keyRows: String? = null

    init {
        start()
    }

    private fun start() {
        try {
            spCustomization = App.getAppContext()
                .getSharedPreferences(PreferencesWriter.APP_PREFERENCES, Context.MODE_PRIVATE)
        } catch (e: NullPointerException) {
            FirebaseCrashlytics.getInstance().recordException(e)
            FirebaseCrashlytics.getInstance().sendUnsentReports()
        }
        val isLetters = spCustomization!!.getBoolean(PreferencesWriter.keyIsLetters, false)
        if (isLetters) {
            keyColumns = PreferencesWriter.keyColumnsLetters
            keyRows = PreferencesWriter.keyRowsLetters
        } else {
            keyColumns = PreferencesWriter.keyColumnsNumbers
            keyRows = PreferencesWriter.keyRowsNumbers
        }
    }

    private fun refresh() {
        start()
    }

    val isTouchCells: Boolean
        get() = spCustomization!!.getBoolean(PreferencesWriter.keyTouchCells, true)

    val columnsOfTable: Int
        get() {
            refresh()
            return spCustomization!!.getInt(keyColumns, 5)
        }

    val rowsOfTable: Int
        get() {
            refresh()
            return spCustomization!!.getInt(keyRows, 5)
        }
    val columnsOfTableNumbers: Int
        get() {
            refresh()
            return spCustomization!!.getInt(PreferencesWriter.keyColumnsNumbers, 5)
        }
    val rowsOfTableNumbers: Int
        get() {
            refresh()
            return spCustomization!!.getInt(PreferencesWriter.keyRowsNumbers, 5)
        }
    val columnsOfTableLetters: Int
        get() {
            refresh()
            return spCustomization!!.getInt(PreferencesWriter.keyColumnsLetters, 5)
        }
    val rowsOfTableLetters: Int
        get() {
            refresh()
            return spCustomization!!.getInt(PreferencesWriter.keyRowsLetters, 5)
        }
    val isLetters: Boolean
        get() = spCustomization!!.getBoolean(PreferencesWriter.keyIsLetters, false)

    val isTwoTables: Boolean
        get() = spCustomization!!.getBoolean(PreferencesWriter.keyTwoTables, false)

    val isEnglish: Boolean
        get() = spCustomization!!.getBoolean(PreferencesWriter.keyRussianOrEnglish, false)

    val isMoveHint: Boolean
        get() = spCustomization!!.getBoolean(PreferencesWriter.keyMoveHint, true)

    val isAnim: Boolean
        get() = spCustomization!!.getBoolean(PreferencesWriter.keyAnimation, false)

}