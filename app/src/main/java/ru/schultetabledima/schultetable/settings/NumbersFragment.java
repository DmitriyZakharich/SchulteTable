package ru.schultetabledima.schultetable.settings;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import ru.schultetabledima.schultetable.R;

public class NumbersFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private final String [] valueSpinner = {"1","2","3","4","5","6","7","8","9","10"};

    private static final String APP_PREFERENCES = "my_settings";
    private static SharedPreferences settings;

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

        settings = requireActivity().getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, valueSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinnerRows = rootView.findViewById(R.id.spinnerRowsNumbers);
        Spinner spinnerColumns = rootView.findViewById(R.id.spinnerColumnsNumbers);
        spinnerRows.setAdapter(adapter);
        spinnerColumns.setAdapter(adapter);

        spinnerRows.setSelection(settings.getInt(SettingsActivity.getKeyRowsNumbers(), 4), false);
        spinnerColumns.setSelection(settings.getInt(SettingsActivity.getKeyColumnsNumbers(), 4), false);

        spinnerRows.setOnItemSelectedListener(this);
        spinnerColumns.setOnItemSelectedListener(this);

        return rootView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences.Editor ed;
        ed = settings.edit();

        int parentId = parent.getId();

        if (parentId == R.id.spinnerColumnsNumbers) {
            ed.putInt(SettingsActivity.getKeyColumnsNumbers(), position);
            ed.apply();

        } else if (parentId == R.id.spinnerRowsNumbers) {
            ed.putInt(SettingsActivity.getKeyRowsNumbers(), position);
            ed.apply();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}
