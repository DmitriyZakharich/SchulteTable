package ru.schultetabledima.schultetable.table;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.util.ArrayMap;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.io.Serializable;
import java.util.ArrayList;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.advice.AdviceActivity;
import ru.schultetabledima.schultetable.donation.DonationActivity;
import ru.schultetabledima.schultetable.settings.SettingsActivity;
import ru.schultetabledima.schultetable.statistic.StatisticsActivity;
import ru.schultetabledima.schultetable.table.tablecreation.TableCreator;
import ru.schultetabledima.schultetable.utils.Converter;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

public class TablePresenter implements Serializable {
    private int count = 0;
    private long saveTime;
    private Context context;
    private transient TableCreator tableCreator;
    private transient LinearLayout table;
    private boolean isMenuShow;
    private ArrayList<Character> listLetters1, listLetters2;
    private ArrayList<Integer> listNumbers1, listNumbers2;
    private int activeTable, saveNumberActiveTable;
    private transient TableLayout firstTable, secondTable;
    private transient ArrayMap<Integer, Integer> cellsIdFirstTable, cellsIdSecondTable;
    private int nextMoveFirstTable, nextMoveSecondTableCountdown;
    private static SharedPreferences sharedPreferencesMenu;
    private final String MENU_PREFERENCES = "PreferencesMenu";
    private final String KEY_MENU_VISIBILITY = "Saved Menu Visibility";
    private boolean isDialogueShow = false;
    private transient EndGameDialogue endGameDialogue;
    private transient PreferencesReader settings;


    public TablePresenter(Context context) {
        this.context = context;
        main();
    }

    private void main() {
        settings = new PreferencesReader(context);
        callTableCreator();
        showTable();
        startChronometer();
        settingForCheckMove();
        settingForMenu(); //возможно перенести вверх стека
    }

    private void settingForMenu() {
        sharedPreferencesMenu = context.getSharedPreferences(MENU_PREFERENCES, MODE_PRIVATE);
        isMenuShow = sharedPreferencesMenu.getBoolean(KEY_MENU_VISIBILITY, true);

        int visibility, imageResource;
        int visibilityHint = View.VISIBLE;
        LinearLayout.LayoutParams layoutParams;

        if (isMenuShow) {
            visibility = View.VISIBLE;
            imageResource = R.drawable.ic_arrow_down;
            layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Converter.getPxFromDP(context, 40));


        } else {
            visibility = View.INVISIBLE;
            imageResource = R.drawable.ic_arrow_up;
            layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Converter.getPxFromDP(context, 20));

        }

        if (!settings.getIsMoveHint() || !settings.getIsTouchCells() || !isMenuShow) {
            visibilityHint = View.INVISIBLE;

        } else if (settings.getIsMoveHint()) {
            visibilityHint = View.VISIBLE;
        }
        ((TableActivity) context).showHideMenu(visibility, visibilityHint, imageResource, layoutParams);
    }


    public void onClickMenuButtonsListener(int viewID) {
        if (viewID == R.id.image_button_settings) {
            context.startActivity(new Intent(context, SettingsActivity.class));


        } else if (viewID == R.id.image_menu) {
            createPopupMenu();


        } else if (viewID == R.id.image_Button_Show_Hide_Menu) {
            SharedPreferences.Editor ed = sharedPreferencesMenu.edit();

            int visibility, visibilityHint, imageResource;
            LinearLayout.LayoutParams layoutParams;

            if (isMenuShow) {
                visibility = View.INVISIBLE;
                imageResource = R.drawable.ic_arrow_up;
                layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        Converter.getPxFromDP(context, 20));
                isMenuShow = false;

            } else {
                visibility = View.VISIBLE;
                imageResource = R.drawable.ic_arrow_down;
                layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        (int) context.getResources().getDimension(R.dimen.customMinHeight));
                isMenuShow = true;

            }

            if (settings.getIsMoveHint() && isMenuShow && settings.getIsTouchCells()) {
                visibilityHint = View.VISIBLE;

            } else {
                visibilityHint = View.INVISIBLE;
            }

            ((TableActivity) context).showHideMenu(visibility, visibilityHint, imageResource, layoutParams);

            ed.putBoolean(KEY_MENU_VISIBILITY, isMenuShow);
            ed.apply();
        }
    }

    private void createPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(context, ((TableActivity) context).findViewById(R.id.image_menu));
        popupMenu.inflate(R.menu.menu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popupMenu.setForceShowIcon(true);
        }

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.item_statistics) {
                    context.startActivity(new Intent(context, StatisticsActivity.class));
                    return true;

                } else if (itemId == R.id.item_advice) {
                    context.startActivity(new Intent(context, AdviceActivity.class));
                    return true;

                } else if (itemId == R.id.item_donation) {
                    context.startActivity(new Intent(context, DonationActivity.class));
                    return true;
                }

                return false;
            }
        });
        popupMenu.show();
    }


    private void callTableCreator() {
        tableCreator = new TableCreator(context, this);
        table = tableCreator.getContainerForTable();
    }


    private void settingForCheckMove() {
        cellsIdFirstTable = new ArrayMap<>();
        cellsIdFirstTable = tableCreator.getCellsIdFirstTable();

        if (settings.getIsTwoTables()) {
            cellsIdSecondTable = new ArrayMap<>();
            cellsIdSecondTable = tableCreator.getCellsIdSecondTable();
        }

        if (settings.getIsLetters()) {
            nextMoveFirstTable = (settings.getIsEnglish()) ? (int) 'A' : (int) 'А'; // eng / rus
            ((TableActivity) context).setMoveHint((char) nextMoveFirstTable);

            if (settings.getIsTwoTables())
                nextMoveSecondTableCountdown = (settings.getIsEnglish()) ?
                        (int) 'A' + cellsIdFirstTable.size() - 1 : (int) 'А' + cellsIdFirstTable.size() - 1;


        } else {
            nextMoveFirstTable = 1;
            ((TableActivity) context).setMoveHint(nextMoveFirstTable);

            if (settings.getIsTwoTables())
                nextMoveSecondTableCountdown = cellsIdSecondTable.size();
        }


        firstTable = (TableLayout) table.getChildAt(0);
        if (settings.getIsTwoTables())
            secondTable = (TableLayout) table.getChildAt(1);

        activeTable = firstTable.getId();
    }


    public void checkMove(int cellId) {
        if (!settings.getIsTouchCells()) {
            endGameDialogue();

        } else {

            if (settings.getIsTwoTables()) {
                checkMoveInTwoTables(cellId);

            } else {
                checkMoveInOneTable(cellId);
            }
        }
    }


    private void checkMoveInOneTable(int cellId) {
        if (cellId == cellsIdFirstTable.get(nextMoveFirstTable)) {
            nextMoveFirstTable++;
            count++;
            if (settings.getIsLetters())
                ((TableActivity) context).setMoveHint((char) nextMoveFirstTable);

            else
                ((TableActivity) context).setMoveHint(nextMoveFirstTable);
        }

        if (count == cellsIdFirstTable.size()) {
            endGameDialogue();
        }
    }


    private void checkMoveInTwoTables(int cellId) {

        //Проверка активной таблицы
        //Если id из другой таблицы, то
        //Нужно выполнять до смены значения activeTable
        if (activeTable == firstTable.getId() && cellsIdSecondTable.containsValue(cellId)) {
            showToastWrongTable();
        }
        if (activeTable == secondTable.getId() && cellsIdFirstTable.containsValue(cellId)) {
            showToastWrongTable();
        }

        if (activeTable == firstTable.getId()) {

            if (cellId == cellsIdFirstTable.get(nextMoveFirstTable)) {
                nextMoveFirstTable++;
                count++;

                activeTable = secondTable.getId();
                saveNumberActiveTable = 1;

                firstTable.setBackgroundColor(ContextCompat.getColor(context, R.color.passiveTable));
                secondTable.setBackgroundColor(ContextCompat.getColor(context, R.color.activeTable));

                if (settings.getIsLetters())
                    ((TableActivity) context).setMoveHint((char) nextMoveSecondTableCountdown);
                else
                    ((TableActivity) context).setMoveHint(nextMoveSecondTableCountdown);
            }

        } else if (activeTable == secondTable.getId()) {

            if (cellId == cellsIdSecondTable.get(nextMoveSecondTableCountdown)) {
                nextMoveSecondTableCountdown--;
                count++;

                activeTable = firstTable.getId();
                saveNumberActiveTable = 0;

                firstTable.setBackgroundColor(ContextCompat.getColor(context, R.color.activeTable));
                secondTable.setBackgroundColor(ContextCompat.getColor(context, R.color.passiveTable));

                if (settings.getIsLetters())
                    ((TableActivity) context).setMoveHint((char) nextMoveFirstTable);
                else
                    ((TableActivity) context).setMoveHint(nextMoveFirstTable);

            }
        }

        if (count == (cellsIdFirstTable.size() + cellsIdSecondTable.size())) {
            endGameDialogue();
            ((TableActivity) context).setMoveHint(' ');
        }
    }


    private void endGameDialogue() {
        ((TableActivity) context).stopChronometer();
        saveTime = ((TableActivity) context).getBaseChronometer() - SystemClock.elapsedRealtime();
        isDialogueShow = true;
        endGameDialogue = new EndGameDialogue(context, this);
    }


    public void preparingToRotateScreen() {
        ((TableActivity) context).removeTable();

        if (!isDialogueShow) {
            saveTime = ((TableActivity) context).getBaseChronometer() - SystemClock.elapsedRealtime();
            ((TableActivity) context).stopChronometer();
        }

        if (isDialogueShow)
            endGameDialogue.dismiss();


        if (settings.getIsLetters()) {
            listLetters1 = new ArrayList<>(tableCreator.getListLetters1());

            if (settings.getIsTwoTables()) {
                listLetters2 = new ArrayList<>(tableCreator.getListLetters2());
            }
        }

        if (!settings.getIsLetters()) {
            listNumbers1 = new ArrayList<>(tableCreator.getListNumbers1());

            if (settings.getIsTwoTables()) {
                listNumbers2 = new ArrayList<>(tableCreator.getListNumbers2());
            }
        }
    }

    public void restoreInstanceState() {
        settings = new PreferencesReader(context);
        refreshTableCreator();
        showTable();
        ((TableActivity) context).setBaseChronometer(SystemClock.elapsedRealtime() + saveTime);

        if (!isDialogueShow)
            startChronometer();

        settingForMenu();
        restoreSettingForCheckMove();

        if (isDialogueShow) {
            endGameDialogue();
        }
    }


    private void refreshTableCreator() {
        if (settings.getIsLetters()) {
            tableCreator = new TableCreator(context, this, listLetters1, listLetters2);

        } else {
            tableCreator = new TableCreator(context, listNumbers1, listNumbers2, this);
        }

        table = tableCreator.getContainerForTable();
        cellsIdFirstTable = tableCreator.getCellsIdFirstTable();

        if (settings.getIsTwoTables()) {
            cellsIdSecondTable = tableCreator.getCellsIdSecondTable();
            restoreSettingForTwoTables();
        }
    }

    private void restoreSettingForCheckMove() {
        cellsIdFirstTable = new ArrayMap<>();
        cellsIdFirstTable = tableCreator.getCellsIdFirstTable();

        if (settings.getIsTwoTables()) {
            cellsIdSecondTable = new ArrayMap<>();
            cellsIdSecondTable = tableCreator.getCellsIdSecondTable();
        }


        if (!settings.getIsLetters()) {
            if (activeTable == firstTable.getId()) {
                ((TableActivity) context).setMoveHint(nextMoveFirstTable);
            } else if (activeTable == secondTable.getId())
                ((TableActivity) context).setMoveHint(nextMoveSecondTableCountdown);


        } else if (settings.getIsLetters()) {
            if (activeTable == firstTable.getId()) {
                ((TableActivity) context).setMoveHint((char) nextMoveFirstTable);
            } else if (activeTable == secondTable.getId())
                ((TableActivity) context).setMoveHint((char) nextMoveSecondTableCountdown);
        }
    }


    private void restoreSettingForTwoTables() {
        firstTable = (TableLayout) table.getChildAt(0);
        secondTable = (TableLayout) table.getChildAt(1);


        if (saveNumberActiveTable == 0) {
            activeTable = firstTable.getId();
            firstTable.setBackgroundColor(ContextCompat.getColor(context, R.color.activeTable));
            secondTable.setBackgroundColor(ContextCompat.getColor(context, R.color.passiveTable));

        } else if (saveNumberActiveTable == 1) {
            activeTable = secondTable.getId();
            firstTable.setBackgroundColor(ContextCompat.getColor(context, R.color.passiveTable));
            secondTable.setBackgroundColor(ContextCompat.getColor(context, R.color.activeTable));
        }
    }

    public void cancelDialogue() {
        isDialogueShow = false;
    }

    public long getSaveTime() {
        return saveTime;
    }

    private void showTable() {
        ((TableActivity) context).showTable(table);
    }

    private void startChronometer() {
        ((TableActivity) context).startChronometer();
    }

    public void attachView(Context context) {
        this.context = context;
    }

    public void detachView() {
        context = null;
    }

    private void showToastWrongTable() {
        Toast toast = Toast.makeText(context, R.string.wrongTable, Toast.LENGTH_SHORT);
        toast.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 500);
    }

}