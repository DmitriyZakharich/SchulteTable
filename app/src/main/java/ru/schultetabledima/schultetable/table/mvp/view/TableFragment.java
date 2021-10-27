package ru.schultetabledima.schultetable.table.mvp.view;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.List;

import moxy.presenter.InjectPresenter;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.common.BaseScreenFragment;
import ru.schultetabledima.schultetable.contracts.TableContract;
import ru.schultetabledima.schultetable.main.MainActivity;
import ru.schultetabledima.schultetable.table.mvp.model.DataCell;
import ru.schultetabledima.schultetable.table.mvp.presenter.TablePresenter;
import ru.schultetabledima.schultetable.table.mvp.view.tablecreation.TableCreator;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

public class TableFragment extends BaseScreenFragment implements TableContract.View,
        EndGameDialogueFragment.PassMeLinkOnObject, TableCreator.PassMeLinkOnPresenter {

    @InjectPresenter
    TablePresenter tablePresenter;

    private ImageButton buttonSettings;
    private Chronometer chronometer;
    private ConstraintLayout placeForTable;
    private ImageButton selectShowHideMenu;
    private TextView moveHint, textMoveHint;
    private ImageButton image_menu;
    private Toolbar toolbar;
    private AppCompatTextView[][] cells1, cells2;
    private PreferencesReader settings;
    private final int ROTATE_VALUE_ANIMATOR = 3;
    private final int SCALE_VALUE_ANIMATOR = 4;

    public TableFragment() {
        super(R.layout.fragment_table);
    }

    public static TableFragment newInstance() {
        return new TableFragment();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() != null)
            ((MainActivity) getActivity()).visibilityBottomNavigationView(View.GONE);

        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        tablePresenter.setFragmentInFocus(true);
    }

    private void init() {
        selectShowHideMenu = requireView().findViewById(R.id.image_Button_Show_Hide_Menu);
        buttonSettings = requireView().findViewById(R.id.image_button_settings);
        image_menu = requireView().findViewById(R.id.image_menu);
        placeForTable = requireView().findViewById(R.id.placeForTable);
        chronometer = requireView().findViewById(R.id.chronometer);
        moveHint = requireView().findViewById(R.id.moveHint);
        textMoveHint = requireView().findViewById(R.id.textMoveHint);

        toolbar = requireView().findViewById(R.id.toolbar);

        buttonSettings.setOnClickListener(onClickMenuButtonsListener);
        selectShowHideMenu.setOnClickListener(onClickMenuButtonsListener);
        image_menu.setOnClickListener(onClickMenuButtonsListener);

        settings = new PreferencesReader();
    }

    public void createTable() {
        TableCreator tableCreator = new TableCreator(this, getActivity(), tablePresenter);
        LinearLayout containerWithTables = tableCreator.getContainerForTables();
        placeForTable.addView(containerWithTables);

        cells1 = tableCreator.getCellsFirstTable();

        if (settings.getIsTwoTables()) {
            cells2 = tableCreator.getCellsSecondTable();
        }
    }

    @Override
    public void setTableData(List<DataCell> dataCellsFirstTable, List<DataCell> dataCellsSecondTable) {

        fillingTable(cells1, dataCellsFirstTable);

        if (settings.getIsTwoTables()) {
            fillingTable(cells2, dataCellsSecondTable);
        }

        if (settings.getIsAnim()) {
            addAnimationGame(dataCellsFirstTable);
            if (settings.getIsTwoTables()) {
                addAnimationGame(dataCellsSecondTable);
            }
        }
    }

    private void fillingTable(AppCompatTextView[][] cells, List<DataCell> dataCells) {
        int count = 0;

        for (int i = 0; i < settings.getRowsOfTable(); i++) {
            for (int j = 0; j < settings.getColumnsOfTable(); j++) {

                cells[i][j].setId(dataCells.get(count).getId());

                if (settings.getIsLetters()) {
                    cells[i][j].setText(String.valueOf((char) (dataCells.get(count).getValue())));

                } else {
                    cells[i][j].setText(String.valueOf(dataCells.get(count).getValue()));
                }

                count++;
            }
        }
    }

    @SuppressLint("RestrictedApi")
    private void addAnimationGame(List<DataCell> dataCells) {

        List<Animation> animationList = new ArrayList<>(3);
        animationList.add(AnimationUtils.loadAnimation(getActivity(), R.anim.myrotate));
        animationList.add(AnimationUtils.loadAnimation(getActivity(), R.anim.myalpha));
        animationList.add(AnimationUtils.loadAnimation(getActivity(), R.anim.myscale));

        for (int i = 0; i < dataCells.size(); i++) {

            if (dataCells.get(i).getTypeAnimation() >= 0 && dataCells.get(i).getTypeAnimation() <= 2) {

                Animation anim = animationList.get(dataCells.get(i).getTypeAnimation());
                requireView().findViewById(dataCells.get(i).getId()).startAnimation(anim);

            } else {

                if (dataCells.get(i).getTypeAnimation() == ROTATE_VALUE_ANIMATOR)
                    new CustomRotateValueAnimator(this, dataCells.get(i).getId());

                if (dataCells.get(i).getTypeAnimation() == SCALE_VALUE_ANIMATOR) {
                    new CustomScaleValueAnimator(this, dataCells.get(i).getId());
                }
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public void setTableColor(int backgroundResourcesFirstTable, int backgroundResourcesSecondTable) {

        for (int i = 0; i < settings.getRowsOfTable(); i++) {
            for (int j = 0; j < settings.getColumnsOfTable(); j++) {
                cells1[i][j].setBackground(this.getResources().getDrawable(backgroundResourcesFirstTable));
                cells2[i][j].setBackground(this.getResources().getDrawable(backgroundResourcesSecondTable));
            }
        }
    }

    @Override
    public void showToastWrongTable(int wrongTable) {
        Toast toast = Toast.makeText(getActivity(), wrongTable, Toast.LENGTH_SHORT);
        toast.show();
        new Handler().postDelayed(toast::cancel, 500);
    }


    @Override
    public void setAnimationToolbar(LayoutTransition layoutTransition) {
        toolbar.setLayoutTransition(layoutTransition);
    }

    View.OnClickListener onClickMenuButtonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tablePresenter.onClickMenuButtonsListener(v.getId());
        }
    };


    @Override
    public void showHideMenu(int visibility, int visibilityHint, int imageResource, LinearLayout.LayoutParams layoutParams) {
        chronometer.setVisibility(visibility);
        buttonSettings.setVisibility(visibility);
        image_menu.setVisibility(visibility);

        textMoveHint.setVisibility(visibilityHint);
        moveHint.setVisibility(visibilityHint);

        selectShowHideMenu.setImageResource(imageResource);
        toolbar.setLayoutParams(layoutParams);
    }

    @Override
    public void setMoveHint(int nextMoveFirstTable) {
        moveHint.setText(String.valueOf(nextMoveFirstTable));
    }

    @Override
    public void setMoveHint(char nextMoveFirstTable) {
        moveHint.setText(String.valueOf(nextMoveFirstTable));
    }

    @Override
    public void showDialogueFragment(boolean needToShow) {
        if (needToShow) {
            FragmentManager fragmentManager = getChildFragmentManager();
            EndGameDialogueFragment endGameDialogueFragment = (EndGameDialogueFragment) fragmentManager.findFragmentByTag("dialogueFragment");

            if (endGameDialogueFragment == null) {
                endGameDialogueFragment = EndGameDialogueFragment.newInstance();
                endGameDialogueFragment.show(fragmentManager, "dialogueFragment");
            }
        }
    }

    @Override
    public void stopStartChronometer(boolean startIt) {
        if (startIt)
            chronometer.start();
        else
            chronometer.stop();
    }

    @Override
    public void showPopupMenu() {
        PopupMenuCreator popupMenuCreator = new PopupMenuCreator(getActivity(), image_menu, tablePresenter);
        popupMenuCreator.getPopupMenu().show();
    }

    @Override
    public long getBaseChronometer() {
        return chronometer.getBase();
    }

    @Override
    public void setBaseChronometer(long base, boolean isDialogueShow) {
        if (isDialogueShow) {
            chronometer.setBase(SystemClock.elapsedRealtime() - base);
        } else
            chronometer.setBase(base);
    }

    @Override
    public TablePresenter getTablePresenter() {
        return tablePresenter;
    }

    @Override
    public void setCellColor(int id, int cellColor) {
        requireView().findViewById(id).setBackgroundColor(cellColor);
    }

    @Override
    public void setBackgroundResources(int cellId, int backgroundResources) {
        requireView().findViewById(cellId).setBackground(getActivity().getResources().getDrawable(backgroundResources));
    }

    public void moveFragment(int idActionNavigation, NavOptions navOptions) {
        NavHostFragment.findNavController(this).navigate(idActionNavigation, null, navOptions);
    }

    @Override
    public void clearingTheCommandQueue() {
    }
}
