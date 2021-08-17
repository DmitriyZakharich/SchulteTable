package ru.schultetabledima.schultetable.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import ru.schultetabledima.schultetable.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button tableButton;
    private Button statisticsButton;
    private Button customizationButton;
    private Button adviceButton;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tableButton = (Button) findViewById(R.id.tableButton);
        statisticsButton = (Button) findViewById(R.id.statisticsButton);
        customizationButton = (Button) findViewById(R.id.customizationButton);
        adviceButton = (Button) findViewById(R.id.adviceButton);

        tableButton.setOnClickListener(this);
        statisticsButton.setOnClickListener(this);
        customizationButton.setOnClickListener(this);
        adviceButton.setOnClickListener(this);
    }


        @Override
        public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tableButton:
                intent = new Intent(MainActivity.this, TableActivity.class);
                startActivity(intent);
                break;
            case R.id.statisticsButton:
                intent = new Intent(MainActivity.this, StatisticsActivity.class);
                startActivity(intent);
                break;
            case R.id.customizationButton:
                intent = new Intent(MainActivity.this, CustomizationActivity.class);
                startActivity(intent);
                break;
                case R.id.adviceButton:
                intent = new Intent(MainActivity.this, AdviceActivity.class);
                startActivity(intent);
                break;
        }
    }

}