package ru.schultetabledima.schultetable;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.TextViewCompat;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
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
import static ru.schultetabledima.schultetable.ActivityCustomization.sPref;

public class TableActivity extends AppCompatActivity {

    private TableRow tableRowMenu;
    private TableLayout tableLayoutTable;
    private Display display;
    private Point size;
    private int width;
    private int height;
    private AppCompatTextView[][] buttonCell;
    private Button buttonSettings;
    private TableRow []tableRow;
    private int str;
    private int columns;
    private int count;
    private int countSave;
    private int nextMove = 1;
    private Animation anim;
    private ArrayList<Integer> listNumber;
    private final String saveArrayOfNumbers ="ArrayOfNumbers";
    private final String saveCountSave ="strCountSave";
    private boolean bSavedTouchСells;
    private HashSet <Integer> hsRandCellAnim;
    private boolean bSavedAnim;

    private int randAnimInt;
    private Chronometer chronometer;

    private int stringCellTenTextSize;
    private int columnCellTenTextSize;
    private boolean booleanMoreTenCells = false;
    private final String saveStringCellTextSize = "strStrCellTextSize";
    private final String saveColumnCellTextSize = "strColumnCellTextSize";
    private final String saveBooleanMoreTenCells = "saveBooleanMoreTenCells";


////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Доделать
    // Таймер при повороте
////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table);




        Log.d("BundleS", "" + savedInstanceState);


        Resources resources = this.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int navigationBarHeight = resources.getDimensionPixelSize(resourceId);



        tableRowMenu = (TableRow) findViewById(R.id.tablerowmenu);
        buttonSettings = (Button)findViewById(R.id.buttonSettings);
        tableLayoutTable = (TableLayout)findViewById(R.id.tablelayouttable);


        //секундомер
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.start();


        //Чтение настроек
        sPref = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);
        int savedIntColumns = sPref.getInt(sPrefSpinnerColumns, 5);
        int savedIntRows = sPref.getInt(sPrefSpinnerRows, 5);
        bSavedAnim = sPref.getBoolean(booleanSwitchAnimation, false);
        bSavedTouchСells = sPref.getBoolean(booleanSwitchTouchСells, false);

        columns = savedIntColumns + 1;
        str = savedIntRows + 1;

        count = columns*str;
        countSave = count;

        tableRow = new TableRow[str];

        for (int i = 0; i < tableRow.length; i++) {
            tableRow[i] = new TableRow(this);
            tableRow[i].setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.MATCH_PARENT,1));
            tableLayoutTable.addView(tableRow[i]);
        }

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

        int numberCellAnim = (columns*str)/2;
        Log.d("randAnim","numberCellAnim = " + numberCellAnim);

        hsRandCellAnim = new HashSet<Integer>();

        hsRandCellAnim.add(10);

        for (int i = 0; i < numberCellAnim; i++) {
            randAnimInt = random.nextInt(columns*str + 1); //????
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




        //Создание кнопок
        //и добавление в tableRow
        //анимация
        buttonCell = new AppCompatTextView[str][columns];
        for (int i = 0 ; i < str; i++){
            for (int j = 0 ; j < columns; j++) {
                //создание кнопок через View и LayoutInflater
//                LayoutInflater layoutInflater = getLayoutInflater();
//                View viewButton = layoutInflater.inflate(R.layout.mycell, tableRow[i] ,false);
//                buttonCell[i][j] = (Button) viewButton;
//                buttonCell[i][j].setMaxLines(1);
//                buttonCell[i][j].setPadding(100,100,100,100);


                //Создание кнопок напрямую
                buttonCell[i][j] = new AppCompatTextView(this);
                buttonCell[i][j].setTextColor(Color.BLACK);
                buttonCell[i][j].setBackgroundColor(Color.WHITE);
//                buttonCell[i][j].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                buttonCell[i][j].setMaxLines(1);
//                buttonCell[i][j].setPadding(2,2,2,2);
                TableRow.LayoutParams lpButton = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, 1);
                lpButton.setMargins(2,2,2,2);
                buttonCell[i][j].setLayoutParams(lpButton);
                buttonCell[i][j].setGravity(Gravity.CENTER);

                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    buttonCell[i][j].setPadding(20, 20, 20, 20);
                }
//                buttonCell[i][j].setEllipsize();


//                ViewGroup.LayoutParams params = button[i][j].getLayoutParams();
//                params.width = (width - columns - 1)/columns;
//                params.height = (height - str - 1)/str;
//                button[i][j].setLayoutParams(params);


                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(buttonCell[i][j],1, 100, 1, TypedValue.COMPLEX_UNIT_SP);
//                TextViewCompat.setAutoSizeTextTypeWithDefaults(buttonCell[i][j], TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);


                tableRow[i].addView(buttonCell[i][j]);
                tableRow[i].setBackgroundColor(Color.BLACK);

                buttonCell[i][j].setOnClickListener(cellClick);

            }
        }


        //Заполнение массива
        listNumber = new ArrayList();
        Log.d("listNumber", "size до ------------ " + listNumber.size());
        for (int j = 1; j <= columns*str; j++ ) {
            listNumber.add( j );
            Log.d("listNumber", "listNumber ------------ " + j);
        }
        Collections.shuffle( listNumber );

        Log.d("listNumber", "size после ------------ " + listNumber.size());



        /**
        Установка текста
        Анимация
         */
        for (int i = 0; i < str; i++) {
            for (int j = 0; j < columns; j++) {
                buttonCell[i][j].setText("" + listNumber.get(str*columns - count)); //может быть добавить просто счеткик i++?

                if (buttonCell[i][j].getText().toString().equals("10")){
                    stringCellTenTextSize = i;
                    columnCellTenTextSize = j;
                    booleanMoreTenCells = true;

                    Log.d("buttonCellTen", "TextSize ------------ " + getSP(buttonCell[i][j].getTextSize()));
                    Log.d("buttonCellTen", "string + 1 ------------ " + (stringCellTenTextSize + 1));
                    Log.d("buttonCellTen", "string реальный ------------ " + stringCellTenTextSize);
                    Log.d("buttonCellTen", "column + 1 ------------ " + (columnCellTenTextSize + 1));
                    Log.d("buttonCellTen", "column реальный ------------ " + columnCellTenTextSize);


                }
                count--;

//                button[i][j].setTextSize(buttonTemplate.getTextSize());
//                button[i][j].setLayoutParams(lp);
//                button[i][j].setTextSize(textSize);


                //Анимация
                if (bSavedAnim){
                    if (hsRandCellAnim.contains(Integer.parseInt(buttonCell[i][j].getText().toString()))) {
                        anim = AnimationUtils.loadAnimation(this, R.anim.myrotate);
                        buttonCell[i][j].startAnimation(anim);
                        Log.d("buttonstartAnimation",""+ buttonCell[i][j].getText().toString());
                    }
                }
            }
        }

        //Ожидание отрисовки и получения размеров
        buttonCell[0][0].post(new Runnable() {
            @Override
            public void run() {
                if(booleanMoreTenCells){
                    int  tenCellTextSize = getSP(buttonCell[stringCellTenTextSize][columnCellTenTextSize].getTextSize());
                    for (int i = 0; i < str; i++) {
                        for (int j = 0; j < columns; j++) {
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(buttonCell[i][j],1,
                                    tenCellTextSize, 1, TypedValue.COMPLEX_UNIT_SP);
                        }
                    }
                }
            }
        });
}



    //Обработчик для ячеек таблицы
    View.OnClickListener cellClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            ((Button)v).setTextSize(TypedValue.COMPLEX_UNIT_SP, 100);

            if (bSavedTouchСells){

                if(nextMove == Integer.parseInt ("" + ((Button)v).getText())){
                    nextMove++;

                    if (nextMove == (countSave+1)){
                        chronometer.stop();
                        Log.d("EndGame","Победа " + chronometer.getText());
                    }
                }

            }else {
                chronometer.stop();
                Log.d("EndGame","Конец игры без нажатия    " + chronometer.getText());
            }
        }
    };


    //Сохранение информации при поворатах Активити
    // сохранение состояния
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putIntegerArrayList(saveArrayOfNumbers, listNumber);
        outState.putInt(saveCountSave, countSave);

        outState.putInt(saveStringCellTextSize, stringCellTenTextSize);
        outState.putInt(saveColumnCellTextSize, columnCellTenTextSize);
        outState.putBoolean(saveBooleanMoreTenCells, booleanMoreTenCells);



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


        for (int i = 0; i < str; i++) {
            for (int j = 0; j < columns; j++) {
                buttonCell[i][j].setText("" + listNumber.get(str*columns - count));
                count--;
            }
        }

        //Ожидание отрисовки и получения размеров
        buttonCell[0][0].post(new Runnable() {
            @Override
            public void run() {
                if(booleanMoreTenCells){
                    int  tenCellTextSize = getSP(buttonCell[stringCellTenTextSize][columnCellTenTextSize].getTextSize());
                    for (int i = 0; i < str; i++) {
                        for (int j = 0; j < columns; j++) {
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(buttonCell[i][j],1,
                                    tenCellTextSize, 1, TypedValue.COMPLEX_UNIT_SP);
                        }
                    }
                }
            }
        });



//        name = savedInstanceState.getString(nameVariableKey);
//        String textViewText= savedInstanceState.getString(textViewTexKey);
//        TextView nameView = (TextView) findViewById(R.id.nameView);
//        nameView.setText(textViewText);
    }


    public int getSP(float px){
        float sp = px / getResources().getDisplayMetrics().scaledDensity;
        return (int) sp;
    }

}