package ru.schultetabledima.schultetable.settings;

import androidx.fragment.app.Fragment;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

public class SettingsPresenter {

    private final Fragment context;
    private transient PreferencesReader preferencesReader;
    private transient PreferencesWriterKotlin preferencesWriterKotlin;

    public SettingsPresenter(Fragment view) {
        this.context = view;
    }

    void start() {
        init();
        customizationSettingsActivity();
    }

    private void init() {
        preferencesReader = new PreferencesReader();
        preferencesWriterKotlin = new PreferencesWriterKotlin();
//        preferencesWriter = new PreferencesWriter();
    }

    private void customizationSettingsActivity() {
        ((SettingsFragment) context).switchTouchCellsSetChecked(preferencesReader.getIsTouchCells());
        ((SettingsFragment) context).switchAnimationSetChecked(preferencesReader.getIsAnim());
        ((SettingsFragment) context).switchTwoTablesSetChecked(preferencesReader.getIsTwoTables());

        boolean isSwitchMoveHintEnabled = preferencesReader.getIsTouchCells();
        ((SettingsFragment) context).customizationSwitchMoveHint(isSwitchMoveHintEnabled, preferencesReader.getIsMoveHint());

        if (preferencesReader.getIsLetters()) {
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
        preferencesWriterKotlin.putBoolean(PreferencesWriterKotlin.keyIsLetters, b);
//        preferencesWriter.putBoolean(PreferencesWriter.getKeyIsLetters(), b);
    }

    public void onClickListenerSwitch(int id, boolean isChecked) {
        String key = "";
        if (id == R.id.switchAnimation) {
            key = PreferencesWriterKotlin.keyAnimation;

        } else if (id == R.id.switchTouchCells) {
            key = PreferencesWriterKotlin.keyTouchCells;

            boolean isSwitchMoveHintEnabled = isChecked;
            ((SettingsFragment) context).customizationSwitchMoveHint(isSwitchMoveHintEnabled, preferencesReader.getIsMoveHint());

        } else if (id == R.id.switchTwoTables) {
            key = PreferencesWriterKotlin.keyTwoTables;

        } else if (id == R.id.switchRussianOrEnglish) {
            key = PreferencesWriterKotlin.keyRussianOrEnglish;

        } else if (id == R.id.switchMoveHint) {
            key = PreferencesWriterKotlin.keyMoveHint;
        }

        if (!key.equals(""))
            preferencesWriterKotlin.putBoolean(key, isChecked);
//            preferencesWriter.putBoolean(key, isChecked);
    }
}
