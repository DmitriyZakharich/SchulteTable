package ru.schultetabledima.schultetable.tablecreation;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.TextViewCompat;

public class CustomCell extends AppCompatTextView {
    boolean isLetters;
    public CustomCell(@NonNull Context context, boolean isLetters) {
        super(context);
        this.isLetters = isLetters;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int minCellsSide = Math.min(this.getMeasuredWidth(), this.getMeasuredHeight());
        float correctionFactor = (isLetters) ? 0.65F : 0.50F;
        int autoTextSizeMax = (int)(minCellsSide * correctionFactor);
        TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(this, 1,
                autoTextSizeMax, 1, TypedValue.COMPLEX_UNIT_PX);
    }
}
