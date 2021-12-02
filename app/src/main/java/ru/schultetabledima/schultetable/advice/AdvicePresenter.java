package ru.schultetabledima.schultetable.advice;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.contracts.AdviceContract;

@InjectViewState
public class AdvicePresenter extends MvpPresenter<AdviceContract.View> implements AdviceContract.Presenter {

    private final AdviceContract.Model adviceModel;
    private List<Integer> adviceResource;


    public AdvicePresenter(AdviceModel adviceModel) {
        this.adviceModel = adviceModel;
        init();
        pushAdviceToView();
    }


    private void init() {
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

//    public void setModel(AdviceModel adviceModel) {
//        this.adviceModel = adviceModel;
//    }

    private void pushAdviceToView() {
        int count = 0;
        for (int id : adviceResource) {
            getViewState().showAdvice(count++, adviceModel.getAdvice(id));
        }
    }
}
