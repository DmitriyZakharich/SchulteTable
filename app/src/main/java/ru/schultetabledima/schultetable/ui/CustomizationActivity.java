package ru.schultetabledima.schultetable.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.switchmaterial.SwitchMaterial;

import ru.schultetabledima.schultetable.R;

public class CustomizationActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private final String [] valueSpinner = {"1","2","3","4","5","6","7","8","9","10"};

    private static SharedPreferences sPrefCustomization;
    private static final String APP_PREFERENCES = "my_settings";

    private static final String KEY_NUMBER_COLUMNS = "SAVED spinnerColumns";
    private static final String KEY_NUMBER_ROWS = "SAVED spinnerRows";
    private static final String KEY_ANIMATION = "switchAnimation";
    private static final String KEY_TOUCH_CELLS = "switchTouchCells";
    private static final String KEY_NUMBERS_LETTERS = "switchNumbersLetters";


    private static final String KEY_TWO_TABLES = "switchTwoTables";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customization);

        Spinner spinnerColumns = findViewById(R.id.spinnerColumns);
        Spinner spinnerRows = findViewById(R.id.spinnerRows);
        SwitchMaterial switchAnimation = findViewById(R.id.switchAnimation);
        SwitchMaterial switchTouchCells = findViewById(R.id.switchTouchСells);
        SwitchMaterial switchTwoTableS = findViewById(R.id.switchTwoTables);
        SwitchMaterial switchNumbersLetters = findViewById(R.id.switchNumbersLetters);
        Button buttonToTable = findViewById(R.id.buttonToTable);

        sPrefCustomization = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, valueSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerColumns.setAdapter(adapter);
        spinnerRows.setAdapter(adapter);

        spinnerColumns.setSelection(sPrefCustomization.getInt(KEY_NUMBER_COLUMNS, 4), false);
        spinnerRows.setSelection(sPrefCustomization.getInt(KEY_NUMBER_ROWS, 4), false);
        switchAnimation.setChecked(sPrefCustomization.getBoolean(KEY_ANIMATION, false));
        switchTouchCells.setChecked(sPrefCustomization.getBoolean(KEY_TOUCH_CELLS, true));
        switchNumbersLetters.setChecked(sPrefCustomization.getBoolean(KEY_NUMBERS_LETTERS, false));
        switchTwoTableS.setChecked(sPrefCustomization.getBoolean(KEY_TWO_TABLES, false));

        spinnerColumns.setOnItemSelectedListener(this);
        spinnerRows.setOnItemSelectedListener(this);
        switchAnimation.setOnClickListener(this);
        switchTouchCells.setOnClickListener(this);
        switchNumbersLetters.setOnClickListener(this);
        switchTwoTableS.setOnClickListener(this);
        buttonToTable.setOnClickListener(this);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences.Editor ed;
        ed = sPrefCustomization.edit();
        switch (parent.getId()) {
            case R.id.spinnerColumns:
                ed.putInt(KEY_NUMBER_COLUMNS, position);
                ed.apply();
                break;
            case R.id.spinnerRows:
                ed.putInt(KEY_NUMBER_ROWS, position);
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
                ed.putBoolean(KEY_ANIMATION, ((SwitchMaterial)v).isChecked());
                ed.apply();
            break;
            case R.id.switchTouchСells:
                ed.putBoolean(KEY_TOUCH_CELLS, ((SwitchMaterial)v).isChecked());
                ed.apply();
                break;
            case R.id.buttonToTable:
                startActivity(new Intent(CustomizationActivity.this, TableActivity.class));
                break;
            case R.id.switchTwoTables:
                ed.putBoolean(KEY_TWO_TABLES, ((SwitchMaterial)v).isChecked());
                ed.apply();
                break;
            case R.id.switchNumbersLetters:
                ed.putBoolean(KEY_NUMBERS_LETTERS, ((SwitchMaterial)v).isChecked());
                ed.apply();
                break;
        }
    }

    public static SharedPreferences getsPrefCustomization() {
        return sPrefCustomization;
    }

    public static String getAppPreferences() {
        return APP_PREFERENCES;
    }

    public static String getKeyNumberColumns() {
        return KEY_NUMBER_COLUMNS;
    }

    public static String getKeyNumberRows() {
        return KEY_NUMBER_ROWS;
    }

    public static String getKeyAnimation() {
        return KEY_ANIMATION;
    }

    public static String getKeyTouchCells() {
        return KEY_TOUCH_CELLS;
    }
    public static String getKeyNumbersLetters() {
        return KEY_NUMBERS_LETTERS;
    }
    public static String getKeyTwoTables() {
        return KEY_TWO_TABLES;
    }
}