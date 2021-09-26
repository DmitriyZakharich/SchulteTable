package ru.schultetabledima.schultetable.table.tablecreation;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.appcompat.widget.AppCompatTextView;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import ru.schultetabledima.schultetable.App;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

public class AnimationInGame {

    private AppCompatTextView[][] cells;
    private PreferencesReader settings;


    public AnimationInGame(AppCompatTextView[][] cells) {
        this.cells = cells;
        main();

    }

    private void main() {
        settings = new PreferencesReader(App.getContext());
        addAnimation();
    }


    private void addAnimation() {
        Random random = new Random();

        int amountCellAnim = (settings.getColumnsOfTable() * settings.getRowsOfTable())/2;
        Set<Integer> hsRandomForCellAnim = new HashSet<>();

        int min = 100;
        int max = 200;
        int diff = max - min;
        int firstLetter;

        if (settings.getIsLetters()) {
            if (settings.getIsEnglish()) {
                firstLetter = 'A'; //eng

            }else
                firstLetter = 'А'; //rus

        } else {
            firstLetter = 1;
        }

        for (int i = 0; i < amountCellAnim; i++) {
            int randAnimInt = random.nextInt(settings.getColumnsOfTable() * settings.getRowsOfTable() + firstLetter); //????
            if (!hsRandomForCellAnim.add(randAnimInt)){
                i--;
            }
        }

        for (int i = 0; i < settings.getRowsOfTable(); i++) {
            for (int j = 0; j < settings.getColumnsOfTable(); j++) {


                if (hsRandomForCellAnim.contains( Integer.parseInt(cells[i][j].getText().toString())) ) {
                    Animation anim = AnimationUtils.loadAnimation(App.getContext(), R.anim.myrotate);



                    cells[i][j].startAnimation(anim);
                }
            }
        }
    }
}
