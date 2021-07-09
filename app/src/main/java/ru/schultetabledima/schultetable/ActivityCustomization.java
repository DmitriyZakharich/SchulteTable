package ru.schultetabledima.schultetable;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityCustomization extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private String [] valueSpinner = {"1","2","3","4","5","6","7","8","9","10"};
    Spinner spinnerColumns;
    Spinner spinnerRows;
    Switch switchAnimation;
    Switch switchTouchСells;

    public static SharedPreferences sPref;
    public static final String APP_PREFERENCES = "mysettings";

    static String sPrefSpinnerColumns = "SAVED spinnerColumns";
    static String sPrefSpinnerRows = "SAVED spinnerRows";
    static String booleanSwitchAnimation = "booleanSwitchAnimation";
    static String booleanSwitchTouchСells = "booleanSwitchTouchСells";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customization);

        spinnerColumns = findViewById(R.id.spinnerColumns);
        spinnerRows = findViewById(R.id.spinnerRows);
        switchAnimation = findViewById(R.id.switchAnimation);
        switchTouchСells = findViewById(R.id.switchTouchСells);

        sPref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, valueSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerColumns.setAdapter(adapter);
        spinnerRows.setAdapter(adapter);

        spinnerColumns.setSelection(sPref.getInt(sPrefSpinnerColumns, 4), false);
        spinnerRows.setSelection(sPref.getInt(sPrefSpinnerRows, 4), false);
        switchAnimation.setChecked(sPref.getBoolean(booleanSwitchAnimation, false));
        switchTouchСells.setChecked(sPref.getBoolean(booleanSwitchTouchСells, false));

        spinnerColumns.setOnItemSelectedListener(this);
        spinnerRows.setOnItemSelectedListener(this);
        switchAnimation.setOnClickListener(this);
        switchTouchСells.setOnClickListener(this);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences.Editor ed;
        ed = sPref.edit();
        switch (parent.getId()) {
            case R.id.spinnerColumns:
                ed.putInt(sPrefSpinnerColumns, position);
                ed.apply();
                break;
            case R.id.spinnerRows:
                ed.putInt(sPrefSpinnerRows, position);
                ed.apply();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor ed;
        ed = sPref.edit();

        switch (v.getId()){
            case R.id.switchAnimation:
                ed.putBoolean(booleanSwitchAnimation, ((Switch)v).isChecked());
                ed.apply();
            break;
            case R.id.switchTouchСells:
                ed.putBoolean(booleanSwitchTouchСells, ((Switch)v).isChecked());
                ed.apply();
                break;
        }


    }
}