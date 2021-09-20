package ru.schultetabledima.schultetable.table;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.contracts.TableContract;


public class TableActivity extends MvpAppCompatActivity implements TableContract.View {

    @InjectPresenter
    TablePresenter tablePresenter;

//    private static final String KEY_SERIALIZABLE_TABLE_PRESENTER = "tablePresenterPutSerializable";
    private ImageButton settings;
    private Chronometer chronometer;
    private ConstraintLayout placeForTable;
    private ImageButton selectShowHideMenu;
    private TextView moveHint, textMoveHint;
    private ImageButton image_menu;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        selectShowHideMenu = (ImageButton) findViewById(R.id.image_Button_Show_Hide_Menu);
        settings = (ImageButton) findViewById(R.id.image_button_settings);
        image_menu = (ImageButton) findViewById(R.id.image_menu);
        placeForTable = (ConstraintLayout) findViewById(R.id.placeForTable);
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        moveHint = (TextView) findViewById(R.id.moveHint);
        textMoveHint = (TextView) findViewById(R.id.textMoveHint);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        settings.setOnClickListener(onClickMenuButtonsListener);
        selectShowHideMenu.setOnClickListener(onClickMenuButtonsListener);
        image_menu.setOnClickListener(onClickMenuButtonsListener);

//        if (savedInstanceState == null)
//            tablePresenter = new TablePresenter(this);

    }


    @Override
    public void showTable(LinearLayout table) {
        placeForTable.addView(table);
    }

    @Override
    public void addAnimationToolbar(LayoutTransition layoutTransition) {
        toolbar.setLayoutTransition(layoutTransition);
    }

    View.OnClickListener onClickMenuButtonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tablePresenter.onClickMenuButtonsListener(v.getId(), v);
        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        tablePresenter.preparingToRotateScreen();
//        tablePresenter.detachView();
//        outState.putSerializable(KEY_SERIALIZABLE_TABLE_PRESENTER, tablePresenter);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
//        tablePresenter = (TablePresenter) savedInstanceState.getSerializable(KEY_SERIALIZABLE_TABLE_PRESENTER);
//        tablePresenter.attachView(this);
//        tablePresenter.restoreInstanceState();
    }

    @Override
    public void showHideMenu(int visibility, int visibilityHint, int imageResource, LinearLayout.LayoutParams layoutParams) {
        chronometer.setVisibility(visibility);
        settings.setVisibility(visibility);
        image_menu.setVisibility(visibility);

        textMoveHint.setVisibility(visibilityHint);
        moveHint.setVisibility(visibilityHint);

        selectShowHideMenu.setImageResource(imageResource);
        toolbar.setLayoutParams(layoutParams);
    }


    public ImageButton getImageMenu() {
        return image_menu;
    }

    @Override
    public void setMoveHint(int nextMoveFirstTable) {
        moveHint.setText(String.valueOf(nextMoveFirstTable));
    }

    @Override
    public void setMoveHint(char nextMoveFirstTable) {
        moveHint.setText(String.valueOf(nextMoveFirstTable));
    }

    @Override
    public void removeTable() {
        placeForTable.removeAllViews();
    }

    @Override
    public void startChronometer() {
        chronometer.start();
    }

    @Override
    public void showDialogueFragment(EndGameDialogueFragment dialogueFragment) {
//        dialogueFragment.start();
        dialogueFragment.show(getSupportFragmentManager(), "custom");
    }

//    @Override
//    public void setAlertDialog(AlertDialog alertDialog) {
//        alertDialog.show();
//        alertDialog.
//    }

    @Override
    public void stopChronometer() {
        chronometer.stop();
    }

//    @Override
//    public String getTextChronometer() {
//        return (String) chronometer.getText();
//    }

    public long getBaseChronometer() {
        return chronometer.getBase();
    }

    @Override
    public void setBaseChronometer(long base) {
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