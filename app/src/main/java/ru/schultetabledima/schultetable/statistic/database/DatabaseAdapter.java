package ru.schultetabledima.schultetable.statistic.database;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.settings.SettingsActivity;
import ru.schultetabledima.schultetable.utils.SettingsReader;

public class DatabaseAdapter {
    private Activity activity;
    private String time;
    private int quantityTables;
    private String tableSize;
    private String valueType;
    private DatabaseHelper databaseHelper;
    private String currentDate;
    private SQLiteDatabase db;
    private String language;
    private boolean isLetters, isTwoTables, isEnglish;


    public DatabaseAdapter(Activity activity, String time, String tableSize, String currentDate) {
        this.activity = activity;
        this.time = time;
        this.tableSize = tableSize;
        this.currentDate = currentDate;;
        readSharedPreferences();
        init();
    }

    public DatabaseAdapter(Activity activity) {
        this.activity = activity;
        readSharedPreferences();
        init();
    }

    private void readSharedPreferences() {
        SharedPreferences settings = activity.getSharedPreferences(SettingsActivity.getAppPreferences(), MODE_PRIVATE);
        isLetters = settings.getBoolean(SettingsActivity.getKeyNumbersOrLetters(), false);
        isTwoTables = settings.getBoolean(SettingsActivity.getKeyTwoTables(), false);
        isEnglish = settings.getBoolean(SettingsActivity.getKeyRussianOrEnglish(), false);
    }

    private void init() {
        valueType = isLetters ? activity.getString(R.string.valueTypeLetters) : activity.getString(R.string.valueTypeNumbers);
        if (isLetters){
            language = isEnglish ? activity.getString(R.string.languageEnglish) : activity.getString(R.string.languageRussian);
        }else{
            language = null;
        }
        quantityTables = isTwoTables ? 2 : 1;
    }

    public void insert(){
        databaseHelper = new DatabaseHelper(activity);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_SIZE_FIELD, tableSize);
        cv.put(DatabaseHelper.COLUMN_TIME, time);
        cv.put(DatabaseHelper.COLUMN_DATE, currentDate);
        cv.put(DatabaseHelper.COLUMN_QUANTITY_TABLES, quantityTables);
        cv.put(DatabaseHelper.COLUMN_VALUE_TYPE, valueType);
        cv.put(DatabaseHelper.COLUMN_LANGUAGE, language);
        db.insert(DatabaseHelper.TABLE_RESULTS, null, cv);
        databaseHelper.close();
    }

    public void open() {
        databaseHelper = new DatabaseHelper(activity);
        db = databaseHelper.getReadableDatabase();
    }


    public Cursor getCursorPlayedSizes(){
        String[] columns = {DatabaseHelper.COLUMN_SIZE_FIELD};

        Cursor cursor = db.query(true, DatabaseHelper.TABLE_RESULTS,
                columns, null, null, null, null,
                DatabaseHelper.COLUMN_ID + " DESC", null);
        return cursor;
    }

    public Cursor getCursor(int quantityTables, String valueType, String valueLanguage, String playedSizes){

        StringBuffer stringBufferSelection = new StringBuffer();
        ArrayList <String> arrayListSelectionArgs = new ArrayList<>();

        boolean insertAlready = false;

        if (quantityTables == 1 || quantityTables == 2){
            stringBufferSelection.append("" + DatabaseHelper.COLUMN_QUANTITY_TABLES + " = ?");
            arrayListSelectionArgs.add(String.valueOf(quantityTables));
            insertAlready = true;
        }

        if (valueType.equals(activity.getString(R.string.valueTypeLetters)) ||
                valueType.equals(activity.getString(R.string.valueTypeNumbers))){

            if(insertAlready)
                stringBufferSelection.append(" AND ");

            stringBufferSelection.append(DatabaseHelper.COLUMN_VALUE_TYPE + " = ?");
            arrayListSelectionArgs.add(valueType);
            insertAlready = true;
        }

        if (valueLanguage.equals(activity.getString(R.string.languageEnglish)) ||
                valueLanguage.equals(activity.getString(R.string.languageRussian))){

            if(insertAlready)
                stringBufferSelection.append(" AND ");

            stringBufferSelection.append(DatabaseHelper.COLUMN_LANGUAGE + " = ?");
            arrayListSelectionArgs.add(valueLanguage);
            insertAlready = true;
        }

        if (!playedSizes.equals(activity.getString(R.string.allSize))) {
            if(insertAlready)
                stringBufferSelection.append(" AND ");

            stringBufferSelection.append(DatabaseHelper.COLUMN_SIZE_FIELD + " = ?");
            arrayListSelectionArgs.add(playedSizes);
            insertAlready = true;
        }

        String selection = stringBufferSelection.toString();
        String[] selectionArgs = arrayListSelectionArgs.toArray(new String[0]);
        String[] columns = null;
        String orderBy = DatabaseHelper.COLUMN_ID + " DESC";

        Cursor cursor = db.query(true, DatabaseHelper.TABLE_RESULTS,
                columns, selection, selectionArgs, null, null, orderBy, null);
        return cursor;
    }

    public void closeDataBase(){
        databaseHelper.close();
    }


    static public class DatabaseHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "game_statistics.db"; // название бд
        private static final int DB_VERSION = 1; // версия базы данных
        static final String TABLE_RESULTS = "results"; // название таблицы в бд
        // названия столбцов
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_SIZE_FIELD = "size_field";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_QUANTITY_TABLES = "quantity_tables";
        public static final String COLUMN_VALUE_TYPE = "value_type";
        public static final String COLUMN_LANGUAGE = "language";

        public DatabaseHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE " + TABLE_RESULTS + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_DATE + " TEXT, "
                    + COLUMN_SIZE_FIELD + " TEXT, "
                    + COLUMN_QUANTITY_TABLES + " INTEGER, "
                    + COLUMN_VALUE_TYPE + " TEXT, "
                    + COLUMN_LANGUAGE + " TEXT, "
                    + COLUMN_TIME + " TEXT);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESULTS);
            onCreate(db);
        }
    }
}

