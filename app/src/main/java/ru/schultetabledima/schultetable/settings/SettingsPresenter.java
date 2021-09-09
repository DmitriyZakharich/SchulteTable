package ru.schultetabledima.schultetable.settings;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.Serializable;
import java.util.ArrayList;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.statistic.MyAdapter;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

public class SettingsPresenter implements Serializable {

    private Context context;
    private transient PreferencesReader preferencesReader;
    private transient PreferencesWriter preferencesWriter;
    private int indexOfNumbers, indexOfLetters;
    private NumbersFragment numbersFragment;
    private LettersFragment lettersFragment;

    public SettingsPresenter(Context context) {
        this.context = context;
        main();
    }

    private void main(){
        init();
        customizationSettingsActivity();
        createFragments();
        addFragments();
    }


    private void init() {
        preferencesReader = new PreferencesReader(context);
        preferencesWriter = new PreferencesWriter(context);
    }

    private void customizationSettingsActivity() {
        ((SettingsActivity) context).switchTouchCellsSetChecked(preferencesReader.getIsTouchCells());
        ((SettingsActivity) context).switchAnimationSetChecked(preferencesReader.getIsAnim());
        ((SettingsActivity) context).switchTwoTablesSetChecked(preferencesReader.getIsTwoTables());

        boolean isSwitchMoveHintEnabled = preferencesReader.getIsTouchCells();
        ((SettingsActivity) context).customizationSwitchMoveHint(isSwitchMoveHintEnabled, preferencesReader.getIsMoveHint());
    }

    private void createFragments(){
        numbersFragment = NumbersFragment.newInstance();
        lettersFragment = LettersFragment.newInstance();

        numbersFragment.attachPresenter(this);
        lettersFragment.attachPresenter(this);
    }

    private void addFragments() {
        Log.d("addFragmentsFragment", "addFragments " + numbersFragment);

        ArrayList<Fragment> fragments = new ArrayList<>();

        fragments.add(numbersFragment);
        fragments.add(lettersFragment);

        indexOfNumbers = fragments.indexOf(numbersFragment);
        indexOfLetters = fragments.indexOf(lettersFragment);

        MyAdapter pageAdapter = new MyAdapter((SettingsActivity) context);
        pageAdapter.setListFragments(fragments);
        ((SettingsActivity) context).setViewPagerAdapter(pageAdapter);


        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(((SettingsActivity) context).getTabLayout(),
                ((SettingsActivity) context).getViewPager(), (tab, position) -> {
                    if (position == indexOfNumbers)
                        tab.setText(R.string.numbers);
                    if (position == indexOfLetters)
                        tab.setText(R.string.letters);
                });
        tabLayoutMediator.attach();

        if (!preferencesReader.getIsLetters())
            ((SettingsActivity) context).setViewPagerCurrentItem(indexOfNumbers);
        else
            ((SettingsActivity) context).setViewPagerCurrentItem(indexOfLetters);
        Log.d("addFragmentsFragment", "addFragments end" + numbersFragment.getClass());

    }


    public void onTabSelectedListener(int position) {
        boolean b = false;
        if (position == indexOfNumbers) {
            b = false;
        } else if (position == indexOfLetters) {
            b = true;
        }
        preferencesWriter.putBoolean(PreferencesWriter.getKeyIsLetters(), b);

    }


    public void onClickListenerSwitch(int id, boolean isChecked) {
        String key = "";
        if (id == R.id.switchAnimation) {
            key = PreferencesWriter.getKeyAnimation();

        } else if (id == R.id.switchPressButtons) {
            key = PreferencesWriter.getKeyTouchCells();

            boolean isSwitchMoveHintEnabled = isChecked;
            ((SettingsActivity) context).customizationSwitchMoveHint(isSwitchMoveHintEnabled, preferencesReader.getIsMoveHint());


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


    public void customizationNumbersFragment() {
        final String[] valueSpinner = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(numbersFragment.getActivity(), android.R.layout.simple_spinner_item, valueSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        numbersFragment.setSpinnerRowsAdapter(adapter);
        numbersFragment.setSpinnerColumnsAdapter(adapter);

        numbersFragment.setSpinnerRowsSelection(preferencesReader.getRowsOfTableNumbers() - 1);
        numbersFragment.setSpinnerColumnsSelection(preferencesReader.getColumnsOfTableNumbers() - 1);
    }

    public void numbersFragmentListener(int id, int position) {
        int amount = position + 1;

        if (id == R.id.spinnerColumnsNumbers) {
            preferencesWriter.putInt(PreferencesWriter.getKeyColumnsNumbers(), amount);

        } else if (id == R.id.spinnerRowsNumbers) {
            preferencesWriter.putInt(PreferencesWriter.getKeyRowsNumbers(), amount);
        }

    }


    public void customizationLettersFragment() {

        final String[] valueSpinner = {"1", "2", "3", "4", "5"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(lettersFragment.getActivity(), android.R.layout.simple_spinner_item, valueSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        lettersFragment.setSpinnerRowsAdapter(adapter);
        lettersFragment.setSpinnerColumnsAdapter(adapter);

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

    public void attachView(SettingsActivity settingsActivity) {
        context = settingsActivity;
    }

    public void detachView() {
        context = null;
        numbersFragment = null;
        lettersFragment = null;
    }

    public void attachViewFragment(NumbersFragment numbersFragment) {
        this.numbersFragment = numbersFragment;
    }
    public void attachViewFragment(LettersFragment lettersFragment) {
        this.lettersFragment = lettersFragment;
    }

    public void restoreInstanceState() {
        init();
        addFragments();
    }
}
