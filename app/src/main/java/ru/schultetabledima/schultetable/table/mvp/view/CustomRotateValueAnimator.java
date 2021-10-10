package ru.schultetabledima.schultetable.table.mvp.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;

import ru.schultetabledima.schultetable.table.mvp.view.tablecreation.CustomCell;

public class CustomRotateValueAnimator {

    private Context context;
    private int id;

    public CustomRotateValueAnimator(Context context, int id) {
        this.context = context;
        this.id = id;
        init();
    }

    private void init() {
        final float startRotate = -60;
        final float endRotate = 60;
        long animationDuration = 1500;

        ValueAnimator animator = ValueAnimator.ofFloat(startRotate, endRotate);
        animator.setDuration(animationDuration);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);

        animator.addUpdateListener(valueAnimator -> {
            float animatedValue = (float) valueAnimator.getAnimatedValue();
            int ROTATE_ANIMATION = 1;
            ((CustomCell) ((TableActivity) context).findViewById(id)).setAnimation(animatedValue, ROTATE_ANIMATION);

            ((CustomCell) ((TableActivity) context).findViewById(id)).setTextColor(Color.TRANSPARENT);
        });
        animator.start();
    }
}
