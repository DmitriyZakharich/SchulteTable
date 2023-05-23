package ru.schultetabledima.schultetable.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.crashlytics.FirebaseCrashlytics
import ru.schultetabledima.schultetable.App
import ru.schultetabledima.schultetable.settings.PreferencesWriter

object PreferencesReaderKotlin {

    private var spCustomization: SharedPreferences? = null
    private var keyColumns: String? = null
    private var keyRows: String? = null

    init {
        init()
    }

    private fun init() {
        try {
            spCustomization = App.getContext()
                .getSharedPreferences(PreferencesWriter.getAppPreferences(), Context.MODE_PRIVATE)
        } catch (e: NullPointerException) {
            FirebaseCrashlytics.getInstance().recordException(e)
            FirebaseCrashlytics.getInstance().sendUnsentReports()
        }
        val isLetters = spCustomization!!.getBoolean(PreferencesWriter.getKeyIsLetters(), false)
        if (isLetters) {
            keyColumns = PreferencesWriter.getKeyColumnsLetters()
            keyRows = PreferencesWriter.getKeyRowsLetters()
        } else {
            keyColumns = PreferencesWriter.getKeyColumnsNumbers()
            keyRows = PreferencesWriter.getKeyRowsNumbers()
        }
    }

    private fun refresh() {
        init()
    }

    val isTouchCells: Boolean
        get() = spCustomization!!.getBoolean(PreferencesWriter.getKeyTouchCells(), true)
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
            return spCustomization!!.getInt(PreferencesWriter.getKeyColumnsNumbers(), 5)
        }
    val rowsOfTableNumbers: Int
        get() {
            refresh()
            return spCustomization!!.getInt(PreferencesWriter.getKeyRowsNumbers(), 5)
        }
    val columnsOfTableLetters: Int
        get() {
            refresh()
            return spCustomization!!.getInt(PreferencesWriter.getKeyColumnsLetters(), 5)
        }
    val rowsOfTableLetters: Int
        get() {
            refresh()
            return spCustomization!!.getInt(PreferencesWriter.getKeyRowsLetters(), 5)
        }
    val isLetters: Boolean
        get() = spCustomization!!.getBoolean(PreferencesWriter.getKeyIsLetters(), false)
    val isTwoTables: Boolean
        get() = spCustomization!!.getBoolean(PreferencesWriter.getKeyTwoTables(), false)
    val isEnglish: Boolean
        get() = spCustomization!!.getBoolean(PreferencesWriter.getKeyRussianOrEnglish(), false)
    val isMoveHint: Boolean
        get() = spCustomization!!.getBoolean(PreferencesWriter.getKeyMoveHint(), true)
    val isAnim: Boolean
        get() = spCustomization!!.getBoolean(PreferencesWriter.getKeyAnimation(), false)
}