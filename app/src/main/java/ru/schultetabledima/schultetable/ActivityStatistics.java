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

    private static final int MOVIE_LOADER = 0;




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
        cursor.moveToFirst();
        Log.d("RecyclerView","onCreate");

//        if (cursor.moveToFirst()) {
//            int idColumnIndex = cursor.getColumnIndex("_id");
//            int sizeFieldColumnIndex = cursor.getColumnIndex("size_field");
//            int timeColumnIndex = cursor.getColumnIndex("time");
//            int dateColumnIndex = cursor.getColumnIndex("date");
//
//            do {
//                textView.setText("" + textView.getText() + cursor.getInt(idColumnIndex) + "  "
//                                + cursor.getString(sizeFieldColumnIndex) + "  "
//                                + cursor.getString(timeColumnIndex) + "  "
//                                + cursor.getString(dateColumnIndex) + "\n");
//            } while (cursor.moveToNext());
//        } else
//            textView.setText("0 rows");

        statisticsAdapter = new StatisticsAdapter(this);
        recyclerView.setAdapter(statisticsAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Initialize Movie Loader
        getSupportLoaderManager().initLoader(MOVIE_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch(id) {
            case MOVIE_LOADER:
                return new CursorLoader(
                        this,
                        MovieContract.MovieEntry.CONTENT_URI,
                        statisticsAdapter.MOVIE_COLUMNS,
                        null,
                        null,
                        null
                );
            default:
                throw new UnsupportedOperationException("Unknown loader id: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        switch(loader.getId()) {
            case MOVIE_LOADER:
                statisticsAdapter.swapCursor(data);
                break;
            default:
                throw new UnsupportedOperationException("Unknown loader id: " + loader.getId());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        switch(loader.getId()) {
            case MOVIE_LOADER:
                statisticsAdapter.swapCursor(null);
                break;
            default:
                throw new UnsupportedOperationException("Unknown loader id: " + loader.getId());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        dbController.closeDataBase();
    }
}





