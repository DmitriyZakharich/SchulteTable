package ru.schultetabledima.schultetable.table;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class EndGameDialogue {
    private Context context;
    private TablePresenter tablePresenter;
    AlertDialog alert;

    public EndGameDialogue(Context context, TablePresenter tablePresenter) {
        this.context = context;
        this.tablePresenter = tablePresenter;
        init();
    }


     private void init(){
         new EndGameDialoguePresenter(context, this, tablePresenter);
    }

    public void showDialogue(AlertDialog.Builder builder){
        alert = builder.create();
        alert.show();
    }

    public void dismiss() {
        alert.dismiss();
    }
}
