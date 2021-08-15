package ru.schultetabledima.schultetable.ui;

import android.animation.LayoutTransition;
import android.content.Context;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.TableCreator;
import ru.schultetabledima.schultetable.contracts.TableContract;
import ru.schultetabledima.schultetable.presenters.TablePresenter;
import ru.schultetabledima.schultetable.utils.Converter;

public class TableActivity extends AppCompatActivity implements TableContract.View{


    private static final String KEY_GSON_TABLE_PRESENTER = "key_table_presenter";
    private ImageButton ibSettings, ibStatistics;

    private Chronometer chronometer;
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

        Log.d("Logssss", "onCreate");


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

    //Сохранение информации при поворатах Активити
    // сохранение состояния
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //Сохранить. Нужно разобраться с Json
//        tablePresenter.saveInstanceState();
        chronometer.stop();
        outState.putLong(saveChronometer, chronometer.getBase() - SystemClock.elapsedRealtime());

        tablePresenter.detachView();
        outState.putSerializable("tablePresenterputSerializable", tablePresenter);
        llForTable.removeAllViews();

        Log.d("Logssss", "onSaveInstanceState");


//        GsonBuilder builder = new GsonBuilder();
//        Gson gson = builder.create();
//        String gsonTablePresenter = gson.toJson(tablePresenter);
//        outState.putString(KEY_GSON_TABLE_PRESENTER, gsonTablePresenter);


        super.onSaveInstanceState(outState);
    }
    // получение ранее сохраненного состояния
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //Сохранить. Нужно разобраться с Json
//        tablePresenter.restoreInstanceState();
        chronometer.setBase(SystemClock.elapsedRealtime() + savedInstanceState.getLong(saveChronometer));
        chronometer.start();
        Log.d("Logssss", "onRestoreInstanceState");


//        GsonBuilder builder = new GsonBuilder();
//        Gson gson = builder.create();
//        tablePresenter = gson.fromJson(savedInstanceState.getString(KEY_GSON_TABLE_PRESENTER), TablePresenter.class);
//        llForTable.addView(tablePresenter.getTable());

        tablePresenter = (TablePresenter)savedInstanceState.getSerializable("tablePresenterputSerializable");
        tablePresenter.attachView(this);
        llForTable.addView(tablePresenter.getTable());


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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tablePresenter.detachView();
        Log.d("Logssss", "onDestroy");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Logssss", "onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Logssss", "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Logssss", "onStop");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
        Log.d("Logssss", "onRestart");
    }
}