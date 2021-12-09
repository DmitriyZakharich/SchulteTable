package ru.schultetabledima.schultetable.table.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import ru.schultetabledima.schultetable.table.presenter.EndGameDialoguePresenter;
import ru.schultetabledima.schultetable.table.presenter.TablePresenter;


public class EndGameDialogueFragment extends DialogFragment {

    private TablePresenter tablePresenter;
    private Dialog dialog;
    private PassMeLinkOnObject view;

    public static EndGameDialogueFragment newInstance() {
        return new EndGameDialogueFragment();
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        tablePresenter.onNegativeOrCancelDialogue();
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        view = (PassMeLinkOnObject) getParentFragment();
        tablePresenter = view.getTablePresenter();
        new EndGameDialoguePresenter(this, tablePresenter);
        return dialog;
    }

}
