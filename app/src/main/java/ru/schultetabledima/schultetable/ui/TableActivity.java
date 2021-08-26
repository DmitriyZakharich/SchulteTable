package ru.schultetabledima.schultetable.ui;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
    private static final String MENU_PREFERENCES = "PreferencesMenu";
    private static final String KEY_MENU_VISIBILITY = "Saved Menu Visibility";
    private TablePresenter tablePresenter;
    TextView moveHint, textMoveHint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        selectShowHideMenu = (ImageButton)findViewById(R.id.image_Button_Show_Hide_Menu);
        settings = (ImageButton) findViewById(R.id.image_button_settings);
        statistics = (ImageButton) findViewById(R.id.image_button_statistics);
        placeForTable = (RelativeLayout) findViewById(R.id.placeForTable);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        moveHint = (TextView) findViewById(R.id.moveHint);
        textMoveHint = (TextView) findViewById(R.id.textMoveHint);

        menu = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(menu);

        settings.setOnClickListener(onClickMenuButtonsListener);
        statistics.setOnClickListener(onClickMenuButtonsListener);
        selectShowHideMenu.setOnClickListener(onClickMenuButtonsListener);

        if (savedInstanceState == null)
            tablePresenter = new TablePresenter(this);


        //Анимация показать-скрыть меню навигации
        LayoutTransition layoutTransitionToolbar = menu.getLayoutTransition();
        layoutTransitionToolbar.enableTransitionType(LayoutTransition.CHANGING);
}

    @Override
    public void showTable(LinearLayout table) {
        placeForTable.addView(table);
        addAnim(table);
    }

    void addAnim(@NonNull LinearLayout table){
        LayoutTransition layoutTransitionTable = new LayoutTransition();
        table.setLayoutTransition(layoutTransitionTable);
        layoutTransitionTable.enableTransitionType(LayoutTransition.CHANGING);
    }


    View.OnClickListener onClickMenuButtonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tablePresenter.onClickMenuButtonsListener(v.getId());
        }
    };


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        tablePresenter.saveInstanceState();
        tablePresenter.detachView();
        outState.putSerializable(KEY_SERIALIZABLE_TABLE_PRESENTER, tablePresenter);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        tablePresenter = (TablePresenter)savedInstanceState.getSerializable(KEY_SERIALIZABLE_TABLE_PRESENTER);
        tablePresenter.attachView(this);
        tablePresenter.restoreInstanceState();
    }


    public void showHideMenu(int visibility,int visibilityHint, int imageResource, LinearLayout.LayoutParams layoutParams){
        chronometer.setVisibility(visibility);
        settings.setVisibility(visibility);
        statistics.setVisibility(visibility);

        textMoveHint.setVisibility(visibilityHint);
        moveHint.setVisibility(visibilityHint);

        menu.setLayoutParams(layoutParams);
        selectShowHideMenu.setImageResource(imageResource);
    }


    public void setMoveHint(int nextMoveFirstTable) {
        moveHint.setText(String.valueOf(nextMoveFirstTable));
    }
    public void setMoveHint(char nextMoveFirstTable) {
        moveHint.setText(String.valueOf(nextMoveFirstTable));
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
    public static String getKeyMenuVisibility() {
        return KEY_MENU_VISIBILITY;
    }

    public static String getMenuPreferences() {
        return MENU_PREFERENCES;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}