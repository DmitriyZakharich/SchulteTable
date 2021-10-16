package ru.schultetabledima.schultetable.settings;

public interface ObservationContract {

    interface CustomSubject {

        void subscribeObserver(CustomObserver customObserver);
        void unSubscribeObserver(CustomObserver customObserver);
        void updateNotifyObservers();
    }

    interface CustomObserver {
        void updateSubjectNumbersFragment();
        void updateSubjectLettersFragment();

        void subscribeNumbersFragment();
        void subscribeLettersFragment();
        void unSubscribeLettersFragment();
        void unSubscribeNumbersFragment();
    }

}
