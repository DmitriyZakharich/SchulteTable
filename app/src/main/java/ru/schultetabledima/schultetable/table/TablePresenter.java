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
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TableLayout;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import java.io.Serializable;
import java.util.ArrayList;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import ru.schultetabledima.schultetable.MyApplication;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.advice.AdviceActivity;
import ru.schultetabledima.schultetable.contracts.TableContract;
import ru.schultetabledima.schultetable.donation.DonationActivity;
import ru.schultetabledima.schultetable.settings.SettingsActivity;
import ru.schultetabledima.schultetable.statistic.StatisticsActivity;
import ru.schultetabledima.schultetable.table.tablecreation.AnimationTransition;
import ru.schultetabledima.schultetable.table.tablecreation.TableCreator;
import ru.schultetabledima.schultetable.utils.Converter;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

@InjectViewState
public class TablePresenter extends MvpPresenter<TableContract.View> implements TableContract.Presenter{
    //implements Serializable

    private int count = 0;
    private long saveTime;
    private transient TableCreator tableCreator;
    private transient LinearLayout table;
    private boolean isMenuShow;
    private int activeTable, saveNumberActiveTable;
    private transient TableLayout firstTable, secondTable;
    private int nextMoveFirstTable, nextMoveSecondTableCountdown;
    private static SharedPreferences sharedPreferencesMenu;
    private final String MENU_PREFERENCES = "PreferencesMenu";
    private final String KEY_MENU_VISIBILITY = "Saved Menu Visibility";
    private boolean isDialogueShow = false;
    private transient PreferencesReader settings;

    private ArrayList<Character> listLetters1, listLetters2;
    private ArrayList<Integer> listNumbers1, listNumbers2;
    private transient ArrayMap<Integer, Integer> cellsIdFirstTable, cellsIdSecondTable;
    private long baseChronometer;


    public TablePresenter(Context context) {
//        this.context = context;
        main();
    }

    public TablePresenter() {
//        this.context = context;
        main();
    }

    private void main() {
        settings = new PreferencesReader(MyApplication.getContext());
        callTableCreator();
        showTable();
        startChronometer();
        settingForCheckMove();
        settingForMenu(); //возможно перенести вверх стека
    }


//////////////////
//Меню
    private void settingForMenu() {
        sharedPreferencesMenu = MyApplication.getContext().getSharedPreferences(MENU_PREFERENCES, MODE_PRIVATE);
        isMenuShow = sharedPreferencesMenu.getBoolean(KEY_MENU_VISIBILITY, true);

        int visibility, imageResource;
        int visibilityHint = View.VISIBLE;
        LinearLayout.LayoutParams layoutParams;

        if (isMenuShow) {
            visibility = View.VISIBLE;
            imageResource = R.drawable.ic_arrow_down;
            layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Converter.getPxFromDP(MyApplication.getContext(), 40));


        } else {
            visibility = View.INVISIBLE;
            imageResource = R.drawable.ic_arrow_up;
            layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Converter.getPxFromDP(MyApplication.getContext(), 20));

        }

        if (!settings.getIsMoveHint() || !settings.getIsTouchCells() || !isMenuShow) {
            visibilityHint = View.INVISIBLE;

        } else if (settings.getIsMoveHint()) {
            visibilityHint = View.VISIBLE;
        }
        getViewState().showHideMenu(visibility, visibilityHint, imageResource, layoutParams);


        getViewState().addAnimationToolbar(new AnimationTransition().createAnimation());

    }

    public void onClickMenuButtonsListener(int viewID, View v) {
        if (viewID == R.id.image_button_settings) {
            Intent intent = new Intent(MyApplication.getContext(), SettingsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            MyApplication.getContext().startActivity(intent);

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
                        Converter.getPxFromDP(MyApplication.getContext(), 20));
                isMenuShow = false;

            } else {
                visibility = View.VISIBLE;
                imageResource = R.drawable.ic_arrow_down;
                layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        (int) MyApplication.getContext().getResources().getDimension(R.dimen.customMinHeight));
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
        PopupMenu popupMenu = new PopupMenu(MyApplication.getContext(), image_menu);
//                ((TableActivity) context).findViewById(R.id.image_menu));


        popupMenu.inflate(R.menu.menu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            popupMenu.setForceShowIcon(true);
        }

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.item_statistics) {
                    Intent intent = new Intent(MyApplication.getContext(), StatisticsActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    MyApplication.getContext().startActivity(intent);
                    return true;

                } else if (itemId == R.id.item_advice) {
                    Intent intent = new Intent(MyApplication.getContext(), AdviceActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    MyApplication.getContext().startActivity(intent);
                    return true;

                } else if (itemId == R.id.item_donation) {
                    Intent intent = new Intent(MyApplication.getContext(), DonationActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    MyApplication.getContext().startActivity(intent);
                    return true;
                }

                return false;
            }
        });
        popupMenu.show();
    }
//////////////////////


    private void callTableCreator() {
        tableCreator = new TableCreator(MyApplication.getContext(), this);
        table = tableCreator.getContainerForTable();
    }


////////////////////////
    //Проверка хода
    private void settingForCheckMove() {
        cellsIdFirstTable = new ArrayMap<>();
        cellsIdFirstTable = tableCreator.getCellsIdFirstTable();

        if (settings.getIsTwoTables()) {
            cellsIdSecondTable = new ArrayMap<>();
            cellsIdSecondTable = tableCreator.getCellsIdSecondTable();
        }

        if (settings.getIsLetters()) {
            nextMoveFirstTable = (settings.getIsEnglish()) ? (int) 'A' : (int) 'А'; // eng / rus
            getViewState().setMoveHint((char) nextMoveFirstTable);

            if (settings.getIsTwoTables())
                nextMoveSecondTableCountdown = (settings.getIsEnglish()) ?
                        (int) 'A' + cellsIdFirstTable.size() - 1 : (int) 'А' + cellsIdFirstTable.size() - 1;


        } else {
            nextMoveFirstTable = 1;
            getViewState().setMoveHint(nextMoveFirstTable);

            if (settings.getIsTwoTables())
                nextMoveSecondTableCountdown = cellsIdSecondTable.size();
        }


        firstTable = (TableLayout) table.getChildAt(0);
        if (settings.getIsTwoTables())
            secondTable = (TableLayout) table.getChildAt(2);

        activeTable = firstTable.getId();
    }

    public void checkMove(int cellId, long baseChronometer) {
        this.baseChronometer = baseChronometer;
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
                getViewState().setMoveHint((char) nextMoveFirstTable);

            else
                getViewState().setMoveHint(nextMoveFirstTable);
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

                firstTable.setBackgroundColor(ContextCompat.getColor(MyApplication.getContext(), R.color.passiveTable));
                secondTable.setBackgroundColor(ContextCompat.getColor(MyApplication.getContext(), R.color.activeTable));

                if (settings.getIsLetters())
                    getViewState().setMoveHint((char) nextMoveSecondTableCountdown);
                else
                    getViewState().setMoveHint(nextMoveSecondTableCountdown);
            }

        } else if (activeTable == secondTable.getId()) {

            if (cellId == cellsIdSecondTable.get(nextMoveSecondTableCountdown)) {
                nextMoveSecondTableCountdown--;
                count++;

                activeTable = firstTable.getId();
                saveNumberActiveTable = 0;

                firstTable.setBackgroundColor(ContextCompat.getColor(MyApplication.getContext(), R.color.activeTable));
                secondTable.setBackgroundColor(ContextCompat.getColor(MyApplication.getContext(), R.color.passiveTable));

                if (settings.getIsLetters())
                    getViewState().setMoveHint((char) nextMoveFirstTable);
                else
                    getViewState().setMoveHint(nextMoveFirstTable);

            }
        }

        if (count == (cellsIdFirstTable.size() + cellsIdSecondTable.size())) {
            endGameDialogue();
            getViewState().setMoveHint(' ');
        }
    }


    private void endGameDialogue() {
        getViewState().stopChronometer();
        saveTime = baseChronometer - SystemClock.elapsedRealtime();
        isDialogueShow = true;


        EndGameDialogueFragment endGameDialogueFragment = new EndGameDialogueFragment(this, baseChronometer);


//        endGameDialogue = new EndGameDialogue(MyApplication.getContext(), this, baseChronometer);



        getViewState().showDialogueFragment(endGameDialogueFragment);
//        getViewState().setAlertDialog(endGameDialogue.getAlertDialog());
    }
///////////////////////////

//    public Intent getTableActivityIntent() {
//        return getViewState().getIntent();
//    }


////////////////////
    //Восстановление
    public void preparingToRotateScreen() {
        getViewState().removeTable();

        if (!isDialogueShow) {
            saveTime = baseChronometer - SystemClock.elapsedRealtime();
            getViewState().stopChronometer();
        }

        if (isDialogueShow)
//            endGameDialogue.dismiss();


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
        settings = new PreferencesReader(MyApplication.getContext());
        refreshTableCreator();
        showTable();
        getViewState().setBaseChronometer(SystemClock.elapsedRealtime() + saveTime);

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
            tableCreator = new TableCreator(MyApplication.getContext(), this, listLetters1, listLetters2);

        } else {
            tableCreator = new TableCreator(MyApplication.getContext(), listNumbers1, listNumbers2, this);
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
                getViewState().setMoveHint(nextMoveFirstTable);
            } else if (activeTable == secondTable.getId())
                getViewState().setMoveHint(nextMoveSecondTableCountdown);


        } else if (settings.getIsLetters()) {
            if (activeTable == firstTable.getId()) {
                getViewState().setMoveHint((char) nextMoveFirstTable);
            } else if (activeTable == secondTable.getId())
                getViewState().setMoveHint((char) nextMoveSecondTableCountdown);
        }
    }


    private void restoreSettingForTwoTables() {
        firstTable = (TableLayout) table.getChildAt(0);
        secondTable = (TableLayout) table.getChildAt(2);


        if (saveNumberActiveTable == 0) {
            activeTable = firstTable.getId();
            firstTable.setBackgroundColor(ContextCompat.getColor(MyApplication.getContext(), R.color.activeTable));
            secondTable.setBackgroundColor(ContextCompat.getColor(MyApplication.getContext(), R.color.passiveTable));

        } else if (saveNumberActiveTable == 1) {
            activeTable = secondTable.getId();
            firstTable.setBackgroundColor(ContextCompat.getColor(MyApplication.getContext(), R.color.passiveTable));
            secondTable.setBackgroundColor(ContextCompat.getColor(MyApplication.getContext(), R.color.activeTable));
        }
    }
/////////////////



    public void cancelDialogue() {
        isDialogueShow = false;
    }

    public long getSaveTime() {
        return saveTime;
    }

    private void showTable() {
        getViewState().showTable(table);
    }

    private void startChronometer() {
        getViewState().startChronometer();
    }


    private void showToastWrongTable() {
        Toast toast = Toast.makeText(MyApplication.getContext(), R.string.wrongTable, Toast.LENGTH_SHORT);
        toast.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 500);
    }

    public void onNegativeCancelDialogue() {
        getViewState().setBaseChronometer(SystemClock.elapsedRealtime() + saveTime);
        getViewState().startChronometer();
        cancelDialogue();
    }
}