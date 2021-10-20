package ru.schultetabledima.schultetable.table.mvp.presenter;

import androidx.navigation.fragment.NavHostFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.schultetabledima.schultetable.App;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.database.AppDatabase;
import ru.schultetabledima.schultetable.database.Result;
import ru.schultetabledima.schultetable.database.ResultDao;
import ru.schultetabledima.schultetable.table.mvp.model.EndGameDialogueCreator;
import ru.schultetabledima.schultetable.table.mvp.model.TimeResultFromBaseChronometer;
import ru.schultetabledima.schultetable.table.mvp.view.EndGameDialogueFragment;
import ru.schultetabledima.schultetable.utils.PreferencesReader;


public class EndGameDialoguePresenter {

    private EndGameDialogueFragment dialogFragment;
    private TablePresenter tablePresenter;
    private long saveTime;
    private PreferencesReader settings;
    private String dateText, tableSize, valueType, timeResult;
    private int quantityTables;


    public EndGameDialoguePresenter(EndGameDialogueFragment dialogFragment, TablePresenter tablePresenter) {
        this.dialogFragment = dialogFragment;
        this.tablePresenter = tablePresenter;

        init();
    }

    private void init() {
        settings = new PreferencesReader();
        saveTime = tablePresenter.getSaveTime();

        EndGameDialogueCreator endGameDialogueCreator = new EndGameDialogueCreator(dialogFragment, this, saveTime);
        dialogFragment.setDialog(endGameDialogueCreator.getDialog());
    }


    public void onClickPositiveButtonListener() {
        databaseInsert();
        NavHostFragment.findNavController(dialogFragment).navigate(R.id.action_tableFragment2_to_tableFragment2);
    }

    public void onClickNeutralButtonListener() {
        databaseInsert();
        NavHostFragment.findNavController(dialogFragment).navigate(R.id.action_tableFragment2_to_statisticFragment);
    }

    public void onNegativeButtonListener() {
        tablePresenter.onNegativeOrCancelDialogue();
    }

    public void onCancelDialogueListener() {
        tablePresenter.onNegativeOrCancelDialogue();
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

        tableSize = settings.getRowsOfTable() + "x" + settings.getColumnsOfTable();

        quantityTables = settings.getIsTwoTables() ? 2 : 1;
    }


    private void getData() {
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yy", Locale.getDefault());
        dateText = dateFormat.format(currentDate);
    }

    private void getValueType() {
        if (settings.getIsLetters()) {
            valueType = settings.getIsEnglish() ?
                    App.getContext().getString(R.string.languageEnglish) : App.getContext().getString(R.string.languageRussian);
        } else
            valueType = App.getContext().getString(R.string.valueTypeNumbers);
    }

    private void getTimeResult() {
        timeResult = TimeResultFromBaseChronometer.getTime(saveTime);
    }
}
