package ru.schultetabledima.schultetable.utils;

import static android.content.Context.MODE_PRIVATE;
import android.content.SharedPreferences;
import com.google.firebase.crashlytics.FirebaseCrashlytics;
import ru.schultetabledima.schultetable.App;
import ru.schultetabledima.schultetable.settings.PreferencesWriterKotlin;

public class PreferencesReader {    //TODO Используется в TablePresenter, изменить на PreferencesReader Kotlin

    private SharedPreferences spCustomization;
    private String keyColumns, keyRows;

    public PreferencesReader() {
        init();
    }

    private void init() {
        try {
            spCustomization = App.getContext().getSharedPreferences(PreferencesWriterKotlin.APP_PREFERENCES, MODE_PRIVATE);
//            spCustomization = App.getContext().getSharedPreferences(PreferencesWriter.getAppPreferences(), MODE_PRIVATE);
        } catch (NullPointerException e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            FirebaseCrashlytics.getInstance().sendUnsentReports();
        }

        boolean isLetters = spCustomization.getBoolean(PreferencesWriterKotlin.keyIsLetters, false);
//        boolean isLetters = spCustomization.getBoolean(PreferencesWriter.getKeyIsLetters(), false);

        if (isLetters){
            keyColumns = PreferencesWriterKotlin.keyColumnsLetters;
            keyRows = PreferencesWriterKotlin.keyRowsLetters;
//            keyColumns = PreferencesWriter.getKeyColumnsLetters();
//            keyRows = PreferencesWriter.getKeyRowsLetters();

        } else{
            keyColumns = PreferencesWriterKotlin.keyColumnsNumbers;
            keyRows = PreferencesWriterKotlin.keyRowsNumbers;
        }
    }

    public void refresh(){
        init();
    }

    public boolean getIsTouchCells() {
        return spCustomization.getBoolean(PreferencesWriterKotlin.keyTouchCells, true);
    }

    public int getColumnsOfTable() {
        refresh();
        return spCustomization.getInt(keyColumns, 5);
    }

    public int getRowsOfTable() {
        refresh();
        return spCustomization.getInt(keyRows, 5);
    }

    public int getColumnsOfTableNumbers() {
        refresh();
        return spCustomization.getInt(PreferencesWriterKotlin.keyColumnsNumbers, 5);
    }

    public int getRowsOfTableNumbers() {
        refresh();
        return spCustomization.getInt(PreferencesWriterKotlin.keyRowsNumbers, 5);
    }

    public int getColumnsOfTableLetters() {
        refresh();
        return spCustomization.getInt(PreferencesWriterKotlin.keyColumnsLetters, 5);
    }

    public int getRowsOfTableLetters() {
        refresh();
        return spCustomization.getInt(PreferencesWriterKotlin.keyRowsLetters, 5);
    }

    public boolean getIsLetters() {
        return spCustomization.getBoolean(PreferencesWriterKotlin.keyIsLetters, false);
    }

    public boolean getIsTwoTables() {
        return spCustomization.getBoolean(PreferencesWriterKotlin.keyTwoTables, false);
    }

    public boolean getIsEnglish() {
        return spCustomization.getBoolean(PreferencesWriterKotlin.keyRussianOrEnglish, false);
    }

    public boolean getIsMoveHint() {
        return spCustomization.getBoolean(PreferencesWriterKotlin.keyMoveHint, true);
    }

    public boolean getIsAnim() {
        return spCustomization.getBoolean(PreferencesWriterKotlin.keyAnimation, false);
    }
}
