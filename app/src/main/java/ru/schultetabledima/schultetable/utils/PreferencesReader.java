package ru.schultetabledima.schultetable.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import ru.schultetabledima.schultetable.settings.CustomSharedPreferences;

public class PreferencesReader {
    private Context context;
    private SharedPreferences spCustomization;
    private int columnsOfTable, rowsOfTable;


    public PreferencesReader(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        spCustomization = context.getSharedPreferences(CustomSharedPreferences.getAppPreferences(), MODE_PRIVATE);
        boolean isLetters = spCustomization.getBoolean(CustomSharedPreferences.getKeyNumbersOrLetters(), false);


        if (isLetters){
            columnsOfTable = spCustomization.getInt(CustomSharedPreferences.getKeyColumnsLetters(), 4) + 1;
            rowsOfTable = spCustomization.getInt(CustomSharedPreferences.getKeyRowsLetters(), 4) + 1;
        } else{
            columnsOfTable = spCustomization.getInt(CustomSharedPreferences.getKeyColumnsNumbers(), 4) + 1;
            rowsOfTable = spCustomization.getInt(CustomSharedPreferences.getKeyRowsNumbers(), 4) + 1;
        }

    }

    public void refresh(){
        init();
    }


    public boolean getIsTouchCells() {
        return spCustomization.getBoolean(CustomSharedPreferences.getKeyTouchCells(), true);
    }

    public int getColumnsOfTable() {
        refresh();
        return columnsOfTable;
    }

    public int getRowsOfTable() {
        refresh();
        return rowsOfTable;
    }

    public boolean getIsLetters() {
        return spCustomization.getBoolean(CustomSharedPreferences.getKeyNumbersOrLetters(), false);
    }

    public boolean getIsTwoTables() {
        return spCustomization.getBoolean(CustomSharedPreferences.getKeyTwoTables(), false);
    }

    public boolean getIsEnglish() {
        return spCustomization.getBoolean(CustomSharedPreferences.getKeyRussianOrEnglish(), false);
    }

    public boolean getIsMoveHint() {
        return spCustomization.getBoolean(CustomSharedPreferences.getKeyMoveHint(), true);
    }

    public boolean getIsAnim() {
        return spCustomization.getBoolean(CustomSharedPreferences.getKeyAnimation(), false);
    }
}
