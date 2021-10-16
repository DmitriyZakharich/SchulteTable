package ru.schultetabledima.schultetable.contracts;

import moxy.MvpView;
import moxy.viewstate.strategy.AddToEndSingleTagStrategy;
import moxy.viewstate.strategy.AddToEndStrategy;
import moxy.viewstate.strategy.StateStrategyType;

public interface EndGameDialogContract {

    @StateStrategyType(AddToEndSingleTagStrategy.class)
    interface View extends MvpView {

    }

    interface Presenter {
    }

    interface Model {
    }
}
