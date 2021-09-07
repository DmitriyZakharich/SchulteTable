package ru.schultetabledima.schultetable.statistic.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

public class DatabaseAdapter {
    private Context context;
    private String time;
    private int quantityTables;
    private String tableSize;
    private String valueType;
    private DatabaseHelper databaseHelper;
    private String currentDate;
    private SQLiteDatabase db;
    private String language;
    private PreferencesReader settings;



    public DatabaseAdapter(Context context, String time, String tableSize, String currentDate) {
        this.context = context;
        this.time = time;
        this.tableSize = tableSize;
        this.currentDate = currentDate;;
        init();
    }

    public DatabaseAdapter(Context context) {
        this.context = context;
        settings = new PreferencesReader(context);
        init();
    }


    private void init() {
        valueType = settings.getIsLetters() ? context.getString(R.string.valueTypeLetters) : context.getString(R.string.valueTypeNumbers);
        if (settings.getIsLetters()){
            language = settings.getIsEnglish() ? context.getString(R.string.languageEnglish) : context.getString(R.string.languageRussian);
        }else{
            language = null;
        }
        quantityTables = settings.getIsTwoTables() ? 2 : 1;
    }

    public void insert(){
        databaseHelper = new DatabaseHelper(context);
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
        databaseHelper = new DatabaseHelper(context);
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

        if (valueType.equals(context.getString(R.string.valueTypeLetters)) ||
                valueType.equals(context.getString(R.string.valueTypeNumbers))){

            if(insertAlready)
                stringBufferSelection.append(" AND ");

            stringBufferSelection.append(DatabaseHelper.COLUMN_VALUE_TYPE + " = ?");
            arrayListSelectionArgs.add(valueType);
            insertAlready = true;
        }

        if (valueLanguage.equals(context.getString(R.string.languageEnglish)) ||
                valueLanguage.equals(context.getString(R.string.languageRussian))){

            if(insertAlready)
                stringBufferSelection.append(" AND ");

            stringBufferSelection.append(DatabaseHelper.COLUMN_LANGUAGE + " = ?");
            arrayListSelectionArgs.add(valueLanguage);
            insertAlready = true;
        }

        if (!playedSizes.equals(context.getString(R.string.allSize))) {
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

