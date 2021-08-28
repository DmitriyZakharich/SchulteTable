package ru.schultetabledima.schultetable.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import ru.schultetabledima.schultetable.settings.SettingsActivity;

public class SettingsReader {
    Context context;

    private final SharedPreferences spCustomization = context.getSharedPreferences(SettingsActivity.getAppPreferences(), MODE_PRIVATE);
    private final boolean isPressButtons = spCustomization.getBoolean(SettingsActivity.getKeyTouchCells(), true);
    private final int columnsOfTable = spCustomization.getInt(SettingsActivity.getKeyColumnsNumbers(), 4) + 1;
    private final int rowsOfTable = spCustomization.getInt(SettingsActivity.getKeyRowsNumbers(), 4) + 1;
    private final boolean isLetters = spCustomization.getBoolean(SettingsActivity.getKeyNumbersOrLetters(), false);
    private final boolean isTwoTables = spCustomization.getBoolean(SettingsActivity.getKeyTwoTables(), false);

    public SettingsReader(Context context) {
        this.context = context;
    }
}
