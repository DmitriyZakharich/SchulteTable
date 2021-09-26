package ru.schultetabledima.schultetable.table;

import android.content.Intent;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.schultetabledima.schultetable.App;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.statistic.StatisticsActivity;
import ru.schultetabledima.schultetable.statistic.database.AppDatabase;
import ru.schultetabledima.schultetable.statistic.database.Result;
import ru.schultetabledima.schultetable.statistic.database.ResultDao;
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

    private void init() {
        settings = new PreferencesReader(App.getContext());

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
        tablePresenter.onNegativeOrCancelDialogue();
    }

    public void onCancelDialogueListener() {
        tablePresenter.onNegativeOrCancelDialogue();
    }


    private void databaseInsert() {


        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yy", Locale.getDefault());
        String dateText = dateFormat.format(currentDate);

        String tableSize = settings.getRowsOfTable() + "x" + settings.getColumnsOfTable();


        int quantityTables = settings.getIsTwoTables() ? 2 : 1;


        String valueType;
        if (settings.getIsLetters()) {
            valueType = settings.getIsEnglish() ?
                    App.getContext().getString(R.string.languageEnglish) : App.getContext().getString(R.string.languageRussian);

        }else
            valueType = App.getContext().getString(R.string.valueTypeNumbers);


//        valueType = settings.getIsLetters() ?
//                App.getContext().getString(R.string.valueTypeLetters) : App.getContext().getString(R.string.valueTypeNumbers);


//        String language;
//        if (settings.getIsLetters()) {
//            language = settings.getIsEnglish() ?
//                    App.getContext().getString(R.string.languageEnglish) : App.getContext().getString(R.string.languageRussian);
//        } else {
//            language = null;
//        }


        AppDatabase db = App.getInstance().getDatabase();
        ResultDao resultDao = db.resultDao();
        Result result = new Result(dateText, tableSize, "10:00", quantityTables, valueType);
        resultDao.insert(result);

        Log.d("databaseInsertX", "result: " + result.toString());


//        DatabaseAdapter databaseAdapter = new DatabaseAdapter(context, ((TableActivity) context).getTextChronometer(), tableSize, dateText);
//        databaseAdapter.insert();
    }
}
