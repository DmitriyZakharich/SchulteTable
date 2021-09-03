package ru.schultetabledima.schultetable.table;

import android.app.Activity;
import android.content.Context;
import android.os.SystemClock;

import androidx.appcompat.app.AlertDialog;

import ru.schultetabledima.schultetable.statistic.database.DatabaseAdapter;

public class EndGameDialogue {
    private AlertDialog.Builder builder;
    private DatabaseAdapter databaseAdapter;
    private Context context;
    private Boolean booleanTouchCells,isLetters, isTwoTables, isEnglish;
    private long stopTime;

    public EndGameDialogue(Context context, Boolean booleanTouchCells) {
        this.context = context;
        this.booleanTouchCells = booleanTouchCells;
        stopTime = ((TableActivity) context).getBaseChronometer()- SystemClock.elapsedRealtime();
        init();
    }


     private void init(){
         new EndGameDialoguePresenter(context, this);
    }

    public void showDialogue(AlertDialog.Builder builder){
        builder.create().show();
    }

}
