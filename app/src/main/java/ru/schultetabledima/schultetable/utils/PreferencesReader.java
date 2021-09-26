package ru.schultetabledima.schultetable.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import ru.schultetabledima.schultetable.settings.PreferencesWriter;

public class PreferencesReader {
    private Context context;
    private SharedPreferences spCustomization;
    private String keyColumns, keyRows;


    public PreferencesReader(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        spCustomization = context.getSharedPreferences(PreferencesWriter.getAppPreferences(), MODE_PRIVATE);
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
        return spCustomization.getInt(keyColumns, 4);
    }

    public int getRowsOfTable() {
        refresh();
        return spCustomization.getInt(keyRows, 4);
    }

    public int getColumnsOfTableNumbers() {
        refresh();
        return spCustomization.getInt(PreferencesWriter.getKeyColumnsNumbers(), 4);
    }

    public int getRowsOfTableNumbers() {
        refresh();
        return spCustomization.getInt(PreferencesWriter.getKeyRowsNumbers(), 4);
    }

    public int getColumnsOfTableLetters() {
        refresh();
        return spCustomization.getInt(PreferencesWriter.getKeyColumnsLetters(), 4);
    }

    public int getRowsOfTableLetters() {
        refresh();
        return spCustomization.getInt(PreferencesWriter.getKeyRowsLetters(), 4);
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
