package ru.schultetabledima.schultetable.table;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.contracts.TableContract;

public class TableActivity extends AppCompatActivity implements TableContract.View{


    private static final String KEY_SERIALIZABLE_TABLE_PRESENTER = "tablePresenterPutSerializable";
    private ImageButton settings;
    private Chronometer chronometer;
    private ConstraintLayout placeForTable;
    private ImageButton selectShowHideMenu;
    private TablePresenter tablePresenter;
    private TextView moveHint, textMoveHint;
    public ImageButton menu;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        selectShowHideMenu = (ImageButton)findViewById(R.id.image_Button_Show_Hide_Menu);
        settings = (ImageButton) findViewById(R.id.image_button_settings);
        menu = (ImageButton) findViewById(R.id.image_menu);
        placeForTable = (ConstraintLayout) findViewById(R.id.placeForTable);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        moveHint = (TextView) findViewById(R.id.moveHint);
        textMoveHint = (TextView) findViewById(R.id.textMoveHint);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        settings.setOnClickListener(onClickMenuButtonsListener);
        selectShowHideMenu.setOnClickListener(onClickMenuButtonsListener);
        menu.setOnClickListener(onClickMenuButtonsListener);

        if (savedInstanceState == null)
            tablePresenter = new TablePresenter(this);


//        Анимация показать-скрыть меню навигации
        LayoutTransition layoutTransitionToolbar = toolbar.getLayoutTransition();
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
        tablePresenter.preparingToRotateScreen();
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
        menu.setVisibility(visibility);

        textMoveHint.setVisibility(visibilityHint);
        moveHint.setVisibility(visibilityHint);

        selectShowHideMenu.setImageResource(imageResource);
        toolbar.setLayoutParams(layoutParams);
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


    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}