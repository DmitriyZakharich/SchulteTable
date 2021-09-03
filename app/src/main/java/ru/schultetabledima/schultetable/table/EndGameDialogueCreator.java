package ru.schultetabledima.schultetable.table;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import androidx.appcompat.app.AlertDialog;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.settings.SettingsActivity;

public class EndGameDialogueCreator {

    private AlertDialog.Builder builder;
    private Context context;
    EndGameDialoguePresenter endGameDialoguePresenter;
    private Boolean isPressButtons,isLetters;

    public EndGameDialogueCreator(Context context, EndGameDialoguePresenter endGameDialoguePresenter) {
        this.context = context;
        this.endGameDialoguePresenter = endGameDialoguePresenter;
        readSharedPreferences();
        main();
    }

    private void readSharedPreferences() {
        SharedPreferences settings = context.getSharedPreferences(SettingsActivity.getAppPreferences(), MODE_PRIVATE);
        isLetters = settings.getBoolean(SettingsActivity.getKeyNumbersOrLetters(), false);
        isPressButtons = settings.getBoolean(SettingsActivity.getKeyTouchCells(), true);
    }

    private void main(){
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


        if(!isPressButtons){
            builder.setCancelable(true);
            builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    endGameDialoguePresenter.onCancelListener();
                }
            });
            builder.setNegativeButtonIcon(context.getDrawable(R.drawable.ic_resume));
            builder.setNegativeButton(R.string.continueCurrentGame, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    endGameDialoguePresenter.onNegativeButtonListener();
                }
            });
        }
    }

    public AlertDialog.Builder getAlertDialog() {
        return builder;
    }
}
