package ru.schultetabledima.schultetable.screens.game.model;

import android.view.View;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import ru.schultetabledima.schultetable.contracts.TableContract;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

public class CellValuesCreator implements TableContract.Model.ValuesCreator {
    private List<Integer> listIdsForCheck;
    private List<DataCell> dataCells;
    private int firstValue;

    public CellValuesCreator() {
        main();
    }

    private void main() {
        calculationFirstValue();
        createValues();
        if (PreferencesReader.INSTANCE.isAnim()) {
            addAnimation();
        }
    }

    private void calculationFirstValue() {
        if (PreferencesReader.INSTANCE.isLetters()) {
            firstValue = (PreferencesReader.INSTANCE.isEnglish()) ? (int) 'A' : (int) 'А'; // eng/rus
        } else {
            firstValue = 1;
        }
    }

    private void createValues() {
        dataCells = new ArrayList<>();
        listIdsForCheck = new ArrayList<>();
        int nextValue = firstValue;

        for (int i = 0; i < PreferencesReader.INSTANCE.getColumnsOfTable() * PreferencesReader.INSTANCE.getRowsOfTable(); i++) {
            int id = View.generateViewId();
            dataCells.add(new DataCell(id, nextValue));
            listIdsForCheck.add(id);
            nextValue++;
        }
        Collections.shuffle(dataCells);
    }

    private void addAnimation() {
        Random random = new Random();
        Set<Integer> hsRandomForCellAnim = new HashSet<>();
        int amountCellAnim = dataCells.size();

        for (int i = 0; i < amountCellAnim; i++) {
            int cellNumberForAnimation = random.nextInt(dataCells.size());

            if (!hsRandomForCellAnim.add(cellNumberForAnimation)) {
                i--;

            } else {
                dataCells.get(cellNumberForAnimation).setTypeAnimation(new Random().nextInt(5));
            }
        }
    }

    @Override
    public List<DataCell> getDataCells() {
        return dataCells;
    }

    @Override
    public List<Integer> getListIdsForCheck() {
        return listIdsForCheck;
    }

    @Override
    public int getFirstValue() {
        return firstValue;
    }
}