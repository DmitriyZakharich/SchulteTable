package ru.schultetabledima.schultetable.table.view.tablecreation;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import androidx.appcompat.widget.AppCompatTextView;

import ru.schultetabledima.schultetable.table.presenter.TablePresenter;
import ru.schultetabledima.schultetable.utils.Converter;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

public class FieldCreator {
    private TableLayout field;
    private TableCreator.PassMeLinkOnPresenter container;
    private Context context;
    private CustomCell[][] cells;
    private LinearLayout[] rows;
    private int backgroundResources;
    private TablePresenter tablePresenter;
    private PreferencesReader settings;


    public FieldCreator(TableCreator.PassMeLinkOnPresenter container, Context context, int backgroundResources, TablePresenter tablePresenter) {
        this.container = container;
        this.context = context;
        this.backgroundResources = backgroundResources;
        this.tablePresenter = tablePresenter;
        main();
    }

    private void main() {
        settings = new PreferencesReader();
        createField();
        createRows();
        createCells();
    }


    private void createField() {
        field = new TableLayout(context);
        LinearLayout.LayoutParams llLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT, 1);
        field.setLayoutParams(llLayoutParams);

        field.setId(View.generateViewId());
        field.setMotionEventSplittingEnabled(false);
    }

    private void createRows() {
        rows = new LinearLayout[settings.getRowsOfTable()];
        for (int i = 0; i < rows.length; i++) {
            rows[i] = new LinearLayout(context);
            TableLayout.LayoutParams tlLayoutParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                    0, 1);
            tlLayoutParams.setLayoutDirection(LinearLayout.HORIZONTAL);
            rows[i].setLayoutParams(tlLayoutParams);
            rows[i].setMotionEventSplittingEnabled(false);
            field.addView(rows[i]);
        }
    }

    private void createCells() {

        cells = new CustomCell[settings.getRowsOfTable()][settings.getColumnsOfTable()];
        for (int i = 0; i < settings.getRowsOfTable(); i++) {
            for (int j = 0; j < settings.getColumnsOfTable(); j++) {
                cells[i][j] = new CustomCell(context, settings.getIsLetters());
                cells[i][j].setTextColor(Color.BLACK);
                cells[i][j].setBackgroundColor(Color.WHITE);
                cells[i][j].setMaxLines(1);
                cells[i][j].setGravity(Gravity.CENTER);
                cells[i][j].setBackground(context.getResources().getDrawable(backgroundResources));

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
                    tablePresenter.cellActionDown(view.getId(), container.getBaseChronometer());
                    return true;
                case MotionEvent.ACTION_UP:
                    tablePresenter.cellActionUp(view.getId());
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
