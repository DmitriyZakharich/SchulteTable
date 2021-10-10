package ru.schultetabledima.schultetable.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import ru.schultetabledima.schultetable.R;

public class NumbersFragment extends BaseValueFragment implements AdapterView.OnItemSelectedListener {

    private NumbersFragmentPresenter presenter;
    private View view;

    public static NumbersFragment newInstance() {

        return new NumbersFragment();
    }

    public NumbersFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_numbers, container, false);

        spinnerRows = rootView.findViewById(R.id.spinnerRowsNumbers);
        spinnerColumns = rootView.findViewById(R.id.spinnerColumnsNumbers);


        spinnerRows.setOnItemSelectedListener(this);
        spinnerColumns.setOnItemSelectedListener(this);


        final String[] valueSpinner = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.custom_spinner_style, valueSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerRows.setAdapter(adapter);
        spinnerColumns.setAdapter(adapter);

        presenter = new NumbersFragmentPresenter(this);

        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        presenter.numbersFragmentListener(parent.getId(), position);
    }

    @Override
    public void setSpinnerRowsSelection(int position) {
        spinnerRows.setSelection(position);
    }

    @Override
    public void setSpinnerColumnsSelection(int position) {
        spinnerColumns.setSelection(position);
    }
}
