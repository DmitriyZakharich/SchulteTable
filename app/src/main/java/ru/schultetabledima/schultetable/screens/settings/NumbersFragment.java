package ru.schultetabledima.schultetable.screens.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import ru.schultetabledima.schultetable.R;

public class NumbersFragment extends BaseValueFragment implements AdapterView.OnItemSelectedListener {

    private NumbersFragmentPresenter presenter;

    public static NumbersFragment newInstance() {
        return new NumbersFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_numbers, container, false);

        findView(rootView);
        setListener();
        setSpinnerAdapter();

        presenter = new NumbersFragmentPresenter(this);
        return rootView;
    }

    private void findView(View rootView) {
        spinnerRows = rootView.findViewById(R.id.spinnerRowsNumbers);
        spinnerColumns = rootView.findViewById(R.id.spinnerColumnsNumbers);
    }

    private void setListener() {
        spinnerRows.setOnItemSelectedListener(this);
        spinnerColumns.setOnItemSelectedListener(this);
    }

    private void setSpinnerAdapter() {
        final String[] valueSpinner = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.custom_spinner_style, valueSpinner);
        adapter.setDropDownViewResource(R.layout.spin_item);

        spinnerRows.setAdapter(adapter);
        spinnerColumns.setAdapter(adapter);
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