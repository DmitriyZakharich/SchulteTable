package ru.schultetabledima.schultetable.settings;

import android.content.Context;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

public class SettingsPresenter {

    private Context context;
    private PreferencesReader preferencesReader;

    public SettingsPresenter(Context context) {
        this.context = context;
        init();
        customizationSettingsActivity();
    }


    private void init() {
        preferencesReader = new PreferencesReader(context);
    }

    private void customizationSettingsActivity() {
        ((SettingsActivity)context).switchTouchCellsSetChecked(preferencesReader.getIsTouchCells());
        ((SettingsActivity)context).switchAnimationSetChecked(preferencesReader.getIsAnim());
        ((SettingsActivity)context).switchTwoTablesSetChecked(preferencesReader.getIsTwoTables());

        boolean isSwitchMoveHintEnabled = preferencesReader.getIsTouchCells();
        ((SettingsActivity)context).customizationSwitchMoveHint(isSwitchMoveHintEnabled, preferencesReader.getIsMoveHint());
    }

    public void onClickListenerSwitch(int id, boolean isChecked) {

        CustomSharedPreferences preferencesWriter = new CustomSharedPreferences(context);


        if (id == R.id.switchAnimation) {
            preferencesWriter.putBoolean(CustomSharedPreferences.getKeyAnimation(), isChecked);

        } else if (id == R.id.switchPressButtons) {
            preferencesWriter.putBoolean(CustomSharedPreferences.getKeyTouchCells(), isChecked);

            boolean isSwitchMoveHintEnabled = isChecked;
            ((SettingsActivity)context).customizationSwitchMoveHint(isSwitchMoveHintEnabled, preferencesReader.getIsMoveHint());


        } else if (id == R.id.switchTwoTables) {
            preferencesWriter.putBoolean(CustomSharedPreferences.getKeyTwoTables(), isChecked);

        } else if (id == R.id.switchRussianOrEnglish) {
            preferencesWriter.putBoolean(CustomSharedPreferences.getKeyRussianOrEnglish(), isChecked);

        } else if (id == R.id.switchMoveHint) {
            preferencesWriter.putBoolean(CustomSharedPreferences.getKeyMoveHint(), isChecked);
        }
    }
}
