package ru.schultetabledima.schultetable.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseValueFragment extends Fragment implements AdapterView.OnItemSelectedListener, CustomSubject {
    protected SettingsPresenter settingsPresenter;
    protected Spinner spinnerRows, spinnerColumns;

    protected List<CustomObserver> customObservers = new ArrayList<>();

    public BaseValueFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();

    }

    @Override
    public void onResume() {
        super.onResume();
        updateNotifyObservers();
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
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void setSpinnerRowsSelection(int position) {
        spinnerRows.setSelection(position, false);
    }

    public void setSpinnerColumnsSelection(int position) {
        spinnerColumns.setSelection(position, false);
    }

    public void setSpinnerRowsAdapter(ArrayAdapter<String> adapter) {
        spinnerRows.setAdapter(adapter);
    }

    public void setSpinnerColumnsAdapter(ArrayAdapter<String> adapter) {
        spinnerColumns.setAdapter(adapter);
    }


    @Override
    public void subscribeObserver(CustomObserver customObserver) {
        customObservers.add(customObserver);
    }

    @Override
    public void unSubscribeObserver(CustomObserver customObserver) {
        customObservers.remove(customObserver);
    }


}
