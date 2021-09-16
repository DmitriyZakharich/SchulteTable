package ru.schultetabledima.schultetable.table.tablecreation;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.ArrayMap;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.table.TablePresenter;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

public class TableCreator {
    private ArrayList<Integer> listNumbers2;
    private ArrayList<Integer> listNumbers1;
    private LinearLayout containerForTable;
    private Context context;
    private TablePresenter tablePresenter;
    private FieldFiller fieldFiller1;
    private FieldFiller fieldFiller2;
    private ArrayList<Character> listLetters1;
    private ArrayList<Character> listLetters2;
    private FieldCreator fieldCreator1;
    private FieldCreator fieldCreator2;
    private PreferencesReader settings;


    public TableCreator(Context context, TablePresenter tablePresenter) {
        this.context = context;
        this.tablePresenter = tablePresenter;
        main();
    }

    //Constructor to restore activity with letters
    public TableCreator(Context context, TablePresenter tablePresenter, ArrayList<Character> listLetters1, ArrayList<Character> listLetters2) {
        this.context = context;
        this.tablePresenter = tablePresenter;
        this.listLetters1 = listLetters1;
        this.listLetters2 = listLetters2;
        restoreTable();
    }

    //Constructor to restore activity with numbers
    public TableCreator(Context context, ArrayList<Integer> listNumbers1, ArrayList<Integer> listNumbers2, TablePresenter tablePresenter) {
        this.context = context;
        this.tablePresenter = tablePresenter;
        this.listNumbers1 = listNumbers1;
        this.listNumbers2 = listNumbers2;
        restoreTable();
    }


    void main() {
        settings = new PreferencesReader(context);
        creatingContainerForTable();
        creatingField();
        fillingTable();
    }


    private void creatingContainerForTable() {
        containerForTable = new LinearLayout(context);
        containerForTable.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));


        if (settings.getIsTwoTables()) {
            //Создание разделительной полосы между таблицами
            GradientDrawable drawable = new GradientDrawable();
            drawable.setColor(Color.BLACK);
            drawable.setSize(30, 30);
            containerForTable.setShowDividers(TableLayout.SHOW_DIVIDER_MIDDLE);
            containerForTable.setDividerDrawable(drawable);

            if ((context).getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                containerForTable.setOrientation(LinearLayout.HORIZONTAL);
            } else {
                containerForTable.setOrientation(LinearLayout.VERTICAL);
            }

        }
    }

    private void creatingField() {
        fieldCreator1 = new FieldCreator(context, ContextCompat.getColor(context, R.color.activeTable));
        containerForTable.addView(fieldCreator1.getField());

        if (settings.getIsTwoTables()) {
            fieldCreator2 = new FieldCreator(context, ContextCompat.getColor(context, R.color.passiveTable));
            containerForTable.addView(fieldCreator2.getField());
        }
    }

    private void fillingTable() {
        AppCompatTextView[][] cells1 = fieldCreator1.getCells();
        fieldFiller1 = new FieldFiller(context, cells1, tablePresenter);

        if (settings.getIsTwoTables()) {
            AppCompatTextView[][] cells2 = fieldCreator2.getCells();
            fieldFiller2 = new FieldFiller(context, cells2, tablePresenter);
        }
    }


    private void restoreTable() {
        settings = new PreferencesReader(context);
        creatingContainerForTable();
        creatingField();
        restoreFillingTable();
    }

    private void restoreFillingTable() {

        if (settings.getIsLetters()) {
            AppCompatTextView[][] cells1 = fieldCreator1.getCells();
            fieldFiller1 = new FieldFiller(context, tablePresenter, listLetters1, cells1);

            if (settings.getIsTwoTables()) {
                AppCompatTextView[][] cells2 = fieldCreator2.getCells();
                fieldFiller2 = new FieldFiller(context, tablePresenter, listLetters2, cells2);
            }
        }

        if (!settings.getIsLetters()) {
            AppCompatTextView[][] cells1 = fieldCreator1.getCells();
            fieldFiller1 = new FieldFiller(cells1, tablePresenter, listNumbers1, context);

            if (settings.getIsTwoTables()) {
                AppCompatTextView[][] cells2 = fieldCreator2.getCells();
                fieldFiller2 = new FieldFiller(cells2, tablePresenter, listNumbers2, context);
            }
        }
    }


    public LinearLayout getContainerForTable() {
        return containerForTable;
    }

    public ArrayList<Integer> getListNumbers1() {
        return fieldFiller1.getListNumbers();
    }

    public ArrayList<Integer> getListNumbers2() {
        return fieldFiller2.getListNumbers();
    }


    public ArrayList<Character> getListLetters1() {
        return fieldFiller1.getListLetters();
    }

    public ArrayList<Character> getListLetters2() {
        return fieldFiller2.getListLetters();
    }


    public ArrayMap<Integer, Integer> getCellsIdFirstTable() {
        return fieldFiller1.getCellsId();
    }

    public ArrayMap<Integer, Integer> getCellsIdSecondTable() {
        return fieldFiller2.getCellsId();
    }
}
