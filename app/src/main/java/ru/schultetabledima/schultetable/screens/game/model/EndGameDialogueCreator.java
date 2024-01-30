package ru.schultetabledima.schultetable.screens.game.model;

import android.app.Dialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.DialogFragment;

import com.yandex.mobile.ads.banner.AdSize;
import com.yandex.mobile.ads.banner.BannerAdEventListener;
import com.yandex.mobile.ads.banner.BannerAdView;
import com.yandex.mobile.ads.common.AdRequest;
import com.yandex.mobile.ads.common.AdRequestError;
import com.yandex.mobile.ads.common.ImpressionData;

import ru.schultetabledima.schultetable.R;
import ru.schultetabledima.schultetable.screens.game.presenter.EndGameDialoguePresenter;
import ru.schultetabledima.schultetable.utils.PreferencesReader;

public class EndGameDialogueCreator {

    private AlertDialog.Builder builder;
    private EndGameDialoguePresenter endGameDialoguePresenter;
    private long baseChronometer;
    private DialogFragment dialogFragment;
    private String time;

    public EndGameDialogueCreator(DialogFragment dialogFragment, EndGameDialoguePresenter endGameDialoguePresenter, long baseChronometer) {
        this.dialogFragment = dialogFragment;
        this.endGameDialoguePresenter = endGameDialoguePresenter;
        this.baseChronometer = baseChronometer;
        main();
    }

    private void main() {
        time = TimeResultFromBaseChronometer.getTime(baseChronometer);
        createDialogue();
    }

    private void createDialogue() {
        builder = new AlertDialog.Builder(dialogFragment.getActivity(), R.style.AlertDialogCustom);

        LayoutInflater inflater = dialogFragment.getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.alertdialog_custom_layout, null);
        builder.setView(view);

        AppCompatTextView textViewMessage = view.findViewById(R.id.dialog_message);
        textViewMessage.setText(textViewMessage.getText().toString().concat(time));

        view.findViewById(R.id.dialog_positive_button).setOnClickListener(item -> endGameDialoguePresenter.onClickPositiveButtonListener());
        view.findViewById(R.id.dialog_neutral_button).setOnClickListener(item -> endGameDialoguePresenter.onClickNeutralButtonListener());

        builder.setOnKeyListener((dialog, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK &&
                    event.getAction() == KeyEvent.ACTION_UP &&
                    !event.isCanceled()) {
                dialogFragment.getActivity().onBackPressed();
                return true;
            }
            return false;
        });

        dialogFragment.setCancelable(false);

        if (!PreferencesReader.INSTANCE.isTouchCells()) {
            builder.setCancelable(true);
            dialogFragment.setCancelable(true);

            builder.setOnCancelListener(dialog -> endGameDialoguePresenter.onCancelDialogueListener());

            Button button = view.findViewById(R.id.dialog_negative_button);
            button.setOnClickListener(item -> endGameDialoguePresenter.onNegativeButtonListener());
            button.setVisibility(View.VISIBLE); //По умолчанию gone в XML
        }

        BannerAdView mBannerAdView = view.findViewById(R.id.banner_view2);
                mBannerAdView.setAdUnitId("R-M-DEMO-300x250"); //тестовый id R-M-DEMO-300x250

        AdRequest adRequest = new AdRequest.Builder().build();
        mBannerAdView.setBannerAdEventListener(new BannerAdEventListener() {
            @Override
            public void onAdLoaded() {}
            @Override
            public void onAdFailedToLoad(@NonNull AdRequestError adRequestError) {}
            @Override
            public void onAdClicked() {}
            @Override
            public void onLeftApplication() {}
            @Override
            public void onReturnedToApplication() {}
            @Override
            public void onImpression(@Nullable ImpressionData impressionData) {}

        });
        mBannerAdView.loadAd(adRequest);
        mBannerAdView.setAdSize(AdSize.stickySize(AdSize.FULL_SCREEN.getWidth()));
    }

    public Dialog getDialog() {
        return builder.create();
    }
}