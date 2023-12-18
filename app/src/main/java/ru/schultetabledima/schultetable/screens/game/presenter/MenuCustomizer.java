package ru.schultetabledima.schultetable.screens.game.presenter;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.view.View;
import android.widget.LinearLayout;

import ru.schultetabledima.schultetable.App;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.screens.game.view.tablecreation.AnimationTransition;
import ru.schultetabledima.schultetable.utils.Converter;
import ru.schultetabledima.schultetable.utils.PreferencesReader;
//import ru.schultetabledima.schultetable.utils.PreferencesReader;

public class MenuCustomizer {

    private TablePresenter presenter;
    private SharedPreferences sharedPreferencesMenu;
    private final String MENU_PREFERENCES = "PreferencesMenu";
    private final String KEY_MENU_VISIBILITY = "Saved Menu Visibility";
    private boolean isMenuShow;
//    private PreferencesReader settings;
    private DataForMenuButtonsHandler data;

    public MenuCustomizer(TablePresenter presenter) {
        this.presenter = presenter;
        main();
    }

    private void main() {
        data = new DataForMenuButtonsHandler();
//        settings = new PreferencesReader();
        settingForMenu();
    }

    private void settingForMenu() {
        sharedPreferencesMenu = App.getContext().getSharedPreferences(MENU_PREFERENCES, MODE_PRIVATE);
        isMenuShow = sharedPreferencesMenu.getBoolean(KEY_MENU_VISIBILITY, true);

        data.setMenuShow(isMenuShow);
        data.setKEY_MENU_VISIBILITY(KEY_MENU_VISIBILITY);
        data.setSharedPreferencesMenu(sharedPreferencesMenu);

        int visibility, imageResource;
        int visibilityHint = View.VISIBLE;
        LinearLayout.LayoutParams layoutParams;

        if (isMenuShow) {
            visibility = View.VISIBLE;
            imageResource = R.drawable.ic_arrow_down;
            layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Converter.getPxFromDP(App.getContext(), 40));

        } else {
            visibility = View.INVISIBLE;
            imageResource = R.drawable.ic_arrow_up;
            layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Converter.getPxFromDP(App.getContext(), 20));
        }

        if (!PreferencesReader.INSTANCE.isMoveHint() || !PreferencesReader.INSTANCE.isTouchCells() || !isMenuShow) {
            visibilityHint = View.INVISIBLE;

        } else if (PreferencesReader.INSTANCE.isMoveHint()) {
            visibilityHint = View.VISIBLE;
        }
        presenter.getViewState().showHideMenu(visibility, visibilityHint, imageResource, layoutParams);

        presenter.getViewState().setAnimationToolbar(new AnimationTransition().createAnimation());
    }

    public DataForMenuButtonsHandler getData() {
        return data;
    }
}
