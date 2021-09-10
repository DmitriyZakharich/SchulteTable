package ru.schultetabledima.schultetable.settings;

public interface CustomObserver {
    public void updateSubjectNumbersFragment();
    public void updateSubjectLettersFragment();

    public void subscribeNumbersFragment();
    public void subscribeLettersFragment();
    public void unSubscribeLettersFragment();
    public void unSubscribeNumbersFragment();
}
