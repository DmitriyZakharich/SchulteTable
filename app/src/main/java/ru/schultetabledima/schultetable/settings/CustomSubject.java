package ru.schultetabledima.schultetable.settings;


public interface CustomSubject {

    void subscribeObserver(CustomObserver customObserver);
    void unSubscribeObserver(CustomObserver customObserver);
    void updateNotifyObservers();
}
