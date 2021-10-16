package ru.schultetabledima.schultetable.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Result.class}, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ResultDao resultDao();
}
