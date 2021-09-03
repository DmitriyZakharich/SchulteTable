package ru.schultetabledima.schultetable.advice;

import android.content.Context;
import android.content.Intent;

import java.io.Serializable;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.contracts.AdviceContract;
import ru.schultetabledima.schultetable.table.TableActivity;


public class AdvicePresenter implements AdviceContract.Presenter, Serializable {
    private Context context;
    private AdviceModel adviceModel;

    public AdvicePresenter(Context context){
        this.context = context;
        init();
        showAdvice();
    }


    private void init() {
        adviceModel = new AdviceModel(context);
    }

    private void showAdvice() {
        ((AdviceContract.View)context).showAdvice(adviceModel.getAdvice());
    }

    public void detachView(){
        context = null;
        adviceModel.detachView();
    }

    public void attachView (Context context){
        this.context = context;
        adviceModel.attachView(context);
    }

    public void restart() {
        showAdvice();
    }

    public void onClickListener(int id) {
        if (id == R.id.toTable)
            context.startActivity(new Intent (context, TableActivity.class));
    }
}
