package ru.schultetabledima.schultetable.ui;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.contracts.TableContract;
import ru.schultetabledima.schultetable.presenters.TablePresenter;

public class TableActivity extends AppCompatActivity implements TableContract.View{


    private static final String KEY_SERIALIZABLE_TABLE_PRESENTER = "tablePresenterPutSerializable";
    private ImageButton settings, statistics;
    private Chronometer chronometer;
    private RelativeLayout placeForTable;
    private Toolbar menu;
    private ImageButton selectShowHideMenu;
    private static SharedPreferences sharedPreferencesMenu;
    private static final String MENU_PREFERENCES = "PreferencesMenu";
    static final String KEY_MENU_VISIBILITY = "Saved Menu Visibility";
    private boolean isMenuShow;
    private TablePresenter tablePresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        selectShowHideMenu = (ImageButton)findViewById(R.id.image_Button_Show_Hide_Menu);
        settings = (ImageButton) findViewById(R.id.image_button_settings);
        statistics = (ImageButton) findViewById(R.id.image_button_statistics);
        placeForTable = (RelativeLayout) findViewById(R.id.placeForTable);
        chronometer = (Chronometer) findViewById(R.id.chronometer);

        menu = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(menu);

        settings.setOnClickListener(moveAnotherActivity);
        statistics.setOnClickListener(moveAnotherActivity);
        selectShowHideMenu.setOnClickListener(methodShowHideMenu);


        //Чтение настроек строки меню
        sharedPreferencesMenu = getSharedPreferences(MENU_PREFERENCES, MODE_PRIVATE);
        isMenuShow = sharedPreferencesMenu.getBoolean(KEY_MENU_VISIBILITY, true);

        if (savedInstanceState == null)
            tablePresenter = new TablePresenter(this);


        Log.d("nextMoveNumber", "placeForTable---------------           " + placeForTable.getId());


        //Анимация показать-скрыть меню навигации
        LayoutTransition layoutTransitionToolbar = menu.getLayoutTransition();
        layoutTransitionToolbar.enableTransitionType(LayoutTransition.CHANGING);

        if(isMenuShow){
            menu.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getPx(40)));

            chronometer.setVisibility(View.VISIBLE);
            settings.setVisibility(View.VISIBLE);
            statistics.setVisibility(View.VISIBLE);
            selectShowHideMenu.setImageResource(R.drawable.ic_arrow_down);
        }else{
            menu.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getPx(20)));

            chronometer.setVisibility(View.INVISIBLE);
            settings.setVisibility(View.INVISIBLE);
            statistics.setVisibility(View.INVISIBLE);
            selectShowHideMenu.setImageResource(R.drawable.ic_arrow_up);
        }
}

    @Override
    public void showTable(LinearLayout table) {
        placeForTable.addView(table);
        addAnim(table);
        Log.d("nextMoveNumber", "---------------           " + table.getId());

    }

    void addAnim(LinearLayout table){
        LayoutTransition layoutTransitionTable = new LayoutTransition();
        table.setLayoutTransition(layoutTransitionTable);
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
                settings.setVisibility(View.INVISIBLE);
                statistics.setVisibility(View.INVISIBLE);

                menu.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getPx(20)));
                selectShowHideMenu.setImageResource(R.drawable.ic_arrow_up);

            }else{
                isMenuShow = true;
                chronometer.setVisibility(View.VISIBLE);
                settings.setVisibility(View.VISIBLE);
                statistics.setVisibility(View.VISIBLE);

                menu.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getPx(40)));
                selectShowHideMenu.setImageResource(R.drawable.ic_arrow_down);
            }
            ed.putBoolean(KEY_MENU_VISIBILITY, isMenuShow);
            ed.apply();
        }
    };


    View.OnClickListener moveAnotherActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tablePresenter.moveAnotherActivity(v.getId());
        }
    };

    //Сохранение информации при поворатах Активити
    // сохранение состояния
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d("Трасировка", "Активити onSave");
        tablePresenter.saveInstanceState();
        tablePresenter.detachView();
        outState.putSerializable(KEY_SERIALIZABLE_TABLE_PRESENTER, tablePresenter);

        super.onSaveInstanceState(outState);
    }
    // получение ранее сохраненного состояния
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d("Трасировка", "Активити onRestore");
        tablePresenter = (TablePresenter)savedInstanceState.getSerializable(KEY_SERIALIZABLE_TABLE_PRESENTER);
        tablePresenter.attachView(this);
        tablePresenter.restoreInstanceState();
    }

    public void removeTable() {
        placeForTable.removeAllViews();
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
    protected void onRestart() {
        super.onRestart();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}