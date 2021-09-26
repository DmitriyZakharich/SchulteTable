package ru.schultetabledima.schultetable.table.tablecreation;

import android.content.Context;
import android.content.res.Configuration;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import ru.schultetabledima.schultetable.App;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.table.TablePresenter;
import ru.schultetabledima.schultetable.utils.Converter;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

public class TableCreator {
    private LinearLayout containerForTable;
    private Context context;
    private TablePresenter tablePresenter;
    private FieldCreator fieldCreator1;
    private FieldCreator fieldCreator2;
    private PreferencesReader settings;
    private View viewDivider;
    private AppCompatTextView[][] cells1, cells2;
    private ArrayList<Integer> listNumbers2;
    private ArrayList<Integer> listNumbers1;
    private ArrayList<Character> listLetters1;
    private ArrayList<Character> listLetters2;


    public TableCreator(Context context, TablePresenter tablePresenter) {
        this.context = context;
        this.tablePresenter = tablePresenter;
        main();
    }

    void main() {
        settings = new PreferencesReader(context);
        creatingContainerForTable();
        creatingField();
//        fillingTable();
//        addAnimationTransition();

//        if (settings.getIsAnim()) {
//            addAnimationInGame();
//        }
    }


    private void creatingContainerForTable() {

        containerForTable = new LinearLayout(context);
        containerForTable.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT));
        containerForTable.setGravity(Gravity.CENTER);


        if (settings.getIsTwoTables()) {

            viewDivider = new View(context);
            viewDivider.setBackground(AppCompatResources.getDrawable(App.getContext(), R.drawable.table_separator));

            int widthViewDivider;
            int heightViewDivider;

            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                widthViewDivider = 15;
                heightViewDivider = 100;

                containerForTable.setOrientation(LinearLayout.HORIZONTAL);

            } else {
                widthViewDivider = 100;
                heightViewDivider = 15;

                containerForTable.setOrientation(LinearLayout.VERTICAL);
            }

            viewDivider.setLayoutParams(new LinearLayout.LayoutParams(Converter.getPxFromDP(context, widthViewDivider),
                    Converter.getPxFromDP(context, heightViewDivider)));

        }
    }

    private void creatingField() {
        fieldCreator1 = new FieldCreator(context, ContextCompat.getColor(context, R.color.activeTable), tablePresenter);
        containerForTable.addView(fieldCreator1.getField());

        if (settings.getIsTwoTables()) {

            containerForTable.addView(viewDivider);

            fieldCreator2 = new FieldCreator(context, ContextCompat.getColor(context, R.color.passiveTable), tablePresenter);
            containerForTable.addView(fieldCreator2.getField());
        }
    }

    private void addAnimationTransition() {
        new AnimationTransition(containerForTable);
    }

    private void addAnimationInGame() {
        new AnimationInGame(cells1);

        if (settings.getIsTwoTables())
            new AnimationInGame(cells2);
    }


//    private void restoreTable() {
//        settings = new PreferencesReader(context);
//        creatingContainerForTable();
//        creatingField();
//        restoreFillingTable();
//    }

//    private void restoreFillingTable() {
//
//        if (settings.getIsLetters()) {
//            AppCompatTextView[][] cells1 = fieldCreator1.getCells();
//            fieldFiller1 = new FieldFiller(context, tablePresenter, listLetters1, cells1);
//
//            if (settings.getIsTwoTables()) {
//                AppCompatTextView[][] cells2 = fieldCreator2.getCells();
//                fieldFiller2 = new FieldFiller(context, tablePresenter, listLetters2, cells2);
//            }
//        }
//
//        if (!settings.getIsLetters()) {
//            AppCompatTextView[][] cells1 = fieldCreator1.getCells();
//            fieldFiller1 = new FieldFiller(cells1, tablePresenter, listNumbers1, context);
//
//            if (settings.getIsTwoTables()) {
//                AppCompatTextView[][] cells2 = fieldCreator2.getCells();
//                fieldFiller2 = new FieldFiller(cells2, tablePresenter, listNumbers2, context);
//            }
//        }
//    }


    public LinearLayout getContainerForTables() {
        return containerForTable;
    }

//    public ArrayList<Integer> getListNumbers1() {
//        return fieldFiller1.getListNumbers();
//    }
//    public ArrayList<Integer> getListNumbers2() {
//        return fieldFiller2.getListNumbers();
//    }
//
//
//    public ArrayList<Character> getListLetters1() {
//        return fieldFiller1.getListLetters();
//    }
//    public ArrayList<Character> getListLetters2() {
//        return fieldFiller2.getListLetters();
//    }


//    public List<Integer> getCellsIdFirstTable() {
//        return fieldCreator1.getCellsId();
//    }
//    public List<Integer> getCellsIdSecondTable() {
//        return fieldCreator2.getCellsId();
//    }

    public AppCompatTextView[][] getCellsFirstTable() {
        return fieldCreator1.getCells();
    }
    public AppCompatTextView[][] getCellsSecondTable() {
        return fieldCreator2.getCells();
    }
}
