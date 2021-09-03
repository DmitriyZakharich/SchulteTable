package ru.schultetabledima.schultetable.utils;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import ru.schultetabledima.schultetable.settings.SettingsActivity;

public class SettingsReader {
    Context context;

    private SharedPreferences spCustomization = context.getSharedPreferences(SettingsActivity.getAppPreferences(), MODE_PRIVATE);
    private boolean isPressButtons = spCustomization.getBoolean(SettingsActivity.getKeyTouchCells(), true);
    private int columnsOfTable = spCustomization.getInt(SettingsActivity.getKeyColumnsNumbers(), 4) + 1;
    private int rowsOfTable = spCustomization.getInt(SettingsActivity.getKeyRowsNumbers(), 4) + 1;
    private boolean isLetters = spCustomization.getBoolean(SettingsActivity.getKeyNumbersOrLetters(), false);
    private boolean isTwoTables = spCustomization.getBoolean(SettingsActivity.getKeyTwoTables(), false);
    private boolean isEnglish = spCustomization.getBoolean(SettingsActivity.getKeyRussianOrEnglish(), false);
    private boolean isMoveHint = spCustomization.getBoolean(SettingsActivity.getKeyMoveHint(), true);

    public SettingsReader(Context context) {
        this.context = context;
    }

    public boolean getIsPressButtons() {
        return isPressButtons;
    }

    public int getColumnsOfTable() {
        return columnsOfTable;
    }

    public int getRowsOfTable() {
        return rowsOfTable;
    }

    public boolean getIsLetters() {
        return isLetters;
    }

    public boolean getIsTwoTables() {
        return isTwoTables;
    }

    public boolean getIsEnglish() {
        return isEnglish;
    }

    public boolean getIsMoveHint() {
        return isMoveHint;
    }
}
