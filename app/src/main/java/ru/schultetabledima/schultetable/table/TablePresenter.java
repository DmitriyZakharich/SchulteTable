package ru.schultetabledima.schultetable.table;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.SystemClock;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.schultetabledima.schultetable.App;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.advice.AdviceActivity;
import ru.schultetabledima.schultetable.contracts.TableContract;
import ru.schultetabledima.schultetable.settings.SettingsActivity;
import ru.schultetabledima.schultetable.statistic.StatisticsActivity;
import ru.schultetabledima.schultetable.table.tablecreation.AnimationTransition;
import ru.schultetabledima.schultetable.table.tablecreation.DataCell;
import ru.schultetabledima.schultetable.table.tablecreation.ValuesAndIdsCreator;
import ru.schultetabledima.schultetable.utils.Converter;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

@InjectViewState
public class TablePresenter extends MvpPresenter<TableContract.View> implements TableContract.Presenter {

    private int countFirstTable = 0, countdownSecondTable;
    private int nextMove;
    private long saveTime = 0;
    private boolean isMenuShow;
    private int activeTable;
    private final int FIRST_TABLE_ID = 0, SECOND_TABLE_ID = 2;
    private int nextMoveFirstTable, nextMoveSecondTableCountdown;
    private static SharedPreferences sharedPreferencesMenu;
    private int colorFirstTable, colorSecondTable;
    private final String MENU_PREFERENCES = "PreferencesMenu";
    private final String KEY_MENU_VISIBILITY = "Saved Menu Visibility";
    private boolean isDialogueShow = false, booleanStartChronometer = true;
    private transient PreferencesReader settings;
    private ValuesAndIdsCreator valuesAndIdsCreatorFirstTable, valuesAndIdsCreatorSecondTable;
    private List<Integer> cellsIdFirstTableForCheck, cellsIdSecondTableForCheck;
    private List<DataCell> dataCellsFirstTableForFilling, dataCellsSecondTableForFilling;
    private boolean isRightCell = false;
    private int cellColor, backgroundCellResources;


    public TablePresenter() {
        main();
    }

    private void main() {
        settings = new PreferencesReader();

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

    public void onClickMenuButtonsListener(int viewID) {
        if (viewID == R.id.image_button_settings) {
            Intent intent = new Intent(App.getContext(), SettingsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            App.getContext().startActivity(intent);

        } else if (viewID == R.id.image_menu) {
            getViewState().showPopupMenu();

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

        if (viewID == R.id.item_statistics) {
            Intent intent = new Intent(App.getContext(), StatisticsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            App.getContext().startActivity(intent);

        } else if (viewID == R.id.item_advice) {
            Intent intent = new Intent(App.getContext(), AdviceActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            App.getContext().startActivity(intent);

        }
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

                if (!isValidTable(cellId)) {

                    return;
                }

                checkMoveInTwoTables(cellId);
            }
        }
    }

    private void checkMoveInOneTable(int cellId) {
        cellColor = Color.RED;
        if (cellId == cellsIdFirstTableForCheck.get(countFirstTable)) {
            nextMoveFirstTable++;
            countFirstTable++;
            cellColor = Color.GREEN;
            isRightCell = true;
        }

        getViewState().setCellColor(cellId, cellColor);
    }

    public void applyCellSelection(int cellId) {

        if (!settings.getIsTwoTables()) {
            applyCellSelectionInOneTable(cellId);
        } else
            applyCellSelectionInTwoTables(cellId);


    }

    private void applyCellSelectionInOneTable(int cellId) {
        if (isRightCell) {
            if (settings.getIsLetters())
                getViewState().setMoveHint((char) nextMoveFirstTable);

            else
                getViewState().setMoveHint(nextMoveFirstTable);
        }
        backgroundCellResources = R.drawable.border_cell_active_color;
        getViewState().setBackgroundResources(cellId, backgroundCellResources);
        isRightCell = false;

        if (countFirstTable == cellsIdFirstTableForCheck.size()) {
            getViewState().setMoveHint('☑');
            endGameDialogue();
        }
    }


    private boolean isValidTable(int cellId) {
        cellColor = Color.RED;

        if (activeTable == FIRST_TABLE_ID && cellsIdSecondTableForCheck.contains(cellId)) {
            getViewState().setCellColor(cellId, cellColor);
            showToastWrongTable();
            backgroundCellResources = R.drawable.border_cell_passive_color;
            return false;
        }
        if (activeTable == SECOND_TABLE_ID && cellsIdFirstTableForCheck.contains(cellId)) {
            getViewState().setCellColor(cellId, cellColor);
            showToastWrongTable();
            backgroundCellResources = R.drawable.border_cell_passive_color;
            return false;
        }

        backgroundCellResources = R.drawable.border_cell_active_color;
        return true;
    }

    private void checkMoveInTwoTables(int cellId) {

        cellColor = Color.RED;

        if (activeTable == FIRST_TABLE_ID) {

            if (cellId == cellsIdFirstTableForCheck.get(countFirstTable)) {
                countFirstTable++;
                nextMoveFirstTable++;
                activeTable = SECOND_TABLE_ID;

                cellColor = Color.GREEN;

                isRightCell = true;

                colorFirstTable = R.drawable.border_cell_passive_color;
                colorSecondTable = R.drawable.border_cell_active_color;

                nextMove = nextMoveSecondTableCountdown;

            }

        } else if (activeTable == SECOND_TABLE_ID) {

            if (cellId == cellsIdSecondTableForCheck.get(countdownSecondTable)) {
                countdownSecondTable--;
                nextMoveSecondTableCountdown--;

                activeTable = FIRST_TABLE_ID;

                cellColor = Color.GREEN;

                isRightCell = true;


                colorFirstTable = R.drawable.border_cell_active_color;
                colorSecondTable = R.drawable.border_cell_passive_color;

                nextMove = nextMoveFirstTable;
            }
        }

        getViewState().setCellColor(cellId, cellColor);
    }

    private void applyCellSelectionInTwoTables(int cellId) {


        if (isRightCell) {
            getViewState().setTableColor(colorFirstTable, colorSecondTable);

            if (settings.getIsLetters())
                getViewState().setMoveHint((char) nextMove);
            else
                getViewState().setMoveHint(nextMove);


            isRightCell = false;

        } else
            getViewState().setBackgroundResources(cellId, backgroundCellResources);


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