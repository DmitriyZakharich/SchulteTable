package ru.schultetabledima.schultetable.statistic;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.statistic.database.DatabaseAdapter;
import ru.schultetabledima.schultetable.statistic.database.StatisticAdapter;

public class StatisticsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>,
        AdapterView.OnItemSelectedListener {

    private DatabaseAdapter databaseAdapter;
    private StatisticAdapter statisticAdapter;
    private int quantityTables = 0;
    private String valueType = "";
    private String valueLanguage = "";
    private int cursorId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        Spinner selectQuantityTables1 = findViewById(R.id.spinnerQuantityTables);
        Spinner selectValueType1 = findViewById(R.id.spinnerValueType);

        String[] valueSpinnerQuantityTables = new String[]{getString(R.string.oneTable), getString(R.string.twoTables), getString(R.string.allOptions)};
        String[] valueSpinnerValueType = new String[]{getString(R.string.numbers), getString(R.string.englishLetters), getString(R.string.russianLetters),
                getString(R.string.allLetters), getString(R.string.allValueTypes)};

        ArrayAdapter<String> adapterQuantityTables = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, valueSpinnerQuantityTables);
        adapterQuantityTables.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapterValueType = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, valueSpinnerValueType);
        adapterValueType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        selectQuantityTables1.setAdapter(adapterQuantityTables);
        selectValueType1.setAdapter(adapterValueType);

        selectQuantityTables1.setOnItemSelectedListener(this);
        selectValueType1.setOnItemSelectedListener(this);


        databaseAdapter = new DatabaseAdapter(this);
        databaseAdapter.open();

        statisticAdapter = new StatisticAdapter(null, this);
        recyclerView.setAdapter(statisticAdapter);

        //Запуск загрузки курсора с помощью MyCursorLoader
        LoaderManager.getInstance(this).initLoader(cursorId, null, this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseAdapter.closeDataBase();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new MyCursorLoader(this, databaseAdapter, quantityTables, valueType, valueLanguage);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        statisticAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long idSpinner) {
        int parentId = parent.getId();

        if (parentId == R.id.spinnerQuantityTables) {
            quantityTables = position + 1;

        } else if (parentId == R.id.spinnerValueType) {
            switch (position){
                case 0:
                    valueType = getString(R.string.valueTypeNumbers);
                    valueLanguage = "";
                    break;
                case 1:
                    valueType = getString(R.string.valueTypeLetters);
                    valueLanguage = getString(R.string.languageEnglish);
                    break;
                case 2:
                    valueType = getString(R.string.valueTypeLetters);
                    valueLanguage = getString(R.string.languageRussian);
                    break;
                case 3:
                    valueType = getString(R.string.valueTypeLetters);
                    valueLanguage = "";
                    break;
                case 4:
                    valueType = "";
                    valueLanguage = "";
                    break;
            }
        }
        LoaderManager.getInstance(this).initLoader(++cursorId, null, this);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}


    private static class MyCursorLoader extends CursorLoader{
        DatabaseAdapter db;
        private int quantityTables;
        private String valueType;
        private String valueLanguage;

        public MyCursorLoader(@NonNull Context context, DatabaseAdapter db, int quantityTables, String valueType, String valueLanguage) {
            super(context);
            this.db = db;
            this.quantityTables = quantityTables;
            this.valueType = valueType;
            this.valueLanguage = valueLanguage;
        }

        @Override
        public Cursor loadInBackground() {
            return db.getCursor(quantityTables, valueType, valueLanguage);
        }
    }
}





