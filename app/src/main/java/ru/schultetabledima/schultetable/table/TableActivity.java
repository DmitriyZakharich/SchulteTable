package ru.schultetabledima.schultetable.table;

import android.animation.LayoutTransition;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.contracts.TableContract;
import ru.schultetabledima.schultetable.table.tablecreation.CustomCell;
import ru.schultetabledima.schultetable.table.tablecreation.DataCell;
import ru.schultetabledima.schultetable.table.tablecreation.TableCreator;
import ru.schultetabledima.schultetable.utils.PreferencesReader;


public class TableActivity extends MvpAppCompatActivity implements TableContract.View, EndGameDialogueFragment.PassMeLinkOnObject,
        PopupMenu.OnMenuItemClickListener {

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
    private LinearLayout containerWithTables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);

        selectShowHideMenu = findViewById(R.id.image_Button_Show_Hide_Menu);
        buttonSettings = findViewById(R.id.image_button_settings);
        image_menu = findViewById(R.id.image_menu);
        placeForTable = findViewById(R.id.placeForTable);
        chronometer = findViewById(R.id.chronometer);
        moveHint = findViewById(R.id.moveHint);
        textMoveHint = findViewById(R.id.textMoveHint);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        buttonSettings.setOnClickListener(onClickMenuButtonsListener);
        selectShowHideMenu.setOnClickListener(onClickMenuButtonsListener);
        image_menu.setOnClickListener(onClickMenuButtonsListener);

        settings = new PreferencesReader();


        TableCreator tableCreator = new TableCreator(this, tablePresenter);
        containerWithTables = tableCreator.getContainerForTables();
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

    @Override
    public void setTableColor(int table_id, int color) {
        containerWithTables.getChildAt(table_id).setBackgroundColor(getResources().getColor(color));
    }

    @Override
    public void showToastWrongTable(int wrongTable) {
        Toast toast = Toast.makeText(this, wrongTable, Toast.LENGTH_SHORT);
        toast.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, 500);
    }


    @SuppressLint("RestrictedApi")
    private void addAnimationGame(List<DataCell> dataCells) {

        List<Animation> animationList = new ArrayList<>(3);
        animationList.add(AnimationUtils.loadAnimation(this, R.anim.myrotate));
        animationList.add(AnimationUtils.loadAnimation(this, R.anim.myalpha));
        animationList.add(AnimationUtils.loadAnimation(this, R.anim.myscale));

        for (int i = 0; i < dataCells.size(); i++) {
            if (dataCells.get(i).getTypeAnimation() <= 2) {
                Animation anim = animationList.get(dataCells.get(i).getTypeAnimation());
                findViewById(dataCells.get(i).getId()).startAnimation(anim);
            } else {

                if (dataCells.get(i).getTypeAnimation() == 3)
                    new CustomRotateValueAnimator(dataCells.get(i).getId());

                if (dataCells.get(i).getTypeAnimation() == 4) {
                    new CustomScaleValueAnimator(dataCells.get(i).getId());
                }
            }
        }
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
    public void removeTable() {
        placeForTable.removeAllViews();
    }

    @Override
    public void showDialogueFragment(boolean isShow) {

        if (isShow) {

            FragmentManager fragmentManager = getSupportFragmentManager();
            EndGameDialogueFragment endGameDialogueFragment = (EndGameDialogueFragment) fragmentManager.findFragmentByTag("custom");

            if (endGameDialogueFragment == null) {
                endGameDialogueFragment = EndGameDialogueFragment.newInstance();
                endGameDialogueFragment.show(fragmentManager, "custom");
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
        PopupMenuCreator popupMenuCreator = new PopupMenuCreator(this, image_menu, tablePresenter);
        popupMenuCreator.getPopupMenu().show();
    }


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
    protected void onRestart() {
        super.onRestart();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public TablePresenter getTablePresenter() {
        return tablePresenter;
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        return false;
    }


    private class CustomRotateValueAnimator {

        private int id;

        public CustomRotateValueAnimator(int id) {
            this.id = id;
            init();
        }

        private void init() {
            final float startRotate = -60;
            final float endRotate = 60;
            long animationDuration = 1500;

            ValueAnimator animator = ValueAnimator.ofFloat(startRotate, endRotate);
            animator.setDuration(animationDuration);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setRepeatMode(ValueAnimator.REVERSE);

            animator.addUpdateListener(valueAnimator -> {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                int ROTATE_ANIMATION = 1;
                ((CustomCell) findViewById(id)).setAnimation(animatedValue, ROTATE_ANIMATION);

                ((CustomCell) findViewById(id)).setTextColor(Color.TRANSPARENT);
            });
            animator.start();
        }
    }

    private class CustomScaleValueAnimator implements ObservationContract.CellTextSizeObserver {
        private int id;

        public CustomScaleValueAnimator(int id) {
            this.id = id;
            subscribeSubject();
        }

        private void main() {
            final float startSize = 20;
            final float endSize = ((CustomCell) findViewById(id)).getTextSize();
            long animationDuration = 1900;

            ValueAnimator animator = ValueAnimator.ofFloat(startSize, endSize);
            animator.setDuration(animationDuration);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.setRepeatMode(ValueAnimator.REVERSE);

            animator.addUpdateListener(valueAnimator -> {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                int SCALE_ANIMATION = 2;
                ((CustomCell) findViewById(id)).setAnimation(animatedValue, SCALE_ANIMATION);
                ((CustomCell) findViewById(id)).setTextColor(Color.TRANSPARENT);

            });
            animator.start();
        }


        @Override
        public void updateSubject() {
            main();
        }

        @Override
        public void subscribeSubject() {
            ((CustomCell) findViewById(id)).subscribeObserver(this);
        }

        @Override
        public void unSubscribeSubject() {
            ((CustomCell) findViewById(id)).unSubscribeObserver(this);
        }
    }
}
