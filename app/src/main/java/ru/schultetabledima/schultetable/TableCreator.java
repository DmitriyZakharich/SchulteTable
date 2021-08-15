package ru.schultetabledima.schultetable;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.TextViewCompat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

import ru.schultetabledima.schultetable.presenters.TablePresenter;
import ru.schultetabledima.schultetable.utils.Converter;
import ru.schultetabledima.schultetable.ui.CustomizationActivity;

public class TableCreator implements Serializable{
    private TableLayout tlTable;
    private TableRow[] tableRow;
    private AppCompatTextView[][] cellsOfTable;
    private int rowsOfTable;
    private int columnsOfTable;
    private Context context;
    private ArrayList<Integer> listNumber;
    private int rowCellTen;
    private int columnCellTen;
    HashSet <Integer> hsRandomForCellAnim;
    private int randAnimInt;
    private boolean booleanAnim;
    TablePresenter tablePresenter;
    private boolean amountIsMoreTen = false;



    public TableCreator(Context context, TablePresenter tablePresenter) {
        this.context = context;
        this.tablePresenter = tablePresenter;
        init();
    }

    void init(){
        readSharedPreferences();
        creator();
        fillingCells();
        if (booleanAnim)
                addAnimation();

        if(10 <= rowsOfTable*columnsOfTable)
            correctionTextSizeCells();
    }


    private void readSharedPreferences() {
        SharedPreferences spCustomization = context.getSharedPreferences(CustomizationActivity.getAppPreferences(), MODE_PRIVATE);
        booleanAnim = spCustomization.getBoolean(CustomizationActivity.getPreferencesKeyAnimation(), false);
        columnsOfTable = spCustomization.getInt(CustomizationActivity.getPreferencesKeyNumberColumns(), 4) + 1;
        rowsOfTable = spCustomization.getInt(CustomizationActivity.getPreferencesKeyNumberRows(), 4) + 1;
    }

    private void creator(){
        tlTable = new TableLayout(context);
        tlTable.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT,1));

        //Создание разделительных полос
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.BLACK);
        drawable.setSize(2, 2);

        tlTable.setDividerPadding(2);
        tlTable.setShowDividers(TableLayout.SHOW_DIVIDER_MIDDLE);
        tlTable.setDividerDrawable(drawable);

        //создание рядов
        tableRow = new TableRow[rowsOfTable];
        for (int i = 0; i < tableRow.length; i++) {
            tableRow[i] = new TableRow(context);
            tableRow[i].setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT,1));
            tlTable.addView(tableRow[i]);
        }

        //Создание кнопок
        cellsOfTable = new AppCompatTextView[rowsOfTable][columnsOfTable];
        for (int i = 0; i < rowsOfTable; i++){
            for (int j = 0; j < columnsOfTable; j++) {
                cellsOfTable[i][j] = new AppCompatTextView(context);
                cellsOfTable[i][j].setTextColor(Color.BLACK);
                cellsOfTable[i][j].setBackgroundColor(Color.WHITE);
                cellsOfTable[i][j].setMaxLines(1);
                TableRow.LayoutParams lpButton = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1);
                cellsOfTable[i][j].setLayoutParams(lpButton);
                cellsOfTable[i][j].setGravity(Gravity.CENTER);

                cellsOfTable[i][j].setPadding(20, 20, 20, 20);

                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(cellsOfTable[i][j],1, 100, 1, TypedValue.COMPLEX_UNIT_SP);

                tableRow[i].addView(cellsOfTable[i][j]);
                tableRow[i].setDividerDrawable(drawable);
                tableRow[i].setDividerPadding(2);
                tableRow[i].setShowDividers(TableRow.SHOW_DIVIDER_MIDDLE);
            }
        }
    }

    private void fillingCells(){
        //Массива для заполнения ячеек
        listNumber = new ArrayList();
        for (int j = 1; j <= columnsOfTable * rowsOfTable; j++ ) {
            listNumber.add( j );
        }
        Collections.shuffle( listNumber );

        /*
        Установка текста.
        Запоминание координат ячейки с "10",
        для корректировки размера текста в ячейках.
         */
        int count = 0;
        for (int i = 0; i < rowsOfTable; i++) {
            for (int j = 0; j < columnsOfTable; j++) {
                cellsOfTable[i][j].setOnClickListener(cellClick);

                cellsOfTable[i][j].setText("" + listNumber.get(count));
                count++;

                if (cellsOfTable[i][j].getText().toString().equals("10")){
                    rowCellTen = i;
                    columnCellTen = j;
                    amountIsMoreTen = true;
                }
            }
        }
    }

    private void addAnimation() {
        //Рандом для анимации
        Random random = new Random();

        int amountCellAnim = (columnsOfTable * rowsOfTable)/2;
        hsRandomForCellAnim = new HashSet<Integer>();

        for (int i = 0; i < amountCellAnim; i++) {
            randAnimInt = random.nextInt(columnsOfTable * rowsOfTable + 1); //????
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

    View.OnClickListener cellClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tablePresenter.checkMove(v);
        }
    };

    public TableLayout getTable() {
        return tlTable;
    }


    //Корректировка размера шрифта в таблице
    //При количестве ячеек больше 9 авторазмер делает 1-9 больше.
    void correctionTextSizeCells(){
        cellsOfTable[0][0].post(new Runnable() {
            @Override
            public void run() {
                int tenCellTextSize = new Converter().getSP(context, cellsOfTable[rowCellTen][columnCellTen].getTextSize());
                for (int i = 0; i < rowsOfTable; i++) {
                    for (int j = 0; j < columnsOfTable; j++) {
                        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(cellsOfTable[i][j], 1,
                                tenCellTextSize, 1, TypedValue.COMPLEX_UNIT_SP);
                    }
                }
            }
        });
    }


}
