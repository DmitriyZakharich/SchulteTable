package ru.schultetabledima.schultetable.settings;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

public class NumbersFragmentPresenter {

    private PreferencesWriter preferencesWriter;
    private NumbersFragment numbersFragment;

    public NumbersFragmentPresenter(NumbersFragment numbersFragment) {
        this.numbersFragment = numbersFragment;
        main();
    }

    private void main() {
        preferencesWriter = new PreferencesWriter();

        numbersFragment.setSpinnerRowsSelection(PreferencesReader.INSTANCE.getRowsOfTableNumbers() - 1);
        numbersFragment.setSpinnerColumnsSelection(PreferencesReader.INSTANCE.getColumnsOfTableNumbers() - 1);
    }

    public void numbersFragmentListener(int id, int position) {
        int amount = position + 1;

        if (id == R.id.spinnerColumnsNumbers) {
            preferencesWriter.putInt(PreferencesWriter.keyColumnsNumbers, amount);

        } else if (id == R.id.spinnerRowsNumbers) {
            preferencesWriter.putInt(PreferencesWriter.keyRowsNumbers, amount);
        }
    }
}
