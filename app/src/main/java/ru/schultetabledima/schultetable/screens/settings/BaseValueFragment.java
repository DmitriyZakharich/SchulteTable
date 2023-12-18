package ru.schultetabledima.schultetable.screens.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseValueFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    protected Spinner spinnerRows, spinnerColumns;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container,
                                      Bundle savedInstanceState);

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
}
