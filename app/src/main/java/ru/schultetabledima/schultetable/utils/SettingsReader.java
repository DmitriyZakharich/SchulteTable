package ru.schultetabledima.schultetable.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import ru.schultetabledima.schultetable.ui.CustomizationActivity;

public class SettingsReader {
    Context context;

    private final SharedPreferences spCustomization = context.getSharedPreferences(CustomizationActivity.getAppPreferences(), MODE_PRIVATE);
    private final boolean isPressButtons = spCustomization.getBoolean(CustomizationActivity.getKeyTouchCells(), true);
    private final int columnsOfTable = spCustomization.getInt(CustomizationActivity.getKeyNumberColumns(), 4) + 1;
    private final int rowsOfTable = spCustomization.getInt(CustomizationActivity.getKeyNumberRows(), 4) + 1;
    private final boolean isLetters = spCustomization.getBoolean(CustomizationActivity.getKeyNumbersLetters(), false);
    private final boolean isTwoTables = spCustomization.getBoolean(CustomizationActivity.getKeyTwoTables(), false);

    public SettingsReader(Context context) {
        this.context = context;
    }
}
