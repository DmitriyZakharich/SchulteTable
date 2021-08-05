package ru.schultetabledima.schultetable.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database {
    Activity activity;
    String time;
    int columnsOfTable;
    int stringsOfTable;
    private DatabaseHelper databaseHelper;
    String currentDate;
    SQLiteDatabase db;

    public Database(Activity activity, String time, int columnsOfTable, int stringsOfTable, String currentDate) {
        this.activity = activity;
        this.time = time;
        this.columnsOfTable = columnsOfTable;
        this.stringsOfTable = stringsOfTable;
        this.currentDate = currentDate;
    }

    public Database(Activity activity) {
        this.activity = activity;
    }

    public void insert(){
        databaseHelper = new DatabaseHelper(activity);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_SIZE_FIELD, "" + columnsOfTable + "x" + stringsOfTable);
        cv.put(DatabaseHelper.COLUMN_TIME, time);
        cv.put(DatabaseHelper.COLUMN_DATE, currentDate);
        long id = db.insert(DatabaseHelper.TABLE_RESULTS, null, cv);
        databaseHelper.close();
    }

    public void open() {
        databaseHelper = new DatabaseHelper(activity);
        db = databaseHelper.getWritableDatabase();
    }

    public Cursor getCursor(){
        Cursor cursor = db.query(DatabaseHelper.TABLE_RESULTS,
                null, null, null, null, null, DatabaseHelper.COLUMN_ID + " DESC");
        return cursor;
    }

    public void closeDataBase(){
        databaseHelper.close();
    }


    static class DatabaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "gamestatistics.db"; // название бд
        private static final int DB_VERSION = 1; // версия базы данных
        static final String TABLE_RESULTS = "results"; // название таблицы в бд
        // названия столбцов
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_SIZE_FIELD = "size_field";
        public static final String COLUMN_TIME = "time";

        public DatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_RESULTS + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_DATE + " TEXT, "
                    + COLUMN_SIZE_FIELD + " TEXT, "
                    + COLUMN_TIME + " TEXT);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULTS);
            onCreate(db);
        }
    }

}

