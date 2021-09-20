package ru.schultetabledima.schultetable.advice;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

        Button toTable = findViewById(R.id.toTable);
        toTable.setOnClickListener(moveToActivity);
    }

    @Override
    public void showAdvice(int index, String advice) {
        textViewList.get(index).setText(advice);
    }

    View.OnClickListener moveToActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            advicePresenter.onClickListener(v.getId());
        }
    };
}