package ru.schultetabledima.schultetable.table;

import android.app.Dialog;
import android.os.SystemClock;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import ru.schultetabledima.schultetable.App;
import ru.schultetabledima.schultetable.R;
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
        settings = new PreferencesReader(App.getContext());
        time = TimeResultFromBaseChronometer.getTime(baseChronometer);
        createDialogue();
    }


    private void createDialogue() {

        builder = new AlertDialog.Builder(dialogFragment.getActivity());
        builder.setTitle(R.string.end_game)
                .setMessage(dialogFragment.getActivity().getString(R.string.yourTime) + time)
                .setPositiveButton(R.string.newGame, (dialog, id) -> endGameDialoguePresenter.onClickPositiveButtonListener())
                .setNeutralButton(R.string.statistics, (dialog, which) -> endGameDialoguePresenter.onClickNeutralButtonListener())
                .setPositiveButtonIcon(dialogFragment.getActivity().getDrawable(R.drawable.ic_playbutton))
                .setCancelable(false);


        if (!settings.getIsTouchCells()) {
            builder.setCancelable(true);
            builder.setOnCancelListener(dialog -> endGameDialoguePresenter.onCancelDialogueListener());
            builder.setNegativeButtonIcon(dialogFragment.getActivity().getDrawable(R.drawable.ic_resume));
            builder.setNegativeButton(R.string.continueCurrentGame, (dialog, id) -> endGameDialoguePresenter.onNegativeButtonListener());
        }
    }

    public Dialog getDialog() {
        return builder.create();
    }
}
