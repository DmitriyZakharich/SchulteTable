package ru.schultetabledima.schultetable.settings;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class CustomSharedPreferences {

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

    private Context context;

    public CustomSharedPreferences(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        settings = context.getSharedPreferences(CustomSharedPreferences.getAppPreferences(), MODE_PRIVATE);
    }

    public void putBoolean(String keyAnimation, boolean isChecked) {
        SharedPreferences.Editor ed = settings.edit();
        ed.putBoolean(keyAnimation, isChecked);
        ed.apply();
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
