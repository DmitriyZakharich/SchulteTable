package ru.schultetabledima.schultetable.table.tablecreation;

import android.content.Context;
import android.util.ArrayMap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.contracts.TableContract;
import ru.schultetabledima.schultetable.table.TableActivity;
import ru.schultetabledima.schultetable.table.TablePresenter;
import ru.schultetabledima.schultetable.utils.PreferencesReader;


public class FieldFiller {
    private AppCompatTextView[][] cells;
    private Context context;
    private TablePresenter tablePresenter;
    private boolean isNewFilling = true;
    private PreferencesReader settings;

    private ArrayList<Integer> listNumbers;
    private ArrayList<Character> listLetters;
    private ArrayMap<Integer, Integer> cellsId;


    public FieldFiller(Context context, AppCompatTextView[][] cells, TablePresenter tablePresenter) {
        this.context = context;
        this.cells = cells;
        this.tablePresenter = tablePresenter;
        main();
    }

    //Конструктор для восстановления активити с буквами
    public FieldFiller(Context context, TablePresenter tablePresenter, ArrayList<Character> listLetters, AppCompatTextView[][] cells) {
        this.context = context;
        this.cells = cells;
        this.tablePresenter = tablePresenter;
        this.listLetters = listLetters;
        isNewFilling = false;
        main();
    }

    //Конструктор для восстановления активити c цифрами
    public FieldFiller(AppCompatTextView[][] cells, TablePresenter tablePresenter, ArrayList<Integer> listNumbers, Context context) {
        this.context = context;
        this.cells = cells;
        this.tablePresenter = tablePresenter;
        this.listNumbers = listNumbers;
        isNewFilling = false;
        main();
    }


    private void main() {
        settings = new PreferencesReader(context);

        if (settings.getIsLetters())
            fillingLetters();
        else
            fillingNumbers();

        if (settings.getIsAnim())
            addAnimation(cells);
    }

    private void fillingLetters() {
        //Новое заполнение. Если иначе, то получение данных из конструктора
        if (isNewFilling){
            char firstLetter = (settings.getIsEnglish())? 'A' : 'А'; // eng/rus

            //Массив для заполнения ячеек
            listLetters = new ArrayList<>();
            for (int j = 1; j <= settings.getColumnsOfTable() * settings.getRowsOfTable(); j++ ) {
                listLetters.add( firstLetter );
                firstLetter++;
            }
            Collections.shuffle(listLetters);
        }

        cellsId = new ArrayMap<>();

        int count = 0;
        for (int i = 0; i < settings.getRowsOfTable(); i++) {
            for (int j = 0; j < settings.getColumnsOfTable(); j++) {
                cells[i][j].setText(String.valueOf(listLetters.get(count)));
                count++;
                cells[i][j].setOnClickListener(cellClick);

                char c = cells[i][j].getText().toString().charAt(0);
                cellsId.put((int)c, cells[i][j].getId());
            }
        }
    }


    private void fillingNumbers(){
        //Новое заполнение. Если иначе, то получение данных из конструктора
        if (isNewFilling){
            //Массив для заполнения ячеек
            listNumbers = new ArrayList<>();
            for (int j = 1; j <= settings.getColumnsOfTable() * settings.getRowsOfTable(); j++ ) {
                listNumbers.add( j );
            }
            Collections.shuffle(listNumbers);
        }

        cellsId = new ArrayMap<>();

        int count = 0;
        for (int i = 0; i < settings.getRowsOfTable(); i++) {
            for (int j = 0; j < settings.getColumnsOfTable(); j++) {
                cells[i][j].setOnClickListener(cellClick);

                ((TableActivity)cells[i][j].getContext()).getBaseChronometer();

                cells[i][j].setText(String.valueOf(listNumbers.get(count)));
                count++;

                cellsId.put(Integer.valueOf(cells[i][j].getText().toString()), cells[i][j].getId());
            }
        }
    }


    View.OnClickListener cellClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            tablePresenter.checkMove(v.getId(), ((TableActivity)(v.getParent().getContext())).getBaseChronometer());

            ViewGroup parent = (ViewGroup) v.getParent();

            while (parent.getClass() != ConstraintLayout.class){
                parent = (ViewGroup) parent.getParent();
            }
            tablePresenter.checkMove(v.getId(), ((TableActivity)(parent.getContext())).getBaseChronometer());

        }
    };

    public ArrayList<Integer> getListNumbers() {
        return listNumbers;
    }

    public ArrayList<Character> getListLetters() {
        return listLetters;
    }

    public ArrayMap<Integer, Integer> getCellsId(){
        return cellsId;
    }




    private void addAnimation(AppCompatTextView[][] cellsOfTable) {
        Random random = new Random();

        int amountCellAnim = (settings.getColumnsOfTable() * settings.getRowsOfTable())/2;
        HashSet<Integer> hsRandomForCellAnim = new HashSet<>();

        for (int i = 0; i < amountCellAnim; i++) {
            int randAnimInt = random.nextInt(settings.getColumnsOfTable() * settings.getRowsOfTable() + 1); //????
            if (!hsRandomForCellAnim.add(Integer.valueOf(randAnimInt))){
                i--;
            }
        }

        for (int i = 0; i < settings.getRowsOfTable(); i++) {
            for (int j = 0; j < settings.getColumnsOfTable(); j++) {
                if (hsRandomForCellAnim.contains(Integer.parseInt(cellsOfTable[i][j].getText().toString()))) {
                    Animation anim = AnimationUtils.loadAnimation(context, R.anim.myrotate);
                    cellsOfTable[i][j].startAnimation(anim);
                }
            }
        }
    }
}
