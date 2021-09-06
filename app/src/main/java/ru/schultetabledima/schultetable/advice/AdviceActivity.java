package ru.schultetabledima.schultetable.advice;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ActionMenuView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.contracts.AdviceContract;
import ru.schultetabledima.schultetable.table.TableActivity;

public class AdviceActivity extends AppCompatActivity implements AdviceContract.View {

    private AdvicePresenter advicePresenter;
    private final String KEY_SERIALIZABLE_ADVICE_PRESENTER = "key_serializable_advice_presenter";


    TextView tvAdvice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);

        tvAdvice = findViewById(R.id.tvAdvice);

        if (savedInstanceState == null)
            advicePresenter = new AdvicePresenter(this);

        Button toTable = findViewById(R.id.toTable);
        toTable.setOnClickListener(moveToActivity);






    }

    @Override
    public void showAdvice(String advice) {
        tvAdvice.setText(advice);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        advicePresenter.detachView();
        outState.putSerializable(KEY_SERIALIZABLE_ADVICE_PRESENTER, advicePresenter);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        advicePresenter = (AdvicePresenter)savedInstanceState.getSerializable(KEY_SERIALIZABLE_ADVICE_PRESENTER);
        advicePresenter.attachView(this);
        advicePresenter.restart();
    }

    View.OnClickListener moveToActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            advicePresenter.onClickListener(v.getId());
        }
    };
}