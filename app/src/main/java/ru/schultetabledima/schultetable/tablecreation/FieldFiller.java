package ru.schultetabledima.schultetable.tablecreation;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.TextViewCompat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.presenters.TablePresenter;
import ru.schultetabledima.schultetable.ui.CustomizationActivity;
import ru.schultetabledima.schultetable.utils.Converter;


public class FieldFiller {
    private AppCompatTextView[][] cells;
    private Context context;
    private int columnsOfTable;
    private int rowsOfTable;
    TablePresenter tablePresenter;
    private ArrayList<Integer> listNumbers;
    private int rowCellTen;
    private int columnCellTen;
    private boolean amountIsMoreTen = false;
    private ArrayList<Character> listLetters;
    private boolean isLetters;
    private boolean booleanAnim;
    private boolean isEnglish;
    private boolean isNewFilling = true;


    public FieldFiller(Context context, AppCompatTextView[][] cells, TablePresenter tablePresenter) {
        Log.d("Трасировка", "FieldFiller");
        this.context = context;
        this.cells = cells;
        this.tablePresenter = tablePresenter;
        init();
    }

    //Конструктор для восстановления активити с буквами
    public FieldFiller(Context context, TablePresenter tablePresenter, ArrayList<Character> listLetters, AppCompatTextView[][] cells) {
        Log.d("Трасировка", "FieldFiller восстановление с буквами");
        this.context = context;
        this.cells = cells;
        this.tablePresenter = tablePresenter;
        this.listLetters = listLetters;
        isNewFilling = false;
        init();



        for (char i:
                listLetters) {
            Log.d("isLetters", "" + i);

        }

    }

    //Конструктор для восстановления активити c цифрами
    public FieldFiller(AppCompatTextView[][] cells, TablePresenter tablePresenter, ArrayList<Integer> listNumbers, Context context) {
        Log.d("Трасировка", "FieldFiller восстановление с цифрами");
        this.context = context;
        this.cells = cells;
        this.tablePresenter = tablePresenter;
        this.listNumbers = listNumbers;
        isNewFilling = false;
        init();

    }



    private void init() {
        readSharedPreferences();
        if (isLetters)
            fillingLetters();
        else
            fillingNumbers();

        if (booleanAnim)
            addAnimation(cells);

    }



    private void readSharedPreferences() {
        SharedPreferences spCustomization = context.getSharedPreferences(CustomizationActivity.getAppPreferences(), MODE_PRIVATE);
        columnsOfTable = spCustomization.getInt(CustomizationActivity.getKeyNumberColumns(), 4) + 1;
        rowsOfTable = spCustomization.getInt(CustomizationActivity.getKeyNumberRows(), 4) + 1;
        isLetters = spCustomization.getBoolean(CustomizationActivity.getKeyNumbersLetters(), false);
        booleanAnim = spCustomization.getBoolean(CustomizationActivity.getKeyAnimation(), false);
//        isEnglish = spCustomization.getBoolean();

    }

    private void fillingLetters() {
        //Новое заполнение. Если иначе, то получение данных из конструктора
        if (isNewFilling){
            char firstLetter = (isEnglish)? 'A': 'А'; // анг/рус
            //Массив для заполнения ячеек
            listLetters = new ArrayList();
            for (int j = 1; j <= columnsOfTable * rowsOfTable; j++ ) {
                listLetters.add( firstLetter );
                firstLetter++;
            }
            Collections.shuffle(listLetters);
        }


        int count = 0;
        for (int i = 0; i < rowsOfTable; i++) {
            for (int j = 0; j < columnsOfTable; j++) {
                cells[i][j].setText(String.valueOf(listLetters.get(count)));
                count++;
                cells[i][j].setOnClickListener(cellClick);

                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(cells[i][j], 1,
                        100, 1, TypedValue.COMPLEX_UNIT_SP);
            }
        }
    }

    private void fillingNumbers(){
        //Новое заполнение. Если иначе, то получение данных из конструктора
        if (isNewFilling){
            //Массив для заполнения ячеек
            listNumbers = new ArrayList();
            for (int j = 1; j <= columnsOfTable * rowsOfTable; j++ ) {
                listNumbers.add( j );
            }
            Collections.shuffle(listNumbers);
        }


        int count = 0;
        for (int i = 0; i < rowsOfTable; i++) {
            for (int j = 0; j < columnsOfTable; j++) {
                cells[i][j].setOnClickListener(cellClick);
                cells[i][j].setText(String.valueOf(listNumbers.get(count)));
                count++;

                /*Запоминание координат ячейки с "10",
                для корректировки размера текста в ячейках
                в методе correctionTextSizeCells()
                */
                if (cells[i][j].getText().toString().equals("10")){
                    rowCellTen = i;
                    columnCellTen = j;
                    amountIsMoreTen = true;
                }

//                cellsOfTable[i][j].setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(cells[i][j], 1,
                        100, 1, TypedValue.COMPLEX_UNIT_SP);

            }
        }
        if(amountIsMoreTen)
            correctionTextSizeCells();

    }

    /*Корректировка размера шрифта в таблице
    При количестве ячеек больше 9 авторазмер делает 1-9 больше, чем нужно.*/
    private void correctionTextSizeCells(){
        cells[0][0].post(new Runnable() {
            @Override
            public void run() {

                int padding = cells[0][0].getHeight() /5;

                for (int i = 0; i < rowsOfTable; i++) {
                    for (int j = 0; j < columnsOfTable; j++) {
                        cells[i][j].setPadding(0,padding,0,padding);
                    }
                }

                int tenthCellTextSize = Converter.getSP(context, cells[rowCellTen][columnCellTen].getTextSize());
                for (int i = 0; i < rowsOfTable; i++) {
                    for (int j = 0; j < columnsOfTable; j++) {

                        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(cells[i][j], 1,
                                tenthCellTextSize, 1, TypedValue.COMPLEX_UNIT_SP);
                        Log.d("Отступы", "" + cells[i][j].getText() + " - " + cells[i][j].getTextSize());

                    }
                }
            }
        });
    }

    View.OnClickListener cellClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tablePresenter.checkMove(((AppCompatTextView)v).getText().toString());
        }
    };

    public ArrayList<Integer> getListNumbers() {
        return listNumbers;
    }

    public ArrayList<Character> getListLetters() {
        return listLetters;
    }



    private void addAnimation(AppCompatTextView[][] cellsOfTable) {
        Random random = new Random();

        int amountCellAnim = (columnsOfTable * rowsOfTable)/2;
        HashSet<Integer> hsRandomForCellAnim = new HashSet<Integer>();

        for (int i = 0; i < amountCellAnim; i++) {
            int randAnimInt = random.nextInt(columnsOfTable * rowsOfTable + 1); //????
            if (!hsRandomForCellAnim.add(Integer.valueOf(randAnimInt))){
                i--;
            }
        }

        for (int i = 0; i < rowsOfTable; i++) {
            for (int j = 0; j < columnsOfTable; j++) {
                if (hsRandomForCellAnim.contains(Integer.parseInt(cellsOfTable[i][j].getText().toString()))) {
                    Animation anim = AnimationUtils.loadAnimation(context, R.anim.myrotate);
                    cellsOfTable[i][j].startAnimation(anim);
                }
            }
        }
    }
}