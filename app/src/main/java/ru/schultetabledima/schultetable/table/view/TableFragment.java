package ru.schultetabledima.schultetable.table.view;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavOptions;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.List;

import moxy.presenter.InjectPresenter;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.common.BaseScreenFragment;
import ru.schultetabledima.schultetable.contracts.TableContract;
import ru.schultetabledima.schultetable.databinding.FragmentTableBinding;
import ru.schultetabledima.schultetable.main.MainActivity;
import ru.schultetabledima.schultetable.table.model.DataCell;
import ru.schultetabledima.schultetable.table.presenter.TablePresenter;
import ru.schultetabledima.schultetable.table.view.tablecreation.TableCreator;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

public class TableFragment extends BaseScreenFragment implements TableContract.View,
        EndGameDialogueFragment.PassMeLinkOnObject, TableCreator.PassMeLinkOnPresenter {

    @InjectPresenter
    TablePresenter tablePresenter;

    private FragmentTableBinding binding;
    private AppCompatTextView[][] cells1, cells2;
    private final int ROTATE_VALUE_ANIMATOR = 3;
    private final int SCALE_VALUE_ANIMATOR = 4;

    public TableFragment() {
        super(R.layout.fragment_table);
    }

    public static TableFragment newInstance() {
        return new TableFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentTableBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getActivity() != null){
            ((MainActivity) getActivity()).visibilityBottomNavigationView(View.GONE);
            ((MainActivity) getActivity()).setWindowFlags("Add_Flag_KEEP_SCREEN_ON");
        }
        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        tablePresenter.setFragmentInFocus(true);
    }

    private void init() {
        binding.imageButtonSettings.setOnClickListener(onClickMenuButtonsListener);
        binding.imageButtonShowHideMenu.setOnClickListener(onClickMenuButtonsListener);
        binding.imageMenu.setOnClickListener(onClickMenuButtonsListener);
    }

    public void createTable() {
        TableCreator tableCreator = new TableCreator(this, getActivity(), tablePresenter);
        LinearLayout containerWithTables = tableCreator.getContainerForTables();
        binding.placeForTable.addView(containerWithTables);

        cells1 = tableCreator.getCellsFirstTable();

        if (PreferencesReader.INSTANCE.isTwoTables()) {
            cells2 = tableCreator.getCellsSecondTable();
        }
    }

    @Override
    public void setTableData(List<DataCell> dataCellsFirstTable, List<DataCell> dataCellsSecondTable) {

        fillingTable(cells1, dataCellsFirstTable);

        if (PreferencesReader.INSTANCE.isTwoTables()) {
            fillingTable(cells2, dataCellsSecondTable);
        }

        if (PreferencesReader.INSTANCE.isAnim()) {
            addAnimationGame(dataCellsFirstTable);
            if (PreferencesReader.INSTANCE.isTwoTables()) {
                addAnimationGame(dataCellsSecondTable);
            }
        }
    }

    private void fillingTable(AppCompatTextView[][] cells, List<DataCell> dataCells) {
        int count = 0;

        for (int i = 0; i < PreferencesReader.INSTANCE.getRowsOfTable(); i++) {
            for (int j = 0; j < PreferencesReader.INSTANCE.getColumnsOfTable(); j++) {

                cells[i][j].setId(dataCells.get(count).getId());

                if (PreferencesReader.INSTANCE.isLetters()) {
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

        for (int i = 0; i < PreferencesReader.INSTANCE.getRowsOfTable(); i++) {
            for (int j = 0; j < PreferencesReader.INSTANCE.getColumnsOfTable(); j++) {
                cells1[i][j].setBackground(this.getResources().getDrawable(backgroundResourcesFirstTable));
                cells2[i][j].setBackground(this.getResources().getDrawable(backgroundResourcesSecondTable));
            }
        }
    }

    @Override
    public void showToast(int wrongTable, int lengthToast) {
        Toast toast = Toast.makeText(getActivity(), wrongTable, lengthToast);
        toast.show();
        new Handler().postDelayed(toast::cancel, 500);
    }


    @Override
    public void setAnimationToolbar(LayoutTransition layoutTransition) {
        binding.toolbar.setLayoutTransition(layoutTransition);
    }

    View.OnClickListener onClickMenuButtonsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            tablePresenter.onClickMenuButtonsListener(v.getId());
        }
    };


    @Override
    public void showHideMenu(int visibility, int visibilityHint, int imageResource, LinearLayout.LayoutParams layoutParams) {
        binding.chronometer.setVisibility(visibility);
        binding.imageButtonSettings.setVisibility(visibility);
        binding.imageMenu.setVisibility(visibility);

        binding.textMoveHint.setVisibility(visibilityHint);
        binding.moveHint.setVisibility(visibilityHint);

        binding.imageButtonShowHideMenu.setImageResource(imageResource);
        binding.toolbar.setLayoutParams(layoutParams);
    }

    @Override
    public void setMoveHint(int nextMoveFirstTable) {
        binding.moveHint.setText(String.valueOf(nextMoveFirstTable));
    }

    @Override
    public void setMoveHint(char nextMoveFirstTable) {
        binding.moveHint.setText(String.valueOf(nextMoveFirstTable));
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
            binding.chronometer.start();
        else
            binding.chronometer.stop();
    }

    @Override
    public void showPopupMenu() {
        PopupMenuCreator popupMenuCreator = new PopupMenuCreator(getActivity(), binding.imageMenu, tablePresenter);
        popupMenuCreator.getPopupMenu().show();
    }

    @Override
    public long getBaseChronometer() {
        return binding.chronometer.getBase();
    }

    @Override
    public void setBaseChronometer(long base, boolean isDialogueShow) {
        if (isDialogueShow) {
            binding.chronometer.setBase(SystemClock.elapsedRealtime() - base);
        } else
            binding.chronometer.setBase(base);
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
    public void clearingTheCommandQueue() {}

    @Override
    public void onDestroyView() {
        ((MainActivity) getActivity()).setWindowFlags("");
        super.onDestroyView();
        binding = null;
    }
}
