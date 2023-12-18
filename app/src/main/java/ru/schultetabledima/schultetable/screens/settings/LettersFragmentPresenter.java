package ru.schultetabledima.schultetable.screens.settings;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

public class LettersFragmentPresenter {
    private PreferencesWriter preferencesWriter;
    private LettersFragment lettersFragment;

    public LettersFragmentPresenter(LettersFragment lettersFragment) {
        this.lettersFragment = lettersFragment;
        main();
    }

    private void main() {
        preferencesWriter = new PreferencesWriter();

        lettersFragment.setSpinnerRowsSelection(PreferencesReader.INSTANCE.getRowsOfTableLetters() - 1);
        lettersFragment.setSpinnerColumnsSelection(PreferencesReader.INSTANCE.getColumnsOfTableLetters() - 1);
        lettersFragment.setSwitchRussianOrEnglish(PreferencesReader.INSTANCE.isEnglish());

    }

    public void lettersFragmentListener(int id, int position) {
        int amount = position + 1;

        if (id == R.id.spinnerColumnsLetters) {
            preferencesWriter.putInt(PreferencesWriter.keyColumnsLetters, amount);

        } else if (id == R.id.spinnerRowsLetters) {
            preferencesWriter.putInt(PreferencesWriter.keyRowsLetters, amount);
        }
    }

    public void lettersFragmentListener(int id, boolean isChecked) {
        if (id == R.id.switchRussianOrEnglish) {
            preferencesWriter.putBoolean(PreferencesWriter.keyRussianOrEnglish, isChecked);
        }
    }
}
