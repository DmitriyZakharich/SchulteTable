package ru.schultetabledima.schultetable.table;

import android.animation.LayoutTransition;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import java.util.List;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.contracts.TableContract;
import ru.schultetabledima.schultetable.table.tablecreation.DataCell;
import ru.schultetabledima.schultetable.table.tablecreation.TableCreator;
import ru.schultetabledima.schultetable.utils.PreferencesReader;


public class TableActivity extends MvpAppCompatActivity implements TableContract.View, EndGameDialogueFragment.PassMeLinkOnObject {

    @InjectPresenter
    TablePresenter tablePresenter;

    private ImageButton buttonSettings;
    private Chronometer chronometer;
    private ConstraintLayout placeForTable;
    private ImageButton selectShowHideMenu;
    private TextView moveHint, textMoveHint;
    private ImageButton image_menu;
    private Toolbar toolbar;
    private AppCompatTextView[][] cells1, cells2;
    private PreferencesReader settings;
    private LinearLayout containerWithTables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        Log.d("TAGTAGTAG1244", "activity onCreate  tablePresenter " + tablePresenter.toString() );




        selectShowHideMenu = findViewById(R.id.image_Button_Show_Hide_Menu);
        buttonSettings = findViewById(R.id.image_button_settings);
        image_menu = findViewById(R.id.image_menu);
        placeForTable = findViewById(R.id.placeForTable);
        chronometer = findViewById(R.id.chronometer);
        moveHint = findViewById(R.id.moveHint);
        textMoveHint = findViewById(R.id.textMoveHint);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        buttonSettings.setOnClickListener(onClickMenuButtonsListener);
        selectShowHideMenu.setOnClickListener(onClickMenuButtonsListener);
        image_menu.setOnClickListener(onClickMenuButtonsListener);

        settings = new PreferencesReader(this);


        TableCreator tableCreator = new TableCreator(this, tablePresenter);
        containerWithTables = tableCreator.getContainerForTables();
        placeForTable.addView(containerWithTables);

        cells1 = tableCreator.getCellsFirstTable();

        if (settings.getIsTwoTables()) {
            cells2 = tableCreator.getCellsSecondTable();
        }
    }


    @Override
    public void setTableData(List<DataCell> dataCellsFirstTable, List<DataCell> dataCellsSecondTable) {

        fillingTable(cells1, dataCellsFirstTable);

        if (settings.getIsTwoTables()) {
            fillingTable(cells2, dataCellsSecondTable);
        }
    }

    @Override
    public void setTableColor(int table_id, int color) {
        containerWithTables.getChildAt(table_id).setBackgroundColor(getResources().getColor(color));
    }

    @Override
    public void showToastWrongTable(int wrongTable) {
        Toast toast = Toast.makeText(this, wrongTable, Toast.LENGTH_SHORT);
        toast.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 500);
    }


    private void fillingTable(AppCompatTextView[][] cells, List<DataCell> dataCells) {

        int count = 0;

        for (int i = 0; i < settings.getRowsOfTable(); i++) {
            for (int j = 0; j < settings.getColumnsOfTable(); j++) {
                cells[i][j].setId(dataCells.get(count).getId());


                if (settings.getIsLetters()) {
                    cells[i][j].setText(String.valueOf((char) (dataCells.get(count).getValue())));

                } else {
                    cells[i][j].setText(String.valueOf(dataCells.get(count).getValue()));
                }

                count++;
            }
        }
    }

    @Override
    public void setAnimationToolbar(LayoutTransition layoutTransition) {
        toolbar.setLayoutTransition(layoutTransition);
    }

    View.OnClickListener onClickMenuButtonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tablePresenter.onClickMenuButtonsListener(v.getId(), v);
        }
    };


    @Override
    public void showHideMenu(int visibility, int visibilityHint, int imageResource, LinearLayout.LayoutParams layoutParams) {
        chronometer.setVisibility(visibility);
        buttonSettings.setVisibility(visibility);
        image_menu.setVisibility(visibility);

        textMoveHint.setVisibility(visibilityHint);
        moveHint.setVisibility(visibilityHint);

        selectShowHideMenu.setImageResource(imageResource);
        toolbar.setLayoutParams(layoutParams);
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
    public void showDialogueFragment(boolean isShow) {
        Log.d("TAGTAGTAG1244", "showDialogueFragment Start: ");

        if (isShow) {
            EndGameDialogueFragment endGameDialogueFragment = EndGameDialogueFragment.newInstance();
            FragmentManager fragmentManager = getSupportFragmentManager();


//        endGameDialogueFragment.show(fragmentManager, "custom");

            fragmentManager.beginTransaction()
                    .add(endGameDialogueFragment, "custom")
                    .commit();
        }



//        endGameDialogueFragment.setPresenter(tablePresenter);
        Log.d("TAGTAGTAG1244", "showDialogueFragment stop: ");
    }



    @Override
    public void stopChronometer() {
        chronometer.stop();
    }


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

    @Override
    public TablePresenter getTablePresenter() {
        return tablePresenter;
    }
}