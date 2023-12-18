package ru.schultetabledima.schultetable.screens.game.view.tablecreation;

import android.animation.LayoutTransition;
import android.widget.LinearLayout;

public class AnimationTransition {

    private LinearLayout containerForTable;

    public AnimationTransition(LinearLayout containerForTable) {
        this.containerForTable = containerForTable;
        addAnimation();
    }

    public AnimationTransition() {
        createAnimation();
    }

    private void addAnimation() {
        LayoutTransition layoutTransitionTable = new LayoutTransition();
        layoutTransitionTable.enableTransitionType(LayoutTransition.CHANGING);
        containerForTable.setLayoutTransition(layoutTransitionTable);
    }

    public LayoutTransition createAnimation() {
        LayoutTransition layoutTransition = new LayoutTransition();
        layoutTransition.enableTransitionType(LayoutTransition.CHANGING);
        return layoutTransition;
    }
}
