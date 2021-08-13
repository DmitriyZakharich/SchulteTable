package ru.schultetabledima.schultetable.presenters;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class TablePresenter {
    Context context;
    Boolean booleanTouchCells;
    int nextMove = 1;
    private int columnsOfTable;
    private int rowsOfTable;
    TableLayout tlTable;


    public TablePresenter(Context context) {
        this.context = context;
        readSharedPreferences();
        callTableCreator();
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
        TableCreator tableCreator = new TableCreator(context, this);
        tlTable = tableCreator.getTable();
        ((TableActivity)context).showTable(tlTable);
    }

    public TableLayout getTable(){
        return tlTable;
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
        EndGameDialogue endGameDialogue = new EndGameDialogue((TableActivity)context,
                booleanTouchCells, columnsOfTable, rowsOfTable);
        endGameDialogue.start();
    }
}
