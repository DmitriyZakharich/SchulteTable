package ru.schultetabledima.schultetable.screens.game;


public interface ObservationContract {

    interface CellTextSizeSubject {
        void subscribeObserver(ObservationContract.CellTextSizeObserver observer);
        void unSubscribeObserver(ObservationContract.CellTextSizeObserver observer);
        void updateNotifyObservers();
    }

    interface CellTextSizeObserver {
        void updateSubject();
        void subscribeSubject();
        void unSubscribeSubject();
    }
}
