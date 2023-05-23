package ru.schultetabledima.schultetable.settings;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.utils.PreferencesReader;
import ru.schultetabledima.schultetable.utils.PreferencesReaderKotlin;

public class LettersFragmentPresenter {
    private PreferencesWriter preferencesWriter;
    private LettersFragment lettersFragment;

    public LettersFragmentPresenter(LettersFragment lettersFragment) {
        this.lettersFragment = lettersFragment;
        main();
    }

    private void main() {
        PreferencesReader preferencesReader = new PreferencesReader();

        int i1 = preferencesReader.getColumnsOfTable();
        int i2 = PreferencesReaderKotlin.INSTANCE.getColumnsOfTable();



        preferencesWriter = new PreferencesWriter();

        lettersFragment.setSpinnerRowsSelection(preferencesReader.getRowsOfTableLetters() - 1);
        lettersFragment.setSpinnerColumnsSelection(preferencesReader.getColumnsOfTableLetters() - 1);
        lettersFragment.setSwitchRussianOrEnglish(preferencesReader.getIsEnglish());

    }

    public void lettersFragmentListener(int id, int position) {
        int amount = position + 1;

        if (id == R.id.spinnerColumnsLetters) {
            preferencesWriter.putInt(PreferencesWriter.getKeyColumnsLetters(), amount);

        } else if (id == R.id.spinnerRowsLetters) {
            preferencesWriter.putInt(PreferencesWriter.getKeyRowsLetters(), amount);
        }
    }

    public void lettersFragmentListener(int id, boolean isChecked) {
        if (id == R.id.switchRussianOrEnglish) {
            preferencesWriter.putBoolean(PreferencesWriter.getKeyRussianOrEnglish(), isChecked);
        }
    }
}
