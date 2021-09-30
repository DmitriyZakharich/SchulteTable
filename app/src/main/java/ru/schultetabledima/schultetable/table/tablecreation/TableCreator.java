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


    public TableCreator(Context context, TablePresenter tablePresenter) {
        this.context = context;
        this.tablePresenter = tablePresenter;
        main();
    }

    void main() {
        settings = new PreferencesReader();
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


    public LinearLayout getContainerForTables() {
        return containerForTable;
    }

    public AppCompatTextView[][] getCellsFirstTable() {
        return fieldCreator1.getCells();
    }

    public AppCompatTextView[][] getCellsSecondTable() {
        return fieldCreator2.getCells();
    }
}
