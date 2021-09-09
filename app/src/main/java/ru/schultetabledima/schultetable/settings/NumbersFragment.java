package ru.schultetabledima.schultetable.settings;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import ru.schultetabledima.schultetable.R;

public class NumbersFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private SettingsPresenter settingsPresenter;
    private Spinner spinnerRows, spinnerColumns;

    public static NumbersFragment newInstance() {
        return new NumbersFragment();
    }

    public NumbersFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_numbers, container, false);

        spinnerRows = rootView.findViewById(R.id.spinnerRowsNumbers);
        spinnerColumns = rootView.findViewById(R.id.spinnerColumnsNumbers);


        spinnerRows.setOnItemSelectedListener(this);
        spinnerColumns.setOnItemSelectedListener(this);
        settingsPresenter.customizationNumbersFragment();

        return rootView;
    }


    public void setSpinnerRowsSelection(int position){
        spinnerRows.setSelection(position, false);
    }

    public void setSpinnerColumnsSelection(int position){
        spinnerColumns.setSelection(position, false);
    }

    public void setSpinnerRowsAdapter(ArrayAdapter<String> adapter){
        spinnerRows.setAdapter(adapter);
    }

    public void setSpinnerColumnsAdapter(ArrayAdapter<String> adapter){
        spinnerColumns.setAdapter(adapter);
    }
    

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        settingsPresenter.numbersFragmentListener(parent.getId(), position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    public void attachPresenter(SettingsPresenter settingsPresenter) {
        this.settingsPresenter = settingsPresenter;
    }
}
