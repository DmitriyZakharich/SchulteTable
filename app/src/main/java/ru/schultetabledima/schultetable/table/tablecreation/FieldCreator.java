package ru.schultetabledima.schultetable.table.tablecreation;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.ArrayMap;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import androidx.appcompat.widget.AppCompatTextView;

import java.util.ArrayList;
import java.util.List;

import ru.schultetabledima.schultetable.table.TableActivity;
import ru.schultetabledima.schultetable.table.TablePresenter;
import ru.schultetabledima.schultetable.utils.Converter;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

public class FieldCreator {
    private TableLayout field;
    private Context context;
    private CustomCell [][] cells;
    private int backgroundColor;
    private TablePresenter tablePresenter;
    private PreferencesReader settings;
//    private List<Integer> cellsId;



    public FieldCreator(Context context, int backgroundColor, TablePresenter tablePresenter) {
        this.context = context;
        this.backgroundColor = backgroundColor;
        this.tablePresenter = tablePresenter;
        main();
    }

    private void main() {
        settings = new PreferencesReader(context);

        creator();
    }

    private void creator(){
        field = new TableLayout(context);
        LinearLayout.LayoutParams llLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1);
        field.setLayoutParams(llLayoutParams);

        field.setId(View.generateViewId());
        field.setBackgroundColor(backgroundColor);


        //создание рядов
        LinearLayout[] rows = new LinearLayout[settings.getRowsOfTable()];
        for (int i = 0; i < rows.length; i++) {
            rows[i] = new LinearLayout(context);
            TableLayout.LayoutParams tlLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    0,1);
            tlLayoutParams.setLayoutDirection(LinearLayout.HORIZONTAL);
            rows[i].setLayoutParams(tlLayoutParams);
            field.addView(rows[i]);
        }

//        cellsId = new ArrayList<>();

        //Создание кнопок
        cells = new CustomCell[settings.getRowsOfTable()][settings.getColumnsOfTable()];
        for (int i = 0; i < settings.getRowsOfTable(); i++){
            for (int j = 0; j < settings.getColumnsOfTable(); j++) {
                cells[i][j] = new CustomCell(context, settings.getIsLetters());
                cells[i][j].setTextColor(Color.BLACK);
                cells[i][j].setBackgroundColor(Color.WHITE);
                cells[i][j].setMaxLines(1);
                cells[i][j].setGravity(Gravity.CENTER);

                LinearLayout.LayoutParams layoutParamsCell = new LinearLayout.LayoutParams(0,
                        TableLayout.LayoutParams.MATCH_PARENT, 1);

                layoutParamsCell.setMargins(1,1,1,1);
                cells[i][j].setLayoutParams(layoutParamsCell);

                if ((context).getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    int padding = Converter.getPxFromDP(context, 6);
                    cells[i][j].setPadding(padding, padding, padding, padding);
                }

//                cells[i][j].setId(View.generateViewId());
                rows[i].addView(cells[i][j]);

                cells[i][j].setOnClickListener(cellClick);

//                cellsId.add(cells[i][j].getId());
            }
        }
    }

    View.OnClickListener cellClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            tablePresenter.checkMove(view.getId(), ((TableActivity)context).getBaseChronometer());
        }
    };


    public TableLayout getField() {
        return field;
    }

    public AppCompatTextView[][] getCells() {
        return cells;
    }

//    public List<Integer> getCellsId(){
//        return cellsId;
//    }

}
