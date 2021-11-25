package ru.schultetabledima.schultetable.table.view;

import android.animation.ValueAnimator;
import android.graphics.Color;

import ru.schultetabledima.schultetable.table.ObservationContract;
import ru.schultetabledima.schultetable.table.view.tablecreation.CustomCell;

public class CustomScaleValueAnimator implements ObservationContract.CellTextSizeObserver {
    private final int id;
    private TableFragment tableFragment;


    public CustomScaleValueAnimator(TableFragment tableFragment, int id) {
        this.tableFragment = tableFragment;
        this.id = id;
        subscribeSubject();
    }

    private void main() {
        final float startSize = 20;
        final float endSize = ((CustomCell) tableFragment.getView().findViewById(id)).getTextSize();
        long animationDuration = 1900;

        ValueAnimator animator = ValueAnimator.ofFloat(startSize, endSize);
        animator.setDuration(animationDuration);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);

        animator.addUpdateListener(valueAnimator -> {
            float animatedValue = (float) valueAnimator.getAnimatedValue();
            int SCALE_ANIMATION = 2;

            if (tableFragment.getView() != null && ((CustomCell) tableFragment.requireView().findViewById(id)) != null &&
                    ((CustomCell) tableFragment.requireView().findViewById(id)) != null) {

                ((CustomCell) tableFragment.requireView().findViewById(id)).setAnimation(animatedValue, SCALE_ANIMATION);
                ((CustomCell) tableFragment.requireView().findViewById(id)).setTextColor(Color.TRANSPARENT);
            }

        });
        animator.start();
    }


    @Override
    public void updateSubject() {
        main();
    }

    @Override
    public void subscribeSubject() {
        if (tableFragment.getView() != null)
            ((CustomCell) tableFragment.getView().findViewById(id)).subscribeObserver(this);
    }

    @Override
    public void unSubscribeSubject() {
        if (tableFragment.getView() != null)
            ((CustomCell) tableFragment.getView().findViewById(id)).unSubscribeObserver(this);
    }
}

