package ru.schultetabledima.schultetable.table.presenter;

import android.os.SystemClock;

import java.util.List;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.schultetabledima.schultetable.contracts.TableContract;
import ru.schultetabledima.schultetable.table.model.CellValuesCreator;
import ru.schultetabledima.schultetable.table.model.DataCell;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

@InjectViewState
public class TablePresenter extends MvpPresenter<TableContract.View> implements TableContract.Presenter {

    private long saveTime = 0;
    private int nextMoveFirstTable, nextMoveSecondTableCountdown;
    private boolean isDialogueShow = false, booleanStartChronometer = true;
    private transient PreferencesReader settings;
    private TableContract.Model.ValuesCreator cellValuesCreatorFirstTable, cellValuesCreatorSecondTable;
    private List<DataCell> dataCellsFirstTableForFilling, dataCellsSecondTableForFilling;
    private MoveInspector moveInspector;
    private MenuCustomizer menuCustomizer;
    private DataForMoveInspector dataForMoveInspector;
    private MenuButtonsHandler menuButtonsHandler;
    private DataForMenuButtonsHandler dataForMenuButtonsHandler;
    private boolean newSession = true; //Обработка BackStack. newSession - обнуление предыдущего сеанса до
                                        //перехода на другой фрагмент


    public TablePresenter(PreferencesReader settings, CellValuesCreator cellValuesCreatorFirstTable,
                          CellValuesCreator cellValuesCreatorSecondTable, MenuCustomizer menuCustomizer,
                          MoveInspector moveInspector, MenuButtonsHandler menuButtonsHandler) {
        this.settings = settings;
        this.cellValuesCreatorFirstTable = cellValuesCreatorFirstTable;
        this.cellValuesCreatorSecondTable = cellValuesCreatorSecondTable;
        this.moveInspector = moveInspector;
        this.menuCustomizer = menuCustomizer;
        this.menuButtonsHandler = menuButtonsHandler;
        main();
    }

    private void main() {
        settings = new PreferencesReader();

        getViewState().createTable();

        dataForMoveInspector = new DataForMoveInspector();

        callValuesCreator();
        pushValuesToTable();

        settingForMenu();
        settingForCheckMove();
        startChronometer();

        creatingHandlers();

        newSession = false;
    }


    private void cleaningData() {
        saveTime = 0;
        isDialogueShow = false;
        booleanStartChronometer = true;
    }


    private void callValuesCreator() {
        dataCellsFirstTableForFilling = cellValuesCreatorFirstTable.getDataCells();
        dataForMoveInspector.setCellsIdFirstTableForCheck(cellValuesCreatorFirstTable.getListIdsForCheck());

        if (settings.getIsTwoTables()) {
            dataCellsSecondTableForFilling = cellValuesCreatorSecondTable.getDataCells();
            dataForMoveInspector.setCellsIdSecondTableForCheck(cellValuesCreatorSecondTable.getListIdsForCheck());
        }
    }

    private void pushValuesToTable() {
        getViewState().setTableData(dataCellsFirstTableForFilling, dataCellsSecondTableForFilling);
    }


    private void settingForMenu() {
        menuCustomizer.setPresenter(this);
        menuCustomizer.start();
        dataForMenuButtonsHandler = menuCustomizer.getData();
    }

    public boolean onClickMenuButtonsListener(int viewID) {
        return menuButtonsHandler.checkClick(viewID);
    }


    private void settingForCheckMove() {
        nextMoveFirstTable = cellValuesCreatorFirstTable.getFirstValue();
        dataForMoveInspector.setNextMoveFirstTable(nextMoveFirstTable);

        if (settings.getIsTwoTables()) {
            int countdownSecondTable = dataCellsSecondTableForFilling.size() - 1;
            nextMoveSecondTableCountdown = cellValuesCreatorSecondTable.getFirstValue() + countdownSecondTable;

            dataForMoveInspector.setCountdownSecondTable(countdownSecondTable);
            dataForMoveInspector.setNextMoveSecondTableCountdown(nextMoveSecondTableCountdown);
        }

        if (settings.getIsLetters()) {
            getViewState().setMoveHint((char) nextMoveFirstTable);

        } else {
            getViewState().setMoveHint(nextMoveFirstTable);
        }
    }


    public void cellActionDown(int cellId, long baseChronometer) {
        saveTime = SystemClock.elapsedRealtime() - baseChronometer;
        moveInspector.cellActionDown(cellId);
    }

    public void cellActionUp(int cellId) {
        moveInspector.cellActionUp(cellId);
    }


    public void endGameDialogue() {
        booleanStartChronometer = false;
        getViewState().stopStartChronometer(booleanStartChronometer);
        isDialogueShow = true;

        getViewState().setBaseChronometer(saveTime, isDialogueShow);
        getViewState().showDialogueFragment(isDialogueShow);
    }

    public void cancelDialogue() {
        isDialogueShow = false;
        getViewState().showDialogueFragment(isDialogueShow);
    }


    private void startChronometer() {
        getViewState().setBaseChronometer(SystemClock.elapsedRealtime() - saveTime, isDialogueShow);
        booleanStartChronometer = true;
        getViewState().stopStartChronometer(booleanStartChronometer);
    }

    private void creatingHandlers() {
        moveInspector.setPresenter(this);
        moveInspector.setData(dataForMoveInspector);
        menuButtonsHandler.setPresenter(this);
        menuButtonsHandler.setData(dataForMenuButtonsHandler);
    }

    public void onNegativeOrCancelDialogue() {
        booleanStartChronometer = true;
        cancelDialogue();
        getViewState().setBaseChronometer(SystemClock.elapsedRealtime() - saveTime, isDialogueShow);
        getViewState().stopStartChronometer(booleanStartChronometer);
    }

    public long getSaveTime() {
        return saveTime;
    }


    /* Обработка BackStack.
       Фрагмент скрывается, поэтому Презентер нужно отчистить
       от старых данных и подготовиться к возвращению на фрагмент
       через BackPress.*/
    public void setFragmentInFocus(boolean isFragmentInFocus) {
        if (!isFragmentInFocus) {
            getViewState().clearingTheCommandQueue();
            cleaningData();
            newSession = true;

        } else if (isFragmentInFocus && newSession) {
            main();
        }
    }
}