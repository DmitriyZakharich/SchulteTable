package ru.schultetabledima.schultetable.utils;

import android.content.Context;

import java.io.Serializable;

public class Converter {
    public static int getSP(Context context, float px){
        float sp = px / context.getResources().getDisplayMetrics().scaledDensity;
        return (int) sp;
    }

    public static int getPxFromDP(Context context, int dp){
        float scale = context.getResources().getDisplayMetrics().density;
        return((int) (dp * scale + 0.5f));
    }
}
