package ru.schultetabledima.schultetable.contracts;

import android.animation.LayoutTransition;
import android.widget.LinearLayout;

import androidx.navigation.NavOptions;

import java.util.List;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.AddToEndStrategy;
import moxy.viewstate.strategy.OneExecutionStateStrategy;
import moxy.viewstate.strategy.SingleStateStrategy;
import moxy.viewstate.strategy.SkipStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import ru.schultetabledima.schultetable.table.model.DataCell;

public interface TableContract {
    @StateStrategyType(AddToEndSingleStrategy.class)
    interface View extends MvpView {

        void showHideMenu(int visibility, int visibilityHint, int imageResource, LinearLayout.LayoutParams layoutParams);

        void setAnimationToolbar(LayoutTransition animation);

        void setMoveHint(int nextMoveFirstTable);

        void setMoveHint(char nextMoveFirstTable);

        void setBaseChronometer(long l, boolean isDialogueShow);

        void showDialogueFragment(boolean needToShow);

        void showErrorDialogueFragment(boolean needToShow);

        @StateStrategyType(AddToEndStrategy.class)
        void setTableData(List<DataCell> dataCellsFirstTable, List<DataCell> dataCellsSecondTable);

        void setTableColor(int backgroundResourcesFirstTable, int backgroundResourcesSecondTable);

        @StateStrategyType(OneExecutionStateStrategy.class)
        void showToast(int errorMessage, int lengthToast);

        void stopStartChronometer(boolean startIt);

        @StateStrategyType(SkipStrategy.class)
        void showPopupMenu();

        @StateStrategyType(SkipStrategy.class)
        void setCellColor(int id, int cellColor);

        @StateStrategyType(SkipStrategy.class)
        void setBackgroundResources(int cellId, int backgroundResources);

        void createTable();

        @StateStrategyType(SkipStrategy.class)
        void moveFragment(int idActionNavigation, NavOptions navOptions);

        //Clearing the command queue to getViewState
        @StateStrategyType(SingleStateStrategy.class)
        void clearingTheCommandQueue();
    }

    interface Presenter {
    }

    interface Model {
        interface ValuesCreator{
            List<DataCell> getDataCells();

            List<Integer> getListIdsForCheck();

            int getFirstValue();
        }
    }
}
