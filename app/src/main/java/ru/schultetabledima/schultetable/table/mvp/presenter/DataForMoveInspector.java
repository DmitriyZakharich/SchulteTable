package ru.schultetabledima.schultetable.table.mvp.presenter;

import java.util.List;

public class DataForMoveInspector {


    private List<Integer> cellsIdFirstTableForCheck;
    private List<Integer> cellsIdSecondTableForCheck;
    private int nextMoveFirstTable;
    private int nextMoveSecondTableCountdown;
    private int countdownSecondTable;



    public List<Integer> getCellsIdFirstTableForCheck() {
        return cellsIdFirstTableForCheck;
    }

    public void setCellsIdFirstTableForCheck(List<Integer> cellsIdFirstTableForCheck) {
        this.cellsIdFirstTableForCheck = cellsIdFirstTableForCheck;
    }

    public List<Integer> getCellsIdSecondTableForCheck() {
        return cellsIdSecondTableForCheck;
    }

    public void setCellsIdSecondTableForCheck(List<Integer> cellsIdSecondTableForCheck) {
        this.cellsIdSecondTableForCheck = cellsIdSecondTableForCheck;
    }

    public int getNextMoveFirstTable() {
        return nextMoveFirstTable;
    }

    public void setNextMoveFirstTable(int nextMoveFirstTable) {
        this.nextMoveFirstTable = nextMoveFirstTable;
    }

    public int getNextMoveSecondTableCountdown() {
        return nextMoveSecondTableCountdown;
    }

    public void setNextMoveSecondTableCountdown(int nextMoveSecondTableCountdown) {
        this.nextMoveSecondTableCountdown = nextMoveSecondTableCountdown;
    }

    public int getCountdownSecondTable() {
        return countdownSecondTable;
    }

    public void setCountdownSecondTable(int countdownSecondTable) {
        this.countdownSecondTable = countdownSecondTable;
    }
}
