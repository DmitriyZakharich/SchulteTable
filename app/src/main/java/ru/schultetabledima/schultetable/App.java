package ru.schultetabledima.schultetable;

import android.app.Application;
import android.util.Log;

import androidx.room.Room;

import com.google.firebase.FirebaseApp;
import com.yandex.mobile.ads.common.MobileAds;

import ru.schultetabledima.schultetable.database.AppDatabase;

public class App extends Application {

    private AppDatabase database;
    private static App appContext;
    private final String YANDEX_MOBILE_ADS_TAG = "YandexMobileAds";

    @Override
    public void onCreate() {
        FirebaseApp.initializeApp(this);

        super.onCreate();
        appContext = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
                .fallbackToDestructiveMigration()
                .build();

        MobileAds.initialize(getAppContext(), () -> Log.d(YANDEX_MOBILE_ADS_TAG, "SDK initialized"));
    }

    public static App getAppContext() {
        return appContext;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}
