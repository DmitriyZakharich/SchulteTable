package ru.schultetabledima.schultetable.statistic;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.schultetabledima.schultetable.MyApplication;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.contracts.StatisticsContract;
import ru.schultetabledima.schultetable.statistic.database.DatabaseAdapter;
import ru.schultetabledima.schultetable.statistic.database.StatisticAdapter;

@InjectViewState
public class StatisticsPresenter extends MvpPresenter<StatisticsContract.View> implements StatisticsContract.Presenter {

    private DatabaseAdapter databaseAdapter;
    private StatisticAdapter statisticAdapter;
    private final Context context = MyApplication.getContext();
    private int quantityTables = 0;
    private String valueType = "";
    private String valueLanguage = "";
    private String playedSizes = context.getString(R.string.allSize);
    private Handler handlerResults, handlerPlayedSizes;
    private SharedPreferences sharedPreferencesStatistics;
    private List<String> valueSpinnerValueType, valueSpinnerQuantityTables;

    private final String STATISTICS_PREFERENCES = "Preferences_Statistics";
    private final String KEY_QUANTITY_TABLES = "key_quantity_tables";
    private final String KEY_VALUE_TYPE = "key_value_type";
    private final String KEY_PLAYED_SIZES = "key_played_sizes";
    private final int ALL_OPTIONS = 2;
    private final int ALL_TABLE_SIZE = 0;
    private List<String> arrayPlayedSizes;

    final int SPINNER_VALUE_TYPE_NUMBERS = 0;
    final int SPINNER_VALUE_TYPE_ENGLISH_LETTERS = 1;
    final int SPINNER_VALUE_TYPE_RUSSIAN_LETTERS = 2;
    final int SPINNER_VALUE_TYPE_ALL_LETTERS = 3;
    final int SPINNER_VALUE_TYPE_ALL = 4;


    public StatisticsPresenter() {
        main();
    }

    private void main() {
        openDatabase();
        initStatisticAdapter();
        fillingSpinners();

        getCursorPlayedSizes();
        getResults();
    }


    private void openDatabase() {
        databaseAdapter = new DatabaseAdapter(context);
        databaseAdapter.open();
    }

    private void initStatisticAdapter() {
        statisticAdapter = new StatisticAdapter(null, context);
    }


    private void fillingSpinners() {

        valueSpinnerQuantityTables = Arrays.asList(context.getResources().getStringArray(R.array.spinnerQuantityTables));
        valueSpinnerValueType = Arrays.asList(context.getResources().getStringArray(R.array.spinnerValueType));


        ArrayAdapter<String> adapterQuantityTables = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item,
                valueSpinnerQuantityTables);
        adapterQuantityTables.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapterValueType = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, valueSpinnerValueType);
        adapterValueType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        getViewState().setQuantityTablesAdapter(adapterQuantityTables);
        getViewState().setValueTypeAdapter(adapterValueType);
    }


    private void getCursorPlayedSizes() {
        handlerPlayedSizes = new Handler(Looper.getMainLooper()) {
            public void handleMessage(android.os.Message msg) {
                Cursor cursor = (Cursor) msg.obj;
                fillingSpinnerPlayedSizes(cursor);
            }
        };
        loadPlayedSizesForSpinner();
    }

    private void loadPlayedSizesForSpinner() {
        new Thread(() -> {
            Message msg = new Message();
            msg.obj = databaseAdapter.getCursorPlayedSizes();
            handlerPlayedSizes.sendMessage(msg);
        }).start();
    }

    @SuppressLint("Range")
    private void fillingSpinnerPlayedSizes(Cursor cursor) {
        arrayPlayedSizes = new ArrayList<>();
        arrayPlayedSizes.add(context.getString(R.string.allSize));

        while (cursor.moveToNext()) {
            arrayPlayedSizes.add(cursor.getString(
                    cursor.getColumnIndex(DatabaseAdapter.DatabaseHelper.COLUMN_SIZE_FIELD)));
        }

        ArrayAdapter<String> adapterPlayedSizes = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, arrayPlayedSizes);
        adapterPlayedSizes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        getViewState().setPlayedSizesAdapter(adapterPlayedSizes);

        customizationSpinners();
    }


    private void getResults() {
        handlerResults = new Handler(Looper.getMainLooper()) {
            public void handleMessage(android.os.Message msg) {
                Cursor cursor = (Cursor) msg.obj;
                swapCursor(cursor);
            }
        };
        loadResults();
    }

    private void loadResults() {
        new Thread(() -> {
            Message msg = new Message();
            msg.obj = databaseAdapter.getCursor(quantityTables, valueType, valueLanguage, playedSizes);
            handlerResults.sendMessage(msg);
        }).start();
    }

    private void swapCursor(Cursor cursor) {
        statisticAdapter.swapCursor(cursor);
        getViewState().setRecyclerViewAdapter(statisticAdapter);
    }


    private void customizationSpinners() {
        sharedPreferencesStatistics = context.getSharedPreferences(STATISTICS_PREFERENCES, MODE_PRIVATE);

        getViewState().setSelectionQuantityTables(sharedPreferencesStatistics.getInt(KEY_QUANTITY_TABLES, ALL_OPTIONS));
        getViewState().setSelectionPlayedSizes(sharedPreferencesStatistics.getInt(KEY_PLAYED_SIZES, ALL_TABLE_SIZE));
        getViewState().setSelectionSpinnerValueType(sharedPreferencesStatistics.getInt(KEY_VALUE_TYPE, SPINNER_VALUE_TYPE_ALL));
    }


    public void spinnerItemSelected(int parentId, int position, String itemText) {

        SharedPreferences.Editor ed = sharedPreferencesStatistics.edit();

        if (parentId == R.id.spinnerQuantityTables) {
            quantityTables = position + 1;

            ed.putInt(KEY_QUANTITY_TABLES, position);

        } else if (parentId == R.id.spinnerValueType) {
            switch (position) {
                case SPINNER_VALUE_TYPE_NUMBERS:
                    valueType = context.getString(R.string.valueTypeNumbers);
                    valueLanguage = "";
                    break;
                case SPINNER_VALUE_TYPE_ENGLISH_LETTERS:
                    valueType = context.getString(R.string.valueTypeLetters);
                    valueLanguage = context.getString(R.string.languageEnglish);
                    break;
                case SPINNER_VALUE_TYPE_RUSSIAN_LETTERS:
                    valueType = context.getString(R.string.valueTypeLetters);
                    valueLanguage = context.getString(R.string.languageRussian);
                    break;
                case SPINNER_VALUE_TYPE_ALL_LETTERS:
                    valueType = context.getString(R.string.valueTypeLetters);
                    valueLanguage = "";
                    break;
                case SPINNER_VALUE_TYPE_ALL:
                    valueType = "";
                    valueLanguage = "";
                    break;
            }
            ed.putInt(KEY_VALUE_TYPE, position);

        }
        if (parentId == R.id.spinnerPlayedSizes) {
            playedSizes = itemText;
            ed.putInt(KEY_PLAYED_SIZES, position);
        }

        ed.apply();

        loadResults();
    }
}
