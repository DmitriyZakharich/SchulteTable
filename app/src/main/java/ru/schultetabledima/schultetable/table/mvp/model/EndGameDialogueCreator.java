package ru.schultetabledima.schultetable.table.mvp.model;

import android.app.Dialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.KeyEvent;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.table.mvp.presenter.EndGameDialoguePresenter;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

public class EndGameDialogueCreator {

    private AlertDialog.Builder builder;
    private EndGameDialoguePresenter endGameDialoguePresenter;
    private long baseChronometer;
    private PreferencesReader settings;
    private DialogFragment dialogFragment;
    private String time;


    public EndGameDialogueCreator(DialogFragment dialogFragment, EndGameDialoguePresenter endGameDialoguePresenter, long baseChronometer) {
        this.dialogFragment = dialogFragment;
        this.endGameDialoguePresenter = endGameDialoguePresenter;
        this.baseChronometer = baseChronometer;
        main();
    }

    private void main() {
        settings = new PreferencesReader();
        time = TimeResultFromBaseChronometer.getTime(baseChronometer);
        createDialogue();
    }


    private void createDialogue() {

        builder = new AlertDialog.Builder(dialogFragment.getActivity(), R.style.AlertDialogCustom);

        builder.setOnKeyListener((dialog, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK &&
                    event.getAction() == KeyEvent.ACTION_UP &&
                    !event.isCanceled()) {
                dialogFragment.getActivity().onBackPressed();
                return true;
            }
            return false;
        });

        builder.setTitle(R.string.end_game)
                .setMessage(dialogFragment.getActivity().getString(R.string.yourTime) + time)
                .setPositiveButton(R.string.newGame, (dialog, id) -> endGameDialoguePresenter.onClickPositiveButtonListener())
                .setPositiveButtonIcon(dialogFragment.getActivity().getDrawable(R.drawable.ic_playbutton))
                .setNeutralButton(R.string.statistics, (dialog, which) -> endGameDialoguePresenter.onClickNeutralButtonListener())
                .setNeutralButtonIcon(dialogFragment.getActivity().getDrawable(R.drawable.ic_statistic_dialogue))
                .setCancelable(false);

        dialogFragment.setCancelable(false);


        if (!settings.getIsTouchCells()) {
            builder.setCancelable(true);

            dialogFragment.setCancelable(true);

            builder.setOnCancelListener(dialog -> endGameDialoguePresenter.onCancelDialogueListener());
            builder.setNegativeButton(R.string.continueCurrentGame, (dialog, id) -> endGameDialoguePresenter.onNegativeButtonListener());
            builder.setNegativeButtonIcon(dialogFragment.getActivity().getDrawable(R.drawable.ic_resume));
        }

    }

    public Dialog getDialog() {
        return builder.create();
    }
}
