package ru.schultetabledima.schultetable.presenters;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.Toast;

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
    private int columnsOfTable, rowsOfTable;
    private long saveTime;
    private Context context;
    private transient TableCreator tableCreator;
    private transient LinearLayout table;
    private boolean isLetters, isTwoTables, isEnglish, isMenuShow, isMoveHint;
    private ArrayList<Character> listLetters1, listLetters2;
    private ArrayList<Integer> listNumbers1, listNumbers2;
    private int activeTable, saveNumberActiveTable;
    private transient TableLayout firstTable, secondTable;
    private transient ArrayMap<Integer, Integer> cellsIdFirstTable, cellsIdSecondTable;
    private int nextMoveFirstTable, nextMoveSecondTableCountdown;
    private static SharedPreferences sharedPreferencesMenu;


    public TablePresenter(Context context) {
        this.context = context;
        main();
    }

    private void main(){
        readSharedPreferences();
        callTableCreator();
        showTable();
        startChronometer();
        settingForCheckMove();
        settingForMenu();

    }


    private void readSharedPreferences() {
        SharedPreferences settings = context.getSharedPreferences(SettingsActivity.getAppPreferences(), MODE_PRIVATE);
        isPressButtons = settings.getBoolean(SettingsActivity.getKeyTouchCells(), true);
        columnsOfTable = settings.getInt(SettingsActivity.getKeyColumnsNumbers(), 4) + 1;
        rowsOfTable = settings.getInt(SettingsActivity.getKeyRowsNumbers(), 4) + 1;
        isLetters = settings.getBoolean(SettingsActivity.getKeyNumbersOrLetters(), false);
        isTwoTables = settings.getBoolean(SettingsActivity.getKeyTwoTables(), false);
        isEnglish = settings.getBoolean(SettingsActivity.getKeyRussianOrEnglish(), false);
        isMoveHint = settings.getBoolean(SettingsActivity.getKeyMoveHint(), true);
    }



    private void settingForMenu() {
        sharedPreferencesMenu = context.getSharedPreferences(TableActivity.getMenuPreferences(), MODE_PRIVATE);
        isMenuShow = sharedPreferencesMenu.getBoolean(TableActivity.getKeyMenuVisibility(), true);

        int visibility, imageResource;
        int visibilityHint = View.VISIBLE;
        LinearLayout.LayoutParams layoutParams;

        if(isMenuShow){
            visibility = View.VISIBLE;
            imageResource = R.drawable.ic_arrow_down;
            layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Converter.getPx(context, 40));


        }else{
            visibility = View.INVISIBLE;
            imageResource = R.drawable.ic_arrow_up;
            layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Converter.getPx(context,20));

        }

        if (!isMoveHint || !isPressButtons || !isMenuShow){
            visibilityHint = View.INVISIBLE;

        } else if (isMoveHint){
            visibilityHint = View.VISIBLE;
        }
        ((TableActivity)context).showHideMenu(visibility, visibilityHint, imageResource, layoutParams);
    }



    public void onClickMenuButtonsListener(int viewID){
        if (viewID == R.id.image_button_settings) {
            context.startActivity(new Intent(context, SettingsActivity.class));


        } else if (viewID == R.id.image_button_statistics) {
            context.startActivity(new Intent(context, StatisticsActivity.class));



        } else if (viewID == R.id.image_Button_Show_Hide_Menu) {
                SharedPreferences.Editor ed = sharedPreferencesMenu.edit();

                int visibility, visibilityHint, imageResource;
                LinearLayout.LayoutParams layoutParams;

                if (isMenuShow){
                    visibility = View.INVISIBLE;
                    imageResource = R.drawable.ic_arrow_up;
                    layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Converter.getPx(context, 20));
                    isMenuShow = false;

                }else{
                    visibility = View.VISIBLE;
                    imageResource = R.drawable.ic_arrow_down;
                    layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Converter.getPx(context,40));
                    isMenuShow = true;

                }

                if (isMoveHint && isMenuShow && isPressButtons){
                    visibilityHint = View.VISIBLE;

                } else {
                    visibilityHint = View.INVISIBLE;
                }

                ((TableActivity)context).showHideMenu(visibility, visibilityHint, imageResource, layoutParams);

                ed.putBoolean(TableActivity.getKeyMenuVisibility(), isMenuShow);
                ed.apply();
        }
    }


    private void callTableCreator() {
        tableCreator = new TableCreator(context, this);
        table = tableCreator.getContainerForTable();
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
            ((TableActivity)context).setMoveHint((char)nextMoveFirstTable);

            if(isTwoTables)
                nextMoveSecondTableCountdown = (isEnglish) ? (int)'A' +  cellsIdFirstTable.size() - 1 : (int)'А' +  cellsIdFirstTable.size() - 1;


        } else {
            nextMoveFirstTable = 1;
            ((TableActivity)context).setMoveHint(nextMoveFirstTable);

            if(isTwoTables)
                nextMoveSecondTableCountdown = cellsIdSecondTable.size();
        }


        firstTable = (TableLayout) table.getChildAt(0);
        if (isTwoTables)
            secondTable = (TableLayout)table.getChildAt(1);

        activeTable = firstTable.getId();
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
            if (isLetters)
                ((TableActivity)context).setMoveHint((char)nextMoveFirstTable);

            else
                ((TableActivity)context).setMoveHint(nextMoveFirstTable);
        }

        if (count == cellsIdFirstTable.size()){
            endGameDialogue();
        }
    }


    private void checkMoveInTwoTables(int cellId) {

        //Проверка активной таблицы
        //Если id из другой таблицы, то
        //Нужно выполнять до смены значения activeTable

         if (activeTable == firstTable.getId() && cellsIdSecondTable.containsValue(cellId)){
             showToastWrongTable();
         }
         if (activeTable == secondTable.getId() && cellsIdFirstTable.containsValue(cellId)){
             showToastWrongTable();
         }

        if (activeTable == firstTable.getId()) {

                if (cellId == cellsIdFirstTable.get(nextMoveFirstTable)){
                    nextMoveFirstTable++;
                    count++;

                    activeTable = secondTable.getId();
                    saveNumberActiveTable = 1;

                    firstTable.setBackgroundColor(ContextCompat.getColor(context, R.color.passiveTable));
                    secondTable.setBackgroundColor(ContextCompat.getColor(context, R.color.activeTable));

                    if (isLetters)
                        ((TableActivity)context).setMoveHint((char)nextMoveSecondTableCountdown);
                    else
                        ((TableActivity)context).setMoveHint(nextMoveSecondTableCountdown);
                }


        } else if (activeTable == secondTable.getId()) {

                if (cellId == cellsIdSecondTable.get(nextMoveSecondTableCountdown)){
                    nextMoveSecondTableCountdown--;
                    count++;

                    activeTable = firstTable.getId();
                    saveNumberActiveTable = 0;

                    firstTable.setBackgroundColor(ContextCompat.getColor(context, R.color.activeTable));
                    secondTable.setBackgroundColor(ContextCompat.getColor(context, R.color.passiveTable));

                    if (isLetters)
                        ((TableActivity)context).setMoveHint((char)nextMoveFirstTable);
                    else
                        ((TableActivity)context).setMoveHint(nextMoveFirstTable);

                }
        }


        if (count == (cellsIdFirstTable.size() + cellsIdSecondTable.size())){
            endGameDialogue();
            ((TableActivity)context).setMoveHint(' ');
        }
    }



    private void endGameDialogue(){
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
        refreshTableCreator();
        showTable();
        ((TableActivity)context).setBaseChronometer(SystemClock.elapsedRealtime() + saveTime);
        startChronometer();
        settingForMenu();
        restoreSettingForCheckMove();
    }



    private void refreshTableCreator(){
        if (isLetters){
            tableCreator = new TableCreator(context, this, listLetters1, listLetters2);

        }else {
            tableCreator = new TableCreator(context,  listNumbers1, listNumbers2, this);
        }

        table = tableCreator.getContainerForTable();
        cellsIdFirstTable = tableCreator.getCellsIdFirstTable();

        if (isTwoTables){
            cellsIdSecondTable = tableCreator.getCellsIdSecondTable();
            restoreSettingForTwoTables();
        }
    }

    private void restoreSettingForCheckMove() {
        cellsIdFirstTable = new ArrayMap<>();
        cellsIdFirstTable = tableCreator.getCellsIdFirstTable();

        if (isTwoTables) {
            cellsIdSecondTable = new ArrayMap<>();
            cellsIdSecondTable = tableCreator.getCellsIdSecondTable();
        }


        if (!isLetters) {
            if (activeTable == firstTable.getId()) {
                ((TableActivity) context).setMoveHint(nextMoveFirstTable);
            } else if (activeTable == secondTable.getId())
                ((TableActivity) context).setMoveHint(nextMoveSecondTableCountdown);


        } else if(isLetters){
            if (activeTable == firstTable.getId()) {
                ((TableActivity) context).setMoveHint((char) nextMoveFirstTable);
            } else if (activeTable == secondTable.getId())
                ((TableActivity) context).setMoveHint((char) nextMoveSecondTableCountdown);
        }
    }


    private void restoreSettingForTwoTables() {
        firstTable = (TableLayout) table.getChildAt(0);
        secondTable = (TableLayout) table.getChildAt(1);


        if (saveNumberActiveTable == 0){
            activeTable = firstTable.getId();
            firstTable.setBackgroundColor(ContextCompat.getColor(context, R.color.activeTable));
            secondTable.setBackgroundColor(ContextCompat.getColor(context, R.color.passiveTable));

        }else if (saveNumberActiveTable == 1){
            activeTable = secondTable.getId();
            firstTable.setBackgroundColor(ContextCompat.getColor(context, R.color.passiveTable));
            secondTable.setBackgroundColor(ContextCompat.getColor(context, R.color.activeTable));
        }
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

    private void showToastWrongTable() {
        Toast toast = Toast.makeText(context, R.string.wrongTable, Toast.LENGTH_SHORT);
        toast.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 500);
    }

}