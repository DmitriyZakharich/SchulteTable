package ru.schultetabledima.schultetable.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.advice.AdviceActivity;
import ru.schultetabledima.schultetable.donation.DonationActivity;
import ru.schultetabledima.schultetable.settings.SettingsActivity;
import ru.schultetabledima.schultetable.statistic.StatisticsActivity;
import ru.schultetabledima.schultetable.table.TableActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tableButton).setOnClickListener(this);
        findViewById(R.id.statisticsButton).setOnClickListener(this);
        findViewById(R.id.customizationButton).setOnClickListener(this);
        findViewById(R.id.adviceButton).setOnClickListener(this);
        findViewById(R.id.donationButton).setOnClickListener(this);
    }

        @Override
        public void onClick(View v) {
            int id = v.getId();

            if (id == R.id.tableButton) {
                startActivity(new Intent(MainActivity.this, TableActivity.class));

            } else if (id == R.id.statisticsButton) {
                startActivity(new Intent(MainActivity.this, StatisticsActivity.class));

            } else if (id == R.id.customizationButton) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));

            } else if (id == R.id.adviceButton) {
                startActivity(new Intent(MainActivity.this, AdviceActivity.class));

            }else if (id == R.id.donationButton) {
                startActivity(new Intent(MainActivity.this, DonationActivity.class));
            }
    }
}