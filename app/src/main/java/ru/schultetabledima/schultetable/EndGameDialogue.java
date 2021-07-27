package ru.schultetabledima.schultetable;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Chronometer;
import androidx.appcompat.app.AlertDialog;

public class EndGameDialogue {
    AlertDialog.Builder builder;
    DatabaseController databaseController;


    public EndGameDialogue(Activity activity, Chronometer chronometer, Boolean booleanTouchСells, long stopTime,
                           int columnsOfTable, int stringsOfTable) {
        builder = new AlertDialog.Builder(activity);
        builder.setTitle("Конец игры")
                .setMessage("Ваше время " + chronometer.getText())
                .setPositiveButton("Новая игра", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d("SaveResultsCheck","Новая игра");
                        databaseController = new DatabaseController(activity, chronometer.getText().toString(), columnsOfTable, stringsOfTable);
                        databaseController.insert();

                        Intent intent = activity.getIntent();
                        activity.finish();
                        activity.startActivity(intent);
                    }
                })

                .setNeutralButton("Статистика", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseController = new DatabaseController(activity, chronometer.getText().toString(), columnsOfTable, stringsOfTable);
                        databaseController.insert();
                        Log.d("SaveResultsCheck","Статистика");

                        Intent intent = new Intent(activity, ActivityStatistics.class);
                        activity.startActivity(intent);
                    }
                }).setPositiveButtonIcon(activity.getDrawable(R.drawable.ic_playbutton))
                    .setCancelable(false);


        if(!booleanTouchСells){
            builder.setCancelable(true);
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    chronometer.setBase(SystemClock.elapsedRealtime() + stopTime);
                    chronometer.start();
                }
            });
            builder.setNegativeButtonIcon(activity.getDrawable(R.drawable.ic_resume));
            builder.setNegativeButton("Продолжить текущую игру", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    chronometer.setBase(SystemClock.elapsedRealtime() + stopTime);
                    chronometer.start();
                }
            });
        }
    }

    void start(){
        builder.create().show();
    }

}
