package ru.schultetabledima.schultetable.advice;

import android.content.Context;
import android.util.Log;

import java.io.Serializable;

import ru.schultetabledima.schultetable.contracts.AdviceContract;


public class AdvicePresenter implements AdviceContract.Presenter, Serializable {
    private Context context;
    String advice = "Hello world";

    public AdvicePresenter(Context context){
        this.context = context;
        init();
        Log.d("AdvicePresenterHash","Конструктор " + this.hashCode());
    }
    public AdvicePresenter(){
        init();
    }

    private void init() {
//        AdviceDatabase adviceDatabase = new AdviceDatabase(context);
//        adviceDatabase.open();
//        adviceDatabase.getCursor();

        ((AdviceContract.View)context).showAdvice(advice);
    }

    public void detachView(){
        context = null;
    }

    public void attachView (Context context){
        this.context = context;
    }


    public void restart() {
        Log.d("Serializable1","restart");

        init();
    }
}
