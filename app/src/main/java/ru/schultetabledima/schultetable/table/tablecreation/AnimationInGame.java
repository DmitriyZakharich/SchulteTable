package ru.schultetabledima.schultetable.table.tablecreation;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import ru.schultetabledima.schultetable.utils.PreferencesReader;

public class AnimationInGame {

    private PreferencesReader settings;
    private List<Integer> cellsId;
    Set<Integer> hsRandomForCellAnim = new HashSet<>();


    public AnimationInGame(List<Integer> cellsId) {
        this.cellsId = cellsId;
        main();
    }

    private void main() {
        settings = new PreferencesReader();
        addAnimation();
    }


    private void addAnimation() {
        Random random = new Random();

        int amountCellAnim = cellsId.size()/2;

        for (int i = 0; i < amountCellAnim; i++) {
            int randAnimInt = random.nextInt(cellsId.size());

            if (!hsRandomForCellAnim.add(cellsId.get(randAnimInt))){
                i--;
            }
        }
    }

    public Set<Integer> getRandomIdsForAnim() {
        return hsRandomForCellAnim;
    }
}
