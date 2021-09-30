package ru.schultetabledima.schultetable.table;

import android.app.Dialog;
import android.util.Log;

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
        settings = new PreferencesReader();
        time = TimeResultFromBaseChronometer.getTime(baseChronometer);
        createDialogue();
    }


    private void createDialogue() {
        Log.d("TAGTAGTAG1244", "dialogFragment " + dialogFragment);
        Log.d("TAGTAGTAG1244", "dialogFragment.getActivity() " + dialogFragment.getActivity());

        builder = new AlertDialog.Builder(dialogFragment.getActivity());
        builder.setTitle(R.string.end_game)
                .setMessage(dialogFragment.getActivity().getString(R.string.yourTime) + time)
                .setPositiveButton(R.string.newGame, (dialog, id) -> endGameDialoguePresenter.onClickPositiveButtonListener())
                .setPositiveButtonIcon(dialogFragment.getActivity().getDrawable(R.drawable.ic_playbutton))
                .setNeutralButton(R.string.statistics, (dialog, which) -> endGameDialoguePresenter.onClickNeutralButtonListener())
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
