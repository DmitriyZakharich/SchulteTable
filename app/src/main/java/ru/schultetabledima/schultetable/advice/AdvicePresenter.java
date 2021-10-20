package ru.schultetabledima.schultetable.advice;

import java.util.ArrayList;
import java.util.List;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.contracts.AdviceContract;

@InjectViewState
public class AdvicePresenter extends MvpPresenter<AdviceContract.View> implements AdviceContract.Presenter {

    private AdviceModel adviceModel;
    private List<Integer> adviceResource;

    public AdvicePresenter() {
        init();
        showAdvice();
    }

//    public void start() {
//
//
//    }

    private void init() {
        adviceModel = new AdviceModel();
        adviceResource = new ArrayList<>(2);

        adviceResource.add(R.raw.advice1);
        adviceResource.add(R.raw.advice2);
    }

    private void showAdvice() {
        int count = 0;
        for (int id : adviceResource) {
            getViewState().showAdvice(count++, adviceModel.getAdvice(id));

        }
    }
}
