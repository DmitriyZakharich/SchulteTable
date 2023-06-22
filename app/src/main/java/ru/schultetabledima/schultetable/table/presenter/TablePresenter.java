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
    private TableContract.Model.ValuesCreator cellValuesCreatorFirstTable, cellValuesCreatorSecondTable;
    private List<DataCell> dataCellsFirstTableForFilling, dataCellsSecondTableForFilling;
    private MoveInspector moveInspector;
    private DataForMoveInspector dataForMoveInspector;
    private MenuButtonsHandler menuButtonsHandler;
    private DataForMenuButtonsHandler dataForMenuButtonsHandler;
    private boolean newSession = true; //Обработка BackStack. newSession - обнуление предыдущего сеанса до
                                        //перехода на другой фрагмент


    public TablePresenter() {
        main();
    }

    private void main() {
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
        cellValuesCreatorFirstTable = new CellValuesCreator();
        dataCellsFirstTableForFilling = cellValuesCreatorFirstTable.getDataCells();
        dataForMoveInspector.setCellsIdFirstTableForCheck(cellValuesCreatorFirstTable.getListIdsForCheck());

        if (PreferencesReader.INSTANCE.isTwoTables()) {
            cellValuesCreatorSecondTable = new CellValuesCreator();
            dataCellsSecondTableForFilling = cellValuesCreatorSecondTable.getDataCells();
            dataForMoveInspector.setCellsIdSecondTableForCheck(cellValuesCreatorSecondTable.getListIdsForCheck());
        }
    }

    private void pushValuesToTable() {
        getViewState().setTableData(dataCellsFirstTableForFilling, dataCellsSecondTableForFilling);
    }

    private void settingForMenu() {
        MenuCustomizer menuCustomizer = new MenuCustomizer(this);
        dataForMenuButtonsHandler = menuCustomizer.getData();
    }

    public boolean onClickMenuButtonsListener(int viewID) {
        return menuButtonsHandler.checkClick(viewID);
    }

    private void settingForCheckMove() {
        nextMoveFirstTable = cellValuesCreatorFirstTable.getFirstValue();
        dataForMoveInspector.setNextMoveFirstTable(nextMoveFirstTable);

        if (PreferencesReader.INSTANCE.isTwoTables()) {
            int countdownSecondTable = dataCellsSecondTableForFilling.size() - 1;
            nextMoveSecondTableCountdown = cellValuesCreatorSecondTable.getFirstValue() + countdownSecondTable;

            dataForMoveInspector.setCountdownSecondTable(countdownSecondTable);
            dataForMoveInspector.setNextMoveSecondTableCountdown(nextMoveSecondTableCountdown);
        }

        if (PreferencesReader.INSTANCE.isLetters()) {
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
        moveInspector = new MoveInspector(this, dataForMoveInspector);
        menuButtonsHandler = new MenuButtonsHandler(this, dataForMenuButtonsHandler);
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