package ru.schultetabledima.schultetable.table.mvp.presenter;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.SystemClock;
import android.view.View;
import android.widget.LinearLayout;

import java.util.List;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.schultetabledima.schultetable.App;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.contracts.TableContract;
import ru.schultetabledima.schultetable.table.mvp.model.CellValuesCreator;
import ru.schultetabledima.schultetable.table.mvp.model.DataCell;
import ru.schultetabledima.schultetable.table.mvp.view.tablecreation.AnimationTransition;
import ru.schultetabledima.schultetable.utils.Converter;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

@InjectViewState
public class TablePresenter extends MvpPresenter<TableContract.View> implements TableContract.Presenter {

    private long saveTime = 0;
    private boolean isMenuShow;
    private int nextMoveFirstTable, nextMoveSecondTableCountdown;
    private SharedPreferences sharedPreferencesMenu;
    private final String MENU_PREFERENCES = "PreferencesMenu";
    private final String KEY_MENU_VISIBILITY = "Saved Menu Visibility";
    private boolean isDialogueShow = false, booleanStartChronometer = true;
    private transient PreferencesReader settings;
    private CellValuesCreator cellValuesCreatorFirstTable, cellValuesCreatorSecondTable;
    private List<DataCell> dataCellsFirstTableForFilling, dataCellsSecondTableForFilling;
    private MoveInspector moveInspector;
    private DataForMoveInspector dataForMoveInspector;
    private MenuButtonsHandler menuButtonsHandler;
    private DataForMenuButtonsHandler dataForMenuButtonsHandler;


    public TablePresenter() {
        main();
    }

    private void main() {
        settings = new PreferencesReader();

        getViewState().createTable();

        dataForMoveInspector = new DataForMoveInspector();
        dataForMenuButtonsHandler = new DataForMenuButtonsHandler();

        callValuesCreator();
        pushValuesToTable();

        startChronometer();
        settingForCheckMove();
        settingForMenu();

        moveInspector = new MoveInspector(this, dataForMoveInspector);
        menuButtonsHandler = new MenuButtonsHandler(this, dataForMenuButtonsHandler);
    }


    private void callValuesCreator() {
        cellValuesCreatorFirstTable = new CellValuesCreator();
        dataCellsFirstTableForFilling = cellValuesCreatorFirstTable.getDataCells();
        dataForMoveInspector.setCellsIdFirstTableForCheck(cellValuesCreatorFirstTable.getListIdsForCheck());

        if (settings.getIsTwoTables()) {
            cellValuesCreatorSecondTable = new CellValuesCreator();
            dataCellsSecondTableForFilling = cellValuesCreatorSecondTable.getDataCells();
            dataForMoveInspector.setCellsIdSecondTableForCheck(cellValuesCreatorSecondTable.getListIdsForCheck());
        }
    }


    private void pushValuesToTable() {
        getViewState().setTableData(dataCellsFirstTableForFilling, dataCellsSecondTableForFilling);
    }


    private void settingForMenu() {
        sharedPreferencesMenu = App.getContext().getSharedPreferences(MENU_PREFERENCES, MODE_PRIVATE);
        isMenuShow = sharedPreferencesMenu.getBoolean(KEY_MENU_VISIBILITY, true);

        dataForMenuButtonsHandler.setMenuShow(isMenuShow);
        dataForMenuButtonsHandler.setKEY_MENU_VISIBILITY(KEY_MENU_VISIBILITY);
        dataForMenuButtonsHandler.setSharedPreferencesMenu(sharedPreferencesMenu);

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

    public boolean onClickMenuButtonsListener(int viewID) {

        return menuButtonsHandler.checkClick(viewID);
    }


    private void settingForCheckMove() {
        nextMoveFirstTable = cellValuesCreatorFirstTable.getFirstValue();
        dataForMoveInspector.setNextMoveFirstTable(nextMoveFirstTable);

        if (settings.getIsTwoTables()) {
            int countdownSecondTable = dataCellsSecondTableForFilling.size() - 1;
            nextMoveSecondTableCountdown = cellValuesCreatorSecondTable.getFirstValue() + countdownSecondTable;

            dataForMoveInspector.setCountdownSecondTable(countdownSecondTable);
            dataForMoveInspector.setNextMoveSecondTableCountdown(nextMoveSecondTableCountdown);
        }

        if (settings.getIsLetters()) {
            getViewState().setMoveHint((char) nextMoveFirstTable);

        } else {
            getViewState().setMoveHint(nextMoveFirstTable);
        }
    }


    public void cellActionDown(int cellId, long baseChronometer) {
        saveTime = SystemClock.elapsedRealtime() - baseChronometer;
        moveInspector.cellActionDown(cellId);
    }

    public void cellActionUp(int cellId) {
        moveInspector.cellActionUp(cellId);
    }


    public void endGameDialogue() {
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