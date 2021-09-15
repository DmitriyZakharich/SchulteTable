package ru.schultetabledima.schultetable.statistic;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.recyclerview.widget.RecyclerView;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.contracts.StatisticsContract;
import ru.schultetabledima.schultetable.statistic.database.StatisticAdapter;

public class StatisticsActivity extends MvpAppCompatActivity implements StatisticsContract.View, AdapterView.OnItemSelectedListener {

    @InjectPresenter
    StatisticsPresenter statisticsPresenter;

    private Spinner selectPlayedSizes;
    private RecyclerView recyclerView;
    private Spinner selectQuantityTables, selectValueType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        recyclerView = findViewById(R.id.recyclerview);
        selectQuantityTables = findViewById(R.id.spinnerQuantityTables);
        selectValueType = findViewById(R.id.spinnerValueType);
        selectPlayedSizes = findViewById(R.id.spinnerPlayedSizes);


        selectQuantityTables.setOnItemSelectedListener(this);
        selectValueType.setOnItemSelectedListener(this);
        selectPlayedSizes.setOnItemSelectedListener(this);
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long idSpinner) {
        statisticsPresenter.spinnerItemSelected(parent.getId(), position, parent.getSelectedItem().toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}


    @Override
    public void setRecyclerViewAdapter(StatisticAdapter statisticAdapter){
        recyclerView.setAdapter(statisticAdapter);
    }

    @Override
    public void setQuantityTablesAdapter (ArrayAdapter<String> adapterQuantityTables){
        selectQuantityTables.setAdapter(adapterQuantityTables);
    }

    @Override
    public void setValueTypeAdapter (ArrayAdapter<String> adapterValueType){
        selectValueType.setAdapter(adapterValueType);
    }

    @Override
    public void setPlayedSizesAdapter(ArrayAdapter<String> adapterPlayedSizes) {
        selectPlayedSizes.setAdapter(adapterPlayedSizes);
    }
}





