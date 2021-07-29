package ru.schultetabledima.schultetable;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityStatistics extends AppCompatActivity {

    DatabaseController dbController;
    Cursor cursor;
    StatisticsAdapter statisticsAdapter;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        TextView textView = (TextView)findViewById(R.id.textView);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview);


        dbController = new DatabaseController(this);
        cursor =  dbController.getCursor();


        statisticsAdapter = new StatisticsAdapter(this, cursor);
        recyclerView.setAdapter(statisticsAdapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        dbController.closeDataBase();
    }
}





