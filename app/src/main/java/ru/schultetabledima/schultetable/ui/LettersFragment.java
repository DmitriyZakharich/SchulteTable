package ru.schultetabledima.schultetable.ui;

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

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Objects;

import ru.schultetabledima.schultetable.R;


public class LettersFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {


    private int pageNumber;
    private final String [] valueSpinner = {"1","2","3","4","5"};
    private static final String APP_PREFERENCES = "my_settings";

    private static SharedPreferences settings;


    public static LettersFragment newInstance() {
        LettersFragment fragment = new LettersFragment();
        return fragment;
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

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, valueSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinnerRows = rootView.findViewById(R.id.spinnerRowsLetters);
        Spinner spinnerColumns = rootView.findViewById(R.id.spinnerColumnsLetters);
        SwitchMaterial switchRussianOrEnglish = rootView.findViewById(R.id.switchRussianOrEnglish);

        spinnerRows.setAdapter(adapter);
        spinnerColumns.setAdapter(adapter);

        settings = requireActivity().getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);

        spinnerRows.setSelection(settings.getInt(SettingsActivity.getKeyRowsLetters(), 4), false);
        spinnerColumns.setSelection(settings.getInt(SettingsActivity.getKeyColumnsLetters(), 4), false);
        switchRussianOrEnglish.setChecked(settings.getBoolean(SettingsActivity.getKeyRussianOrEnglish(), false));

        spinnerRows.setOnItemSelectedListener(this);
        spinnerColumns.setOnItemSelectedListener(this);
        switchRussianOrEnglish.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor ed;
        ed = settings.edit();

        if (v.getId() == R.id.switchRussianOrEnglish) {
            ed.putBoolean(SettingsActivity.getKeyRussianOrEnglish(), ((SwitchMaterial) v).isChecked());
            ed.apply();
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences.Editor ed;
        ed = settings.edit();

        int parentId = parent.getId();

        if (parentId == R.id.spinnerColumnsLetters) {
            ed.putInt(SettingsActivity.getKeyColumnsLetters(), position);
            ed.apply();

        } else if (parentId == R.id.spinnerRowsLetters) {
            ed.putInt(SettingsActivity.getKeyRowsLetters(), position);
            ed.apply();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}