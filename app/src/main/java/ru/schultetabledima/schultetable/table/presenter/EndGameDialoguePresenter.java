package ru.schultetabledima.schultetable.table.presenter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.schultetabledima.schultetable.App;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.database.AppDatabase;
import ru.schultetabledima.schultetable.database.Result;
import ru.schultetabledima.schultetable.database.ResultDao;
import ru.schultetabledima.schultetable.table.model.EndGameDialogueCreator;
import ru.schultetabledima.schultetable.table.model.TimeResultFromBaseChronometer;
import ru.schultetabledima.schultetable.table.view.EndGameDialogueFragment;
import ru.schultetabledima.schultetable.utils.PreferencesReaderKotlin;
import ru.schultetabledima.schultetable.utils.ScreenAnimationKt;


public class EndGameDialoguePresenter {

    private EndGameDialogueFragment dialogFragment;
    private TablePresenter tablePresenter;
    private long saveTime;
    private String dateText, tableSize, valueType, timeResult;
    private int quantityTables;

    public EndGameDialoguePresenter(EndGameDialogueFragment dialogFragment, TablePresenter tablePresenter) {
        this.dialogFragment = dialogFragment;
        this.tablePresenter = tablePresenter;
        init();
    }

    private void init() {
        saveTime = tablePresenter.getSaveTime();

        EndGameDialogueCreator endGameDialogueCreator = new EndGameDialogueCreator(dialogFragment, this, saveTime);
        dialogFragment.setDialog(endGameDialogueCreator.getDialog());
    }

    //New game button
    public void onClickPositiveButtonListener() {
        databaseInsert();
        tablePresenter.cancelDialogue();
        tablePresenter.getViewState().moveFragment(R.id.action_tableFragment_to_tableFragment, null);
        dialogFragment.dismiss();
    }

    //Statistic button
    public void onClickNeutralButtonListener() {
        databaseInsert();
        tablePresenter.cancelDialogue();
        tablePresenter.setFragmentInFocus(false);
        tablePresenter.getViewState().moveFragment(R.id.action_tableFragment_to_statisticFragment, ScreenAnimationKt.enterFromLeftExitToRight());
        dialogFragment.dismiss();
    }

    //Continue current game button
    public void onNegativeButtonListener() {
        tablePresenter.onNegativeOrCancelDialogue();
        dialogFragment.dismiss();
    }

    //Cancel
    public void onCancelDialogueListener() {
        tablePresenter.onNegativeOrCancelDialogue();
        dialogFragment.dismiss();
    }


    private void databaseInsert() {
        dataPreparation();

        AppDatabase db = App.getInstance().getDatabase();
        ResultDao resultDao = db.resultDao();
        Result result = new Result(dateText, tableSize, timeResult, quantityTables, valueType);

        new Thread(() -> {
            resultDao.insert(result);
        }).start();
    }


    private void dataPreparation() {
        getData();
        getValueType();
        getTimeResult();

        tableSize = PreferencesReaderKotlin.INSTANCE.getRowsOfTable() + "x" + PreferencesReaderKotlin.INSTANCE.getColumnsOfTable();

        quantityTables = PreferencesReaderKotlin.INSTANCE.isTwoTables() ? 2 : 1;
    }


    private void getData() {
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yy", Locale.getDefault());
        dateText = dateFormat.format(currentDate);
    }

    private void getValueType() {
        if (PreferencesReaderKotlin.INSTANCE.isLetters()) {
            valueType = PreferencesReaderKotlin.INSTANCE.isEnglish() ?
                    App.getContext().getString(R.string.languageEnglish) : App.getContext().getString(R.string.languageRussian);
        } else
            valueType = App.getContext().getString(R.string.valueTypeNumbers);
    }

    private void getTimeResult() {
        timeResult = TimeResultFromBaseChronometer.getTime(saveTime);
    }
}
