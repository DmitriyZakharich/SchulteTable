package ru.schultetabledima.schultetable.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.tabs.TabLayout;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.table.TableActivity;
import ru.schultetabledima.schultetable.statistic.MyAdapter;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private static SharedPreferences settings;
    private static final String APP_PREFERENCES = "my_settings";

    private static final String KEY_RUSSIAN_OR_ENGLISH = "switchRussianOrEnglish";
    private static final String KEY_ROWS_NUMBERS = "saveSpinnerRowsNumbers";
    private static final String KEY_COLUMNS_NUMBERS = "saveSpinnerColumnsNumbers";
    private static final String KEY_ROWS_LETTERS = "saveSpinnerRowsLetters";
    private static final String KEY_COLUMNS_LETTERS = "saveSpinnerColumnsLetters";

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

        settings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);


        switchAnimation.setOnClickListener(this);
        switchTouchCells.setOnClickListener(this);
        switchTwoTables.setOnClickListener(this);
        switchMoveHint.setOnClickListener(this);
        findViewById(R.id.buttonToTable).setOnClickListener(clickListener);


        Log.d("numbersGetActivity", "onCreate ");

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


    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            if (id == R.id.buttonToTable) {
                startActivity(new Intent(SettingsActivity.this, TableActivity.class));
            }
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


    public static String getKeyRussianOrEnglish() {
        return KEY_RUSSIAN_OR_ENGLISH;
    }
    public static String getKeyRowsLetters() {
        return KEY_ROWS_LETTERS;
    }
    public static String getKeyColumnsLetters() {
        return KEY_COLUMNS_LETTERS;
    }
    public static String getKeyColumnsNumbers() {
        return KEY_COLUMNS_NUMBERS;
    }
    public static String getKeyRowsNumbers() {
        return KEY_ROWS_NUMBERS;
    }

}