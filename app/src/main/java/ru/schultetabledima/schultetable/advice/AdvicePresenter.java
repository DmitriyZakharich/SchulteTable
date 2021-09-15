package ru.schultetabledima.schultetable.advice;

import android.content.Intent;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.schultetabledima.schultetable.MyApplication;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.contracts.AdviceContract;
import ru.schultetabledima.schultetable.table.TableActivity;

@InjectViewState
public class AdvicePresenter extends MvpPresenter<AdviceContract.View> implements AdviceContract.Presenter {

    private AdviceModel adviceModel;
    private Intent intent;


    public AdvicePresenter(){
        init();
        showAdvice();
    }

    private void init() {
        adviceModel = new AdviceModel();
    }

    private void showAdvice() {
        getViewState().showAdvice(adviceModel.getAdvice());
    }

    public void onClickListener(int id) {
        if (id == R.id.toTable)
            intent = new Intent (MyApplication.getContext(), TableActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MyApplication.getContext().startActivity(intent);
    }
}
