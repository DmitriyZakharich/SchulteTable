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

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.table.presenter.EndGameDialoguePresenter;
import ru.schultetabledima.schultetable.table.presenter.TablePresenter;


public class EndGameDialogueFragment extends BottomSheetDialogFragment {

    private TablePresenter tablePresenter;
    private Dialog dialog;

    private PassMeLinkOnObject view;

    public interface PassMeLinkOnObject {
        TablePresenter getTablePresenter();
    }

    public EndGameDialogueFragment() {}

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