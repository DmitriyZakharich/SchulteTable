package ru.schultetabledima.schultetable.screens.game.view.tablecreation;

import android.content.Context;
import android.content.res.Configuration;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import ru.schultetabledima.schultetable.App;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.screens.game.presenter.TablePresenter;
import ru.schultetabledima.schultetable.utils.Converter;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

public class TableCreator {
    private LinearLayout containerForTable;
    private PassMeLinkOnPresenter container;
    private Context context;
    private TablePresenter tablePresenter;
    private FieldCreator fieldCreator1;
    private FieldCreator fieldCreator2;
    private View viewDivider;

    public interface PassMeLinkOnPresenter{
        long getBaseChronometer();
        TablePresenter getTablePresenter();
    }

    public TableCreator(PassMeLinkOnPresenter container, Context context, TablePresenter tablePresenter) {
        this.container = container;
        this.context = context;
        this.tablePresenter = tablePresenter;
        main();
    }

    void main() {
        creatingContainerForTable();
        creatingField();
        addAnimationTransition();
    }


    private void creatingContainerForTable() {

        containerForTable = new LinearLayout(context);
        containerForTable.setLayoutParams(new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,
                ConstraintLayout.LayoutParams.MATCH_PARENT));
        containerForTable.setGravity(Gravity.CENTER);
        containerForTable.setMotionEventSplittingEnabled(false);


        if (PreferencesReader.INSTANCE.isTwoTables()) {

            viewDivider = new View(context);
            viewDivider.setBackground(AppCompatResources.getDrawable(App.getAppContext(), R.drawable.table_separator));

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
        fieldCreator1 = new FieldCreator(container, context, R.drawable.border_cell_active_color, tablePresenter);
        containerForTable.addView(fieldCreator1.getField());

        if (PreferencesReader.INSTANCE.isTwoTables()) {

            containerForTable.addView(viewDivider);

            fieldCreator2 = new FieldCreator(container, context, R.drawable.border_cell_passive_color, tablePresenter);
            containerForTable.addView(fieldCreator2.getField());
        }
    }

    private void addAnimationTransition() {
        new AnimationTransition(containerForTable);
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
