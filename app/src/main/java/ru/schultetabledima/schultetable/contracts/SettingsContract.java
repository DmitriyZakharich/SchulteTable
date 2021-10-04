package ru.schultetabledima.schultetable.contracts;

import moxy.MvpView;
import ru.schultetabledima.schultetable.statistic.MyAdapter;

public interface SettingsContract {

    interface View extends MvpView {

        void switchTouchCellsSetChecked(boolean isChecked);

        void switchAnimationSetChecked(boolean isChecked);

        void switchTwoTablesSetChecked(boolean isChecked);

        void customizationSwitchMoveHint(boolean isEnabled, boolean isChecked);

        void setViewPagerAdapter(MyAdapter pageAdapter);


        void setViewPagerCurrentItem(int index);
    }

    interface Presenter {

    }

    interface Model {

    }
}
