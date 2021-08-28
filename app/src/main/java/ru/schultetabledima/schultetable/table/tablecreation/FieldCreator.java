package ru.schultetabledima.schultetable.table.tablecreation;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import androidx.appcompat.widget.AppCompatTextView;

import ru.schultetabledima.schultetable.settings.SettingsActivity;
import ru.schultetabledima.schultetable.utils.Converter;

public class FieldCreator {
    private TableLayout field;
    private Context context;
    private int rowsOfTable;
    private int columnsOfTable;
    private CustomCell [][] cells;
    private int backgroundColor;
    private boolean isLetters;



    public FieldCreator(Context context, int backgroundColor) {
        this.context = context;
        this.backgroundColor = backgroundColor;
        main();
    }

    private void main() {
        readSharedPreferences();
        creator();
    }


    private void readSharedPreferences() {
        SharedPreferences settings = context.getSharedPreferences(SettingsActivity.getAppPreferences(), MODE_PRIVATE);
        isLetters = settings.getBoolean(SettingsActivity.getKeyNumbersOrLetters(), false);

        if (isLetters){
            columnsOfTable = settings.getInt(SettingsActivity.getKeyColumnsLetters(), 4) + 1;
            rowsOfTable = settings.getInt(SettingsActivity.getKeyRowsLetters(), 4) + 1;
        } else{
            columnsOfTable = settings.getInt(SettingsActivity.getKeyColumnsNumbers(), 4) + 1;
            rowsOfTable = settings.getInt(SettingsActivity.getKeyRowsNumbers(), 4) + 1;
        }
    }

    private void creator(){
        field = new TableLayout(context);
        LinearLayout.LayoutParams llLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1);
        field.setLayoutParams(llLayoutParams);

        field.setId(View.generateViewId());
        field.setBackgroundColor(backgroundColor);



        //создание рядов
        LinearLayout[] rows = new LinearLayout[rowsOfTable];
        for (int i = 0; i < rows.length; i++) {
            rows[i] = new LinearLayout(context);
            TableLayout.LayoutParams tlLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    0,1);
            tlLayoutParams.setLayoutDirection(LinearLayout.HORIZONTAL);
            rows[i].setLayoutParams(tlLayoutParams);
            field.addView(rows[i]);
        }


        //Создание кнопок
        cells = new CustomCell[rowsOfTable][columnsOfTable];
        for (int i = 0; i < rowsOfTable; i++){
            for (int j = 0; j < columnsOfTable; j++) {
                cells[i][j] = new CustomCell(context, isLetters);
                cells[i][j].setTextColor(Color.BLACK);
                cells[i][j].setBackgroundColor(Color.WHITE);
                cells[i][j].setMaxLines(1);
                cells[i][j].setGravity(Gravity.CENTER);

                LinearLayout.LayoutParams layoutParamsCell = new LinearLayout.LayoutParams(0,
                        TableLayout.LayoutParams.MATCH_PARENT, 1);

                layoutParamsCell.setMargins(1,1,1,1);
                cells[i][j].setLayoutParams(layoutParamsCell);

                if ((context).getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    int padding = Converter.getPx(context, 6);
                    cells[i][j].setPadding(padding, padding, padding, padding);
                }

                cells[i][j].setId(View.generateViewId());
                rows[i].addView(cells[i][j]);
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
