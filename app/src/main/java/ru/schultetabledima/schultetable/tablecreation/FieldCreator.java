package ru.schultetabledima.schultetable.tablecreation;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

import androidx.appcompat.widget.AppCompatTextView;

import java.util.ArrayList;

import ru.schultetabledima.schultetable.ui.SettingsActivity;
import ru.schultetabledima.schultetable.utils.Converter;

public class FieldCreator {
    private TableLayout field;
    private Context context;
    private int rowsOfTable;
    private int columnsOfTable;
    private AppCompatTextView [][] cells;
    private int backgroundColor;
    private ArrayList<Integer> cellsId;


    public FieldCreator(Context context, int backgroundColor) {
        this.context = context;
        this.backgroundColor = backgroundColor;
        init();
    }

    private void init() {
        readSharedPreferences();
        creator();
    }


    private void readSharedPreferences() {
        SharedPreferences settings = context.getSharedPreferences(SettingsActivity.getAppPreferences(), MODE_PRIVATE);
        columnsOfTable = settings.getInt(SettingsActivity.getKeyNumberColumns(), 4) + 1;
        rowsOfTable = settings.getInt(SettingsActivity.getKeyNumberRows(), 4) + 1;
    }

    private void creator(){
        field = new TableLayout(context);
        TableRow.LayoutParams trLayoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT,1);
        field.setLayoutParams(trLayoutParams);

        field.setId(View.generateViewId());
        field.setBackgroundColor(backgroundColor);


//        Создание разделительных полос
//        GradientDrawable drawable = new GradientDrawable();
//        drawable.setColor(dividerColor);
//        drawable.setSize(3, 3);

//        field.setDividerPadding(2);
//        field.setShowDividers(TableLayout.SHOW_DIVIDER_MIDDLE);
//        field.setDividerDrawable(drawable);

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
                cells[i][j].setGravity(Gravity.CENTER);

                TableRow.LayoutParams layoutParamsCell = new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1);
                layoutParamsCell.setMargins(1,1,1,1);
                cells[i][j].setLayoutParams(layoutParamsCell);

                if ((context).getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    int padding = Converter.getPx(context, 6);
                    cells[i][j].setPadding(padding, padding, padding, padding);
                }

                cells[i][j].setId(View.generateViewId());
                tableRow[i].addView(cells[i][j]);
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
