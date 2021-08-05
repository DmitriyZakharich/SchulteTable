package ru.schultetabledima.schultetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.TextViewCompat;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Random;

import static ru.schultetabledima.schultetable.ActivityCustomization.APP_PREFERENCES;
import static ru.schultetabledima.schultetable.ActivityCustomization.PREFERENCES_KEY_ANIMATION;
import static ru.schultetabledima.schultetable.ActivityCustomization.PREFERENCES_KEY_TOUCH_СELLS;
import static ru.schultetabledima.schultetable.ActivityCustomization.PREFERENCES_KEY_NUMBER_COLUMNS;
import static ru.schultetabledima.schultetable.ActivityCustomization.PREFERENCES_KEY_NUMBER_ROWS;
import static ru.schultetabledima.schultetable.ActivityCustomization.sPrefСustomization;

public class TableActivity extends AppCompatActivity {

    private TableRow tableRowMenuShow, tableRowMenuHide;
    private TableLayout tableLayoutTable;
    private Display display;
    private Point size;
    private int width;
    private int height;
    private AppCompatTextView[][] CellOfTable;
    private ImageButton imageButtonSettings, imageButtonStatistics;
    private int stringsOfTable;
    private int columnsOfTable;
    private int count;
    private int countSave;
    private int nextMove = 1;
    private Animation anim;
    private ArrayList<Integer> listNumber;
    private final String saveArrayOfNumbers ="ArrayOfNumbers";
    private final String saveCountSave ="strCountSave";
    private boolean booleanTouchСells;
    private HashSet <Integer> hsRandCellAnim;
    private boolean booleanAnim;

    private int randAnimInt;
    private Chronometer chronometer;

    private int stringCellTenTextSize;
    private int columnCellTenTextSize;
    private boolean booleanMoreTenCells = false;
    private final String saveStringCellTextSize = "strStrCellTextSize";
    private final String saveColumnCellTextSize = "strColumnCellTextSize";
    private final String saveBooleanMoreTenCells = "saveBooleanMoreTenCells";
    private TableRow tableRowForTable;
    private final String saveChronometer = "saveChronometer";
    private ImageButton imageButtonToShowMenu, imageButtonToHideMenu;
    ConstraintLayout constraintLayoutForMenu;

    public static SharedPreferences sharedPreferencesMenu;
    public static final String MENU_PREFERENCES = "PreferencesMenu";
    static final String KEY_MENU_VISIBILITY = "Saved Menu Visibility";
    private boolean isMenuShow;





////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Доделать
    //добавить кнопку паузы
////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);


        Resources resources = this.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int navigationBarHeight = resources.getDimensionPixelSize(resourceId);


        constraintLayoutForMenu = (ConstraintLayout)findViewById(R.id.constraintLayout_for_menu);
        tableRowMenuShow = (TableRow) findViewById(R.id.tablerow_menu_show);
        tableRowMenuHide = (TableRow) findViewById(R.id.tablerowmenu_hide);
        imageButtonToShowMenu = (ImageButton)findViewById(R.id.image_Button_To_Show);
        imageButtonToHideMenu = (ImageButton)findViewById(R.id.image_Button_To_Hide);


        imageButtonSettings = (ImageButton) findViewById(R.id.image_button_settings);
        imageButtonStatistics = (ImageButton) findViewById(R.id.image_button_statistics);
        tableRowForTable = (TableRow) findViewById(R.id.TableRowForTable);

        imageButtonSettings.setOnClickListener(openActivity);
        imageButtonStatistics.setOnClickListener(openActivity);



        //секундомер
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.start();



        //Чтение настроек таблицы
        sPrefСustomization = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        int savedIntColumns = sPrefСustomization.getInt(PREFERENCES_KEY_NUMBER_COLUMNS, 4);
        int savedIntRows = sPrefСustomization.getInt(PREFERENCES_KEY_NUMBER_ROWS, 4);
        booleanAnim = sPrefСustomization.getBoolean(PREFERENCES_KEY_ANIMATION, false);
        booleanTouchСells = sPrefСustomization.getBoolean(PREFERENCES_KEY_TOUCH_СELLS, false);

        //Чтение настроек строки меню
        sharedPreferencesMenu = getSharedPreferences(MENU_PREFERENCES, MODE_PRIVATE);
        isMenuShow = sharedPreferencesMenu.getBoolean(KEY_MENU_VISIBILITY, true);






        columnsOfTable = savedIntColumns + 1;
        stringsOfTable = savedIntRows + 1;


        count = columnsOfTable * stringsOfTable;
        countSave = count;


        TableCreator tableCreator = new TableCreator(stringsOfTable, columnsOfTable, this);
        tableLayoutTable = tableCreator.getTableLayoutTable();
        CellOfTable = tableCreator.getCellOfTable();

        tableRowForTable.addView(tableLayoutTable);



        //получение размеров tableRowMenu
        //для вычитания из высоты экрана
        tableRowMenuShow.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

////Возможно удалить
//        //Попытка узнать ширину экрана
//        //для установки ширины кнопок
//        display = getWindowManager().getDefaultDisplay();
//        size = new Point();
//        display.getSize(size);
//        width = size.x;
//        //высота экрана - высота панели настроек
//        height = size.y - tableRowMenu.getMeasuredHeight() - navigationBarHeight;
//
//        Log.d("tableRowMen","-" + buttonSettings.getMeasuredHeight());


        //Рандом для анимации
        Random random = new Random();

        int numberCellAnim = (columnsOfTable * stringsOfTable)/2;
        Log.d("randAnim","numberCellAnim = " + numberCellAnim);

        hsRandCellAnim = new HashSet<Integer>();

        hsRandCellAnim.add(10);

        for (int i = 0; i < numberCellAnim; i++) {
            randAnimInt = random.nextInt(columnsOfTable * stringsOfTable + 1); //????
            if (!hsRandCellAnim.add(Integer.valueOf(randAnimInt))){
                i--;
            }
        }




//        Log.d("randAnim","randAnimInt = " + randAnimInt);
//        Log.d("randAnim","columns = " + columns);
//        Log.d("randAnimstr","str = " + str);
//
//        int[] randAnimX = new int[randAnimInt];
//        int[] randAnimY = new int[randAnimInt];
//
//        for (int i = 0; i < randAnimInt; i++) {
//            randAnimX [i] = random.nextInt(columns);
//            randAnimY [i] = random.nextInt(str);
//
//            if ((Arrays.asList(randAnimX).contains(randAnimX [i]))  &
//                    (Arrays.asList(randAnimY).contains(randAnimY [i])) ) {
//                        i--;
//            }
//        }
//
//        for (int i = 0; i < randAnimInt; i++) {
//            Log.d("randAnim","x = " + randAnimX [i] + " " + randAnimY [i]);
//        }

//        LayoutInflater layoutInflaterButton = getLayoutInflater();
//        Button buttonTemplate = (Button) layoutInflaterButton.inflate(R.layout.mybutton, tableRow[0] ,false);
//
//        tableRow[0].removeAllView();
//        for (int i = 0; i < columns; i++) {
//            tableRow[0].addView(buttonTemplate);
//            buttonTemplate.setText(""+ columns*str);
//        }
//        ViewGroup.LayoutParams lp= buttonTemplate.getLayoutParams();
//        int textSize = (int) buttonTemplate.getTextSize();
//        tableRow[0].removeAllViews();



        //Заполнение массива
        listNumber = new ArrayList();
        for (int j = 1; j <= columnsOfTable * stringsOfTable; j++ ) {
            listNumber.add( j );
        }
        Collections.shuffle( listNumber );


        /**
        Установка обработчика кнопки
        Текста
        Анимация
         */
        for (int i = 0; i < stringsOfTable; i++) {
            for (int j = 0; j < columnsOfTable; j++) {
                CellOfTable[i][j].setOnClickListener(cellClick);


                CellOfTable[i][j].setText("" + listNumber.get(stringsOfTable * columnsOfTable - count)); //может быть добавить просто счеткик i++?

                if (CellOfTable[i][j].getText().toString().equals("10")){
                    stringCellTenTextSize = i;
                    columnCellTenTextSize = j;
                    booleanMoreTenCells = true;
                }
                count--;

                //Анимация
                if (booleanAnim){
                    if (hsRandCellAnim.contains(Integer.parseInt(CellOfTable[i][j].getText().toString()))) {
                        anim = AnimationUtils.loadAnimation(this, R.anim.myrotate);
                        CellOfTable[i][j].startAnimation(anim);
                        Log.d("buttonstartAnimation",""+ CellOfTable[i][j].getText().toString());
                    }
                }
            }
        }

        /**
         * Ожидание отрисовки таблицы для получения размеров
         * корректировка размеров шрифта по 10й ячейке
         */
        if(booleanMoreTenCells) {
            CellOfTable[0][0].post(new Runnable() {
                @Override
                public void run() {
                    int tenCellTextSize = getSP(CellOfTable[stringCellTenTextSize][columnCellTenTextSize].getTextSize());
                    for (int i = 0; i < stringsOfTable; i++) {
                        for (int j = 0; j < columnsOfTable; j++) {
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(CellOfTable[i][j], 1,
                                    tenCellTextSize, 1, TypedValue.COMPLEX_UNIT_SP);
                        }
                    }
                }
            });
        }



        if(isMenuShow){
            tableRowMenuShow.setVisibility(View.VISIBLE);
            tableRowMenuHide.setVisibility(View.GONE);
        }else{
            tableRowMenuShow.setVisibility(View.GONE);
            tableRowMenuHide.setVisibility(View.VISIBLE);
        }
        imageButtonToHideMenu.setOnClickListener(methodShowHideMenu);
        imageButtonToShowMenu.setOnClickListener(methodShowHideMenu);





}

    View.OnClickListener methodShowHideMenu = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Animation animation;
            SharedPreferences.Editor ed;
            ed = sharedPreferencesMenu.edit();

            switch (v.getId()){
                case R.id.image_Button_To_Show:
                    animation = AnimationUtils.loadAnimation(TableActivity.this, R.anim.scale_menu_show);
                    constraintLayoutForMenu.startAnimation(animation);

//                    constraintLayoutForMenu.postDelayed(new Runnable() {
//                                                            @Override
//                                                            public void run() {
//
//                                                            }
//                                                        }
//                            , 500);

                    tableRowMenuShow.setVisibility(View.VISIBLE);
                    tableRowMenuHide.setVisibility(View.GONE);


                    isMenuShow = true;
                    ed.putBoolean(KEY_MENU_VISIBILITY, isMenuShow);
                    ed.apply();
                    break;

                case R.id.image_Button_To_Hide:
                    animation = AnimationUtils.loadAnimation(TableActivity.this, R.anim.scale_menu_hide);
                    constraintLayoutForMenu.startAnimation(animation);

                    constraintLayoutForMenu.postDelayed(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                tableRowMenuShow.setVisibility(View.GONE);
                                                                tableRowMenuHide.setVisibility(View.VISIBLE);
                                                            }
                                                        }
                            , 500);


                    isMenuShow = false;
                    ed.putBoolean(KEY_MENU_VISIBILITY, isMenuShow);
                    ed.apply();
                    break;
            }
        }
    };


    View.OnClickListener openActivity = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.image_button_settings:
                    startActivity(new Intent(TableActivity.this, ActivityCustomization.class));
                    break;
                case R.id.image_button_statistics:
                    startActivity(new Intent(TableActivity.this, ActivityStatistics.class));
                    break;
            }
        }
    };


    //Обработчик для ячеек таблицы
    View.OnClickListener cellClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (booleanTouchСells){

                if(nextMove == Integer.parseInt ("" + ((AppCompatTextView)v).getText())){
                    nextMove++;

                    if (nextMove == (countSave+1)){
                        chronometer.stop();
                        endGameDialogueStart();
                    }
                }
            }else {
                chronometer.stop();
                endGameDialogueStart();
            }
        }
    };

    void endGameDialogueStart(){
        Date currentDate = new Date();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yy", Locale.getDefault());
        String dateText = dateFormat.format(currentDate);

        EndGameDialogue endGameDialogue = new EndGameDialogue(TableActivity.this,
                chronometer, booleanTouchСells, chronometer.getBase()- SystemClock.elapsedRealtime(),
                columnsOfTable, stringsOfTable, dateText);
        endGameDialogue.start();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    //Сохранение информации при поворатах Активити
    // сохранение состояния
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putIntegerArrayList(saveArrayOfNumbers, listNumber);
        outState.putInt(saveCountSave, countSave);

        outState.putInt(saveStringCellTextSize, stringCellTenTextSize);
        outState.putInt(saveColumnCellTextSize, columnCellTenTextSize);
        outState.putBoolean(saveBooleanMoreTenCells, booleanMoreTenCells);

        chronometer.stop();
        outState.putLong(saveChronometer, chronometer.getBase() - SystemClock.elapsedRealtime());


        super.onSaveInstanceState(outState);
    }
    // получение ранее сохраненного состояния
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        listNumber = savedInstanceState.getIntegerArrayList(saveArrayOfNumbers);
        count = savedInstanceState.getInt(saveCountSave);
        stringCellTenTextSize = savedInstanceState.getInt(saveStringCellTextSize);
        columnCellTenTextSize = savedInstanceState.getInt(saveColumnCellTextSize);
        booleanMoreTenCells = savedInstanceState.getBoolean(saveBooleanMoreTenCells);

        chronometer.setBase(SystemClock.elapsedRealtime() + savedInstanceState.getLong(saveChronometer));
        chronometer.start();


        for (int i = 0; i < stringsOfTable; i++) {
            for (int j = 0; j < columnsOfTable; j++) {
                CellOfTable[i][j].setText("" + listNumber.get(stringsOfTable * columnsOfTable - count));
                count--;
            }
        }

        /**
         * Ожидание отрисовки таблицы для получения размеров
         * корректировка размеров шрифта по 10й ячейке
         */
        if(booleanMoreTenCells) {
            CellOfTable[0][0].post(new Runnable() {
                @Override
                public void run() {
                    int tenCellTextSize = getSP(CellOfTable[stringCellTenTextSize][columnCellTenTextSize].getTextSize());
                    for (int i = 0; i < stringsOfTable; i++) {
                        for (int j = 0; j < columnsOfTable; j++) {
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(CellOfTable[i][j], 1,
                                    tenCellTextSize, 1, TypedValue.COMPLEX_UNIT_SP);
                        }
                    }
                }
            });
        }

    }


    public int getSP(float px){
        float sp = px / getResources().getDisplayMetrics().scaledDensity;
        return (int) sp;
    }
}