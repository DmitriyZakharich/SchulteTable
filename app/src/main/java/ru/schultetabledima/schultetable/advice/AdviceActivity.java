package ru.schultetabledima.schultetable.advice;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.contracts.AdviceContract;

public class AdviceActivity extends MvpAppCompatActivity implements AdviceContract.View {

    @InjectPresenter
    AdvicePresenter advicePresenter;
    private List<TextView> textViewList = new ArrayList<>(2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);

        textViewList.add(findViewById(R.id.tvAdvice1));
        textViewList.add(findViewById(R.id.tvAdvice2));
    }

    @Override
    public void showAdvice(int index, String advice) {
        textViewList.get(index).setText(advice);
    }
}