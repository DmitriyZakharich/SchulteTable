package ru.schultetabledima.schultetable.presenters;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;

import androidx.appcompat.widget.AppCompatTextView;

import java.io.Serializable;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.TableCreator;
import ru.schultetabledima.schultetable.ui.CustomizationActivity;
import ru.schultetabledima.schultetable.ui.EndGameDialogue;
import ru.schultetabledima.schultetable.ui.StatisticsActivity;
import ru.schultetabledima.schultetable.ui.TableActivity;

public class TablePresenter implements Serializable{
    private transient Context context;
    private Boolean booleanTouchCells;
    private int nextMove = 1;
    private int columnsOfTable;
    private int rowsOfTable;
    private long saveTime;
    private transient TableCreator tableCreator;
    private transient EndGameDialogue endGameDialogue;


    public TablePresenter(Context context) {
        this.context = context;
        readSharedPreferences();
        callTableCreator();
        Log.d("ContextContext", "TablePresenter Context " + context);
    }

    private void readSharedPreferences() {
        SharedPreferences spCustomization = context.getSharedPreferences(CustomizationActivity.getAppPreferences(), MODE_PRIVATE);
        booleanTouchCells = spCustomization.getBoolean(CustomizationActivity.getPreferencesKeyTouchCells(), true);
        columnsOfTable = spCustomization.getInt(CustomizationActivity.getPreferencesKeyNumberColumns(), 4) + 1;
        rowsOfTable = spCustomization.getInt(CustomizationActivity.getPreferencesKeyNumberRows(), 4) + 1;
    }


    public void moveAnotherActivity(View v){
        switch (v.getId()){
            case R.id.image_button_settings:
                context.startActivity(new Intent(context, CustomizationActivity.class));
                break;
            case R.id.image_button_statistics:
                context.startActivity(new Intent(context, StatisticsActivity.class));
                break;
        }
    }

    public void callTableCreator() {
        tableCreator = new TableCreator(context.getApplicationContext(), this);
        ((TableActivity)context).showTable(tableCreator.getTable());
        ((TableActivity)context).startChronometer();

    }

    public TableLayout getTable(){
        return tableCreator.getTable();
    }

    public void attachView (Context context){
        this.context = context;
    }

    public void detachView(){
        context = null;
        endGameDialogue = null;
        tableCreator.detachView();
    }

    public void checkMove(View v){
        if (booleanTouchCells){

            if(nextMove == Integer.parseInt ("" + ((AppCompatTextView)v).getText())){

                if (nextMove == ((rowsOfTable*columnsOfTable))){
                    endGameDialogue();
                }
                nextMove++;
            }
        }else {
            endGameDialogue();
        }
    }

    void endGameDialogue(){
        ((TableActivity)context).stopChronometer();
        String tableSize = "" + columnsOfTable + "x" + rowsOfTable;
        endGameDialogue = new EndGameDialogue((TableActivity)context,
                booleanTouchCells, tableSize);
        endGameDialogue.start();
    }

    public void saveInstanceState(){
        ((TableActivity)context).stopChronometer();
        saveTime = ((TableActivity)context).getBaseChronometer() - SystemClock.elapsedRealtime();
    }

    public void restoreInstanceState(){
        ((TableActivity)context).setBaseChronometer(SystemClock.elapsedRealtime() + saveTime);
        ((TableActivity)context).startChronometer();
    }
}
