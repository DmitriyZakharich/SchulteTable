package ru.schultetabledima.schultetable.contracts;

import android.animation.LayoutTransition;
import android.util.ArrayMap;
import android.widget.LinearLayout;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.AddToEndStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import ru.schultetabledima.schultetable.table.EndGameDialogueFragment;
import ru.schultetabledima.schultetable.table.tablecreation.DataCell;

public interface TableContract {
    @StateStrategyType(AddToEndSingleStrategy.class)
    interface View extends MvpView {

        void showHideMenu(int visibility, int visibilityHint, int imageResource, LinearLayout.LayoutParams layoutParams);

        void setAnimationToolbar(LayoutTransition animation);

        void setMoveHint(int nextMoveFirstTable);

        void setMoveHint(char nextMoveFirstTable);

//        void stopChronometer();
//
//        @StateStrategyType(SkipStrategy.class)
//        void startChronometer();

        void removeTable();

        void setBaseChronometer(long l, boolean isDialogueShow);

        void showDialogueFragment(boolean isShow);

        @StateStrategyType(AddToEndStrategy.class)
        void setTableData(List<DataCell> dataCellsFirstTable, List<DataCell> dataCellsSecondTable);

        void setTableColor(int table_id, int color);

        @StateStrategyType(OneExecutionStateStrategy.class)
        void showToastWrongTable(int wrongTable);

        void stopStartChronometer(boolean startIt);
    }

    interface Presenter{

    }


    interface Model{

    }
}
