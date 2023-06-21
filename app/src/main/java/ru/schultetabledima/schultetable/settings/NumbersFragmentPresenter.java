package ru.schultetabledima.schultetable.settings;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.utils.PreferencesReaderKotlin;

public class NumbersFragmentPresenter {

    private PreferencesWriterKotlin preferencesWriterKotlin;
    private NumbersFragment numbersFragment;

    public NumbersFragmentPresenter(NumbersFragment numbersFragment) {
        this.numbersFragment = numbersFragment;
        main();
    }

    private void main() {
        preferencesWriterKotlin = new PreferencesWriterKotlin();

        numbersFragment.setSpinnerRowsSelection(PreferencesReaderKotlin.INSTANCE.getRowsOfTableNumbers() - 1);
        numbersFragment.setSpinnerColumnsSelection(PreferencesReaderKotlin.INSTANCE.getColumnsOfTableNumbers() - 1);
    }

    public void numbersFragmentListener(int id, int position) {
        int amount = position + 1;

        if (id == R.id.spinnerColumnsNumbers) {
            preferencesWriterKotlin.putInt(PreferencesWriterKotlin.keyColumnsNumbers, amount);

        } else if (id == R.id.spinnerRowsNumbers) {
            preferencesWriterKotlin.putInt(PreferencesWriterKotlin.keyRowsNumbers, amount);
        }
    }
}
