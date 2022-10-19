package ru.schultetabledima.schultetable.table.view;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.fragment.app.DialogFragment;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.table.presenter.EndGameDialoguePresenter;
import ru.schultetabledima.schultetable.table.presenter.TablePresenter;


public class EndGameDialogueFragment extends DialogFragment {

    private TablePresenter tablePresenter;
    private Dialog dialog;

    private PassMeLinkOnObject view;

    public interface PassMeLinkOnObject {
        TablePresenter getTablePresenter();
    }

    public EndGameDialogueFragment() {
    }

    public static EndGameDialogueFragment newInstance() {
        return new EndGameDialogueFragment();
    }


    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        tablePresenter.onNegativeOrCancelDialogue();
        Log.d("rrrrrrrrrrrr", "EndGameDialogueFragment onCancel");
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }


    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d("rrrrrrrrrrrr", "EndGameDialogueFragment onCreateDialog");

        view = (PassMeLinkOnObject) getParentFragment();
        tablePresenter = view.getTablePresenter();
        new EndGameDialoguePresenter(this, tablePresenter);

        return dialog;
    }

    @Override
    public void onResume() {
        super.onResume();
        LinearLayoutCompat layout = dialog.findViewById(R.id.linearLayoutCompatId);

//        for (int i = 0; i < layout.getChildCount(); i++) {
//        ViewGroup container = (ViewGroup)layout.getChildAt(0);
//        ViewGroup cl = (ViewGroup)container.getChildAt(0);
//        View ads = cl.getChildAt(0);

//        Log.d("rrrrrrrrrrrr", "class: " + container.getClass());
//        Log.d("rrrrrrrrrrrr", "class: " + cl.getClass());
//        Log.d("rrrrrrrrrrrr", "class: " + ads.getClass());

//        }
    }
}
