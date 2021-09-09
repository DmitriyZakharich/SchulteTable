package ru.schultetabledima.schultetable.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.google.android.material.switchmaterial.SwitchMaterial;

import ru.schultetabledima.schultetable.R;


public class LettersFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private SettingsPresenter settingsPresenter;
    private Spinner spinnerRows, spinnerColumns;
    private SwitchMaterial switchRussianOrEnglish;


    public static LettersFragment newInstance() {
        return new LettersFragment();
    }

    public LettersFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_letters, container, false);

        spinnerRows = rootView.findViewById(R.id.spinnerRowsLetters);
        spinnerColumns = rootView.findViewById(R.id.spinnerColumnsLetters);
        switchRussianOrEnglish = rootView.findViewById(R.id.switchRussianOrEnglish);


        spinnerRows.setOnItemSelectedListener(this);
        spinnerColumns.setOnItemSelectedListener(this);
        switchRussianOrEnglish.setOnClickListener(this);

        settingsPresenter.customizationLettersFragment();

        return rootView;
    }

    @Override
    public void onClick(View v) {
        settingsPresenter.lettersFragmentListener(v.getId(), ((SwitchMaterial) v).isChecked());
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        settingsPresenter.lettersFragmentListener(parent.getId(), position);
    }

    public void setSpinnerRowsSelection(int position){
        spinnerRows.setSelection(position, false);
    }

    public void setSpinnerColumnsSelection(int position){
        spinnerColumns.setSelection(position, false);
    }

    public void setSwitchRussianOrEnglish(boolean isChecked){
        switchRussianOrEnglish.setChecked(isChecked);
    }

    public void setSpinnerRowsAdapter(ArrayAdapter<String> adapter){
        spinnerRows.setAdapter(adapter);
    }

    public void setSpinnerColumnsAdapter(ArrayAdapter<String> adapter){
        spinnerColumns.setAdapter(adapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    public void attachPresenter(SettingsPresenter settingsPresenter) {
        this.settingsPresenter = settingsPresenter;
    }
}