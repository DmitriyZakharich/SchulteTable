package ru.schultetabledima.schultetable.table;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import ru.schultetabledima.schultetable.MyApplication;
import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

public class EndGameDialogueCreator {

    private AlertDialog.Builder builder;
    private EndGameDialoguePresenter endGameDialoguePresenter;
    private long baseChronometer;
    private PreferencesReader settings;
    private DialogFragment dialogFragment;


    public EndGameDialogueCreator(DialogFragment dialogFragment, EndGameDialoguePresenter endGameDialoguePresenter, long baseChronometer) {
        this.dialogFragment = dialogFragment;
        this.endGameDialoguePresenter = endGameDialoguePresenter;
        this.baseChronometer = baseChronometer;
        main();
    }

    private void main(){
        settings = new PreferencesReader(MyApplication.getContext());
        getTime();
        createDialogue();
    }

    private void getTime() {

        long totalSecs = baseChronometer / 1000;
        // Show Info
        long hours = totalSecs / 3600;
        long minutes = (totalSecs % 3600) / 60;
        long seconds = totalSecs % 60;

        Log.d("TagTagTag", "getTime: " + hours + "   " + "   " + minutes + "  " +  seconds);
    }

    private void createDialogue(){

        builder = new AlertDialog.Builder(dialogFragment.getActivity());
        builder.setTitle(R.string.end_game)
//                .setMessage(MyApplication.getContext().getString(R.string.yourTime) +  ((TableActivity)context).getTextChronometer())
                .setPositiveButton(R.string.newGame, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        endGameDialoguePresenter.onClickPositiveButtonListener();
                    }
                })
                .setNeutralButton(R.string.statistics, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        endGameDialoguePresenter.onClickNeutralButtonListener();
                    }
                }).setPositiveButtonIcon(dialogFragment.getActivity().getDrawable(R.drawable.ic_playbutton))
                .setCancelable(false);


        if(!settings.getIsTouchCells()){
            builder.setCancelable(true);
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    endGameDialoguePresenter.onCancelDialogueListener();
                }
            });
            builder.setNegativeButtonIcon(dialogFragment.getActivity().getDrawable(R.drawable.ic_resume));
            builder.setNegativeButton(R.string.continueCurrentGame, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    endGameDialoguePresenter.onNegativeButtonListener();
                }
            });
        }
    }

    public Dialog getDialog() {
        return builder.create();
    }
}
