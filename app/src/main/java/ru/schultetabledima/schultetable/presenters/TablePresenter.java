package ru.schultetabledima.schultetable.presenters;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import java.io.Serializable;
import java.util.ArrayList;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.tablecreation.TableCreator;
import ru.schultetabledima.schultetable.ui.CustomizationActivity;
import ru.schultetabledima.schultetable.ui.EndGameDialogue;
import ru.schultetabledima.schultetable.ui.StatisticsActivity;
import ru.schultetabledima.schultetable.ui.TableActivity;

public class TablePresenter implements Serializable{
    private boolean isPressButtons;
    private int count = 0;
    private int columnsOfTable;
    private int rowsOfTable;
    private long saveTime;
    private Context context;
    private transient TableCreator tableCreator;
    private transient EndGameDialogue endGameDialogue;
    private transient LinearLayout table;
    private boolean isLetters;
    private boolean isTwoTables;
    private boolean isEnglish;
    private ArrayList<Character> listLetters1, listLetters2;
    private ArrayList<Integer> listNumbers1, listNumbers2;
    private int whichTable;
    private transient TableLayout firstTable;
    private transient TableLayout secondTable;
    private transient GradientDrawable drawableActiveTable, drawablePassiveTable;
    private transient ArrayMap<Integer, Integer> cellsIdFirstTable, cellsIdSecondTable;
    private int nextMoveFirstTable, nextMoveSecondTableCountdown;


    public TablePresenter(Context context) {
        this.context = context;
        init();
    }

    private void init(){
        readSharedPreferences();
        callTableCreator();

        if (isTwoTables) {
            SettingForTwoTables();
        }

        showTable();
        startChronometer();
        preparationForCheckMove();
    }




    private void readSharedPreferences() {
        SharedPreferences spCustomization = context.getSharedPreferences(CustomizationActivity.getAppPreferences(), MODE_PRIVATE);
        isPressButtons = spCustomization.getBoolean(CustomizationActivity.getKeyTouchCells(), true);
        columnsOfTable = spCustomization.getInt(CustomizationActivity.getKeyNumberColumns(), 4) + 1;
        rowsOfTable = spCustomization.getInt(CustomizationActivity.getKeyNumberRows(), 4) + 1;
        isLetters = spCustomization.getBoolean(CustomizationActivity.getKeyNumbersLetters(), false);
        isTwoTables = spCustomization.getBoolean(CustomizationActivity.getKeyTwoTables(), false);
        isEnglish = spCustomization.getBoolean(CustomizationActivity.getKeyRussianOrEnglish(), false);
    }


    private void SettingForTwoTables() {

        firstTable = (TableLayout) table.getChildAt(0);
        secondTable = (TableLayout)table.getChildAt(1);


        whichTable = firstTable.getId();


        drawableActiveTable = new GradientDrawable();
        drawableActiveTable.setColor(Color.GREEN);
        drawableActiveTable.setSize(3, 3);

        drawablePassiveTable = new GradientDrawable();
        drawablePassiveTable.setColor(Color.BLACK);
        drawablePassiveTable.setSize(3, 3);

    }



    public void moveAnotherActivity(int viewID){
        if (viewID == R.id.image_button_settings) {
            context.startActivity(new Intent(context, CustomizationActivity.class));

        } else if (viewID == R.id.image_button_statistics) {
            context.startActivity(new Intent(context, StatisticsActivity.class));
        }
    }

    public void callTableCreator() {
        tableCreator = new TableCreator(context, this);
        table = tableCreator.getTable();
    }


    private void preparationForCheckMove() {

        cellsIdFirstTable = new ArrayMap<Integer, Integer>();
        cellsIdFirstTable = tableCreator.getCellsIdFirstTable();

        if (isTwoTables) {
            cellsIdSecondTable = new ArrayMap<Integer, Integer>();
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
        if (!isPressButtons)
            endGameDialogue();

        if (isPressButtons){

                if (isTwoTables){
                    checkMoveInTwoTables(cellId);
                }
                else{
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

        if (whichTable == firstTable.getId()) {

                if (cellId == cellsIdFirstTable.get(nextMoveFirstTable)){
                    nextMoveFirstTable++;
                    count++;

                    whichTable = secondTable.getId();
                }



        } else if (whichTable == secondTable.getId()) {

                if (cellId == cellsIdSecondTable.get(nextMoveSecondTableCountdown)){
                    nextMoveSecondTableCountdown--;
                    count++;

                    whichTable = firstTable.getId();
                }

        }

        if (count == (cellsIdFirstTable.size() + cellsIdSecondTable.size())){
            endGameDialogue();
        }

    }


    void endGameDialogue(){
        ((TableActivity)context).stopChronometer();
        String tableSize = columnsOfTable + "x" + rowsOfTable;
        endGameDialogue = new EndGameDialogue((TableActivity)context,
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
            SettingForTwoTables();
        }

        showTable();

        ((TableActivity)context).setBaseChronometer(SystemClock.elapsedRealtime() + saveTime);
        ((TableActivity)context).startChronometer();

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
