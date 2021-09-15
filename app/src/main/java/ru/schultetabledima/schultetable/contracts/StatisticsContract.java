package ru.schultetabledima.schultetable.contracts;

import android.widget.ArrayAdapter;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndStrategy;
import moxy.viewstate.strategy.StateStrategyType;
import ru.schultetabledima.schultetable.statistic.database.StatisticAdapter;

public interface StatisticsContract {

    @StateStrategyType(AddToEndStrategy.class)
    interface View extends MvpView{

        void setRecyclerViewAdapter(StatisticAdapter statisticAdapter);

        void setQuantityTablesAdapter(ArrayAdapter<String> adapterQuantityTables);

        void setValueTypeAdapter(ArrayAdapter<String> adapterValueType);

        void setPlayedSizesAdapter(ArrayAdapter<String> adapterPlayedSizes);
    }

    interface Presenter {

        void spinnerItemSelected(int parentId, int position, String itemText);
    }

    interface Model {

    }
}
