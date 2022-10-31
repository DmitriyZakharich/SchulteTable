package ru.schultetabledima.schultetable.settings;

import androidx.fragment.app.Fragment;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

public class SettingsPresenter {

    private final Fragment context;
    private transient PreferencesReader preferencesReader;
    private transient PreferencesWriter preferencesWriter;

    public SettingsPresenter(Fragment view) {
        this.context = view;
    }

    void start() {
        init();
        customizationSettingsActivity();
    }

    private void init() {
        preferencesReader = new PreferencesReader();
        preferencesWriter = new PreferencesWriter();
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
        if (position == 0) {
            b = false;
        } else if (position == 1) {
            b = true;
        }
        preferencesWriter.putBoolean(PreferencesWriter.getKeyIsLetters(), b);
    }

    public void onClickListenerSwitch(int id, boolean isChecked) {
        String key = "";
        if (id == R.id.switchAnimation) {
            key = PreferencesWriter.getKeyAnimation();

        } else if (id == R.id.switchTouchCells) {
            key = PreferencesWriter.getKeyTouchCells();

            boolean isSwitchMoveHintEnabled = isChecked;
            ((SettingsFragment) context).customizationSwitchMoveHint(isSwitchMoveHintEnabled, preferencesReader.getIsMoveHint());


        } else if (id == R.id.switchTwoTables) {
            key = PreferencesWriter.getKeyTwoTables();

        } else if (id == R.id.switchRussianOrEnglish) {
            key = PreferencesWriter.getKeyRussianOrEnglish();

        } else if (id == R.id.switchMoveHint) {
            key = PreferencesWriter.getKeyMoveHint();
        }

        if (!key.equals(""))
            preferencesWriter.putBoolean(key, isChecked);
    }
}
