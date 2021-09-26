package ru.schultetabledima.schultetable;

import android.app.Application;

import androidx.room.Room;

import ru.schultetabledima.schultetable.database.AppDatabase;

public class App extends Application {

    public static App instance;
    private AppDatabase database;

    private static App context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
                .fallbackToDestructiveMigration()
                .build();
    }

    public static App getContext() {
        return context;
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
