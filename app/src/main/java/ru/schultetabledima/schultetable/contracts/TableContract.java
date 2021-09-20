package ru.schultetabledima.schultetable.contracts;

import android.animation.LayoutTransition;
import android.widget.LinearLayout;

import androidx.appcompat.app.AlertDialog;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.AddToEndStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import ru.schultetabledima.schultetable.table.EndGameDialogueFragment;

public interface TableContract {
    @StateStrategyType(AddToEndSingleStrategy.class)
    interface View extends MvpView {


        void showTable(LinearLayout llTable);

        void showHideMenu(int visibility, int visibilityHint, int imageResource, LinearLayout.LayoutParams layoutParams);

        void addAnimationToolbar(LayoutTransition animation);

        void setMoveHint(int nextMoveFirstTable);

        void setMoveHint(char nextMoveFirstTable);

        void stopChronometer();

        void startChronometer();

        void removeTable();

        void setBaseChronometer(long l);

        void showDialogueFragment(EndGameDialogueFragment dialogueFragment);


//        void setAlertDialog(AlertDialog alertDialog);
    }

    interface Presenter{

    }


    interface Model{

    }


}
