package ru.schultetabledima.schultetable.table.presenter;

import android.graphics.Color;
import android.widget.Toast;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.List;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.utils.PreferencesReaderKotlin;

public class MoveInspector {

    private int cellColor, backgroundCellResources;
    private TablePresenter presenter;
    private List<Integer> cellsIdFirstTableForCheck;
    private List<Integer> cellsIdSecondTableForCheck;
    private int nextMoveFirstTable, nextMoveSecondTableCountdown;

    private final int FIRST_TABLE_ID = 0, SECOND_TABLE_ID = 2;
    private int activeTable = FIRST_TABLE_ID;

    private int countFirstTable = 0, countdownSecondTable;
    private boolean isRightCell = false;
    private int colorFirstTable, colorSecondTable;
    private int nextMove;
    private boolean isGameActive = true;


    public MoveInspector(TablePresenter presenter, DataForMoveInspector data) {
        this.presenter = presenter;

        cellsIdFirstTableForCheck = data.getCellsIdFirstTableForCheck();
        cellsIdSecondTableForCheck = data.getCellsIdSecondTableForCheck();
        nextMoveFirstTable = data.getNextMoveFirstTable();
        nextMoveSecondTableCountdown = data.getNextMoveSecondTableCountdown();
        countdownSecondTable = data.getCountdownSecondTable();

        init();
    }

    private void init() {
//        settings = new PreferencesReader();
    }

    public void cellActionDown(int cellId) {

        if (!isGameActive)
            return;

        if (!PreferencesReaderKotlin.INSTANCE.isTouchCells())
            presenter.endGameDialogue();

        try {
            if (PreferencesReaderKotlin.INSTANCE.isTouchCells()) {

                if (!PreferencesReaderKotlin.INSTANCE.isTwoTables())
                    checkMoveInOneTable(cellId);

                if (PreferencesReaderKotlin.INSTANCE.isTwoTables()) {

                    if (!isValidTable(cellId)) {
                        return;
                    }
                    checkMoveInTwoTables(cellId);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            //Ошибка возникает, когда игра уже пройдена,
            //поэтому можно выводить результат,
            //а ошибку игнорировать

            FirebaseCrashlytics.getInstance().recordException(e);
            FirebaseCrashlytics.getInstance().sendUnsentReports();
        }
    }


    private void checkMoveInOneTable(int cellId) throws IndexOutOfBoundsException {
        cellColor = Color.RED;
        if (cellId == cellsIdFirstTableForCheck.get(countFirstTable)) {
            nextMoveFirstTable++;
            countFirstTable++;
            cellColor = Color.GREEN;
            isRightCell = true;
        }
        presenter.getViewState().setCellColor(cellId, cellColor);
    }

    public void cellActionUp(int cellId) {

        if (!isGameActive)
            return;

        if (!PreferencesReaderKotlin.INSTANCE.isTouchCells())
            return;

        if (!PreferencesReaderKotlin.INSTANCE.isTwoTables()) {
            applyCellSelectionInOneTable(cellId);
        } else
            applyCellSelectionInTwoTables(cellId);
    }

    private void applyCellSelectionInOneTable(int cellId) {
        if (isRightCell) {
            if (PreferencesReaderKotlin.INSTANCE.isLetters())
                presenter.getViewState().setMoveHint((char) nextMoveFirstTable);

            else
                presenter.getViewState().setMoveHint(nextMoveFirstTable);
        }
        backgroundCellResources = R.drawable.border_cell_active_color;
        presenter.getViewState().setBackgroundResources(cellId, backgroundCellResources);
        isRightCell = false;

        if (countFirstTable == cellsIdFirstTableForCheck.size()) {
            isGameActive = false;
            presenter.getViewState().setMoveHint('☑');
            presenter.endGameDialogue();
        }
    }


    private boolean isValidTable(int cellId) {
        cellColor = Color.RED;

        if (activeTable == FIRST_TABLE_ID && cellsIdSecondTableForCheck.contains(cellId)) {
            presenter.getViewState().setCellColor(cellId, cellColor);
            presenter.getViewState().showToast(R.string.wrongTable, Toast.LENGTH_SHORT);
            backgroundCellResources = R.drawable.border_cell_passive_color;
            return false;
        }

        if (activeTable == SECOND_TABLE_ID && cellsIdFirstTableForCheck.contains(cellId)) {
            presenter.getViewState().setCellColor(cellId, cellColor);
            presenter.getViewState().showToast(R.string.wrongTable, Toast.LENGTH_SHORT);
            backgroundCellResources = R.drawable.border_cell_passive_color;
            return false;
        }

        backgroundCellResources = R.drawable.border_cell_active_color;
        return true;
    }

    private void checkMoveInTwoTables(int cellId) throws IndexOutOfBoundsException {

        cellColor = Color.RED;

        if (activeTable == FIRST_TABLE_ID) {

            if (cellId == cellsIdFirstTableForCheck.get(countFirstTable)) {
                countFirstTable++;
                nextMoveFirstTable++;
                activeTable = SECOND_TABLE_ID;

                cellColor = Color.GREEN;

                isRightCell = true;

                colorFirstTable = R.drawable.border_cell_passive_color;
                colorSecondTable = R.drawable.border_cell_active_color;

                nextMove = nextMoveSecondTableCountdown;
            }

        } else if (activeTable == SECOND_TABLE_ID) {

            if (cellId == cellsIdSecondTableForCheck.get(countdownSecondTable)) {
                countdownSecondTable--;
                nextMoveSecondTableCountdown--;

                activeTable = FIRST_TABLE_ID;

                cellColor = Color.GREEN;

                isRightCell = true;


                colorFirstTable = R.drawable.border_cell_active_color;
                colorSecondTable = R.drawable.border_cell_passive_color;

                nextMove = nextMoveFirstTable;
            }
        }
        presenter.getViewState().setCellColor(cellId, cellColor);
    }

    private void applyCellSelectionInTwoTables(int cellId) {

        if (isRightCell) {
            presenter.getViewState().setTableColor(colorFirstTable, colorSecondTable);

            if (PreferencesReaderKotlin.INSTANCE.isLetters())
                presenter.getViewState().setMoveHint((char) nextMove);
            else
                presenter.getViewState().setMoveHint(nextMove);

            isRightCell = false;

        } else
            presenter.getViewState().setBackgroundResources(cellId, backgroundCellResources);

        if (countdownSecondTable < 0) {
            isGameActive = false;
            presenter.endGameDialogue();
            presenter.getViewState().setMoveHint('☑');
        }
    }
}
