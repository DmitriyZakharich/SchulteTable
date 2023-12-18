package ru.schultetabledima.schultetable.screens.game.presenter;

import android.content.SharedPreferences;

public class DataForMenuButtonsHandler {
    private boolean isMenuShow;
    private String KEY_MENU_VISIBILITY;
    private SharedPreferences sharedPreferencesMenu;

    public boolean getIsMenuShow() {
        return isMenuShow;
    }

    public void setMenuShow(boolean menuShow) {
        isMenuShow = menuShow;
    }

    public String getKEY_MENU_VISIBILITY() {
        return KEY_MENU_VISIBILITY;
    }

    public void setKEY_MENU_VISIBILITY(String KEY_MENU_VISIBILITY) {
        this.KEY_MENU_VISIBILITY = KEY_MENU_VISIBILITY;
    }

    public SharedPreferences getSharedPreferencesMenu() {
        return sharedPreferencesMenu;
    }

    public void setSharedPreferencesMenu(SharedPreferences sharedPreferencesMenu) {
        this.sharedPreferencesMenu = sharedPreferencesMenu;
    }
}
