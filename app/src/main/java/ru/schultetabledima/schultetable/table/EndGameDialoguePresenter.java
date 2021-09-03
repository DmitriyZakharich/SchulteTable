package ru.schultetabledima.schultetable.table;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;

import androidx.appcompat.app.AlertDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.schultetabledima.schultetable.settings.SettingsActivity;
import ru.schultetabledima.schultetable.statistic.StatisticsActivity;
import ru.schultetabledima.schultetable.statistic.database.DatabaseAdapter;

public class EndGameDialoguePresenter {

    private int columnsOfTable, rowsOfTable;
    private Context context;
    private EndGameDialogue endGameDialogue;
    private long stopTime;
    private AlertDialog.Builder builder;



    public EndGameDialoguePresenter(Context context, EndGameDialogue endGameDialogue) {
        this.context = context;
        this.endGameDialogue = endGameDialogue;
        readSharedPreferences();
        init();
        endGameDialogueShow();
    }

    private void readSharedPreferences() {
        SharedPreferences settings = context.getSharedPreferences(SettingsActivity.getAppPreferences(), MODE_PRIVATE);
        boolean isLetters = settings.getBoolean(SettingsActivity.getKeyNumbersOrLetters(), false);

        if (isLetters){
            columnsOfTable = settings.getInt(SettingsActivity.getKeyColumnsLetters(), 4) + 1;
            rowsOfTable = settings.getInt(SettingsActivity.getKeyRowsLetters(), 4) + 1;
        } else{
            columnsOfTable = settings.getInt(SettingsActivity.getKeyColumnsNumbers(), 4) + 1;
            rowsOfTable = settings.getInt(SettingsActivity.getKeyRowsNumbers(), 4) + 1;
        }
    }


    private void init(){
        EndGameDialogueCreator creator = new EndGameDialogueCreator(context, this);
        stopTime = ((TableActivity)context).getBaseChronometer()- SystemClock.elapsedRealtime();
        builder = creator.getAlertDialog();
    }

    private void endGameDialogueShow() {
        endGameDialogue.showDialogue(builder);
    }

    public void onClickPositiveButtonListener() {
        databaseInsert();

        Intent intent = ((TableActivity)context).getIntent();
        ((TableActivity)context).finish();
        context.startActivity(intent);
    }

    public void onClickNeutralButtonListener() {
        databaseInsert();
        Intent intent = new Intent(context, StatisticsActivity.class);
        context.startActivity(intent);
    }

    public void onCancelListener() {
        ((TableActivity) context).setBaseChronometer(SystemClock.elapsedRealtime() + stopTime);
        ((TableActivity) context).startChronometer();
    }

    public void onNegativeButtonListener() {
        ((TableActivity) context).setBaseChronometer(SystemClock.elapsedRealtime() + stopTime);
        ((TableActivity) context).startChronometer();
    }

    private void databaseInsert(){
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yy", Locale.getDefault());
        String dateText = dateFormat.format(currentDate);

        String tableSize = rowsOfTable + "x" + columnsOfTable;

        DatabaseAdapter databaseAdapter = new DatabaseAdapter((TableActivity) context, ((TableActivity) context).getTextChronometer(), tableSize, dateText);
        databaseAdapter.insert();
    }


}
