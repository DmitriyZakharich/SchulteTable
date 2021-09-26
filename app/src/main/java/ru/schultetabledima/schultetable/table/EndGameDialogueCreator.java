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
    private StringBuilder stringBuilder;


    public EndGameDialogueCreator(DialogFragment dialogFragment, EndGameDialoguePresenter endGameDialoguePresenter, long baseChronometer) {
        this.dialogFragment = dialogFragment;
        this.endGameDialoguePresenter = endGameDialoguePresenter;
        this.baseChronometer = baseChronometer;
        main();
    }

    private void main() {
        settings = new PreferencesReader(App.getContext());
        getTime();
        createDialogue();
    }

    private void getTime() {
        baseChronometer = SystemClock.elapsedRealtime() - baseChronometer;
        long totalSecs = baseChronometer / 1000;

        long hours = totalSecs / 3600;
        long minutes = (totalSecs % 3600) / 60;
        long seconds = totalSecs % 60;

        stringBuilder = new StringBuilder();

        if (hours > 0) {
            if (hours < 10)
                stringBuilder.append("0");

            stringBuilder.append(hours).append(":");

        }
        if (minutes < 10)
            stringBuilder.append("0");

        stringBuilder.append(minutes).append(":");

        if (seconds < 10)
            stringBuilder.append("0");

        stringBuilder.append(seconds);
    }

    private void createDialogue() {

        builder = new AlertDialog.Builder(dialogFragment.getActivity());
        builder.setTitle(R.string.end_game)
                .setMessage(dialogFragment.getActivity().getString(R.string.yourTime) + stringBuilder)
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
