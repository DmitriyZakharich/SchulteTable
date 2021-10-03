package ru.schultetabledima.schultetable.table.tablecreation;

import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import ru.schultetabledima.schultetable.utils.PreferencesReader;


public class ValuesAndIdsCreator {
    private PreferencesReader settings;
    private List<Integer> listIdsForCheck;
    private List<DataCell> dataCells;
    private int firstValue;

    public ValuesAndIdsCreator() {
        main();
    }

    private void main() {
        settings = new PreferencesReader();

        calculationFirstValue();
        createValues();

        if (settings.getIsAnim()) {
            addAnimation();
        }
    }

    private void calculationFirstValue() {
        if (settings.getIsLetters()) {
            firstValue = (settings.getIsEnglish()) ? (int) 'A' : (int) 'А'; // eng/rus

        } else {
            firstValue = 1;
        }
    }

    private void createValues() {
        dataCells = new ArrayList<>();
        listIdsForCheck = new ArrayList<>();

        int nextValue = firstValue;

        for (int i = 0; i < settings.getColumnsOfTable() * settings.getRowsOfTable(); i++) {

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


    public List<DataCell> getDataCells() {
        return dataCells;
    }

    public List<Integer> getListIdsForCheck() {
        return listIdsForCheck;
    }

    public int getFirstValue() {
        return firstValue;
    }
}
