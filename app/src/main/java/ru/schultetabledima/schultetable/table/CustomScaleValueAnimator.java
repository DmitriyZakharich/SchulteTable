package ru.schultetabledima.schultetable.table;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;

import ru.schultetabledima.schultetable.table.tablecreation.CustomCell;

public class CustomScaleValueAnimator implements ObservationContract.CellTextSizeObserver {
    private Context context;
    private int id;

    public CustomScaleValueAnimator(Context context, int id) {
        this.context = context;
        this.id = id;
        subscribeSubject();
    }

    private void main() {
        final float startSize = 20;
        final float endSize = ((CustomCell) ((TableActivity)context).findViewById(id)).getTextSize();
        long animationDuration = 1900;

        ValueAnimator animator = ValueAnimator.ofFloat(startSize, endSize);
        animator.setDuration(animationDuration);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);

        animator.addUpdateListener(valueAnimator -> {
            float animatedValue = (float) valueAnimator.getAnimatedValue();
            int SCALE_ANIMATION = 2;
            ((CustomCell) ((TableActivity)context).findViewById(id)).setAnimation(animatedValue, SCALE_ANIMATION);
            ((CustomCell) ((TableActivity)context).findViewById(id)).setTextColor(Color.TRANSPARENT);

        });
        animator.start();
    }


    @Override
    public void updateSubject() {
        main();
    }

    @Override
    public void subscribeSubject() {
        ((CustomCell) ((TableActivity)context).findViewById(id)).subscribeObserver(this);
    }

    @Override
    public void unSubscribeSubject() {
        ((CustomCell) ((TableActivity)context).findViewById(id)).unSubscribeObserver(this);
    }
}

