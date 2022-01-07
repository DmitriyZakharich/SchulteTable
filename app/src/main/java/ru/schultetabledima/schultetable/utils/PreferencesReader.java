package ru.schultetabledima.schultetable.utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;

import javax.inject.Inject;

import ru.schultetabledima.schultetable.App;
import ru.schultetabledima.schultetable.contracts.SettingsContract;
import ru.schultetabledima.schultetable.settings.PreferencesWriter;

public class PreferencesReader implements SettingsContract.ModelPreferenceReader {
    private SharedPreferences spCustomization;
    private String keyColumns, keyRows;

    @Inject
    public PreferencesReader() {
        init();
    }

    private void init() {
        spCustomization = App.getContext().getSharedPreferences(PreferencesWriter.getAppPreferences(), MODE_PRIVATE);
        boolean isLetters = spCustomization.getBoolean(PreferencesWriter.getKeyIsLetters(), false);


        if (isLetters) {
            keyColumns = PreferencesWriter.getKeyColumnsLetters();
            keyRows = PreferencesWriter.getKeyRowsLetters();

        } else {
            keyColumns = PreferencesWriter.getKeyColumnsNumbers();
            keyRows = PreferencesWriter.getKeyRowsNumbers();
        }

    }

    private void refresh() {
        init();
    }


    @Override
    public boolean getIsTouchCells() {
        return spCustomization.getBoolean(PreferencesWriter.getKeyTouchCells(), true);
    }

    @Override
    public int getColumnsOfTable() {
        refresh();
        return spCustomization.getInt(keyColumns, 5);
    }

    @Override
    public int getRowsOfTable() {
        refresh();
        return spCustomization.getInt(keyRows, 5);
    }

    @Override
    public int getColumnsOfTableNumbers() {
        refresh();
        return spCustomization.getInt(PreferencesWriter.getKeyColumnsNumbers(), 5);
    }

    @Override
    public int getRowsOfTableNumbers() {
        refresh();
        return spCustomization.getInt(PreferencesWriter.getKeyRowsNumbers(), 5);
    }

    @Override
    public int getColumnsOfTableLetters() {
        refresh();
        return spCustomization.getInt(PreferencesWriter.getKeyColumnsLetters(), 5);
    }

    @Override
    public int getRowsOfTableLetters() {
        refresh();
        return spCustomization.getInt(PreferencesWriter.getKeyRowsLetters(), 5);
    }

    @Override
    public boolean getIsLetters() {
        return spCustomization.getBoolean(PreferencesWriter.getKeyIsLetters(), false);
    }

    @Override
    public boolean getIsTwoTables() {
        return spCustomization.getBoolean(PreferencesWriter.getKeyTwoTables(), false);
    }

    @Override
    public boolean getIsEnglish() {
        return spCustomization.getBoolean(PreferencesWriter.getKeyRussianOrEnglish(), false);
    }

    @Override
    public boolean getIsMoveHint() {
        return spCustomization.getBoolean(PreferencesWriter.getKeyMoveHint(), true);
    }

    @Override
    public boolean getIsAnim() {
        return spCustomization.getBoolean(PreferencesWriter.getKeyAnimation(), false);
    }
}
