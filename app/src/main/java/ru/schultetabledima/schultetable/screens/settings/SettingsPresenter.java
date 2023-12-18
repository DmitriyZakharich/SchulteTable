package ru.schultetabledima.schultetable.screens.settings;

import androidx.fragment.app.Fragment;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

public class SettingsPresenter {

    private final Fragment context;
    private transient PreferencesWriter preferencesWriter;

    public SettingsPresenter(Fragment view) {
        this.context = view;
    }

    void start() {
        init();
        customizationSettingsActivity();
    }

    private void init() {
        preferencesWriter = new PreferencesWriter();
    }

    private void customizationSettingsActivity() {
        ((SettingsFragment) context).switchTouchCellsSetChecked(PreferencesReader.INSTANCE.isTouchCells());
        ((SettingsFragment) context).switchAnimationSetChecked(PreferencesReader.INSTANCE.isAnim());
        ((SettingsFragment) context).switchTwoTablesSetChecked(PreferencesReader.INSTANCE.isTwoTables());

        boolean isSwitchMoveHintEnabled = PreferencesReader.INSTANCE.isTouchCells();
        ((SettingsFragment) context).customizationSwitchMoveHint(isSwitchMoveHintEnabled, PreferencesReader.INSTANCE.isMoveHint());

        if (PreferencesReader.INSTANCE.isLetters()) {
            ((SettingsFragment) context).setViewPagerCurrentItem(1);
        } else {
            ((SettingsFragment) context).setViewPagerCurrentItem(0);
        }
    }

    public void onTabSelectedListener(int position) {
        boolean b = false;

        switch (position) {
            case 0:
                b = false;
                break;
            case 1:
                b = true;
                break;
        }
        preferencesWriter.putBoolean(PreferencesWriter.keyIsLetters, b);
    }

    public void onClickListenerSwitch(int id, boolean isChecked) {
        String key = "";
        if (id == R.id.switchAnimation) {
            key = PreferencesWriter.keyAnimation;

        } else if (id == R.id.switchTouchCells) {
            key = PreferencesWriter.keyTouchCells;

            boolean isSwitchMoveHintEnabled = isChecked;
            ((SettingsFragment) context).customizationSwitchMoveHint(isSwitchMoveHintEnabled, PreferencesReader.INSTANCE.isMoveHint());

        } else if (id == R.id.switchTwoTables) {
            key = PreferencesWriter.keyTwoTables;

        } else if (id == R.id.switchRussianOrEnglish) {
            key = PreferencesWriter.keyRussianOrEnglish;

        } else if (id == R.id.switchMoveHint) {
            key = PreferencesWriter.keyMoveHint;
        }

        if (!key.equals(""))
            preferencesWriter.putBoolean(key, isChecked);
    }
}
