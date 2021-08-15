package ru.schultetabledima.schultetable.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.widget.Chronometer;
import androidx.appcompat.app.AlertDialog;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.database.Database;

public class EndGameDialogue implements Serializable{
    private AlertDialog.Builder builder;
    private Database database;
    private Activity activity;
    private Boolean booleanTouchCells;
    private long stopTime;
    private String tableSize;

    public EndGameDialogue(Activity activity, Boolean booleanTouchCells, String tableSize) {
        this.activity = activity;
        this.booleanTouchCells = booleanTouchCells;
        this.tableSize = tableSize;
        stopTime = ((TableActivity)activity).getBaseChronometer()- SystemClock.elapsedRealtime();
        init();
    }

     private void init(){
         Date currentDate = new Date();
         DateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yy", Locale.getDefault());
         String dateText = dateFormat.format(currentDate);

        builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.end_game)
                .setMessage("Ваше время " +  ((TableActivity)activity).getTextChronometer())
                .setPositiveButton("Новая игра", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        database = new Database(activity, ((TableActivity) activity).getTextChronometer(), tableSize, dateText);
                        database.insert();

                        Intent intent = activity.getIntent();
                        activity.finish();
                        activity.startActivity(intent);
                    }
                })
                .setNeutralButton("Статистика", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        database = new Database(activity, ((TableActivity) activity).getTextChronometer(), tableSize, dateText);
                        database.insert();

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
