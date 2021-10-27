package ru.schultetabledima.schultetable.statistic;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ArrayAdapter;

import java.util.Arrays;
import java.util.List;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.schultetabledima.schultetable.App;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.contracts.StatisticsContract;
import ru.schultetabledima.schultetable.database.AppDatabase;
import ru.schultetabledima.schultetable.database.Result;
import ru.schultetabledima.schultetable.database.ResultDao;
import ru.schultetabledima.schultetable.database.StatisticAdapter;

@InjectViewState
public class StatisticsPresenter extends MvpPresenter<StatisticsContract.View> implements StatisticsContract.Presenter {

    private StatisticAdapter statisticAdapter;
    private final Context context = App.getContext();
    private int quantityTables = 0;
    private String valueType = "";
    private String playedSizes = context.getString(R.string.allSize);
    private Handler handlerResults, handlerPlayedSizes;
    private SharedPreferences sharedPreferencesStatistics;
    private List<String> valueSpinnerValueType, valueSpinnerQuantityTables;

    private final String STATISTICS_PREFERENCES = "Preferences_Statistics1";
    private final String KEY_QUANTITY_TABLES = "key_quantity_tables";
    private final String KEY_VALUE_TYPE = "key_value_type";
    private final String KEY_PLAYED_SIZES = "key_played_sizes";

    private final int ALL_OPTIONS = 2;
    private final int ALL_TABLE_SIZE = 0;
    private final int SPINNER_VALUE_TYPE_NUMBERS = 0;
    private final int SPINNER_VALUE_TYPE_ENGLISH_LETTERS = 1;
    private final int SPINNER_VALUE_TYPE_RUSSIAN_LETTERS = 2;
    private final int SPINNER_VALUE_TYPE_ALL = 3;

    public StatisticsPresenter() {
        main();
    }

    private void main() {
        customizationSpinners();
        customizationSpinnerPlayedSizes();
        getResults();
    }

    private void customizationSpinners() {

        valueSpinnerQuantityTables = Arrays.asList(context.getResources().getStringArray(R.array.spinnerQuantityTables));
        valueSpinnerValueType = Arrays.asList(context.getResources().getStringArray(R.array.spinnerValueType));


        ArrayAdapter<String> adapterQuantityTables = new ArrayAdapter(context, R.layout.custom_spinner_style,
                valueSpinnerQuantityTables);
        adapterQuantityTables.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapterValueType = new ArrayAdapter(context, R.layout.custom_spinner_style, valueSpinnerValueType);
        adapterValueType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        getViewState().setQuantityTablesAdapter(adapterQuantityTables);
        getViewState().setValueTypeAdapter(adapterValueType);
    }


    private void customizationSpinnerPlayedSizes() {
        handlerPlayedSizes = new Handler(Looper.getMainLooper()) {
            public void handleMessage(android.os.Message msg) {

                List<String> tableSize = (List<String>) msg.obj;
                fillingSpinnerPlayedSizes(tableSize);
            }
        };
        loadPlayedSizesForSpinner();
    }

    private void loadPlayedSizesForSpinner() {
        new Thread(() -> {
            Message msg = new Message();

            AppDatabase db = App.getInstance().getDatabase();
            ResultDao resultDao = db.resultDao();

            msg.obj = resultDao.getTableSize();

            handlerPlayedSizes.sendMessage(msg);
        }).start();
    }

    private void fillingSpinnerPlayedSizes(List<String> tableSize) {

        tableSize.add(0, context.getString(R.string.allSize));

        ArrayAdapter<String> adapterPlayedSizes = new ArrayAdapter<>(context, R.layout.custom_spinner_style, tableSize);
        adapterPlayedSizes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        getViewState().setPlayedSizesAdapter(adapterPlayedSizes);

        readPreferenceForSpinner();
    }

    private void readPreferenceForSpinner() {
        sharedPreferencesStatistics = context.getSharedPreferences(STATISTICS_PREFERENCES, MODE_PRIVATE);

        getViewState().setSelectionQuantityTables(sharedPreferencesStatistics.getInt(KEY_QUANTITY_TABLES, ALL_OPTIONS));
        getViewState().setSelectionPlayedSizes(sharedPreferencesStatistics.getInt(KEY_PLAYED_SIZES, ALL_TABLE_SIZE));
        getViewState().setSelectionSpinnerValueType(sharedPreferencesStatistics.getInt(KEY_VALUE_TYPE, SPINNER_VALUE_TYPE_ALL));
    }

    private void getResults() {

        handlerResults = new Handler(Looper.getMainLooper()) {
            public void handleMessage(android.os.Message msg) {
                List<Result> results = (List<Result>) msg.obj;

                statisticAdapter = new StatisticAdapter(context, results);
                getViewState().setRecyclerViewAdapter(statisticAdapter);
            }
        };
        loadResultsFromDB();
    }

    private void loadResultsFromDB() {
        new Thread(() -> {
            Message msg = new Message();

            AppDatabase db = App.getInstance().getDatabase();
            ResultDao resultDao = db.resultDao();

            msg.obj = resultDao.getAll(ConstructQuery.getQuery(quantityTables, valueType, playedSizes));

            handlerResults.sendMessage(msg);
        }).start();
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
                    break;
                case SPINNER_VALUE_TYPE_ENGLISH_LETTERS:
                    valueType = context.getString(R.string.languageEnglish);
                    break;
                case SPINNER_VALUE_TYPE_RUSSIAN_LETTERS:
                    valueType = context.getString(R.string.languageRussian);
                    break;
                case SPINNER_VALUE_TYPE_ALL:
                    valueType = "";
                    break;
            }
            ed.putInt(KEY_VALUE_TYPE, position);

        }
        if (parentId == R.id.spinnerPlayedSizes) {
            playedSizes = itemText;
            ed.putInt(KEY_PLAYED_SIZES, position);
        }

        ed.apply();

        loadResultsFromDB();
    }
}
