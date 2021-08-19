package ru.schultetabledima.schultetable.tablecreation;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.widget.AppCompatTextView;

import ru.schultetabledima.schultetable.ui.CustomizationActivity;

public class FieldCreator {
    private TableLayout field;
    private Context context;
    private int rowsOfTable;
    private int columnsOfTable;
    private AppCompatTextView [][] cellsOfTable;


    public FieldCreator(Context context) {
        this.context = context;
        init();
        Log.d("Трасировка", "FieldCreator");
    }

    private void init() {
        readSharedPreferences();
        creator();
    }


    private void readSharedPreferences() {
        SharedPreferences spCustomization = context.getSharedPreferences(CustomizationActivity.getAppPreferences(), MODE_PRIVATE);
        columnsOfTable = spCustomization.getInt(CustomizationActivity.getKeyNumberColumns(), 4) + 1;
        rowsOfTable = spCustomization.getInt(CustomizationActivity.getKeyNumberRows(), 4) + 1;
    }

    private void creator(){
        field = new TableLayout(context);
        TableRow.LayoutParams trLayoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT,1);
        field.setLayoutParams(trLayoutParams);

        //Создание разделительных полос
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.BLACK);
        drawable.setSize(2, 2);

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

                tableRow[i].addView(cellsOfTable[i][j]);
                tableRow[i].setDividerDrawable(drawable);
                tableRow[i].setDividerPadding(2);
                tableRow[i].setShowDividers(TableRow.SHOW_DIVIDER_MIDDLE);
            }
        }
    }


    public TableLayout getField() {
        return field;
    }

    public AppCompatTextView[][] getCellsOfTable() {
        return cellsOfTable;
    }
}
