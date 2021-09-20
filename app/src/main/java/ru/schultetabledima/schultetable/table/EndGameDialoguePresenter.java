package ru.schultetabledima.schultetable.table;

import android.content.Intent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.schultetabledima.schultetable.MyApplication;
import ru.schultetabledima.schultetable.statistic.StatisticsActivity;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

public class EndGameDialoguePresenter {

    private EndGameDialogueFragment dialogFragment;
    private TablePresenter tablePresenter;
    private long baseChronometer;
    private PreferencesReader settings;


    public EndGameDialoguePresenter(EndGameDialogueFragment dialogFragment, TablePresenter tablePresenter, long baseChronometer) {
        this.dialogFragment = dialogFragment;
        this.tablePresenter = tablePresenter;
        this.baseChronometer = baseChronometer;
        init();
    }

    private void init(){
        settings = new PreferencesReader(MyApplication.getContext());

        EndGameDialogueCreator endGameDialogueCreator = new EndGameDialogueCreator(dialogFragment, this, baseChronometer);
        dialogFragment.setDialog(endGameDialogueCreator.getDialog());
    }





    public void onClickPositiveButtonListener() {
        databaseInsert();

        Intent intent = dialogFragment.getActivity().getIntent();
        dialogFragment.getActivity().finish();
        dialogFragment.getActivity().startActivity(intent);
    }

    public void onClickNeutralButtonListener() {
        databaseInsert();

        Intent intent = new Intent(dialogFragment.getActivity(), StatisticsActivity.class);
        dialogFragment.getActivity().startActivity(intent);
    }

    public void onNegativeButtonListener() {
        tablePresenter.onNegativeCancelDialogue();
    }

    public void onCancelDialogueListener() {
        tablePresenter.onNegativeCancelDialogue();
    }





    private void databaseInsert(){
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yy", Locale.getDefault());
        String dateText = dateFormat.format(currentDate);

        String tableSize = settings.getRowsOfTable() + "x" + settings.getColumnsOfTable();

//        DatabaseAdapter databaseAdapter = new DatabaseAdapter(context, ((TableActivity) context).getTextChronometer(), tableSize, dateText);
//        databaseAdapter.insert();
    }
}
