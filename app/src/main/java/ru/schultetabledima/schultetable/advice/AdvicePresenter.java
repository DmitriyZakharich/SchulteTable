package ru.schultetabledima.schultetable.advice;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.contracts.AdviceContract;

@InjectViewState
public class AdvicePresenter extends MvpPresenter<AdviceContract.View> implements AdviceContract.Presenter {

    private AdviceContract.Model adviceModel;
    private List<Integer> adviceResource;


    public AdvicePresenter() {
        init();
        pushAdviceToView();
    }


    private void init() {
        adviceModel = new AdviceModel();
        adviceResource = new ArrayList<>(3);

        if (Locale.getDefault().getLanguage().equals("ru")) {
            adviceResource.add(R.raw.advice1_ru);
            adviceResource.add(R.raw.advice2_ru);
            adviceResource.add(R.raw.advice3_ru);
        } else {
            adviceResource.add(R.raw.advice1_en);
            adviceResource.add(R.raw.advice2_en);
            adviceResource.add(R.raw.advice3_en);
        }
    }

    private void pushAdviceToView() {
        int count = 0;
        for (int id : adviceResource) {
            getViewState().showAdvice(count++, adviceModel.getAdvice(id));
        }
    }
}
