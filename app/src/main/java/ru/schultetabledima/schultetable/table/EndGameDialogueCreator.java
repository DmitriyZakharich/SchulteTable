package ru.schultetabledima.schultetable.table;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

public class EndGameDialogueCreator {

    private AlertDialog.Builder builder;
    private Context context;
    private EndGameDialoguePresenter endGameDialoguePresenter;
    private PreferencesReader settings;
    private AlertDialog alertDialog;


    public EndGameDialogueCreator(Context context, EndGameDialoguePresenter endGameDialoguePresenter) {
        this.context = context;
        this.endGameDialoguePresenter = endGameDialoguePresenter;
        main();
    }

    private void main(){
        settings = new PreferencesReader(context);
        createDialogue();
    }

    private void createDialogue(){
        builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.end_game)
                .setMessage(context.getString(R.string.yourTime) +  ((TableActivity)context).getTextChronometer())
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
                }).setPositiveButtonIcon(context.getDrawable(R.drawable.ic_playbutton))
                .setCancelable(false);


        if(!settings.getIsTouchCells()){
            builder.setCancelable(true);
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    endGameDialoguePresenter.onCancelDialogueListener();
                }
            });
            builder.setNegativeButtonIcon(context.getDrawable(R.drawable.ic_resume));
            builder.setNegativeButton(R.string.continueCurrentGame, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    endGameDialoguePresenter.onNegativeButtonListener();
                }
            });
        }

        alertDialog = builder.create();
    }

    public AlertDialog getAlertDialog() {
        return alertDialog;
    }
}
