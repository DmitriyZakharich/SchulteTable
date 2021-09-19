package ru.schultetabledima.schultetable.advice;

import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
        for (int id : adviceResource){
            Log.d("tag1", "showAdvice count " + count);

            getViewState().showAdvice(count++, adviceModel.getAdvice(id));

        }
    }

    public void onClickListener(int id) {
        if (id == R.id.toTable)
            intent = new Intent(MyApplication.getContext(), TableActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getContext().startActivity(intent);
    }
}
