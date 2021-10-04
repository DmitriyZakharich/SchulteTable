package ru.schultetabledima.schultetable.settings;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.statistic.MyAdapter;
import ru.schultetabledima.schultetable.table.TableActivity;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    SettingsPresenter settingsPresenter;

    private SwitchMaterial switchMoveHint;
    private SwitchMaterial switchAnimation, switchTouchCells, switchTwoTables;
    private ViewPager2 viewPager;
    private NumbersFragment numbersFragment;
    private LettersFragment lettersFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        switchAnimation = findViewById(R.id.switchAnimation);
        switchTouchCells = findViewById(R.id.switchPressButtons);
        switchTwoTables = findViewById(R.id.switchTwoTables);
        switchMoveHint = findViewById(R.id.switchMoveHint);

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.viewPager);

        viewPager.setSaveEnabled(false);

        switchAnimation.setOnClickListener(this);
        switchTouchCells.setOnClickListener(this);
        switchTwoTables.setOnClickListener(this);
        switchMoveHint.setOnClickListener(this);
        findViewById(R.id.buttonToTable).setOnClickListener(clickListener);

        numbersFragment = NumbersFragment.newInstance();
        lettersFragment = LettersFragment.newInstance();

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(numbersFragment);
        fragments.add(lettersFragment);


        int indexOfNumbers = fragments.indexOf(numbersFragment);
        int indexOfLetters = fragments.indexOf(lettersFragment);

        MyAdapter pageAdapter = new MyAdapter(this);

        pageAdapter.setListFragments(fragments);
        viewPager.setAdapter(pageAdapter);



        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == indexOfNumbers)
                tab.setText(R.string.numbers);
            if (position == indexOfLetters)
                tab.setText(R.string.letters);
        });

        tabLayoutMediator.attach();

        settingsPresenter = new SettingsPresenter(this);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                settingsPresenter.onTabSelectedListener(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }


    public void setViewPagerCurrentItem(int index) {
        viewPager.setCurrentItem(index, false);
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


    public void customizationSwitchMoveHint(boolean isEnabled, boolean isChecked) {
        switchMoveHint.setEnabled(isEnabled);

        if (isEnabled) {
            switchMoveHint.setChecked(isChecked);
        } else {
            switchMoveHint.setChecked(false);
        }
    }


    public void switchAnimationSetChecked(boolean isChecked) {
        switchAnimation.setChecked(isChecked);
    }


    public void switchTouchCellsSetChecked(boolean isChecked) {
        switchTouchCells.setChecked(isChecked);
    }


    public void switchTwoTablesSetChecked(boolean isChecked) {
        switchTwoTables.setChecked(isChecked);
    }

    public void switchMoveHintSetChecked(boolean isChecked) {
        switchMoveHint.setChecked(isChecked);
    }
}