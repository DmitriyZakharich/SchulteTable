package ru.schultetabledima.schultetable;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import static androidx.constraintlayout.widget.ConstraintLayout.*;

public class ActivityStatistics extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        //убрать навигационную панель
//        getWindow().
//                getDecorView().
//                setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);

        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        Button button1 = findViewById(R.id.button);


        button1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                button1.setText("123");
                button1.setTextSize(10);
            }
        });


        TextView textView = (TextView)findViewById(R.id.textView2);
        Log.d("setTextSize","setTextSize = " + textView.getTextSize());
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        Log.d("setTextSize","setTextSize = " + textView.getTextSize());


        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.mycell, linearLayout ,false);
        linearLayout.addView(view);


        ((Button)view).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Button)v).setTextSize(1);
                ((Button)v).setText("Агась");
                LinearLayout linearLayout2 = (LinearLayout) ((Button)v).getParent();
                linearLayout2.updateViewLayout((Button)v, v.getLayoutParams());
                Log.d("buttonOnClick", "buttonOnClick");

            }
        });




    }

}





