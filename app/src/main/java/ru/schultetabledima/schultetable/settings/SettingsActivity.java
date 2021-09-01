package ru.schultetabledima.schultetable.settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.table.TableActivity;
import ru.schultetabledima.schultetable.statistic.MyAdapter;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {


    private static SharedPreferences settings;
    private static final String APP_PREFERENCES = "my_settings";

    private static final String KEY_ANIMATION = "switchAnimation";
    private static final String KEY_TOUCH_CELLS = "switchTouchCells";
    private static final String KEY_NUMBERS_OR_LETTERS = "switchNumbersLetters";
    private static final String KEY_RUSSIAN_OR_ENGLISH = "switchRussianOrEnglish";
    private static final String KEY_TWO_TABLES = "switchTwoTables";
    private static final String KEY_MOVE_HINT = "switchMoveHint";
    private static final String KEY_ROWS_NUMBERS = "saveSpinnerRowsNumbers";
    private static final String KEY_COLUMNS_NUMBERS = "saveSpinnerColumnsNumbers";
    private static final String KEY_ROWS_LETTERS = "saveSpinnerRowsLetters";
    private static final String KEY_COLUMNS_LETTERS = "saveSpinnerColumnsLetters";

    SwitchMaterial switchMoveHint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        SwitchMaterial switchAnimation = findViewById(R.id.switchAnimation);
        SwitchMaterial switchTouchCells = findViewById(R.id.switchPressButtons);
        SwitchMaterial switchTwoTables = findViewById(R.id.switchTwoTables);
        switchMoveHint = findViewById(R.id.switchMoveHint);
        Button buttonToTable = findViewById(R.id.buttonToTable);

        settings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);


        switchAnimation.setChecked(settings.getBoolean(KEY_ANIMATION, false));
        switchTouchCells.setChecked(settings.getBoolean(KEY_TOUCH_CELLS, true));
        switchTwoTables.setChecked(settings.getBoolean(KEY_TWO_TABLES, false));
        boolean isLetters = settings.getBoolean(KEY_NUMBERS_OR_LETTERS, false);

        if (switchTouchCells.isChecked()){
            switchMoveHint.setEnabled(true);
            switchMoveHint.setChecked(settings.getBoolean(KEY_MOVE_HINT, true));
        }
        else{
            switchMoveHint.setChecked(false);
            switchMoveHint.setEnabled(false);
        }



        switchAnimation.setOnClickListener(this);
        switchTouchCells.setOnClickListener(this);
        switchTwoTables.setOnClickListener(this);
        switchMoveHint.setOnClickListener(this);
        buttonToTable.setOnClickListener(this);


        ArrayList<Fragment> fragments = new ArrayList<>();

        NumbersFragment numbersFragment = NumbersFragment.newInstance();
        LettersFragment lettersFragment = LettersFragment.newInstance();
        fragments.add(numbersFragment);
        fragments.add(lettersFragment);

        int indexOfNumbers = fragments.indexOf(numbersFragment);
        int indexOfLetters = fragments.indexOf(lettersFragment);


        ViewPager2 viewPager = (ViewPager2)findViewById(R.id.viewPager);
        MyAdapter pageAdapter = new MyAdapter(this);
        pageAdapter.setListFragments(fragments);
        viewPager.setAdapter(pageAdapter);


        TabLayout tabLayout = findViewById(R.id.tab_layout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager, new TabLayoutMediator.TabConfigurationStrategy(){

            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                if (position == indexOfNumbers)
                    tab.setText(R.string.numbers);
                if (position == indexOfLetters)
                    tab.setText(R.string.letters);
            }
        });
        tabLayoutMediator.attach();


        if (!isLetters)
            viewPager.setCurrentItem(indexOfNumbers);
        else
            viewPager.setCurrentItem(indexOfLetters);


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                SharedPreferences.Editor ed;
                ed = settings.edit();

                if (tab.getPosition() == indexOfNumbers){
                    ed.putBoolean(KEY_NUMBERS_OR_LETTERS, false);

                } else if(tab.getPosition() == indexOfLetters){
                    ed.putBoolean(KEY_NUMBERS_OR_LETTERS, true);
                }
                ed.apply();
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

    }


    @Override
    public void onClick(View v) {
        SharedPreferences.Editor ed;
        ed = settings.edit();

        int id = v.getId();
        if (id == R.id.switchAnimation) {
            ed.putBoolean(KEY_ANIMATION, ((SwitchMaterial) v).isChecked());

        } else if (id == R.id.switchPressButtons) {
                boolean isPressButtons = ((SwitchMaterial) v).isChecked();
                ed.putBoolean(KEY_TOUCH_CELLS, isPressButtons);

                if (isPressButtons){
                    switchMoveHint.setEnabled(true);
                    switchMoveHint.setChecked(settings.getBoolean(KEY_MOVE_HINT, true));
                }

                else{
                    switchMoveHint.setChecked(false);
                    switchMoveHint.setEnabled(false);
                }


        } else if (id == R.id.switchTwoTables) {
            ed.putBoolean(KEY_TWO_TABLES, ((SwitchMaterial) v).isChecked());

        } else if (id == R.id.switchRussianOrEnglish) {
            ed.putBoolean(KEY_RUSSIAN_OR_ENGLISH, ((SwitchMaterial) v).isChecked());

        } else if (id == R.id.switchMoveHint) {
            ed.putBoolean(KEY_MOVE_HINT, ((SwitchMaterial) v).isChecked());

        }
        ed.apply();


         if (id == R.id.buttonToTable) {
            startActivity(new Intent(SettingsActivity.this, TableActivity.class));
        }


    }

    public static String getAppPreferences() {
        return APP_PREFERENCES;
    }
    public static String getKeyAnimation() {
        return KEY_ANIMATION;
    }
    public static String getKeyTouchCells() {
        return KEY_TOUCH_CELLS;
    }
    public static String getKeyNumbersOrLetters() {
        return KEY_NUMBERS_OR_LETTERS;
    }
    public static String getKeyTwoTables() {
        return KEY_TWO_TABLES;
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
    public static String getKeyMoveHint() {
        return KEY_MOVE_HINT;
    }
}