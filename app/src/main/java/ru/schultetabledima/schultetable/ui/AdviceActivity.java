package ru.schultetabledima.schultetable.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.Serializable;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.contracts.AdviceContract;
import ru.schultetabledima.schultetable.presenters.AdvicePresenter;

public class AdviceActivity extends AppCompatActivity implements AdviceContract.View, Serializable {

    private AdvicePresenter advicePresenter;
    private final String KEY_SERIALIZABLE_ADVICE_PRESENTER = "key_serializable_advice_presenter";
    private Button toTable;


    TextView tvAdvice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
        tvAdvice = findViewById(R.id.tvAdvice);
        if (savedInstanceState == null)
            advicePresenter = new AdvicePresenter(this);
        toTable = findViewById(R.id.toTable);
        toTable.setOnClickListener(moveToActivity);
        Log.d("Serializable1","onCreate");

    }

    @Override
    public void showAdvice(String advice) {
        tvAdvice.setText(advice);
        Log.d("Serializable1","showAdvice");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        advicePresenter.detachView();
        Log.d("AdvicePresenterHash","onSaveInstanceState  " + advicePresenter.hashCode());

        outState.putSerializable(KEY_SERIALIZABLE_ADVICE_PRESENTER, advicePresenter);
        Log.d("Serializable1","onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        advicePresenter = (AdvicePresenter)savedInstanceState.getSerializable(KEY_SERIALIZABLE_ADVICE_PRESENTER);
        advicePresenter.attachView(this);
        advicePresenter.restart();
        Log.d("AdvicePresenterHash","onRestoreInstanceState  " + advicePresenter.hashCode());

        Log.d("Serializable1","onRestoreInstanceState");
    }

    View.OnClickListener moveToActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(AdviceActivity.this, TableActivity.class));
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Serializable1","onResume");

    }
}