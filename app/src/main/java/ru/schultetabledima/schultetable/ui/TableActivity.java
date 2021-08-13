package ru.schultetabledima.schultetable.ui;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.TextViewCompat;

import java.util.ArrayList;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.TableCreator;
import ru.schultetabledima.schultetable.contracts.TableContract;
import ru.schultetabledima.schultetable.presenters.TablePresenter;
import ru.schultetabledima.schultetable.utils.Converter;

public class TableActivity extends AppCompatActivity implements TableContract.View {


    private AppCompatTextView[][] CellsOfTable;
    private ImageButton ibSettings, ibStatistics;
    private int rowsOfTable;
    private int columnsOfTable;
    private ArrayList<Integer> listNumber;
    private final String KEY_FOR_LIST_NUMBER ="ArrayOfNumbers";
    private final String KEY_FOR_SAVE_COUNT ="strCountSave";

    private Chronometer chronometer;
    private int rowCellTenForTextSize;
    private int columnCellTenForTextSize;
    private boolean booleanMoreTenCells = false;
    private final String KEY_ROW_TEN_CELL_TEXTSIZE = "keyRowTenCellTextSize";
    private final String KEY_COLUMN_TEN_CELL_TEXTSIZE = "keyColumnTenCellTextSize";
    private final String KEY_BOOLEAN_MORE_TEN_CELLS = "keyBooleanMoreTenCells";
    private LinearLayout llForTable;
    private final String saveChronometer = "saveChronometer";

    private Toolbar tbMenu;
    private ImageButton ibShowHideMenu;
    private static SharedPreferences sharedPreferencesMenu;
    private static final String MENU_PREFERENCES = "PreferencesMenu";
    static final String KEY_MENU_VISIBILITY = "Saved Menu Visibility";
    private boolean isMenuShow;
    private TablePresenter tablePresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);


        ibShowHideMenu = (ImageButton)findViewById(R.id.image_Button_Show_Hide_Menu);
        ibSettings = (ImageButton) findViewById(R.id.image_button_settings);
        ibStatistics = (ImageButton) findViewById(R.id.image_button_statistics);
        llForTable = (LinearLayout) findViewById(R.id.LinearLayoutForTable);

        tbMenu = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(tbMenu);

        ibSettings.setOnClickListener(moveAnotherActivity);
        ibStatistics.setOnClickListener(moveAnotherActivity);
        ibShowHideMenu.setOnClickListener(methodShowHideMenu);

        //секундомер
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.start();

        //Чтение настроек строки меню
        sharedPreferencesMenu = getSharedPreferences(MENU_PREFERENCES, MODE_PRIVATE);
        isMenuShow = sharedPreferencesMenu.getBoolean(KEY_MENU_VISIBILITY, true);


        if (savedInstanceState == null)
            tablePresenter = new TablePresenter(this);


        //Анимация показать-скрыть меню навигации
        LayoutTransition layoutTransitionToolbar = tbMenu.getLayoutTransition();
        layoutTransitionToolbar.enableTransitionType(LayoutTransition.CHANGING);



        if(isMenuShow){
            tbMenu.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getPx(40)));

            chronometer.setVisibility(View.VISIBLE);
            ibSettings.setVisibility(View.VISIBLE);
            ibStatistics.setVisibility(View.VISIBLE);
            ibShowHideMenu.setImageResource(R.drawable.ic_arrow_down);
        }else{
            tbMenu.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getPx(20)));

            chronometer.setVisibility(View.INVISIBLE);
            ibSettings.setVisibility(View.INVISIBLE);
            ibStatistics.setVisibility(View.INVISIBLE);
            ibShowHideMenu.setImageResource(R.drawable.ic_arrow_up);
        }
}

    @Override
    public void showTable(TableLayout tlTable) {
        llForTable.addView(tlTable);
        addAnim(tlTable);
    }

    void addAnim(TableLayout tlTable){
        LayoutTransition layoutTransitionTable = new LayoutTransition();
        tlTable.setLayoutTransition(layoutTransitionTable);
        layoutTransitionTable.enableTransitionType(LayoutTransition.CHANGING);
    }

    View.OnClickListener methodShowHideMenu = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            SharedPreferences.Editor ed;
            ed = sharedPreferencesMenu.edit();

            if (isMenuShow){
                isMenuShow = false;
                chronometer.setVisibility(View.INVISIBLE);
                ibSettings.setVisibility(View.INVISIBLE);
                ibStatistics.setVisibility(View.INVISIBLE);

                tbMenu.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getPx(20)));
                ibShowHideMenu.setImageResource(R.drawable.ic_arrow_up);

            }else{
                isMenuShow = true;
                chronometer.setVisibility(View.VISIBLE);
                ibSettings.setVisibility(View.VISIBLE);
                ibStatistics.setVisibility(View.VISIBLE);

                tbMenu.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getPx(40)));
                ibShowHideMenu.setImageResource(R.drawable.ic_arrow_down);
            }
            ed.putBoolean(KEY_MENU_VISIBILITY, isMenuShow);
            ed.apply();
        }
    };


    View.OnClickListener moveAnotherActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tablePresenter.moveAnotherActivity(v);
        }
    };


    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    //Сохранение информации при поворатах Активити
    // сохранение состояния
    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        outState.putIntegerArrayList(KEY_FOR_LIST_NUMBER, listNumber);
//        outState.putInt(KEY_FOR_SAVE_COUNT, countSave);

//        outState.putInt(KEY_ROW_TEN_CELL_TEXTSIZE, rowCellTenForTextSize);
//        outState.putInt(KEY_COLUMN_TEN_CELL_TEXTSIZE, columnCellTenForTextSize);
//        outState.putBoolean(KEY_BOOLEAN_MORE_TEN_CELLS, booleanMoreTenCells);

        outState.putSerializable("key_table_presenter", tablePresenter);
        llForTable.removeAllViews();

        chronometer.stop();
        outState.putLong(saveChronometer, chronometer.getBase() - SystemClock.elapsedRealtime());


        super.onSaveInstanceState(outState);
    }
    // получение ранее сохраненного состояния
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        chronometer.setBase(SystemClock.elapsedRealtime() + savedInstanceState.getLong(saveChronometer));
        chronometer.start();

        tablePresenter = (TablePresenter)savedInstanceState.getSerializable("key_table_presenter");
        llForTable.addView(tablePresenter.getTable());


//        for (int i = 0; i < rowsOfTable; i++) {
//            for (int j = 0; j < columnsOfTable; j++) {
//                CellsOfTable[i][j].setText("" + listNumber.get(rowsOfTable * columnsOfTable - count));
//                count--;
//            }
//        }

        /*
         * Ожидание отрисовки таблицы для получения размеров
         * корректировка размеров шрифта по 10й ячейке
         */
        if(booleanMoreTenCells) {
            CellsOfTable[0][0].post(new Runnable() {
                @Override
                public void run() {
                    int tenCellTextSize = new Converter().getSP(TableActivity.this,
                            CellsOfTable[rowCellTenForTextSize][columnCellTenForTextSize].getTextSize());
                    for (int i = 0; i < rowsOfTable; i++) {
                        for (int j = 0; j < columnsOfTable; j++) {
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(CellsOfTable[i][j], 1,
                                    tenCellTextSize, 1, TypedValue.COMPLEX_UNIT_SP);
                        }
                    }
                }
            });
        }

    }

    public void startChronometer(){
        chronometer.start();
    }
    public void stopChronometer(){
        chronometer.stop();
    }

    public String getTextChronometer(){
        return (String) chronometer.getText();
    }

    public long getBaseChronometer(){
        return chronometer.getBase();
    }
    public void setBaseChronometer(long base){
        chronometer.setBase(base);
    }


    public int getPx(int dp){
        float scale = getResources().getDisplayMetrics().density;
        return((int) (dp * scale + 0.5f));
    }
}