package ru.schultetabledima.schultetable.table;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class EndGameDialogue {
    private Context context;
    private TablePresenter tablePresenter;
    private AlertDialog alertDialog;

    public EndGameDialogue(Context context, TablePresenter tablePresenter) {
        this.context = context;
        this.tablePresenter = tablePresenter;
        init();
    }


     private void init(){
         new EndGameDialoguePresenter(context, this, tablePresenter);
    }

    public void showDialogue(AlertDialog alertDialog){
        this.alertDialog = alertDialog;
        alertDialog.show();
    }

    public void dismiss() {
        alertDialog.dismiss();
    }
}
