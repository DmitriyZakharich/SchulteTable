package ru.schultetabledima.schultetable.screens.game.view.tablecreation;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.TextPaint;
import android.util.TypedValue;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.widget.TextViewCompat;

import java.util.ArrayList;
import java.util.List;

import ru.schultetabledima.schultetable.screens.game.ObservationContract;

@SuppressLint("ViewConstructor")
public class CustomCell extends AppCompatTextView implements ObservationContract.CellTextSizeSubject {
    boolean isLetters;
    private TextPaint mPaint = getPaint();
    private int count = 0;
    private Rect mTextBoundRect = new Rect();
    private float textWidth, textHeight;
    private float animatedValue;
    private int typeAnimation;
    private float width, height, centerX, centerY;
    private String message;
    private boolean isAnimation = false;

    protected List<ObservationContract.CellTextSizeObserver> cellTextSizeObserver = new ArrayList<>();

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

        updateNotifyObservers();
    }


    public void setAnimation(float animatedValue, int typeAnimation) {
        this.animatedValue = animatedValue;
        this.typeAnimation = typeAnimation;
        isAnimation = true;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isAnimation)
            return;

        if (typeAnimation == 1) {
            canvas.rotate(animatedValue, centerX, centerY);

        } else if (typeAnimation == 2){
            mPaint.setTextSize(animatedValue);
        }

        width = getWidth();
        height = getHeight();
        centerX = width / 2;
        centerY = height / 2;

        mPaint.setAntiAlias(true);
        mPaint.setTextSize(getTextSize());

        message = getText().toString();
        mPaint .getTextBounds(message, 0, message.length(), mTextBoundRect);
        textWidth = mPaint.measureText(message);
        textHeight = mTextBoundRect.height();

        mPaint.setColor(Color.BLACK);

        canvas.drawText(message, centerX - (textWidth / 2f), centerY + (textHeight /2f), mPaint);
    }

    @Override
    public void subscribeObserver(ObservationContract.CellTextSizeObserver observer) {
        cellTextSizeObserver.add(observer);
    }

    @Override
    public void unSubscribeObserver(ObservationContract.CellTextSizeObserver observer) {
        cellTextSizeObserver.remove(observer);
    }

    @Override
    public void updateNotifyObservers() {
        for (int i = 0; i < cellTextSizeObserver.size(); i++) {
            cellTextSizeObserver.get(i).updateSubject();
        }
    }
}