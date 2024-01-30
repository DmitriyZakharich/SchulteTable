package ru.schultetabledima.schultetable.contracts;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface AdviceContract {

    interface View extends MvpView {
        @StateStrategyType(AddToEndStrategy.class)
        void showAdvice(int index, String advice);
    }

    interface Presenter {}

    interface Model {
        String getAdvice(int idResource);
    }
}