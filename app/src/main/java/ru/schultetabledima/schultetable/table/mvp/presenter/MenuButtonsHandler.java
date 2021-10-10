package ru.schultetabledima.schultetable.table.mvp.presenter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.LinearLayout;

import ru.schultetabledima.schultetable.App;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.advice.AdviceActivity;
import ru.schultetabledima.schultetable.settings.SettingsActivity;
import ru.schultetabledima.schultetable.statistic.StatisticsActivity;
import ru.schultetabledima.schultetable.utils.Converter;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

public class MenuButtonsHandler {

    TablePresenter presenter;
    private PreferencesReader settings;
    private boolean isMenuShow;
    private String KEY_MENU_VISIBILITY;
    private SharedPreferences sharedPreferencesMenu;

    public MenuButtonsHandler(TablePresenter tablePresenter, DataForMenuButtonsHandler data) {
        presenter = tablePresenter;
        isMenuShow = data.getIsMenuShow();
        sharedPreferencesMenu = data.getSharedPreferencesMenu();
        KEY_MENU_VISIBILITY = data.getKEY_MENU_VISIBILITY();

        init();
    }

    private void init() {
        settings = new PreferencesReader();
    }

    public boolean checkClick(int viewID) {
        if (viewID == R.id.image_button_settings) {
            Intent intent = new Intent(App.getContext(), SettingsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            App.getContext().startActivity(intent);

        } else if (viewID == R.id.image_menu) {
            presenter.getViewState().showPopupMenu();

        } else if (viewID == R.id.image_Button_Show_Hide_Menu) {
            SharedPreferences.Editor ed = sharedPreferencesMenu.edit();

            int visibility, visibilityHint, imageResource;
            LinearLayout.LayoutParams layoutParams;

            if (isMenuShow) {
                visibility = View.INVISIBLE;
                imageResource = R.drawable.ic_arrow_up;
                layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        Converter.getPxFromDP(App.getContext(), 20));
                isMenuShow = false;

            } else {
                visibility = View.VISIBLE;
                imageResource = R.drawable.ic_arrow_down;
                layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        (int) App.getContext().getResources().getDimension(R.dimen.customMinHeight));
                isMenuShow = true;

            }

            if (settings.getIsMoveHint() && isMenuShow && settings.getIsTouchCells()) {
                visibilityHint = View.VISIBLE;

            } else {
                visibilityHint = View.INVISIBLE;
            }

            presenter.getViewState().showHideMenu(visibility, visibilityHint, imageResource, layoutParams);

            ed.putBoolean(KEY_MENU_VISIBILITY, isMenuShow);
            ed.apply();
        }

        if (viewID == R.id.item_statistics) {
            Intent intent = new Intent(App.getContext(), StatisticsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            App.getContext().startActivity(intent);

        } else if (viewID == R.id.item_advice) {
            Intent intent = new Intent(App.getContext(), AdviceActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            App.getContext().startActivity(intent);

        }
        return true;
    }
}