package ru.schultetabledima.schultetable.table;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EndGameDialogueFragment extends DialogFragment {
    private TablePresenter tablePresenter;
    private long baseChronometer;
    private Dialog dialog;

    public EndGameDialogueFragment(TablePresenter tablePresenter, long baseChronometer) {
        this.tablePresenter = tablePresenter;
        this.baseChronometer = baseChronometer;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new EndGameDialoguePresenter(this, tablePresenter, baseChronometer);
    }


    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return dialog;
    }
}
