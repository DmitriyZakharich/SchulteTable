package ru.schultetabledima.schultetable.table;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import androidx.appcompat.app.AlertDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.schultetabledima.schultetable.statistic.StatisticsActivity;
import ru.schultetabledima.schultetable.statistic.database.DatabaseAdapter;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

public class EndGameDialoguePresenter {

    private Context context;
    private EndGameDialogue endGameDialogue;
    private TablePresenter tablePresenter;
    private AlertDialog alertDialog;
    private PreferencesReader settings;


    public EndGameDialoguePresenter(Context context, EndGameDialogue endGameDialogue, TablePresenter tablePresenter) {
        this.context = context;
        this.endGameDialogue = endGameDialogue;
        this.tablePresenter = tablePresenter;
        init();
        endGameDialogueShow();
    }

    private void init(){
        settings = new PreferencesReader(context);

        EndGameDialogueCreator dialogueCreator = new EndGameDialogueCreator(context, this);
        alertDialog = dialogueCreator.getAlertDialog();
    }

    private void endGameDialogueShow() {
        endGameDialogue.showDialogue(alertDialog);
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

    public void onNegativeButtonListener() {
        ((TableActivity) context).setBaseChronometer(SystemClock.elapsedRealtime() + tablePresenter.getSaveTime());
        ((TableActivity) context).startChronometer();
        tablePresenter.cancelDialogue();
    }

    public void onCancelDialogueListener() {
        ((TableActivity) context).setBaseChronometer(SystemClock.elapsedRealtime() + tablePresenter.getSaveTime());
        ((TableActivity) context).startChronometer();
        tablePresenter.cancelDialogue();
    }

    private void databaseInsert(){
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yy", Locale.getDefault());
        String dateText = dateFormat.format(currentDate);

        String tableSize = settings.getRowsOfTable() + "x" + settings.getColumnsOfTable();

        DatabaseAdapter databaseAdapter = new DatabaseAdapter(context, ((TableActivity) context).getTextChronometer(), tableSize, dateText);
        databaseAdapter.insert();
    }
}
