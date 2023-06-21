package ru.schultetabledima.schultetable.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.crashlytics.FirebaseCrashlytics
import ru.schultetabledima.schultetable.App
import ru.schultetabledima.schultetable.settings.PreferencesWriterKotlin

object PreferencesReaderKotlin {

    private var spCustomization: SharedPreferences? = null
    private var keyColumns: String? = null
    private var keyRows: String? = null

    init {
        start()
    }

    private fun start() {
        try {
            spCustomization = App.getContext()
                .getSharedPreferences(PreferencesWriterKotlin.APP_PREFERENCES, Context.MODE_PRIVATE)
        } catch (e: NullPointerException) {
            FirebaseCrashlytics.getInstance().recordException(e)
            FirebaseCrashlytics.getInstance().sendUnsentReports()
        }
        val isLetters = spCustomization!!.getBoolean(PreferencesWriterKotlin.keyIsLetters, false)
        if (isLetters) {
            keyColumns = PreferencesWriterKotlin.keyColumnsLetters
            keyRows = PreferencesWriterKotlin.keyRowsLetters
        } else {
            keyColumns = PreferencesWriterKotlin.keyColumnsNumbers
            keyRows = PreferencesWriterKotlin.keyRowsNumbers
        }
    }

    private fun refresh() {
        start()
    }

    val isTouchCells: Boolean
        get() = spCustomization!!.getBoolean(PreferencesWriterKotlin.keyTouchCells, true)

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
            return spCustomization!!.getInt(PreferencesWriterKotlin.keyColumnsNumbers, 5)
        }
    val rowsOfTableNumbers: Int
        get() {
            refresh()
            return spCustomization!!.getInt(PreferencesWriterKotlin.keyRowsNumbers, 5)
        }
    val columnsOfTableLetters: Int
        get() {
            refresh()
            return spCustomization!!.getInt(PreferencesWriterKotlin.keyColumnsLetters, 5)
        }
    val rowsOfTableLetters: Int
        get() {
            refresh()
            return spCustomization!!.getInt(PreferencesWriterKotlin.keyRowsLetters, 5)
        }
    val isLetters: Boolean
        get() = spCustomization!!.getBoolean(PreferencesWriterKotlin.keyIsLetters, false)

    val isTwoTables: Boolean
        get() = spCustomization!!.getBoolean(PreferencesWriterKotlin.keyTwoTables, false)

    val isEnglish: Boolean
        get() = spCustomization!!.getBoolean(PreferencesWriterKotlin.keyRussianOrEnglish, false)

    val isMoveHint: Boolean
        get() = spCustomization!!.getBoolean(PreferencesWriterKotlin.keyMoveHint, true)

    val isAnim: Boolean
        get() = spCustomization!!.getBoolean(PreferencesWriterKotlin.keyAnimation, false)

}