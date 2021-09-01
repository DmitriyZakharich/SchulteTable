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
    private String tableSize;

    public EndGameDialogue(Activity activity, Boolean booleanTouchCells, String tableSize) {
        this.activity = activity;
        this.booleanTouchCells = booleanTouchCells;
        this.tableSize = tableSize;
        stopTime = ((TableActivity)activity).getBaseChronometer()- SystemClock.elapsedRealtime();
        readSharedPreferences();
        main();
    }

    private void readSharedPreferences() {
        SharedPreferences settings = activity.getSharedPreferences(SettingsActivity.getAppPreferences(), MODE_PRIVATE);
        isLetters = settings.getBoolean(SettingsActivity.getKeyNumbersOrLetters(), false);
        isTwoTables = settings.getBoolean(SettingsActivity.getKeyTwoTables(), false);
        isEnglish = settings.getBoolean(SettingsActivity.getKeyRussianOrEnglish(), false);
    }

     private void main(){
         Date currentDate = new Date();
         DateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yy", Locale.getDefault());
         String dateText = dateFormat.format(currentDate);

        builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.end_game)
                .setMessage("Ваше время " +  ((TableActivity)activity).getTextChronometer())
                .setPositiveButton("Новая игра", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        databaseAdapter = new DatabaseAdapter(activity, ((TableActivity) activity).getTextChronometer(), tableSize, dateText);
                        databaseAdapter.insert();

                        Intent intent = activity.getIntent();
                        activity.finish();
                        activity.startActivity(intent);
                    }
                })
                .setNeutralButton("Статистика", new DialogInterface.OnClickListener(){
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
            builder.setNegativeButton("Продолжить текущую игру", new DialogInterface.OnClickListener() {
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
