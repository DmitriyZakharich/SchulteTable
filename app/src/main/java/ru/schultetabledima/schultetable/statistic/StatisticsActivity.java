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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

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
    private final int loaderIdPlayedSizes = 1;
    private static int loaderId = 2;
    private Spinner selectPlayedSizes;
    private String playedSizes = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        Spinner selectQuantityTables = findViewById(R.id.spinnerQuantityTables);
        Spinner selectValueType = findViewById(R.id.spinnerValueType);
        selectPlayedSizes = findViewById(R.id.spinnerPlayedSizes);

        String[] valueSpinnerQuantityTables = new String[]{getString(R.string.oneTable), getString(R.string.twoTables), getString(R.string.allOptions)};
        String[] valueSpinnerValueType = new String[]{getString(R.string.numbers), getString(R.string.englishLetters), getString(R.string.russianLetters),
                getString(R.string.allLetters), getString(R.string.allValueTypes)};

        ArrayAdapter<String> adapterQuantityTables = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, valueSpinnerQuantityTables);
        adapterQuantityTables.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapterValueType = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, valueSpinnerValueType);
        adapterValueType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        selectQuantityTables.setAdapter(adapterQuantityTables);
        selectValueType.setAdapter(adapterValueType);

        selectQuantityTables.setOnItemSelectedListener(this);
        selectValueType.setOnItemSelectedListener(this);
        selectPlayedSizes.setOnItemSelectedListener(this);


        databaseAdapter = new DatabaseAdapter(this);
        databaseAdapter.open();

        statisticAdapter = new StatisticAdapter(null, this);
        recyclerView.setAdapter(statisticAdapter);

        //Запуск загрузки курсора с помощью MyCursorLoader
        LoaderManager.getInstance(this).initLoader(loaderId, null, this);

        //Загрузка размеров сыгранных таблиц в спиннер
        LoaderManager.getInstance(this).initLoader(loaderIdPlayedSizes, null, this);
    }


    private void FillingSpinnerPlayedSizes(Cursor cursor) {
        ArrayList<String>  arrayPlayedSizes = new ArrayList<>();
        arrayPlayedSizes.add(getString(R.string.allSize));

        while (cursor.moveToNext()){
            arrayPlayedSizes.add(cursor.getString(
                    cursor.getColumnIndex(DatabaseAdapter.DatabaseHelper.COLUMN_SIZE_FIELD)));
        }

        String[] valueSpinnerPlayedSizes = arrayPlayedSizes.toArray(new String[0]);
        ArrayAdapter<String> adapterPlayedSizes = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, valueSpinnerPlayedSizes);
        adapterPlayedSizes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        selectPlayedSizes.setAdapter(adapterPlayedSizes);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseAdapter.closeDataBase();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        if (id == loaderId)
            return new MyCursorLoader(this, databaseAdapter, id, quantityTables, valueType, valueLanguage, playedSizes);
        else
            return new MyCursorLoader(this, databaseAdapter, id);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        if (loader.getId() == loaderId){
            statisticAdapter.swapCursor(data);
        }
        if(loader.getId() == loaderIdPlayedSizes){
            FillingSpinnerPlayedSizes(data);
        }
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

        }if (parentId == R.id.spinnerPlayedSizes){
            playedSizes = parent.getSelectedItem().toString();
        }

        LoaderManager.getInstance(this).initLoader(++loaderId, null, this);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}


    private static class MyCursorLoader extends CursorLoader{
        private DatabaseAdapter db;
        private int id;
        private int quantityTables;
        private String valueType;
        private String valueLanguage;
        private String playedSizes;

        public MyCursorLoader(@NonNull Context context, DatabaseAdapter db, int id, int quantityTables, String valueType,
                              String valueLanguage, String playedSizes) {
            super(context);
            this.db = db;
            this.id = id;
            this.quantityTables = quantityTables;
            this.valueType = valueType;
            this.valueLanguage = valueLanguage;
            this.playedSizes = playedSizes;
        }

        public MyCursorLoader(Context context, DatabaseAdapter db, int id) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            if (id >= loaderId)
                return db.getCursor(quantityTables, valueType, valueLanguage, playedSizes);
            else
                return db.getCursorPlayedSizes();
        }
    }
}





