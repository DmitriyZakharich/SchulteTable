package ru.schultetabledima.schultetable.table.presenter;

import android.graphics.Color;
import android.widget.Toast;

import java.util.List;

import javax.inject.Inject;

import ru.schultetabledima.schultetable.App;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

public class MoveInspector {

    PreferencesReader settings;
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


    @Inject
    public MoveInspector(PreferencesReader settings) {
        this.settings = settings;
    }

    public void setPresenter(TablePresenter presenter) {
        this.presenter = presenter;
    }

    public void setData(DataForMoveInspector data) {
        cellsIdFirstTableForCheck = data.getCellsIdFirstTableForCheck();
        cellsIdSecondTableForCheck = data.getCellsIdSecondTableForCheck();
        nextMoveFirstTable = data.getNextMoveFirstTable();
        nextMoveSecondTableCountdown = data.getNextMoveSecondTableCountdown();
        countdownSecondTable = data.getCountdownSecondTable();
    }


    public void cellActionDown(int cellId) {

        if (!settings.getIsTouchCells())
            presenter.endGameDialogue();



        try {
            if (settings.getIsTouchCells()) {

                if (!settings.getIsTwoTables())
                    checkMoveInOneTable(cellId);

                if (settings.getIsTwoTables()) {

                    if (!isValidTable(cellId)) {
                        return;
                    }
                    checkMoveInTwoTables(cellId);
                }
            }
        } catch (IndexOutOfBoundsException e) {
            presenter.getViewState().showErrorDialogueFragment(true);
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

        if (!settings.getIsTouchCells())
            return;

        if (!settings.getIsTwoTables()) {
            applyCellSelectionInOneTable(cellId);
        } else
            applyCellSelectionInTwoTables(cellId);
    }

    private void applyCellSelectionInOneTable(int cellId) {
        if (isRightCell) {
            if (settings.getIsLetters())
                presenter.getViewState().setMoveHint((char) nextMoveFirstTable);

            else
                presenter.getViewState().setMoveHint(nextMoveFirstTable);
        }
        backgroundCellResources = R.drawable.border_cell_active_color;
        presenter.getViewState().setBackgroundResources(cellId, backgroundCellResources);
        isRightCell = false;

        if (countFirstTable == cellsIdFirstTableForCheck.size()) {
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

            if (settings.getIsLetters())
                presenter.getViewState().setMoveHint((char) nextMove);
            else
                presenter.getViewState().setMoveHint(nextMove);

            isRightCell = false;

        } else
            presenter.getViewState().setBackgroundResources(cellId, backgroundCellResources);

        if (countdownSecondTable < 0) {
            presenter.endGameDialogue();
            presenter.getViewState().setMoveHint('☑');
        }
    }
}
