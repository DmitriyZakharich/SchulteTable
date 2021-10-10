package ru.schultetabledima.schultetable.table.mvp.view.tablecreation;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import androidx.appcompat.widget.AppCompatTextView;

import ru.schultetabledima.schultetable.App;
import ru.schultetabledima.schultetable.table.mvp.view.TableActivity;
import ru.schultetabledima.schultetable.table.mvp.presenter.TablePresenter;
import ru.schultetabledima.schultetable.utils.Converter;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

public class FieldCreator {
    private TableLayout field;
    private Context context;
    private CustomCell[][] cells;
    private int backgroundResources;
    private TablePresenter tablePresenter;
    private PreferencesReader settings;


    public FieldCreator(Context context, int backgroundResources, TablePresenter tablePresenter) {
        this.context = context;
        this.backgroundResources = backgroundResources;
        this.tablePresenter = tablePresenter;
        main();
    }

    private void main() {
        settings = new PreferencesReader();
        creator();
    }

    private void creator() {
        field = new TableLayout(context);
        LinearLayout.LayoutParams llLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1);
        field.setLayoutParams(llLayoutParams);

        field.setId(View.generateViewId());


        //создание рядов
        LinearLayout[] rows = new LinearLayout[settings.getRowsOfTable()];
        for (int i = 0; i < rows.length; i++) {
            rows[i] = new LinearLayout(context);
            TableLayout.LayoutParams tlLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    0, 1);
            tlLayoutParams.setLayoutDirection(LinearLayout.HORIZONTAL);
            rows[i].setLayoutParams(tlLayoutParams);
            field.addView(rows[i]);
        }

        //Создание кнопок
        cells = new CustomCell[settings.getRowsOfTable()][settings.getColumnsOfTable()];
        for (int i = 0; i < settings.getRowsOfTable(); i++) {
            for (int j = 0; j < settings.getColumnsOfTable(); j++) {
                cells[i][j] = new CustomCell(context, settings.getIsLetters());
                cells[i][j].setTextColor(Color.BLACK);
                cells[i][j].setBackgroundColor(Color.WHITE);
                cells[i][j].setMaxLines(1);
                cells[i][j].setGravity(Gravity.CENTER);
                cells[i][j].setBackground(App.getContext().getResources().getDrawable(backgroundResources));

                LinearLayout.LayoutParams layoutParamsCell = new LinearLayout.LayoutParams(0,
                        TableLayout.LayoutParams.MATCH_PARENT, 1);

                layoutParamsCell.setMargins(1, 1, 1, 1);
                cells[i][j].setLayoutParams(layoutParamsCell);

                if ((context).getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    int padding = Converter.getPxFromDP(context, 6);
                    cells[i][j].setPadding(padding, padding, padding, padding);
                }

                rows[i].addView(cells[i][j]);
                cells[i][j].setLongClickable(true);
                cells[i][j].setOnTouchListener(cellTouch);
            }
        }
    }

    View.OnTouchListener cellTouch = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    tablePresenter.checkMove(view.getId(), ((TableActivity) context).getBaseChronometer());
                    return true;
                case MotionEvent.ACTION_UP:
                    tablePresenter.applyCellSelection(view.getId());
                    return true;

            }
            return false;
        }
    };


    public TableLayout getField() {
        return field;
    }

    public AppCompatTextView[][] getCells() {
        return cells;
    }
}
