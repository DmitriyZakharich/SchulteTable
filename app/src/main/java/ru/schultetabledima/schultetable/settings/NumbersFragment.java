package ru.schultetabledima.schultetable.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import ru.schultetabledima.schultetable.R;

public class NumbersFragment extends BaseFragment implements AdapterView.OnItemSelectedListener {

    public static NumbersFragment newInstance() {
        return new NumbersFragment();
    }

    public NumbersFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_numbers, container, false);

        spinnerRows = rootView.findViewById(R.id.spinnerRowsNumbers);
        spinnerColumns = rootView.findViewById(R.id.spinnerColumnsNumbers);

        spinnerRows.setOnItemSelectedListener(this);
        spinnerColumns.setOnItemSelectedListener(this);


        if (savedInstanceState == null)
            settingsPresenter.customizationNumbersFragment();
        else{
            settingsPresenter = ((SettingsActivity) requireActivity()).getPresenter();
            settingsPresenter.attachViewFragment(this);
            settingsPresenter.restoreInstanceState();
            settingsPresenter.customizationNumbersFragment();
        }

        return rootView;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        settingsPresenter.numbersFragmentListener(parent.getId(), position);
    }
}
