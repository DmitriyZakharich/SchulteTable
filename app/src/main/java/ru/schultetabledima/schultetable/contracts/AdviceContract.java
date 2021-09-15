package ru.schultetabledima.schultetable.contracts;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface AdviceContract {

    interface View extends MvpView {
        @StateStrategyType(AddToEndSingleStrategy.class)
        void showAdvice(String advice);
    }
    interface Presenter{
        void onClickListener(int id);

    }
    interface Model{
        String getAdvice();
    }
}
