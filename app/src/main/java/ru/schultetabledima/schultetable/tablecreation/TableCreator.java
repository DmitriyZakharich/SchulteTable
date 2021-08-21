package ru.schultetabledima.schultetable.tablecreation;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import androidx.appcompat.widget.AppCompatTextView;

import java.util.ArrayList;

import ru.schultetabledima.schultetable.presenters.TablePresenter;
import ru.schultetabledima.schultetable.ui.CustomizationActivity;

public class TableCreator{
    private ArrayList<Integer> listNumbers2;
    private ArrayList<Integer> listNumbers1;
    private LinearLayout table;
    private int rowsOfTable;
    private int columnsOfTable;
    private Context context;
    private TablePresenter tablePresenter;
    private boolean isTwoTables;
    private boolean isLetters;
    private FieldFiller fieldFiller1;
    private FieldFiller fieldFiller2;
    ArrayList<Character> listLetters1;
    ArrayList<Character> listLetters2;
    private FieldCreator fieldCreator1;
    private FieldCreator fieldCreator2;


    public TableCreator(Context context, TablePresenter tablePresenter) {
        this.context = context;
        this.tablePresenter = tablePresenter;
        init();
    }

    //Конструктор для восстановления активити с буквами
    public TableCreator(Context context, TablePresenter tablePresenter, ArrayList<Character> listLetters1, ArrayList<Character> listLetters2) {
        this.context = context;
        this.tablePresenter = tablePresenter;
        this.listLetters1 = listLetters1;
        this.listLetters2 = listLetters2;
        restoreTable();
    }

    //Конструктор для восстановления активити с цифрами
    public TableCreator(Context context, ArrayList<Integer> listNumbers1, ArrayList<Integer> listNumbers2, TablePresenter tablePresenter) {
        this.context = context;
        this.tablePresenter = tablePresenter;
        this.listNumbers1 = listNumbers1;
        this.listNumbers2 = listNumbers2;
        restoreTable();
    }


    void init(){
        readSharedPreferences();
        creatingContainerForTable();
        creatingTable();
        fillingTable();
    }

    private void readSharedPreferences() {
        SharedPreferences spCustomization = context.getSharedPreferences(CustomizationActivity.getAppPreferences(), MODE_PRIVATE);
        columnsOfTable = spCustomization.getInt(CustomizationActivity.getKeyNumberColumns(), 4) + 1;
        rowsOfTable = spCustomization.getInt(CustomizationActivity.getKeyNumberRows(), 4) + 1;
        isTwoTables = spCustomization.getBoolean(CustomizationActivity.getKeyTwoTables(), false);
        isLetters = spCustomization.getBoolean(CustomizationActivity.getKeyNumbersLetters(), false);
    }

    private void creatingContainerForTable() {
        table = new LinearLayout(context);
        table.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

        if (isTwoTables) {
            //Создание разделительных полос
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(Color.BLACK);
            drawable.setSize(30, 30);
//        table.setDividerPadding(40);
            table.setShowDividers(TableLayout.SHOW_DIVIDER_MIDDLE);
            table.setDividerDrawable(drawable);

            if ((context).getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                table.setOrientation(LinearLayout.HORIZONTAL);
            } else {
                table.setOrientation(LinearLayout.VERTICAL);
            }

        }
    }

    private void creatingTable() {
            fieldCreator1 = new FieldCreator(context);
            table.addView(fieldCreator1.getField());

            if (isTwoTables){
                    fieldCreator2 = new FieldCreator(context);
                    table.addView(fieldCreator2.getField());

            }
    }

    private void fillingTable(){
        AppCompatTextView[][] cells1 = fieldCreator1.getCells();
        fieldFiller1 = new FieldFiller(context, cells1, tablePresenter);

        if (isTwoTables){
            AppCompatTextView[][] cells2 = fieldCreator2.getCells();
            fieldFiller2 = new FieldFiller(context, cells2, tablePresenter);
        }
    }

    private void restoreFillingTable(){

        if (isLetters){
            AppCompatTextView[][] cells1 = fieldCreator1.getCells();
            fieldFiller1 = new FieldFiller(context, tablePresenter, listLetters1, cells1);

            if (isTwoTables){
                AppCompatTextView[][] cells2 = fieldCreator2.getCells();
                fieldFiller2 = new FieldFiller(context, tablePresenter, listLetters2, cells2);
            }
        }

        if(!isLetters){
            AppCompatTextView[][] cells1 = fieldCreator1.getCells();
            fieldFiller1 = new FieldFiller(cells1, tablePresenter, listNumbers1, context);

            if (isTwoTables){
                AppCompatTextView[][] cells2 = fieldCreator2.getCells();
                fieldFiller2 = new FieldFiller(cells2, tablePresenter, listNumbers2, context);
            }
        }


    }

    private void restoreTable() {
        readSharedPreferences();
        creatingContainerForTable();
        creatingTable();
        restoreFillingTable();
    }


    public LinearLayout getTable() {
        return table;
    }

    public ArrayList<Integer> getListNumbers1(){
        return fieldFiller1.getListNumbers();
    }
    public ArrayList<Integer> getListNumbers2(){
        return fieldFiller2.getListNumbers();
    }


    public ArrayList<Character> getListLetters1(){
        return fieldFiller1.getListLetters();
    }
    public ArrayList<Character> getListLetters2(){
        return fieldFiller2.getListLetters();
    }



    public void detachView() {
        context = null;
    }
}
