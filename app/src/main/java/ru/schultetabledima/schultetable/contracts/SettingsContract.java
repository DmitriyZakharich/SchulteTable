package ru.schultetabledima.schultetable.contracts;

import moxy.MvpView;
import ru.schultetabledima.schultetable.statistic.MyAdapter;

public interface SettingsContract {

    interface View extends MvpView {

        void switchTouchCellsSetChecked(boolean isChecked);

        void switchAnimationSetChecked(boolean isChecked);

        void switchTwoTablesSetChecked(boolean isChecked);

        void customizationSwitchMoveHint(boolean isEnabled, boolean isChecked);

        void setViewPagerCurrentItem(int index);
    }

    interface Presenter {

        void onTabSelectedListener(int position);

        void onClickListenerSwitch(int id, boolean isChecked);
    }

    interface ModelPreferenceReader {
        boolean getIsTouchCells();
        int getColumnsOfTable();
        int getRowsOfTable();
        int getColumnsOfTableNumbers();
        int getRowsOfTableNumbers();
        int getColumnsOfTableLetters();
        int getRowsOfTableLetters();
        boolean getIsLetters();
        boolean getIsTwoTables();
        boolean getIsEnglish();
        boolean getIsMoveHint();
        boolean getIsAnim();
    }


}
