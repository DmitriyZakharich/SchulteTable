package ru.schultetabledima.schultetable.advice;

import java.util.ArrayList;
import java.util.List;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.contracts.AdviceContract;

@InjectViewState
public class AdvicePresenter extends MvpPresenter<AdviceContract.View> {

    private AdviceModel adviceModel;
    private List<Integer> adviceResource = new ArrayList<>(2);


    public AdvicePresenter() {
        init();
        showAdvice();
    }

    private void init() {
        adviceModel = new AdviceModel();

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
