package ru.schultetabledima.schultetable.statistic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
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


    public StatisticsPresenter(){
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
        String[] valueSpinnerQuantityTables = new String[]{context.getString(R.string.oneTable),
                context.getString(R.string.twoTables), context.getString(R.string.allOptions)};

        String[] valueSpinnerValueType = new String[]{context.getString(R.string.numbers),
                context.getString(R.string.englishLetters), context.getString(R.string.russianLetters),
                context.getString(R.string.allLetters), context.getString(R.string.allValueTypes)};


        ArrayAdapter<String> adapterQuantityTables = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item,
                valueSpinnerQuantityTables);
        adapterQuantityTables.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<String> adapterValueType = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, valueSpinnerValueType);
        adapterValueType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        getViewState().setQuantityTablesAdapter(adapterQuantityTables);
        getViewState().setValueTypeAdapter(adapterValueType);
    }



    private void getCursorPlayedSizes() {
        handlerPlayedSizes = new Handler(Looper.getMainLooper()){
            public void handleMessage(android.os.Message msg) {
                Cursor cursor = (Cursor) msg.obj;
                fillingSpinnerPlayedSizes(cursor);
            }
        };
        loadPlayedSizesForSpinner();
    }

    private void loadPlayedSizesForSpinner(){
        new Thread(() -> {
            Message msg = new Message();
            msg.obj = databaseAdapter.getCursorPlayedSizes();
            handlerPlayedSizes.sendMessage(msg);
        }).start();
    }

    @SuppressLint("Range")
    private void fillingSpinnerPlayedSizes(Cursor cursor) {
        List<String> arrayPlayedSizes = new ArrayList<>();
        arrayPlayedSizes.add(context.getString(R.string.allSize));

        while (cursor.moveToNext()){
            arrayPlayedSizes.add(cursor.getString(
                    cursor.getColumnIndex(DatabaseAdapter.DatabaseHelper.COLUMN_SIZE_FIELD)));
        }

        ArrayAdapter<String> adapterPlayedSizes = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, arrayPlayedSizes);
        adapterPlayedSizes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        getViewState().setPlayedSizesAdapter(adapterPlayedSizes);

    }


    private void getResults(){
        handlerResults = new Handler(Looper.getMainLooper()){
            public void handleMessage(android.os.Message msg) {
                Cursor cursor = (Cursor) msg.obj;
                swapCursor(cursor);
            }
        };
        loadResults();
    }

    private void loadResults(){
        new Thread(() -> {
            Message msg = new Message();
            msg.obj = databaseAdapter.getCursor(quantityTables, valueType, valueLanguage, playedSizes);
            handlerResults.sendMessage(msg);
        }).start();
    }

    private void swapCursor(Cursor cursor){
        statisticAdapter.swapCursor(cursor);
        getViewState().setRecyclerViewAdapter(statisticAdapter);
    }

    public void spinnerItemSelected(int parentId, int position, String itemText) {

        final int SPINNER_VALUE_TYPE_NUMBERS = 0;
        final int SPINNER_VALUE_TYPE_ENGLISH_LETTERS = 1;
        final int SPINNER_VALUE_TYPE_RUSSIAN_LETTERS = 2;
        final int SPINNER_VALUE_TYPE_ALL_LETTERS = 3;
        final int SPINNER_VALUE_TYPE_ALL = 4;


        if (parentId == R.id.spinnerQuantityTables) {
            quantityTables = position + 1;

        } else if (parentId == R.id.spinnerValueType) {
            switch (position){
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

        }if (parentId == R.id.spinnerPlayedSizes){
            playedSizes = itemText;
        }

        loadResults();
    }
}
