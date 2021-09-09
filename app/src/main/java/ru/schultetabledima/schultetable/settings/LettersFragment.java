package ru.schultetabledima.schultetable.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.google.android.material.switchmaterial.SwitchMaterial;

import ru.schultetabledima.schultetable.R;


public class LettersFragment extends BaseFragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private SwitchMaterial switchRussianOrEnglish;

    public static LettersFragment newInstance() {
        return new LettersFragment();
    }

    public LettersFragment() {}

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

        if (savedInstanceState == null)
            settingsPresenter.customizationLettersFragment();
        else{
            settingsPresenter = ((SettingsActivity) requireActivity()).getPresenter();
            settingsPresenter.attachViewFragment(this);
            settingsPresenter.restoreInstanceState();
            settingsPresenter.customizationLettersFragment();
        }

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


    public void setSwitchRussianOrEnglish(boolean isChecked){
        switchRussianOrEnglish.setChecked(isChecked);
    }
}