package ru.schultetabledima.schultetable.advice;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.Serializable;

public class AdviceDatabase {

    private Context context;
    private AdviceDatabase.AdviceDatabaseHelper adviceDatabaseHelper;
    private SQLiteDatabase db;

    public AdviceDatabase(Context context) {
        this.context = context;
    }



//    public void insert(){
//        adviceDatabaseHelper = new AdviceDatabaseHelper(activity);
//        SQLiteDatabase db = adviceDatabaseHelper.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(AdviceDatabaseHelper.COLUMN_ADVICE, tableSize);
//        long id = db.insert(AdviceDatabaseHelper.TABLE_ADVICE, null, cv);
//        adviceDatabaseHelper.close();
//    }

    public void open() {
        adviceDatabaseHelper = new AdviceDatabaseHelper(context);
        db = adviceDatabaseHelper.getReadableDatabase();
    }

    public Cursor getCursor(){
        Cursor cursor = db.query(AdviceDatabaseHelper.TABLE_ADVICE,
                null, null, null, null, null, null);
        return cursor;
    }

    public void closeDataBase(){
        adviceDatabaseHelper.close();
    }


    static class AdviceDatabaseHelper extends SQLiteOpenHelper implements Serializable {

        private static final String DATABASE_NAME = "advicedatabase.db"; // название бд
        private static final int DB_VERSION = 1; // версия базы данных
        static final String TABLE_ADVICE = "advice"; // название таблицы в бд
        // названия столбцов
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_ADVICE = "advice";

        public AdviceDatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_ADVICE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_ADVICE + " TEXT);");
            db.execSQL("INSERT INTO " + TABLE_ADVICE + " (" + COLUMN_ADVICE + ") " + "VALUES( " + "'Совет №1');");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADVICE);
            onCreate(db);
        }
    }




}
