package ru.schultetabledima.schultetable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ActivityStatistics extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private Database dbController;
    private StatisticAdapter statisticAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        TextView textView = (TextView)findViewById(R.id.textView);
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview);

        dbController = new Database(this);
        dbController.open();

        statisticAdapter = new StatisticAdapter(null, this);
        recyclerView.setAdapter(statisticAdapter);

        //Запуск загрузки курсора с помощью MyCursorLoader
        LoaderManager.getInstance(this).initLoader(0, null, this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbController.closeDataBase();
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return new MyCursorLoader(this, dbController);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        statisticAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
    }

    static class MyCursorLoader extends CursorLoader{
        Database db;

        public MyCursorLoader(@NonNull Context context, Database db) {
            super(context);
            this.db = db;
        }

        @Override
        public Cursor loadInBackground() {
            Cursor cursor = db.getCursor();
            return cursor;
        }
    }


}





