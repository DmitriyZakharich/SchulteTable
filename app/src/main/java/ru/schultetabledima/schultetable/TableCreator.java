package ru.schultetabledima.schultetable;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.TextViewCompat;

public class TableCreator {
    private TableLayout tableLayoutTable;
    private TableRow[] tableRow;
    private AppCompatTextView[][] CellOfTable;

    public TableCreator(int stringsOfTable, int columnsOfTable, Context context) {
        tableLayoutTable = new TableLayout(context);
        tableLayoutTable.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT,1));

        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(Color.BLACK);
        drawable.setSize(2, 2);

        tableLayoutTable.setDividerPadding(2);
        tableLayoutTable.setShowDividers(TableLayout.SHOW_DIVIDER_MIDDLE);
        tableLayoutTable.setDividerDrawable(drawable);

        tableRow = new TableRow[stringsOfTable];
        for (int i = 0; i < tableRow.length; i++) {
            tableRow[i] = new TableRow(context);
            tableRow[i].setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT,1));
            tableLayoutTable.addView(tableRow[i]);
        }

        /**Создание кнопок
         и добавление в tableRow
         анимация*/
        CellOfTable = new AppCompatTextView[stringsOfTable][columnsOfTable];
        for (int i = 0; i < stringsOfTable; i++){
            for (int j = 0; j < columnsOfTable; j++) {
                CellOfTable[i][j] = new AppCompatTextView(context);
                CellOfTable[i][j].setTextColor(Color.BLACK);
                CellOfTable[i][j].setBackgroundColor(Color.WHITE);
                CellOfTable[i][j].setMaxLines(1);
                TableRow.LayoutParams lpButton = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1);
                CellOfTable[i][j].setLayoutParams(lpButton);
                CellOfTable[i][j].setGravity(Gravity.CENTER);

//                if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    CellOfTable[i][j].setPadding(20, 20, 20, 20);
//                }

                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(CellOfTable[i][j],1, 100, 1, TypedValue.COMPLEX_UNIT_SP);

                tableRow[i].addView(CellOfTable[i][j]);
                tableRow[i].setDividerDrawable(drawable);
                tableRow[i].setDividerPadding(2);
                tableRow[i].setShowDividers(TableRow.SHOW_DIVIDER_MIDDLE);
            }
        }
    }

    public TableLayout getTableLayoutTable() {
        return tableLayoutTable;
    }


    public AppCompatTextView[][] getCellOfTable() {
        return CellOfTable;
    }

}
