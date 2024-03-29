package ru.schultetabledima.schultetable.screens.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.android.material.switchmaterial.SwitchMaterial;

import ru.schultetabledima.schultetable.R;


public class LettersFragment extends BaseValueFragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private SwitchMaterial switchRussianOrEnglish;
    private LettersFragmentPresenter presenter;

    public static LettersFragment newInstance() {
        return new LettersFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_letters, container, false);

        findView(rootView);
        setListener();
        setSpinnerAdapter();

        switchRussianOrEnglish.setChecked(false);
        presenter = new LettersFragmentPresenter(this);
        return rootView;
    }

    private void findView(View rootView) {
        spinnerRows = rootView.findViewById(R.id.spinnerRowsLetters);
        spinnerColumns = rootView.findViewById(R.id.spinnerColumnsLetters);
        switchRussianOrEnglish = rootView.findViewById(R.id.switchRussianOrEnglish);
    }

    private void setListener() {
        spinnerRows.setOnItemSelectedListener(this);
        spinnerColumns.setOnItemSelectedListener(this);
        switchRussianOrEnglish.setOnClickListener(this);
    }

    private void setSpinnerAdapter() {
        final String[] valueSpinner = {"1", "2", "3", "4", "5"};

        ArrayAdapter<String> adapter = new ArrayAdapter(getActivity(), R.layout.custom_spinner_style, valueSpinner);
        adapter.setDropDownViewResource(R.layout.spin_item);

        spinnerRows.setAdapter(adapter);
        spinnerColumns.setAdapter(adapter);
    }

    @Override

    public void onClick(View v) {
        presenter.lettersFragmentListener(v.getId(), ((SwitchMaterial) v).isChecked());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        presenter.lettersFragmentListener(parent.getId(), position);
    }

    public void setSwitchRussianOrEnglish(boolean isChecked) {
        switchRussianOrEnglish.setChecked(isChecked);
    }
}