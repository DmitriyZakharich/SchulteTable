package ru.schultetabledima.schultetable.table;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.SystemClock;

import androidx.appcompat.app.AlertDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.settings.SettingsActivity;
import ru.schultetabledima.schultetable.statistic.database.DatabaseAdapter;
import ru.schultetabledima.schultetable.statistic.StatisticsActivity;

public class EndGameDialogue {
    private AlertDialog.Builder builder;
    private DatabaseAdapter databaseAdapter;
    private Activity activity;
    private Boolean booleanTouchCells,isLetters, isTwoTables, isEnglish;
    private long stopTime;
    private int columnsOfTable, rowsOfTable;

    public EndGameDialogue(Activity activity, Boolean booleanTouchCells) {
        this.activity = activity;
        this.booleanTouchCells = booleanTouchCells;
        stopTime = ((TableActivity)activity).getBaseChronometer()- SystemClock.elapsedRealtime();
        readSharedPreferences();
        main();
    }

    private void readSharedPreferences() {
        SharedPreferences settings = activity.getSharedPreferences(SettingsActivity.getAppPreferences(), MODE_PRIVATE);
        isLetters = settings.getBoolean(SettingsActivity.getKeyNumbersOrLetters(), false);
        isTwoTables = settings.getBoolean(SettingsActivity.getKeyTwoTables(), false);
        isEnglish = settings.getBoolean(SettingsActivity.getKeyRussianOrEnglish(), false);

        if (isLetters){
            columnsOfTable = settings.getInt(SettingsActivity.getKeyColumnsLetters(), 4) + 1;
            rowsOfTable = settings.getInt(SettingsActivity.getKeyRowsLetters(), 4) + 1;
        } else{
            columnsOfTable = settings.getInt(SettingsActivity.getKeyColumnsNumbers(), 4) + 1;
            rowsOfTable = settings.getInt(SettingsActivity.getKeyRowsNumbers(), 4) + 1;
        }
    }

     private void main(){
         String tableSize = columnsOfTable + "x" + rowsOfTable;

         Date currentDate = new Date();
         DateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yy", Locale.getDefault());
         String dateText = dateFormat.format(currentDate);

        builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.end_game)
                .setMessage(activity.getString(R.string.yourTime) +  ((TableActivity)activity).getTextChronometer())
                .setPositiveButton(R.string.newGame, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        databaseAdapter = new DatabaseAdapter(activity, ((TableActivity) activity).getTextChronometer(), tableSize, dateText);
                        databaseAdapter.insert();

                        Intent intent = activity.getIntent();
                        activity.finish();
                        activity.startActivity(intent);
                    }
                })
                .setNeutralButton(R.string.statistics, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseAdapter = new DatabaseAdapter(activity, ((TableActivity) activity).getTextChronometer(), tableSize, dateText);
                        databaseAdapter.insert();

                        Intent intent = new Intent(activity, StatisticsActivity.class);
                        activity.startActivity(intent);
                    }
                }).setPositiveButtonIcon(activity.getDrawable(R.drawable.ic_playbutton))
                    .setCancelable(false);


        if(!booleanTouchCells){
            builder.setCancelable(true);
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    ((TableActivity) activity).setBaseChronometer(SystemClock.elapsedRealtime() + stopTime);
                    ((TableActivity) activity).startChronometer();
                }
            });
            builder.setNegativeButtonIcon(activity.getDrawable(R.drawable.ic_resume));
            builder.setNegativeButton(R.string.continueCurrentGame, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    ((TableActivity) activity).setBaseChronometer(SystemClock.elapsedRealtime() + stopTime);
                    ((TableActivity) activity).startChronometer();
                }
            });
        }
    }

    public void start(){
        builder.create().show();
    }

}
