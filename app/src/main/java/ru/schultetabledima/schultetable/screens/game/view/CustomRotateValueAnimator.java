package ru.schultetabledima.schultetable.screens.game.view;

import android.animation.ValueAnimator;
import android.graphics.Color;

import ru.schultetabledima.schultetable.screens.game.view.tablecreation.CustomCell;

public class CustomRotateValueAnimator {

    private int id;
    private TableFragment tableFragment;


    public CustomRotateValueAnimator(TableFragment tableFragment, int id) {
        this.tableFragment = tableFragment;
        this.id = id;
        init();
    }

    private void init() {
        final float startRotate = -60;
        final float endRotate = 60;
        long animationDuration = 1800;

        ValueAnimator animator = ValueAnimator.ofFloat(startRotate, endRotate);
        animator.setDuration(animationDuration);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);

        animator.addUpdateListener(valueAnimator -> {
            float animatedValue = (float) valueAnimator.getAnimatedValue();
            int ROTATE_ANIMATION = 1;

            if (tableFragment.getView() != null && ((CustomCell) tableFragment.requireView().findViewById(id)) != null &&
                    ((CustomCell) tableFragment.requireView().findViewById(id)) != null) {

                ((CustomCell) tableFragment.requireView().findViewById(id)).setTextColor(Color.TRANSPARENT);
                ((CustomCell) tableFragment.requireView().findViewById(id)).setAnimation(animatedValue, ROTATE_ANIMATION);
            }

        });
        animator.start();
    }
}
