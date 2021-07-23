package ru.schultetabledima.schultetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.TextViewCompat;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TableLayout;
import android.widget.TableRow;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

import static ru.schultetabledima.schultetable.ActivityCustomization.APP_PREFERENCES;
import static ru.schultetabledima.schultetable.ActivityCustomization.booleanSwitchAnimation;
import static ru.schultetabledima.schultetable.ActivityCustomization.booleanSwitchTouchСells;
import static ru.schultetabledima.schultetable.ActivityCustomization.sPrefSpinnerColumns;
import static ru.schultetabledima.schultetable.ActivityCustomization.sPrefSpinnerRows;
import static ru.schultetabledima.schultetable.ActivityCustomization.sPrefСustomization;

public class TableActivity extends AppCompatActivity {

    private TableRow tableRowMenu;
    private TableLayout tableLayoutTable;
    private Display display;
    private Point size;
    private int width;
    private int height;
    private AppCompatTextView[][] CellOfTable;
    private Button buttonSettings;
    private TableRow []tableRow;
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


        tableRowMenu = (TableRow) findViewById(R.id.tablerowmenu);
        buttonSettings = (Button)findViewById(R.id.buttonSettings);
        tableRowForTable = (TableRow) findViewById(R.id.TableRowForTable);
        buttonSettings.setOnClickListener(openSettings);



        //секундомер
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.start();





        //Чтение настроек
        sPrefСustomization = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        int savedIntColumns = sPrefСustomization.getInt(sPrefSpinnerColumns, 5);
        int savedIntRows = sPrefСustomization.getInt(sPrefSpinnerRows, 5);
        booleanAnim = sPrefСustomization.getBoolean(booleanSwitchAnimation, false);
        booleanTouchСells = sPrefСustomization.getBoolean(booleanSwitchTouchСells, false);

        columnsOfTable = savedIntColumns + 1;
        stringsOfTable = savedIntRows + 1;


        count = columnsOfTable * stringsOfTable;
        countSave = count;


        TableCreator tableCreator = new TableCreator(stringsOfTable, columnsOfTable, this);
        tableLayoutTable = tableCreator.getTableLayoutTable();
        tableRow = tableCreator.getTableRow();
        CellOfTable = tableCreator.getCellOfTable();

        tableRowForTable.addView(tableLayoutTable);



        //получение размеров tableRowMenu
        //для вычитания из высоты экрана
        tableRowMenu.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

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
        CellOfTable[0][0].post(new Runnable() {
            @Override
            public void run() {
                if(booleanMoreTenCells){
                    int  tenCellTextSize = getSP(CellOfTable[stringCellTenTextSize][columnCellTenTextSize].getTextSize());
                    for (int i = 0; i < stringsOfTable; i++) {
                        for (int j = 0; j < columnsOfTable; j++) {
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(CellOfTable[i][j],1,
                                    tenCellTextSize, 1, TypedValue.COMPLEX_UNIT_SP);
                        }
                    }
                }
            }
        });
}

    View.OnClickListener openSettings = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(TableActivity.this, ActivityCustomization.class));
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
                        EndGameDialogue endGameDialogue = new EndGameDialogue(TableActivity.this,
                                chronometer, booleanTouchСells, chronometer.getBase() - SystemClock.elapsedRealtime(),
                                columnsOfTable, stringsOfTable);
                        endGameDialogue.start();
                    }
                }

            }else {
                chronometer.stop();
                EndGameDialogue endGameDialogue = new EndGameDialogue(TableActivity.this,
                        chronometer, booleanTouchСells, chronometer.getBase()- SystemClock.elapsedRealtime(),
                        columnsOfTable, stringsOfTable);
                endGameDialogue.start();
            }
        }
    };

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
        CellOfTable[0][0].post(new Runnable() {
            @Override
            public void run() {
                if(booleanMoreTenCells){
                    int  tenCellTextSize = getSP(CellOfTable[stringCellTenTextSize][columnCellTenTextSize].getTextSize());
                    for (int i = 0; i < stringsOfTable; i++) {
                        for (int j = 0; j < columnsOfTable; j++) {
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(CellOfTable[i][j],1,
                                    tenCellTextSize, 1, TypedValue.COMPLEX_UNIT_SP);
                        }
                    }
                }
            }
        });

    }


    public int getSP(float px){
        float sp = px / getResources().getDisplayMetrics().scaledDensity;
        return (int) sp;
    }
}