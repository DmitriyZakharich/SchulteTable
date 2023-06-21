package ru.schultetabledima.schultetable.settings;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.utils.PreferencesReaderKotlin;

public class LettersFragmentPresenter {
    private PreferencesWriterKotlin preferencesWriterKotlin;
    private LettersFragment lettersFragment;

    public LettersFragmentPresenter(LettersFragment lettersFragment) {
        this.lettersFragment = lettersFragment;
        main();
    }

    private void main() {

        int i1 = PreferencesReaderKotlin.INSTANCE.getColumnsOfTable();
        int i2 = PreferencesReaderKotlin.INSTANCE.getColumnsOfTable();

        preferencesWriterKotlin = new PreferencesWriterKotlin();

        lettersFragment.setSpinnerRowsSelection(PreferencesReaderKotlin.INSTANCE.getRowsOfTableLetters() - 1);
        lettersFragment.setSpinnerColumnsSelection(PreferencesReaderKotlin.INSTANCE.getColumnsOfTableLetters() - 1);
        lettersFragment.setSwitchRussianOrEnglish(PreferencesReaderKotlin.INSTANCE.isEnglish());

    }

    public void lettersFragmentListener(int id, int position) {
        int amount = position + 1;

        if (id == R.id.spinnerColumnsLetters) {
            preferencesWriterKotlin.putInt(PreferencesWriterKotlin.keyColumnsLetters, amount);

        } else if (id == R.id.spinnerRowsLetters) {
            preferencesWriterKotlin.putInt(PreferencesWriterKotlin.keyRowsLetters, amount);
        }
    }

    public void lettersFragmentListener(int id, boolean isChecked) {
        if (id == R.id.switchRussianOrEnglish) {
            preferencesWriterKotlin.putBoolean(PreferencesWriterKotlin.keyRussianOrEnglish, isChecked);
        }
    }
}
