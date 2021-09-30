package ru.schultetabledima.schultetable.table;

import android.content.Context;
import android.os.Build;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import ru.schultetabledima.schultetable.R;

public class PopupMenuCreator {
    private Context context;
    private View view;
    private TablePresenter tablePresenter;
    PopupMenu popupMenu;

    public PopupMenuCreator(Context context, View view, TablePresenter tablePresenter) {
        this.context = context;
        this.view = view;
        this.tablePresenter = tablePresenter;
        main();
    }

    private void main() {
        popupMenu = new PopupMenu(context, view);
        popupMenu.inflate(R.menu.menu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popupMenu.setForceShowIcon(true);
        }
        popupMenu.setOnMenuItemClickListener(onMenuItemClickListener);
    }

    public PopupMenu getPopupMenu() {
        return popupMenu;
    }


    PopupMenu.OnMenuItemClickListener onMenuItemClickListener = new PopupMenu.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            tablePresenter.onClickMenuButtonsListener(menuItem.getItemId());
            return false;
        }
    };
}
