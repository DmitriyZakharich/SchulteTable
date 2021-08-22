package ru.schultetabledima.schultetable.tablecreation;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.widget.AppCompatTextView;

import java.util.ArrayList;

import ru.schultetabledima.schultetable.ui.SettingsActivity;

public class FieldCreator {
    private TableLayout field;
    private Context context;
    private int rowsOfTable;
    private int columnsOfTable;
    private AppCompatTextView [][] cells;
    private int dividerColor;
    private ArrayList<Integer> cellsId;


    public FieldCreator(Context context, int dividerColor) {
        this.context = context;
        this.dividerColor = dividerColor;
        init();
    }

    private void init() {
        readSharedPreferences();
        creator();
    }


    private void readSharedPreferences() {
        SharedPreferences spCustomization = context.getSharedPreferences(SettingsActivity.getAppPreferences(), MODE_PRIVATE);
        columnsOfTable = spCustomization.getInt(SettingsActivity.getKeyNumberColumns(), 4) + 1;
        rowsOfTable = spCustomization.getInt(SettingsActivity.getKeyNumberRows(), 4) + 1;
    }

    private void creator(){
        field = new TableLayout(context);
        TableRow.LayoutParams trLayoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT,1);
        field.setLayoutParams(trLayoutParams);

        field.setId(View.generateViewId());


//        Создание разделительных полос
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(dividerColor);
        drawable.setSize(3, 3);

        field.setDividerPadding(2);
        field.setShowDividers(TableLayout.SHOW_DIVIDER_MIDDLE);
        field.setDividerDrawable(drawable);

        //создание рядов
        TableRow[] tableRow = new TableRow[rowsOfTable];
        for (int i = 0; i < tableRow.length; i++) {
            tableRow[i] = new TableRow(context);
            TableLayout.LayoutParams tlLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,1);
            tableRow[i].setLayoutParams(tlLayoutParams);
            field.addView(tableRow[i]);
        }


        //Создание кнопок
        cells = new AppCompatTextView[rowsOfTable][columnsOfTable];
        for (int i = 0; i < rowsOfTable; i++){
            for (int j = 0; j < columnsOfTable; j++) {
                cells[i][j] = new AppCompatTextView(context);
                cells[i][j].setTextColor(Color.BLACK);
                cells[i][j].setBackgroundColor(Color.WHITE);
                cells[i][j].setMaxLines(1);
                TableRow.LayoutParams lpButton = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1);
                cells[i][j].setLayoutParams(lpButton);
                cells[i][j].setGravity(Gravity.CENTER);

                cells[i][j].setId(View.generateViewId());


                tableRow[i].addView(cells[i][j]);
                tableRow[i].setDividerDrawable(drawable);
                tableRow[i].setDividerPadding(2);
                tableRow[i].setShowDividers(TableRow.SHOW_DIVIDER_MIDDLE);
            }
        }
    }


    public TableLayout getField() {
        return field;
    }

    public AppCompatTextView[][] getCells() {
        return cells;
    }


}
