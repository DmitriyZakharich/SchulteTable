package ru.schultetabledima.schultetable.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.tabs.TabLayout;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.statistic.MyAdapter;
import ru.schultetabledima.schultetable.table.TableActivity;
import ru.schultetabledima.schultetable.table.TablePresenter;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String KEY_SERIALIZABLE_SETTINGS_PRESENTER = "key_serializable_settings_presenter";
    private SwitchMaterial switchMoveHint;
    private SettingsPresenter settingsPresenter;

    private SwitchMaterial switchAnimation, switchTouchCells, switchTwoTables;
    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        switchAnimation = findViewById(R.id.switchAnimation);
        switchTouchCells = findViewById(R.id.switchPressButtons);
        switchTwoTables = findViewById(R.id.switchTwoTables);
        switchMoveHint = findViewById(R.id.switchMoveHint);

        viewPager = (ViewPager2)findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tab_layout);


        switchAnimation.setOnClickListener(this);
        switchTouchCells.setOnClickListener(this);
        switchTwoTables.setOnClickListener(this);
        switchMoveHint.setOnClickListener(this);
        findViewById(R.id.buttonToTable).setOnClickListener(clickListener);

        settingsPresenter = new SettingsPresenter(this);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                settingsPresenter.onTabSelectedListener(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }


    public void setViewPagerAdapter(MyAdapter pageAdapter){
        viewPager.setAdapter(pageAdapter);
    }

    public TabLayout getTabLayout(){
        return tabLayout;
    }

    public ViewPager2 getViewPager(){
        return viewPager;
    }

    public void setViewPagerCurrentItem(int index){
        viewPager.setCurrentItem(index);
    }


    View.OnClickListener clickListener = v -> {
        int id = v.getId();
        if (id == R.id.buttonToTable) {
            startActivity(new Intent(SettingsActivity.this, TableActivity.class));
        }
    };


    @Override
    public void onClick(View v) {
        settingsPresenter.onClickListenerSwitch(v.getId(), ((SwitchMaterial) v).isChecked());
    }


    public void customizationSwitchMoveHint(boolean isEnabled, boolean isChecked){
        switchMoveHint.setEnabled(isEnabled);

        if (isEnabled){
            switchMoveHint.setChecked(isChecked);
        }else{
            switchMoveHint.setChecked(false);
        }
    }

    public void switchAnimationSetChecked(boolean isChecked){
        switchAnimation.setChecked(isChecked);
    }

    public void switchTouchCellsSetChecked(boolean isChecked){
        switchTouchCells.setChecked(isChecked);
    }

    public void switchTwoTablesSetChecked(boolean isChecked){
        switchTwoTables.setChecked(isChecked);
    }

    public void switchMoveHintSetChecked(boolean isChecked){
        switchMoveHint.setChecked(isChecked);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        settingsPresenter.detachView();
        outState.putSerializable(KEY_SERIALIZABLE_SETTINGS_PRESENTER, settingsPresenter);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        settingsPresenter = (SettingsPresenter) savedInstanceState.getSerializable(KEY_SERIALIZABLE_SETTINGS_PRESENTER);
        settingsPresenter.attachView(this);
    }

    public SettingsPresenter getPresenter() {
        return settingsPresenter;
    }
}