package ru.schultetabledima.schultetable.table;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import moxy.presenter.InjectPresenter;


public class EndGameDialogueFragment extends DialogFragment {

//    @InjectPresenter
    private TablePresenter tablePresenter;

    private Dialog dialog;

    PassMeLinkOnObject activity;

    public interface PassMeLinkOnObject {
        TablePresenter getTablePresenter();
    }

    public EndGameDialogueFragment() {
    }

    public static EndGameDialogueFragment newInstance() {
        return new EndGameDialogueFragment();
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        tablePresenter.onNegativeOrCancelDialogue();
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        activity = (PassMeLinkOnObject) getActivity();
        tablePresenter = activity.getTablePresenter();
        new EndGameDialoguePresenter(this, tablePresenter);

        return dialog;
    }
}
