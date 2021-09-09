package ru.schultetabledima.schultetable.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    protected SettingsPresenter settingsPresenter;
    protected Spinner spinnerRows, spinnerColumns;


    public BaseFragment(){}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState);


    public void attachPresenter(SettingsPresenter settingsPresenter) {
        this.settingsPresenter = settingsPresenter;
    }


    @Override
    public abstract void onItemSelected(AdapterView<?> parent, View view, int position, long id);

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

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
}
