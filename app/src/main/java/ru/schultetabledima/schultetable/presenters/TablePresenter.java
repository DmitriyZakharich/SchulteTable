package ru.schultetabledima.schultetable.presenters;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import androidx.core.content.ContextCompat;

import java.io.Serializable;
import java.util.ArrayList;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.tablecreation.TableCreator;
import ru.schultetabledima.schultetable.ui.EndGameDialogue;
import ru.schultetabledima.schultetable.ui.SettingsActivity;
import ru.schultetabledima.schultetable.ui.StatisticsActivity;
import ru.schultetabledima.schultetable.ui.TableActivity;
import ru.schultetabledima.schultetable.utils.Converter;

public class TablePresenter implements Serializable{
    private boolean isPressButtons;
    private int count = 0;
    private int columnsOfTable;
    private int rowsOfTable;
    private long saveTime;
    private Context context;
    private transient TableCreator tableCreator;
    private transient LinearLayout table;
    private boolean isLetters;
    private boolean isTwoTables;
    private boolean isEnglish;
    private ArrayList<Character> listLetters1, listLetters2;
    private ArrayList<Integer> listNumbers1, listNumbers2;
    private int activeTable;
    private transient TableLayout firstTable, secondTable;
    private transient ArrayMap<Integer, Integer> cellsIdFirstTable, cellsIdSecondTable;
    private int nextMoveFirstTable, nextMoveSecondTableCountdown;
    private static SharedPreferences sharedPreferencesMenu;
    private boolean isMenuShow;


    public TablePresenter(Context context) {
        this.context = context;
        init();
    }

    private void init(){
        readSharedPreferences();
        callTableCreator();

        if (isTwoTables) {
            settingForTwoTables();
        }

        showTable();
        startChronometer();
        settingForMenu();
        settingForCheckMove();
    }


    private void readSharedPreferences() {
        SharedPreferences settings = context.getSharedPreferences(SettingsActivity.getAppPreferences(), MODE_PRIVATE);
        isPressButtons = settings.getBoolean(SettingsActivity.getKeyTouchCells(), true);
        columnsOfTable = settings.getInt(SettingsActivity.getKeyColumnsNumbers(), 4) + 1;
        rowsOfTable = settings.getInt(SettingsActivity.getKeyRowsNumbers(), 4) + 1;
        isLetters = settings.getBoolean(SettingsActivity.getKeyNumbersOrLetters(), false);
        isTwoTables = settings.getBoolean(SettingsActivity.getKeyTwoTables(), false);
        isEnglish = settings.getBoolean(SettingsActivity.getKeyRussianOrEnglish(), false);
    }


    private void settingForTwoTables() {
        firstTable = (TableLayout) table.getChildAt(0);
        secondTable = (TableLayout)table.getChildAt(1);

        activeTable = firstTable.getId();
    }

    private void settingForMenu() {
        sharedPreferencesMenu = context.getSharedPreferences(TableActivity.getMenuPreferences(), MODE_PRIVATE);
        isMenuShow = sharedPreferencesMenu.getBoolean(TableActivity.getKeyMenuVisibility(), true);

        if(isMenuShow){
            ((TableActivity)context).showMenu(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Converter.getPx(context, 40)));

        }else{
            ((TableActivity)context).hideMenu(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Converter.getPx(context,20)));
        }
    }

    public void processingMenuButtons(int viewID){
        if (viewID == R.id.image_button_settings) {
            context.startActivity(new Intent(context, SettingsActivity.class));

        } else if (viewID == R.id.image_button_statistics) {
            context.startActivity(new Intent(context, StatisticsActivity.class));

        } else if (viewID == R.id.image_Button_Show_Hide_Menu) {
            SharedPreferences.Editor ed = sharedPreferencesMenu.edit();

            if (isMenuShow){
                ((TableActivity)context).hideMenu(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Converter.getPx(context, 20)));
                isMenuShow = false;

            }else{
                ((TableActivity)context).showMenu(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Converter.getPx(context,40)));
                isMenuShow = true;

            }
            ed.putBoolean(TableActivity.getKeyMenuVisibility(), isMenuShow);
            ed.apply();

        }
    }


    public void callTableCreator() {
        tableCreator = new TableCreator(context, this);
        table = tableCreator.getTable();
    }



    private void settingForCheckMove() {
        cellsIdFirstTable = new ArrayMap<>();
        cellsIdFirstTable = tableCreator.getCellsIdFirstTable();

        if (isTwoTables) {
            cellsIdSecondTable = new ArrayMap<>();
            cellsIdSecondTable = tableCreator.getCellsIdSecondTable();
        }

        if (isLetters) {
            nextMoveFirstTable = (isEnglish) ? (int) 'A': (int) 'А'; // eng / rus

            if(isTwoTables)
                nextMoveSecondTableCountdown = (isEnglish) ? (int)'A' +  cellsIdFirstTable.size() - 1 : (int)'А' +  cellsIdFirstTable.size() - 1;

        } else{
            nextMoveFirstTable = 1;

            if(isTwoTables)
                nextMoveSecondTableCountdown = cellsIdSecondTable.size();
        }
    }


    public void checkMove(int cellId){
        if (!isPressButtons){
            endGameDialogue();

        } else if (isPressButtons){

            if (isTwoTables){
                checkMoveInTwoTables(cellId);

            }else{
                checkMoveInOneTable(cellId);
            }
        }
    }


    private void checkMoveInOneTable(int cellId) {
        if (cellId == cellsIdFirstTable.get(nextMoveFirstTable)){
            nextMoveFirstTable++;
            count++;
        }

        if (count == cellsIdFirstTable.size()){
            endGameDialogue();
        }
    }


    private void checkMoveInTwoTables(int cellId) {
        if (activeTable == firstTable.getId()) {

                if (cellId == cellsIdFirstTable.get(nextMoveFirstTable)){
                    nextMoveFirstTable++;
                    count++;

                    activeTable = secondTable.getId();

                    firstTable.setBackgroundColor(ContextCompat.getColor(context, R.color.passiveTable));
                    secondTable.setBackgroundColor(ContextCompat.getColor(context, R.color.activeTable));

                }

        } else if (activeTable == secondTable.getId()) {

                if (cellId == cellsIdSecondTable.get(nextMoveSecondTableCountdown)){
                    nextMoveSecondTableCountdown--;
                    count++;

                    activeTable = firstTable.getId();

                    firstTable.setBackgroundColor(ContextCompat.getColor(context, R.color.activeTable));
                    secondTable.setBackgroundColor(ContextCompat.getColor(context, R.color.passiveTable));
                }
        }

        if (count == (cellsIdFirstTable.size() + cellsIdSecondTable.size())){
            endGameDialogue();
        }
    }


    void endGameDialogue(){
        ((TableActivity)context).stopChronometer();
        String tableSize = columnsOfTable + "x" + rowsOfTable;
        EndGameDialogue endGameDialogue = new EndGameDialogue((TableActivity) context,
                isPressButtons, tableSize);
        endGameDialogue.start();
    }


    public void saveInstanceState(){
        ((TableActivity)context).stopChronometer();
        saveTime = ((TableActivity)context).getBaseChronometer() - SystemClock.elapsedRealtime();
        ((TableActivity)context).removeTable();

        if (isLetters){
            listLetters1 = new ArrayList<>(tableCreator.getListLetters1());

            if (isTwoTables){
                listLetters2 = new ArrayList<>(tableCreator.getListLetters2());
            }
        }

        if (!isLetters){
            listNumbers1 = new ArrayList<>(tableCreator.getListNumbers1());

            if (isTwoTables){
                listNumbers2 = new ArrayList<>(tableCreator.getListNumbers2());
            }
        }
    }

    public void restoreInstanceState(){
        if (isLetters){
            tableCreator = new TableCreator(context, this, listLetters1, listLetters2);

        }else {
            tableCreator = new TableCreator(context,  listNumbers1, listNumbers2, this);
        }

        table = tableCreator.getTable();
        cellsIdFirstTable = tableCreator.getCellsIdFirstTable();

        if (isTwoTables){
            cellsIdSecondTable = tableCreator.getCellsIdSecondTable();
            settingForTwoTables();
        }

        showTable();
        ((TableActivity)context).setBaseChronometer(SystemClock.elapsedRealtime() + saveTime);
        startChronometer();
        settingForMenu();
    }


    private void showTable() {
        ((TableActivity)context).showTable(table);
    }

    private void startChronometer(){
        ((TableActivity)context).startChronometer();
    }

    public void attachView (Context context){
        this.context = context;
    }

    public void detachView(){
        context = null;
    }
}