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

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    private final String [] valueSpinner = {"1","2","3","4","5","6","7","8","9","10"};

    private static SharedPreferences settings;
    private static final String APP_PREFERENCES = "my_settings";

    private static final String KEY_NUMBER_COLUMNS = "SAVED spinnerColumns";
    private static final String KEY_NUMBER_ROWS = "SAVED spinnerRows";
    private static final String KEY_ANIMATION = "switchAnimation";
    private static final String KEY_TOUCH_CELLS = "switchTouchCells";
    private static final String KEY_NUMBERS_LETTERS = "switchNumbersLetters";
    private static final String KEY_RUSSIAN_OR_ENGLISH = "switchRussianOrEnglish";
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
        SwitchMaterial switchRussianOrEnglish = findViewById(R.id.switchRussianOrEnglish);
        Button buttonToTable = findViewById(R.id.buttonToTable);

        settings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, valueSpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerColumns.setAdapter(adapter);
        spinnerRows.setAdapter(adapter);

        spinnerColumns.setSelection(settings.getInt(KEY_NUMBER_COLUMNS, 4), false);
        spinnerRows.setSelection(settings.getInt(KEY_NUMBER_ROWS, 4), false);
        switchAnimation.setChecked(settings.getBoolean(KEY_ANIMATION, false));
        switchTouchCells.setChecked(settings.getBoolean(KEY_TOUCH_CELLS, true));
        switchNumbersLetters.setChecked(settings.getBoolean(KEY_NUMBERS_LETTERS, false));
        switchTwoTableS.setChecked(settings.getBoolean(KEY_TWO_TABLES, false));
        switchRussianOrEnglish.setChecked(settings.getBoolean(KEY_RUSSIAN_OR_ENGLISH, false));

        spinnerColumns.setOnItemSelectedListener(this);
        spinnerRows.setOnItemSelectedListener(this);
        switchAnimation.setOnClickListener(this);
        switchTouchCells.setOnClickListener(this);
        switchNumbersLetters.setOnClickListener(this);
        switchTwoTableS.setOnClickListener(this);
        buttonToTable.setOnClickListener(this);
        switchRussianOrEnglish.setOnClickListener(this);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SharedPreferences.Editor ed;
        ed = settings.edit();

        int parentId = parent.getId();

        if (parentId == R.id.spinnerColumns) {
            ed.putInt(KEY_NUMBER_COLUMNS, position);
            ed.apply();
        } else if (parentId == R.id.spinnerRows) {
            ed.putInt(KEY_NUMBER_ROWS, position);
            ed.apply();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}

    @Override
    public void onClick(View v) {
        SharedPreferences.Editor ed;
        ed = settings.edit();

        int id = v.getId();

        if (id == R.id.switchAnimation) {
            ed.putBoolean(KEY_ANIMATION, ((SwitchMaterial) v).isChecked());
            ed.apply();

        } else if (id == R.id.switchTouchСells) {
            ed.putBoolean(KEY_TOUCH_CELLS, ((SwitchMaterial) v).isChecked());
            ed.apply();

        } else if (id == R.id.buttonToTable) {
            startActivity(new Intent(SettingsActivity.this, TableActivity.class));

        } else if (id == R.id.switchTwoTables) {
            ed.putBoolean(KEY_TWO_TABLES, ((SwitchMaterial) v).isChecked());
            ed.apply();

        } else if (id == R.id.switchNumbersLetters) {
            ed.putBoolean(KEY_NUMBERS_LETTERS, ((SwitchMaterial) v).isChecked());
            ed.apply();

        } else if (id == R.id.switchRussianOrEnglish) {
            ed.putBoolean(KEY_RUSSIAN_OR_ENGLISH, ((SwitchMaterial) v).isChecked());
            ed.apply();
        }
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
    public static String getKeyRussianOrEnglish() {
        return KEY_RUSSIAN_OR_ENGLISH;
    }
}