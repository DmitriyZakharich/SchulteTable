package ru.schultetabledima.schultetable;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
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
                //создание кнопок через View и LayoutInflater
//                LayoutInflater layoutInflater = getLayoutInflater();
//                View viewButton = layoutInflater.inflate(R.layout.mycell, tableRow[i] ,false);
//                buttonCell[i][j] = (Button) viewButton;
//                buttonCell[i][j].setMaxLines(1);
//                buttonCell[i][j].setPadding(100,100,100,100);


                //Создание кнопок напрямую
                CellOfTable[i][j] = new AppCompatTextView(context);
                CellOfTable[i][j].setTextColor(Color.BLACK);
                CellOfTable[i][j].setBackgroundColor(Color.WHITE);
//                buttonCell[i][j].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                CellOfTable[i][j].setMaxLines(1);
//                buttonCell[i][j].setPadding(2,2,2,2);
                TableRow.LayoutParams lpButton = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1);
                lpButton.setMargins(2,2,2,2);
                CellOfTable[i][j].setLayoutParams(lpButton);
                CellOfTable[i][j].setGravity(Gravity.CENTER);

                if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    CellOfTable[i][j].setPadding(20, 20, 20, 20);
                }
//                buttonCell[i][j].setEllipsize();


//                ViewGroup.LayoutParams params = button[i][j].getLayoutParams();
//                params.width = (width - columns - 1)/columns;
//                params.height = (height - str - 1)/str;
//                button[i][j].setLayoutParams(params);


                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(CellOfTable[i][j],1, 100, 1, TypedValue.COMPLEX_UNIT_SP);
//                TextViewCompat.setAutoSizeTextTypeWithDefaults(buttonCell[i][j], TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);


                tableRow[i].addView(CellOfTable[i][j]);
                tableRow[i].setBackgroundColor(Color.BLACK);

            }
        }


    }

    public TableLayout getTableLayoutTable() {
        return tableLayoutTable;
    }

    public TableRow[] getTableRow() {
        return tableRow;
    }

    public AppCompatTextView[][] getCellOfTable() {
        return CellOfTable;
    }

}
