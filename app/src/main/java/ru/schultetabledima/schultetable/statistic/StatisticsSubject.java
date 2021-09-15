package ru.schultetabledima.schultetable.statistic;

import android.database.Cursor;

import ru.schultetabledima.schultetable.settings.CustomObserver;

public interface StatisticsSubject {
    void subscribeObserver(StatisticsPresenter statisticsPresenter);
    void unSubscribeObserver(StatisticsPresenter statisticsPresenter);
    void updateNotifyObservers(Cursor cursor);


}
