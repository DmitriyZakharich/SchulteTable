package ru.schultetabledima.schultetable.presenters;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;
import android.widget.LinearLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.tablecreation.TableCreator;
import ru.schultetabledima.schultetable.ui.CustomizationActivity;
import ru.schultetabledima.schultetable.ui.EndGameDialogue;
import ru.schultetabledima.schultetable.ui.StatisticsActivity;
import ru.schultetabledima.schultetable.ui.TableActivity;
import ru.schultetabledima.schultetable.utils.SettingsReader;

public class TablePresenter implements Serializable{
    private boolean isPressButtons;
    private int nextMoveNumber = 1;
    private char nextMoveLetter = 'А';
    private int count = 1;
    private int columnsOfTable;
    private int rowsOfTable;
    private long saveTime;
    private Context context;
    private transient TableCreator tableCreator;
    private transient EndGameDialogue endGameDialogue;
    private transient LinearLayout table;
    private boolean isLetters;
    private boolean isTwoTables;
    ArrayList<Character> listLetters1, listLetters2;
    ArrayList<Integer> listNumbers1, listNumbers2;


    public TablePresenter(Context context) {
        this.context = context;
        readSharedPreferences();
        callTableCreator();
        showTable();
        startChronometer();
    }

    private void readSharedPreferences() {
        SharedPreferences spCustomization = context.getSharedPreferences(CustomizationActivity.getAppPreferences(), MODE_PRIVATE);
        isPressButtons = spCustomization.getBoolean(CustomizationActivity.getKeyTouchCells(), true);
        columnsOfTable = spCustomization.getInt(CustomizationActivity.getKeyNumberColumns(), 4) + 1;
        rowsOfTable = spCustomization.getInt(CustomizationActivity.getKeyNumberRows(), 4) + 1;
        isLetters = spCustomization.getBoolean(CustomizationActivity.getKeyNumbersLetters(), false);
        isTwoTables = spCustomization.getBoolean(CustomizationActivity.getKeyTwoTables(), false);
    }


    public void moveAnotherActivity(int viewID){
        if (viewID == R.id.image_button_settings) {
            context.startActivity(new Intent(context, CustomizationActivity.class));

        } else if (viewID == R.id.image_button_statistics) {
            context.startActivity(new Intent(context, StatisticsActivity.class));
        }
    }

    public void callTableCreator() {
        Log.d("Трассировка", "Первый вызов callTableCreator");
        tableCreator = new TableCreator(context, this);
        table = tableCreator.getTable();
    }

    private void showTable() {
        ((TableActivity)context).showTable(table);
    }

    private void startChronometer(){
        ((TableActivity)context).startChronometer();
    }


    public LinearLayout getTable(){
        return table;
    }

    public void attachView (Context context){
        this.context = context;
    }

    public void detachView(){
        context = null;
    }

    public void checkMove(String cellText){
        if (!isPressButtons)
            endGameDialogue();

        if (isPressButtons){

            if (isLetters) {
                if (cellText.equals(String.valueOf(nextMoveLetter)));{
                    if (count == rowsOfTable*columnsOfTable) {
                        endGameDialogue();
                    }
                }
                count++;
                nextMoveLetter++;
            }

            if (!isLetters) {
                if (nextMoveNumber == Integer.parseInt(cellText)) {

                    if (nextMoveNumber == ((rowsOfTable * columnsOfTable))) {
                        endGameDialogue();
                    }
                    nextMoveNumber++;
                }
            }
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
        showTable();

        ((TableActivity)context).setBaseChronometer(SystemClock.elapsedRealtime() + saveTime);
        ((TableActivity)context).startChronometer();



    }
}
