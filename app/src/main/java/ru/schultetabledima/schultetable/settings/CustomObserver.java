package ru.schultetabledima.schultetable.settings;

public interface CustomObserver {
    void updateSubjectNumbersFragment();
    void updateSubjectLettersFragment();

    void subscribeNumbersFragment();
    void subscribeLettersFragment();
    void unSubscribeLettersFragment();
    void unSubscribeNumbersFragment();
}
