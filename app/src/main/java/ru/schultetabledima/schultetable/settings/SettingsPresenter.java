package ru.schultetabledima.schultetable.settings;

import androidx.fragment.app.Fragment;

import javax.inject.Inject;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.contracts.SettingsContract;
import ru.schultetabledima.schultetable.utils.PreferencesReader;


public class SettingsPresenter implements SettingsContract.Presenter {

    private final Fragment context;
    private final SettingsContract.ModelPreferenceReader preferencesReader;
    private final PreferencesWriter preferencesWriter;
    private final int itemLetters = 1;
    private final int itemNumbers = 0;


    @Inject
    public SettingsPresenter(Fragment context, PreferencesWriter preferencesWriter, SettingsContract.ModelPreferenceReader preferencesReader) {
        this.context = context;
        this.preferencesWriter = preferencesWriter;
        this.preferencesReader = preferencesReader;
        main();
    }

    private void main() {
        customizationSettingsActivity();
    }


    private void customizationSettingsActivity() {
        ((SettingsContract.View) context).switchTouchCellsSetChecked(preferencesReader.getIsTouchCells());
        ((SettingsContract.View) context).switchAnimationSetChecked(preferencesReader.getIsAnim());
        ((SettingsContract.View) context).switchTwoTablesSetChecked(preferencesReader.getIsTwoTables());

        boolean isSwitchMoveHintEnabled = preferencesReader.getIsTouchCells();
        ((SettingsContract.View) context).customizationSwitchMoveHint(isSwitchMoveHintEnabled, preferencesReader.getIsMoveHint());


        if (preferencesReader.getIsLetters()) {
            ((SettingsContract.View) context).setViewPagerCurrentItem(itemLetters);
        } else {
            ((SettingsContract.View) context).setViewPagerCurrentItem(itemNumbers);
        }
    }

    @Override
    public void onTabSelectedListener(int position) {
        boolean b = false;
        if (position == itemNumbers) {
            b = false;
        } else if (position == itemLetters) {
            b = true;
        }
        preferencesWriter.putBoolean(PreferencesWriter.getKeyIsLetters(), b);
    }


    @Override
    public void onClickListenerSwitch(int id, boolean isChecked) {
        String key = "";
        if (id == R.id.switchAnimation) {
            key = PreferencesWriter.getKeyAnimation();

        } else if (id == R.id.switchPressButtons) {
            key = PreferencesWriter.getKeyTouchCells();

            boolean isSwitchMoveHintEnabled = isChecked;
            ((SettingsContract.View) context).customizationSwitchMoveHint(isSwitchMoveHintEnabled, preferencesReader.getIsMoveHint());


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
