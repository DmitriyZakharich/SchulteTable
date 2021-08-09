package ru.schultetabledima.schultetable.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import ru.schultetabledima.schultetable.R;

public class CustomizationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private final String [] valueSpinner = {"1","2","3","4","5","6","7","8","9","10"};
    private Spinner spinnerColumns;
    private Spinner spinnerRows;
    private Switch switchAnimation;
    private Switch switchTouchCells;
    private Switch switchDoubleTable;
    private Button buttonToTable;

    public static SharedPreferences sPrefCustomization;
    public static final String APP_PREFERENCES = "my_settings";

    static final String PREFERENCES_KEY_NUMBER_COLUMNS = "SAVED spinnerColumns";
    static final String PREFERENCES_KEY_NUMBER_ROWS = "SAVED spinnerRows";
    static final String PREFERENCES_KEY_ANIMATION = "booleanSwitchAnimation";
    static final String PREFERENCES_KEY_TOUCH_CELLS = "booleanSwitchTouchCells";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customization);

        spinnerColumns = findViewById(R.id.spinnerColumns);
        spinnerRows = findViewById(R.id.spinnerRows);
        switchAnimation = findViewById(R.id.switchAnimation);
        switchTouchCells = findViewById(R.id.switchTouchСells);
        switchDoubleTable = findViewById(R.id.switchDoubleTable);
        buttonToTable = findViewById(R.id.buttonToTable);

        sPrefCustomization = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, valueSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerColumns.setAdapter(adapter);
        spinnerRows.setAdapter(adapter);

        spinnerColumns.setSelection(sPrefCustomization.getInt(PREFERENCES_KEY_NUMBER_COLUMNS, 4), false);
        spinnerRows.setSelection(sPrefCustomization.getInt(PREFERENCES_KEY_NUMBER_ROWS, 4), false);
        switchAnimation.setChecked(sPrefCustomization.getBoolean(PREFERENCES_KEY_ANIMATION, false));
        switchTouchCells.setChecked(sPrefCustomization.getBoolean(PREFERENCES_KEY_TOUCH_CELLS, true));

        spinnerColumns.setOnItemSelectedListener(this);
        spinnerRows.setOnItemSelectedListener(this);
        switchAnimation.setOnClickListener(this);
        switchTouchCells.setOnClickListener(this);
        buttonToTable.setOnClickListener(this);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences.Editor ed;
        ed = sPrefCustomization.edit();
        switch (parent.getId()) {
            case R.id.spinnerColumns:
                ed.putInt(PREFERENCES_KEY_NUMBER_COLUMNS, position);
                ed.apply();
                break;
            case R.id.spinnerRows:
                ed.putInt(PREFERENCES_KEY_NUMBER_ROWS, position);
                ed.apply();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor ed;
        ed = sPrefCustomization.edit();

        switch (v.getId()){
            case R.id.switchAnimation:
                ed.putBoolean(PREFERENCES_KEY_ANIMATION, ((Switch)v).isChecked());
                ed.apply();
            break;
            case R.id.switchTouchСells:
                ed.putBoolean(PREFERENCES_KEY_TOUCH_CELLS, ((Switch)v).isChecked());
                ed.apply();
                break;
            case R.id.buttonToTable:
                startActivity(new Intent(CustomizationActivity.this, TableActivity.class));
                break;
        }
    }

    public static SharedPreferences getsPrefCustomization() {
        return sPrefCustomization;
    }

    public static String getAppPreferences() {
        return APP_PREFERENCES;
    }

    public static String getPreferencesKeyNumberColumns() {
        return PREFERENCES_KEY_NUMBER_COLUMNS;
    }

    public static String getPreferencesKeyNumberRows() {
        return PREFERENCES_KEY_NUMBER_ROWS;
    }

    public static String getPreferencesKeyAnimation() {
        return PREFERENCES_KEY_ANIMATION;
    }

    public static String getPreferencesKeyTouchCells() {
        return PREFERENCES_KEY_TOUCH_CELLS;
    }
}