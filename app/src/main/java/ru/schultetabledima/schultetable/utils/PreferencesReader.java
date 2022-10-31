package ru.schultetabledima.schultetable.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.crash.FirebaseCrash;
import com.google.firebase.crashlytics.FirebaseCrashlytics;

import ru.schultetabledima.schultetable.App;
import ru.schultetabledima.schultetable.settings.PreferencesWriter;

public class PreferencesReader {

    private SharedPreferences spCustomization;
    private String keyColumns, keyRows;

    public PreferencesReader() {
        init();
    }

    private void init() {
        try {
            spCustomization = App.getContext().getSharedPreferences(PreferencesWriter.getAppPreferences(), MODE_PRIVATE);
        } catch (NullPointerException e) {
            FirebaseCrashlytics.getInstance().recordException(e);
            FirebaseCrashlytics.getInstance().sendUnsentReports();
        }

        boolean isLetters = spCustomization.getBoolean(PreferencesWriter.getKeyIsLetters(), false);

        if (isLetters){
            keyColumns = PreferencesWriter.getKeyColumnsLetters();
            keyRows = PreferencesWriter.getKeyRowsLetters();

        } else{
            keyColumns = PreferencesWriter.getKeyColumnsNumbers();
            keyRows = PreferencesWriter.getKeyRowsNumbers();
        }
    }

    public void refresh(){
        init();
    }

    public boolean getIsTouchCells() {
        return spCustomization.getBoolean(PreferencesWriter.getKeyTouchCells(), true);
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
        return spCustomization.getInt(PreferencesWriter.getKeyColumnsNumbers(), 5);
    }

    public int getRowsOfTableNumbers() {
        refresh();
        return spCustomization.getInt(PreferencesWriter.getKeyRowsNumbers(), 5);
    }

    public int getColumnsOfTableLetters() {
        refresh();
        return spCustomization.getInt(PreferencesWriter.getKeyColumnsLetters(), 5);
    }

    public int getRowsOfTableLetters() {
        refresh();
        return spCustomization.getInt(PreferencesWriter.getKeyRowsLetters(), 5);
    }

    public boolean getIsLetters() {
        return spCustomization.getBoolean(PreferencesWriter.getKeyIsLetters(), false);
    }

    public boolean getIsTwoTables() {
        return spCustomization.getBoolean(PreferencesWriter.getKeyTwoTables(), false);
    }

    public boolean getIsEnglish() {
        return spCustomization.getBoolean(PreferencesWriter.getKeyRussianOrEnglish(), false);
    }

    public boolean getIsMoveHint() {
        return spCustomization.getBoolean(PreferencesWriter.getKeyMoveHint(), true);
    }

    public boolean getIsAnim() {
        return spCustomization.getBoolean(PreferencesWriter.getKeyAnimation(), false);
    }
}
