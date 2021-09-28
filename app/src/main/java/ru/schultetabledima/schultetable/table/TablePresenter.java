package ru.schultetabledima.schultetable.table;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.SystemClock;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupMenu;

import java.util.List;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.schultetabledima.schultetable.App;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.advice.AdviceActivity;
import ru.schultetabledima.schultetable.contracts.TableContract;
import ru.schultetabledima.schultetable.donation.DonationActivity;
import ru.schultetabledima.schultetable.settings.SettingsActivity;
import ru.schultetabledima.schultetable.statistic.StatisticsActivity;
import ru.schultetabledima.schultetable.table.tablecreation.AnimationTransition;
import ru.schultetabledima.schultetable.table.tablecreation.DataCell;
import ru.schultetabledima.schultetable.table.tablecreation.ValuesAndIdsCreator;
import ru.schultetabledima.schultetable.utils.Converter;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

@InjectViewState
public class TablePresenter extends MvpPresenter<TableContract.View> implements TableContract.Presenter {

    private int count1 = 0, countdownSecondTable;
    private long saveTime = 0;
    private boolean isMenuShow;
    private int activeTable;
    private final int FIRST_TABLE_ID = 0, SECOND_TABLE_ID = 2;
    private int nextMoveFirstTable, nextMoveSecondTableCountdown;
    private static SharedPreferences sharedPreferencesMenu;
    private final String MENU_PREFERENCES = "PreferencesMenu";
    private final String KEY_MENU_VISIBILITY = "Saved Menu Visibility";
    private boolean isDialogueShow = false, booleanStartChronometer = true;
    private transient PreferencesReader settings;
    private ValuesAndIdsCreator valuesAndIdsCreatorFirstTable, valuesAndIdsCreatorSecondTable;
    private List<Integer> cellsIdFirstTableForCheck, cellsIdSecondTableForCheck;
    private List<DataCell> dataCellsFirstTableForFilling, dataCellsSecondTableForFilling;
    private long baseChronometer;


    public TablePresenter() {
        main();
    }

    private void main() {
        settings = new PreferencesReader(App.getContext());

        callValuesCreator();
        pushValuesAndIds();

        startChronometer();
        settingForCheckMove();
        settingForMenu(); //возможно перенести вверх стека
    }


    private void callValuesCreator() {
        valuesAndIdsCreatorFirstTable = new ValuesAndIdsCreator();
        dataCellsFirstTableForFilling = valuesAndIdsCreatorFirstTable.getDataCells();
        cellsIdFirstTableForCheck = valuesAndIdsCreatorFirstTable.getListIdsForCheck();


        if (settings.getIsTwoTables()) {
            valuesAndIdsCreatorSecondTable = new ValuesAndIdsCreator();
            dataCellsSecondTableForFilling = valuesAndIdsCreatorSecondTable.getDataCells();
            cellsIdSecondTableForCheck = valuesAndIdsCreatorSecondTable.getListIdsForCheck();
        }
    }


    private void pushValuesAndIds() {
        getViewState().setTableData(dataCellsFirstTableForFilling, dataCellsSecondTableForFilling);
    }


    private void settingForMenu() {
        sharedPreferencesMenu = App.getContext().getSharedPreferences(MENU_PREFERENCES, MODE_PRIVATE);
        isMenuShow = sharedPreferencesMenu.getBoolean(KEY_MENU_VISIBILITY, true);

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

        if (!settings.getIsMoveHint() || !settings.getIsTouchCells() || !isMenuShow) {
            visibilityHint = View.INVISIBLE;

        } else if (settings.getIsMoveHint()) {
            visibilityHint = View.VISIBLE;
        }
        getViewState().showHideMenu(visibility, visibilityHint, imageResource, layoutParams);

        getViewState().setAnimationToolbar(new AnimationTransition().createAnimation());

    }

    public void onClickMenuButtonsListener(int viewID, View v) {
        if (viewID == R.id.image_button_settings) {
            Intent intent = new Intent(App.getContext(), SettingsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            App.getContext().startActivity(intent);

        } else if (viewID == R.id.image_menu) {
            createPopupMenu(v);


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

            getViewState().showHideMenu(visibility, visibilityHint, imageResource, layoutParams);

            ed.putBoolean(KEY_MENU_VISIBILITY, isMenuShow);
            ed.apply();
        }
    }

    private void createPopupMenu(View image_menu) {
        PopupMenu popupMenu = new PopupMenu(App.getContext(), image_menu);

        popupMenu.inflate(R.menu.menu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popupMenu.setForceShowIcon(true);
        }

        popupMenu.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.item_statistics) {
                Intent intent = new Intent(App.getContext(), StatisticsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                App.getContext().startActivity(intent);
                return true;

            } else if (itemId == R.id.item_advice) {
                Intent intent = new Intent(App.getContext(), AdviceActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                App.getContext().startActivity(intent);
                return true;

            } else if (itemId == R.id.item_donation) {
                Intent intent = new Intent(App.getContext(), DonationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                App.getContext().startActivity(intent);
                return true;
            }

            return false;
        });
        popupMenu.show();
    }


    private void settingForCheckMove() {
        nextMoveFirstTable = valuesAndIdsCreatorFirstTable.getFirstValue();

        if (settings.getIsTwoTables()) {
            countdownSecondTable = dataCellsSecondTableForFilling.size() - 1;
            nextMoveSecondTableCountdown = valuesAndIdsCreatorSecondTable.getFirstValue() + countdownSecondTable;
        }

        if (settings.getIsLetters()) {
            getViewState().setMoveHint((char) nextMoveFirstTable);

        } else {
            getViewState().setMoveHint(nextMoveFirstTable);
        }
        activeTable = FIRST_TABLE_ID;
    }


    public void checkMove(int cellId, long baseChronometer) {
        saveTime = SystemClock.elapsedRealtime() - baseChronometer;

        if (!settings.getIsTouchCells())
            endGameDialogue();


        if (settings.getIsTouchCells()) {

            if (!settings.getIsTwoTables())
                checkMoveInOneTable(cellId);


            if (settings.getIsTwoTables()) {

                if (!isValidTable(cellId))
                    return;

                checkMoveInTwoTables(cellId);
            }
        }
    }

    private void checkMoveInOneTable(int cellId) {
        if (cellId == cellsIdFirstTableForCheck.get(count1)) {
            nextMoveFirstTable++;
            count1++;
            if (settings.getIsLetters())
                getViewState().setMoveHint((char) nextMoveFirstTable);

            else
                getViewState().setMoveHint(nextMoveFirstTable);
        }

        if (count1 == cellsIdFirstTableForCheck.size()) {
            getViewState().setMoveHint(' ');
            endGameDialogue();
        }
    }

    private boolean isValidTable(int cellId) {
        if (activeTable == FIRST_TABLE_ID && cellsIdSecondTableForCheck.contains(cellId)) {
            showToastWrongTable();
            return false;
        }
        if (activeTable == SECOND_TABLE_ID && cellsIdFirstTableForCheck.contains(cellId)) {
            showToastWrongTable();
            return false;
        }
        return true;
    }

    private void checkMoveInTwoTables(int cellId) {

        if (activeTable == FIRST_TABLE_ID) {

            if (cellId == cellsIdFirstTableForCheck.get(count1)) {
                count1++;
                nextMoveFirstTable++;

                activeTable = SECOND_TABLE_ID;

                getViewState().setTableColor(FIRST_TABLE_ID, R.color.passiveTable);
                getViewState().setTableColor(SECOND_TABLE_ID, R.color.activeTable);


                if (settings.getIsLetters())
                    getViewState().setMoveHint((char) nextMoveSecondTableCountdown);
                else
                    getViewState().setMoveHint(nextMoveSecondTableCountdown);
            }

        } else if (activeTable == SECOND_TABLE_ID) {

            if (cellId == cellsIdSecondTableForCheck.get(countdownSecondTable)) {
                countdownSecondTable--;
                nextMoveSecondTableCountdown--;

                activeTable = FIRST_TABLE_ID;

                getViewState().setTableColor(FIRST_TABLE_ID, R.color.activeTable);
                getViewState().setTableColor(SECOND_TABLE_ID, R.color.passiveTable);

                if (settings.getIsLetters())
                    getViewState().setMoveHint((char) nextMoveFirstTable);
                else
                    getViewState().setMoveHint(nextMoveFirstTable);
            }
        }

        if (countdownSecondTable < 0) {
            endGameDialogue();
            getViewState().setMoveHint('☑');
        }
    }


    private void endGameDialogue() {
        booleanStartChronometer = false;
        getViewState().stopStartChronometer(booleanStartChronometer);
        isDialogueShow = true;

        getViewState().setBaseChronometer(saveTime, isDialogueShow);
        getViewState().showDialogueFragment(isDialogueShow);
    }


    public void cancelDialogue() {
        isDialogueShow = false;
        getViewState().showDialogueFragment(isDialogueShow);
    }


    private void startChronometer() {
        getViewState().setBaseChronometer(SystemClock.elapsedRealtime() - saveTime, isDialogueShow);
        booleanStartChronometer = true;
        getViewState().stopStartChronometer(booleanStartChronometer);
    }


    private void showToastWrongTable() {
        getViewState().showToastWrongTable(R.string.wrongTable);
    }

    public void onNegativeOrCancelDialogue() {
        booleanStartChronometer = true;
        cancelDialogue();
        getViewState().setBaseChronometer(SystemClock.elapsedRealtime() - saveTime, isDialogueShow);
        getViewState().stopStartChronometer(booleanStartChronometer);
    }

    public long getSaveTime() {
        return saveTime;
    }
}