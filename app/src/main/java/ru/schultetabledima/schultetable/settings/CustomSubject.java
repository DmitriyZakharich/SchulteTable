package ru.schultetabledima.schultetable.settings;


public interface CustomSubject {

    public void subscribeObserver(CustomObserver customObserver);
    public void unSubscribeObserver(CustomObserver customObserver);
    public void updateNotifyObservers();
}
