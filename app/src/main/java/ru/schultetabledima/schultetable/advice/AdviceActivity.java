package ru.schultetabledima.schultetable.advice;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.contracts.AdviceContract;

public class AdviceActivity extends MvpAppCompatActivity implements AdviceContract.View {

    @InjectPresenter
    AdvicePresenter advicePresenter;

    private TextView tvAdvice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);

        tvAdvice = findViewById(R.id.tvAdvice);

        Button toTable = findViewById(R.id.toTable);
        toTable.setOnClickListener(moveToActivity);
    }

    @Override
    public void showAdvice(String advice) {
        tvAdvice.setText(advice);
    }

    View.OnClickListener moveToActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            advicePresenter.onClickListener(v.getId());
        }
    };
}